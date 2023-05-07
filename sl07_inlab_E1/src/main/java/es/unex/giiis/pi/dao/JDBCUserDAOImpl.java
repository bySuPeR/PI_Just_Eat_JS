package es.unex.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import es.unex.giiis.pi.model.User;


public class JDBCUserDAOImpl implements UserDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());
	
	@Override
	public User get(long id) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM User WHERE id ="+id);
			if (!rs.next()) return null; 
			user  = new User();	 
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			logger.info("fetching User: "+user.getId()+" "+user.getName()+" "+user.getUsername()+" @ "+user.getEmail());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public User get(String username) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM User WHERE username ='"+username+"'");			 
			if (!rs.next()) return null; 
			user  = new User();	 
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			logger.info("fetching User: "+user.getId()+" "+user.getName()+" "+user.getUsername()+" @ "+user.getEmail());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	
	@Override
	public long add(User user) {
		long id=-1;
		long lastid=-1;
		if (conn != null){

			
			Statement stmt;
			try {
				
				ResultSet rs;
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='User'");			 
				if (!rs.next()) return -1; 
				lastid=rs.getLong("seq");
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO User (name,username,email,password) VALUES('"
						+user.getName()+"','"
						+user.getUsername()+"','"
						+user.getEmail()+"','"
						+user.getPassword()+"','"
						+user.getRole()+"')");
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				ResultSet rs;
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='User'");
				if (!rs.next()) return -1; 
				id=rs.getLong("seq");
				if (id<=lastid) return -1;
											
				logger.info("creating User: "+user.getId()+" "+user.getName()+" "+user.getUsername()+" @ "+user.getEmail());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return id;
	}

	
	



	
	@Override
	public boolean save(User user) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE User SET name='"+user.getName()
				+"', username='"+user.getUsername()
				+"', email='"+user.getEmail()
				+"', password='"+user.getPassword()
				+"', role='"+user.getRole()
				+"' WHERE id = "+user.getId());
				logger.info("updating User: "+user.getId()+" "+user.getName()+" "+user.getUsername()+" @ "+user.getEmail());
				done= true;
				
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return done;

	}



	
	@Override
	public boolean delete(long id) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM User WHERE id ="+id);
				logger.info("deleting order: "+id);
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



