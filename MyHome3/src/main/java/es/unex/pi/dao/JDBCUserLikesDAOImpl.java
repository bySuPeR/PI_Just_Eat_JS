package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.UserLikes;

public class JDBCUserLikesDAOImpl implements UserLikesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCHostingCategoriesDAOImpl.class.getName());

	@Override
	public List <UserLikes> getAll() {

		if (conn == null) return null;
						
		ArrayList<UserLikes> UserLikesList = new ArrayList<UserLikes>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM UserLikes");
						
			while ( rs.next() ) {
				UserLikes userLikes = new UserLikes ();
				userLikes.setIdhost (rs.getInt ("idhost"));
				userLikes.setIdLike (rs.getInt ("idLike"));
						
				UserLikesList.add(userLikes);
				logger.info("fetching all UserLikes: " + userLikes.getIdhost () + " " + userLikes.getIdLike ());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return UserLikesList;
	}

	@Override
	public List <UserLikes> getAllByLike (long idLike) {
		
		if (conn == null) return null;
						
		ArrayList <UserLikes> UserLikesList = new ArrayList <UserLikes> ();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM UserLikes WHERE idLike=" + idLike);

			while ( rs.next() ) {
				UserLikes userLikes = new UserLikes ();
				userLikes.setIdhost (rs.getInt ("idhost"));
				userLikes.setIdLike (rs.getInt ("idLike"));

				UserLikesList.add (userLikes);
				logger.info("fetching all UserLikes by idLike: " + userLikes.getIdLike () + "->" + userLikes.getIdhost ());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return UserLikesList;
	}
	
	@Override
	public List <UserLikes> getAllByHosting (long idhost) {
		
		if (conn == null) return null;
						
		ArrayList <UserLikes> userLikesList = new ArrayList <UserLikes>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM UserLikes WHERE idhost=" + idhost);

			while ( rs.next() ) {
				UserLikes userLikes = new UserLikes ();
				userLikes.setIdhost (rs.getInt ("idhost"));
				userLikes.setIdLike (rs.getInt ("idLike"));
							
				userLikesList.add (userLikes);
				logger.info("fetching all UserLikes by idhost: " + userLikes.getIdhost () + "->" + userLikes.getIdLike ());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userLikesList;
	}
	
	
	@Override
	public UserLikes get (long idhost, long idLike) {
		if (conn == null) return null;
		
		UserLikes userLikes = null;		
		
		try {
			Statement stmt = conn.createStatement ();
			ResultSet rs = stmt.executeQuery("SELECT * FROM UserLikes WHERE idhost=" + idhost + " AND idLike=" + idLike);			 
			if (!rs.next()) return null;
			userLikes = new UserLikes ();
			userLikes.setIdhost (rs.getInt ("idhost"));
			userLikes.setIdLike (rs.getInt ("idLike"));

			logger.info("fetching UserLikes by idhost: " + userLikes.getIdhost () + "  and idLike: " + userLikes.getIdLike ());
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return userLikes;
	}
	
	
	@Override
	public boolean add (UserLikes userLikes) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO UserLikes (idhost,idLike) VALUES('"+
									userLikes.getIdhost ()+"','"+
									userLikes.getIdLike ()+"')");
						
				logger.info("creating UserLikes:(" + userLikes.getIdhost () + " " + userLikes.getIdLike ());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save (UserLikes userLikes) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM UserLikes WHERE idhost=" + userLikes.getIdhost ());			 
				if (!rs.next()) 
					return false;
				long idLike = rs.getInt("idLike");

				stmt.executeUpdate("UPDATE UserLikes SET idLike=" + userLikes.getIdLike ()
				+" WHERE idhost = " + userLikes.getIdhost() + " AND idLike = " + idLike);
				
				logger.info("updating UserLikes:(" + userLikes.getIdhost () + " " + userLikes.getIdLike ());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete (long idhost, long idLike) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM UserLikes WHERE idhost =" + idhost +" AND idLike=" + idLike);
				logger.info("deleting UserLikes: " + idhost + " , idL=" + idLike);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
}
