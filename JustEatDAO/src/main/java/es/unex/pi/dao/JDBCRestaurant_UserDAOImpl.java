package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import es.unex.pi.model.Restaurant_User;

public class JDBCRestaurant_UserDAOImpl implements Restaurant_UserDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCRestaurant_UserDAOImpl.class.getName());

	@Override
	public ArrayList <Restaurant_User> getAll () {
		if (conn == null) {
			return null;
		}
		Restaurant_User restaurantUser = null;			
		ArrayList <Restaurant_User> restaurantUserList = null;
		String query = "SELECT * FROM Restaurant_User";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			ResultSet rs = stmt.executeQuery ();
			restaurantUserList = new ArrayList <Restaurant_User> ();
			while (rs.next ()) {
				restaurantUser = new Restaurant_User ();
				restaurantUser.setIdr (rs.getLong ("idr"));
				restaurantUser.setIdu (rs.getLong ("idu"));
				restaurantUser.setReview (rs.getString ("review"));
				restaurantUser.setGrade (rs.getDouble ("grade"));
						
				restaurantUserList.add (restaurantUser);
				logger.info ("Fetching all RestaurantUser: " + restaurantUser.getIdr () + ", " + restaurantUser.getIdu ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurantUserList;
	}

	@Override
	public ArrayList <Restaurant_User> getAllByUser (long idu) {
		if (conn == null) {
			return null;
		}
		Restaurant_User restaurantUser = null;			
		ArrayList <Restaurant_User> restaurantUserList = null;
		String query = "SELECT * FROM Restaurant_User WHERE idu = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idu);
			ResultSet rs = stmt.executeQuery ();
			restaurantUserList = new ArrayList <Restaurant_User> ();
			while (rs.next ()) {
				restaurantUser = new Restaurant_User ();
				restaurantUser.setIdr (rs.getLong ("idr"));
				restaurantUser.setIdu (rs.getLong ("idu"));
				restaurantUser.setReview (rs.getString ("review"));
				restaurantUser.setGrade (rs.getDouble ("grade"));
						
				restaurantUserList.add (restaurantUser);
				logger.info ("Fetching all Restaurants by User: " + restaurantUser.getIdu () + " -> " + restaurantUser.getIdu ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurantUserList;
	}
	
	@Override
	public ArrayList <Restaurant_User> getAllByRestaurant (long idr) {
		if (conn == null) {
			return null;
		}
		Restaurant_User restaurantUser = null;	
		ArrayList <Restaurant_User> restaurantUserList = null;
		String query = "SELECT * FROM Restaurant_User WHERE idr = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idr);
			ResultSet rs = stmt.executeQuery ();
			restaurantUserList = new ArrayList <Restaurant_User> ();
			while (rs.next ()) {
				restaurantUser = new Restaurant_User ();
				restaurantUser.setIdr (rs.getLong ("idr"));
				restaurantUser.setIdu (rs.getLong ("idu"));
				restaurantUser.setReview (rs.getString ("review"));
				restaurantUser.setGrade (rs.getDouble ("grade"));
						
				restaurantUserList.add (restaurantUser);
				logger.info ("Fetching all User by Restaurants: " + restaurantUser.getIdr () + " -> " + restaurantUser.getIdu ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurantUserList;
	}
	
	@Override
	public Restaurant_User get (long idr, long idu) {
		if (conn == null) {
			return null;
		}
		Restaurant_User restaurantUser = null;		
		String query = "SELECT * FROM Restaurant_User WHERE idr = ? AND idu = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idr);
			stmt.setLong (2, idu);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				restaurantUser = new Restaurant_User ();
				restaurantUser.setIdr (rs.getLong ("idr"));
				restaurantUser.setIdu (rs.getLong ("idu"));
				restaurantUser.setReview (rs.getString ("review"));
				restaurantUser.setGrade (rs.getDouble ("grade"));
				
				logger.info ("Fetching Restaurant_User (" + restaurantUser.getIdr () + ", " + restaurantUser.getIdu () + ")");
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurantUser;
	}
	
	@Override
	public boolean add (Restaurant_User restaurantUser) {
		boolean done = false;
		int addedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "INSERT INTO Restaurant_User VALUES (?, ?, ?, ?)";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, restaurantUser.getIdr ());
				stmt.setLong (2, restaurantUser.getIdu ());
				stmt.setString (3, restaurantUser.getReview ());
				stmt.setDouble (4, restaurantUser.getGrade ());
				addedRows = stmt.executeUpdate ();						
				logger.info ("Creating Restaurant_User: ( " + restaurantUser.getIdr () + ", " + restaurantUser.getIdu() + ")");
				if (addedRows == 1) {
					done = true;
				}
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
		}
		return done;
	}

	@Override
	public boolean update (Restaurant_User restaurantUser) {
		boolean updatedRow = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "UPDATE Restaurant_User SET review = ?, grade = ? WHERE idr = ? AND idu = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setString (1, restaurantUser.getReview ());
				stmt.setDouble (2, restaurantUser.getGrade ());
				stmt.setLong (3, restaurantUser.getIdr ());
				stmt.setLong (4, restaurantUser.getIdu ());
				updatedRows = stmt.executeUpdate ();
				logger.info ("Updating Restaurant -> " + restaurantUser.getIdr () +": '" + restaurantUser.getReview () + "'");				
				if (updatedRows == 1) {
					updatedRow = true;
				}
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
		}
		return updatedRow;
	}

	@Override
	public boolean delete (long idr, long idu) {
		boolean deletedRow = false;
		int deletedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "DELETE FROM Restaurant_User WHERE idr = ? AND idu = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, idr);
				stmt.setLong (2, idu);
				deletedRows = stmt.executeUpdate ();
				if (deletedRows == 1) {
					deletedRow = true;
				}
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
		}
		return deletedRow;
	}

	@Override
	public void setConnection (Connection conn) {
		this.conn = conn;
	}
}
