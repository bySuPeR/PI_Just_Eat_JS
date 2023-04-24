package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import es.unex.pi.model.Restaurant_Category;

public class JDBCRestaurant_CategoryDAOImpl implements Restaurant_CategoryDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCRestaurant_CategoryDAOImpl.class.getName());

	@Override
	public ArrayList <Restaurant_Category> getAll () {
		if (conn == null) {
			return null;
		}
		Restaurant_Category restaurantCategory = null;				
		ArrayList <Restaurant_Category> restaurantCategoryList = null;
		String query = "SELECT * FROM Restaurant_Category";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			ResultSet rs = stmt.executeQuery ();						
			restaurantCategoryList = new ArrayList <Restaurant_Category> ();
			while (rs.next ()) {
				restaurantCategory = new Restaurant_Category ();
				restaurantCategory.setIdr (rs.getLong ("idr"));
				restaurantCategory.setIdct (rs.getLong ("idct"));
				restaurantCategoryList.add (restaurantCategory);
				logger.info ("Fetching all RestaurantCategories: " + restaurantCategory.getIdr () + ", " + restaurantCategory.getIdct ());					
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurantCategoryList;
	}

	@Override
	public ArrayList <Restaurant_Category> getAllByCategory (long idct) {
		if (conn == null) {
			return null;
		}
		Restaurant_Category restaurantCategory = null;
		ArrayList <Restaurant_Category> restaurantCategoryList = null;
		String query = "SELECT * FROM Restaurant_Category WHERE idct = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idct);
			ResultSet rs = stmt.executeQuery ();
			restaurantCategoryList = new ArrayList <Restaurant_Category> ();
			while (rs.next ()) {
				restaurantCategory = new Restaurant_Category ();
				restaurantCategory.setIdr (rs.getLong ("idr"));
				restaurantCategory.setIdct (rs.getLong ("idct"));
				restaurantCategoryList.add (restaurantCategory);
				logger.info ("Fetching all Restaurants by idct: " + restaurantCategory.getIdct () + " -> " + restaurantCategory.getIdr ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurantCategoryList;
	}
	
	@Override
	public ArrayList <Restaurant_Category> getAllByRestaurant (long idr) {
		if (conn == null) {
			return null;
		}
		Restaurant_Category restaurantCategory = null;
		ArrayList <Restaurant_Category> restaurantCategoryList = null;
		String query = "SELECT * FROM Restaurant_Category WHERE idr = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idr);
			ResultSet rs = stmt.executeQuery ();
			restaurantCategoryList = new ArrayList <Restaurant_Category> ();
			while (rs.next ()) {
				restaurantCategory = new Restaurant_Category ();
				restaurantCategory.setIdr (rs.getLong ("idr"));
				restaurantCategory.setIdct (rs.getLong ("idct"));
				restaurantCategoryList.add (restaurantCategory);
				logger.info ("Fetching all Categories by idr: " + restaurantCategory.getIdr () + " -> " + restaurantCategory.getIdct ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurantCategoryList;
	}
	
	@Override
	public Restaurant_Category get (long idr, long idct) {
		if (conn == null) {
			return null;
		}
		Restaurant_Category restaurantCategory = null;
		String query = "SELECT * FROM Restaurant_Category WHERE idr = ? AND idct = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idr);
			stmt.setLong (2, idct);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				restaurantCategory = new Restaurant_Category ();
				restaurantCategory.setIdr (rs.getLong ("idr"));
				restaurantCategory.setIdct (rs.getLong ("idct"));
				logger.info ("Fetching RestaurantCategory: (" + restaurantCategory.getIdr () + ", " + restaurantCategory.getIdct () + ")");
			}			
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurantCategory;
	}
	
	@Override
	public boolean add (Restaurant_Category restaurantCategory) {
		boolean done = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "INSERT INTO Restaurant_Category VALUES (?, ?)";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, restaurantCategory.getIdr ());
				stmt.setLong (2, restaurantCategory.getIdct ());
				updatedRows = stmt.executeUpdate ();						
				logger.info ("Creating RestaurantCategory: (" + restaurantCategory.getIdr () + ", " + restaurantCategory.getIdct () + ")");
				if (updatedRows == 1) {
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
	public boolean update (Restaurant_Category previousRestaurantCategory, Restaurant_Category newRestaurantCategory) {
		boolean done = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "UPDATE Restaurant_Category SET idr = ?, idct = ? WHERE idr = ? AND idct = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, newRestaurantCategory.getIdr ());
				stmt.setLong (2, newRestaurantCategory.getIdct ());
				stmt.setLong (3, previousRestaurantCategory.getIdr ());
				stmt.setLong (4, previousRestaurantCategory.getIdct ());
				updatedRows = stmt.executeUpdate ();
				logger.info ("Updating RestaurantCategory: (" + newRestaurantCategory.getIdr () + ", " +newRestaurantCategory.getIdct () + ")");				
				if (updatedRows == 1) {
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
	public boolean delete(long idr, long idct) {
		boolean deletedRow = false;
		int deletedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "DELETE FROM Restaurant_Category WHERE idr = ? AND idct = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, idr);
				stmt.setLong (2, idct);
				deletedRows = stmt.executeUpdate ();
				logger.info ("Deleting RestaurantCategory: (" + idr + ", " + idct + ")");
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
