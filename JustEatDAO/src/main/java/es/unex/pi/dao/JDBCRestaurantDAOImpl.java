package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import es.unex.pi.model.Restaurant;

public class JDBCRestaurantDAOImpl implements RestaurantDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger (JDBCRestaurantDAOImpl.class.getName ());
	
	@Override
	public Restaurant get (long id) {
		if (conn == null) {
			return null;
		}
		
		Restaurant restaurant = null;
		String query = "SELECT * FROM Restaurant WHERE id = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, id);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				restaurant = new Restaurant ();
				restaurant.setId (rs.getLong ("id"));
				restaurant.setName (rs.getString ("name"));
				restaurant.setAddress (rs.getString ("address"));
				restaurant.setTelephone (rs.getString ("telephone"));
				restaurant.setGradesAverage (rs.getDouble ("gradesAverage"));
				restaurant.setCity (rs.getString ("city"));
				restaurant.setMinPrice (rs.getInt ("minPrice"));
				restaurant.setContactEmail (rs.getString ("contactEmail"));
				restaurant.setMaxPrice (rs.getInt ("maxPrice"));
				restaurant.setBikeFriendly (rs.getInt ("bikeFriendly"));
				restaurant.setAvailable (rs.getInt ("available"));
				restaurant.setIdu (rs.getLong ("idu"));
				restaurant.setRestaurantPic (rs.getString ("restaurantPic"));
				logger.info ("Fetching restaurant (" + id + "): " + restaurant.getName () + "...");
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurant;
	}
	
	public ArrayList <Restaurant> getAll () {
		if (conn == null) {
			return null;
		}
		
		Restaurant restaurant = null;
		ArrayList <Restaurant> restaurants = null;
		String query = "SELECT * FROM Restaurant";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			ResultSet rs = stmt.executeQuery ();
			restaurants = new ArrayList <Restaurant> ();
			while (rs.next ()) {
				restaurant = new Restaurant ();
				restaurant.setId (rs.getLong ("id"));
				restaurant.setName (rs.getString ("name"));
				restaurant.setAddress (rs.getString ("address"));
				restaurant.setTelephone (rs.getString ("telephone"));
				restaurant.setGradesAverage (rs.getDouble ("gradesAverage"));
				restaurant.setCity (rs.getString ("city"));
				restaurant.setMinPrice (rs.getInt ("minPrice"));
				restaurant.setContactEmail (rs.getString ("contactEmail"));
				restaurant.setMaxPrice (rs.getInt ("maxPrice"));
				restaurant.setBikeFriendly (rs.getInt ("bikeFriendly"));
				restaurant.setAvailable (rs.getInt ("available"));
				restaurant.setIdu (rs.getLong ("idu"));
				restaurant.setRestaurantPic (rs.getString ("restaurantPic"));
				restaurants.add (restaurant);
				logger.info ("Fetching restaurant (" + restaurant.getId () + "): " + restaurant.getName () + "...");
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurants;
	}
	
	public ArrayList <Restaurant> searchAmongRestaurants (String search) {
		if (conn == null) {
			return null;
		}
		search = search.toUpperCase ();
		search = "%" + search + "%";
		Restaurant restaurant = null;
		ArrayList <Restaurant> restaurants = null;
		String query = "SELECT * FROM Restaurant WHERE (UPPER (name) LIKE ?) OR (UPPER (address) LIKE ?) OR (UPPER (city) LIKE ?)";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setString (1, search);
			stmt.setString (2, search);
			stmt.setString (3, search);
			ResultSet rs = stmt.executeQuery ();
			restaurants = new ArrayList <Restaurant> ();
			while (rs.next ()) {
				restaurant = new Restaurant ();
				restaurant.setId (rs.getLong ("id"));
				restaurant.setName (rs.getString ("name"));
				restaurant.setAddress (rs.getString ("address"));
				restaurant.setTelephone (rs.getString ("telephone"));
				restaurant.setGradesAverage (rs.getDouble ("gradesAverage"));
				restaurant.setCity (rs.getString ("city"));
				restaurant.setMinPrice (rs.getInt ("minPrice"));
				restaurant.setContactEmail (rs.getString ("contactEmail"));
				restaurant.setMaxPrice (rs.getInt ("maxPrice"));
				restaurant.setBikeFriendly (rs.getInt ("bikeFriendly"));
				restaurant.setAvailable (rs.getInt ("available"));
				restaurant.setIdu (rs.getLong ("idu"));
				restaurant.setRestaurantPic (rs.getString ("restaurantPic"));
				restaurants.add (restaurant);
				logger.info ("Fetching restaurant (" + restaurant.getId () + "): " + restaurant.getName () + "...");
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurants;
	}
	
	@Override
	public ArrayList <Restaurant> getAllByUser (long idu) {
		if (conn == null) {
			return null;
		}
		Restaurant restaurant = null;
		ArrayList <Restaurant> restaurants = null;
		String query = "SELECT * FROM Restaurant WHERE idu = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idu);
			ResultSet rs = stmt.executeQuery ();
			restaurants = new ArrayList <Restaurant> ();
			while (rs.next()) {
				restaurant = new Restaurant ();
				restaurant.setId (rs.getLong ("id"));
				restaurant.setName (rs.getString ("name"));
				restaurant.setAddress (rs.getString ("address"));
				restaurant.setTelephone (rs.getString ("telephone"));
				restaurant.setGradesAverage (rs.getDouble ("gradesAverage"));
				restaurant.setCity (rs.getString ("city"));
				restaurant.setMinPrice (rs.getInt ("minPrice"));
				restaurant.setContactEmail (rs.getString ("contactEmail"));
				restaurant.setMaxPrice (rs.getInt ("maxPrice"));
				restaurant.setBikeFriendly (rs.getInt ("bikeFriendly"));
				restaurant.setAvailable (rs.getInt ("available"));
				restaurant.setIdu (rs.getLong ("idu"));
				restaurant.setRestaurantPic (rs.getString ("restaurantPic"));
				restaurants.add (restaurant);
				logger.info ("Fetching restaurant (" + restaurant.getId () + "): " + restaurant.getName () + "...");
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurants;
	}
	
	@Override
	public Restaurant getByName (String name) {
		if (this.conn == null) {
			return null;
		}
		Restaurant restaurant = null;
		String query = "SELECT * FROM Restaurant WHERE name = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setString (1, name);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				restaurant = new Restaurant ();
				restaurant.setId (rs.getLong ("id"));
				restaurant.setName (rs.getString ("name"));
				restaurant.setAddress (rs.getString ("address"));
				restaurant.setTelephone (rs.getString ("telephone"));
				restaurant.setGradesAverage (rs.getDouble ("gradesAverage"));
				restaurant.setCity (rs.getString ("city"));
				restaurant.setMinPrice (rs.getInt ("minPrice"));
				restaurant.setContactEmail (rs.getString ("contactEmail"));
				restaurant.setMaxPrice (rs.getInt ("maxPrice"));
				restaurant.setBikeFriendly (rs.getInt ("bikeFriendly"));
				restaurant.setAvailable (rs.getInt ("available"));
				restaurant.setIdu (rs.getLong ("idu"));
				restaurant.setRestaurantPic (rs.getString ("restaurantPic"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return restaurant;
	}
	
	@Override
	public long add (Restaurant restaurant) {
		long id = -1;
		if (conn != null) {
			long lastid = -1;
			int updatedRows = -1;
			String query = "SELECT * FROM sqlite_sequence WHERE name = 'Restaurant'";
			PreparedStatement stmt = null;
			try {
				stmt = this.conn.prepareStatement (query);
				ResultSet rs = stmt.executeQuery ();
				if (rs.next ()) {
					lastid = rs.getInt ("seq");
				}			
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}			
			
			String query2 = "INSERT INTO Restaurant (name, address, telephone, gradesAverage, city, minPrice, contactEmail, maxPrice, bikeFriendly, available, idu, restaurantPic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				stmt = this.conn.prepareStatement (query2);
				stmt.clearParameters ();
				stmt.setString (1, restaurant.getName ());
				stmt.setString (2, restaurant.getAddress ());
				stmt.setString (3, restaurant.getTelephone ());
				stmt.setDouble (4, restaurant.getGradesAverage ());
				stmt.setString (5, restaurant.getCity ());
				stmt.setInt (6, restaurant.getMinPrice ());
				stmt.setString (7, restaurant.getContactEmail ());
				stmt.setInt (8, restaurant.getMaxPrice ());
				stmt.setInt (9, restaurant.getBikeFriendly ());
				stmt.setInt (10, restaurant.getAvailable ());
				stmt.setLong (11, restaurant.getIdu ());
				stmt.setString (12, restaurant.getRestaurantPic ());
				updatedRows = stmt.executeUpdate ();
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
			
			try {
				stmt = this.conn.prepareStatement (query);
				ResultSet rs = stmt.executeQuery ();			 
				if (rs.next ()) {
					id = rs.getInt ("seq");
				}
				if ((id <= lastid) || (updatedRows != 1)) {
					id = -1;
				}							
				logger.info ("CREATING restaurant (" + id + "): " + restaurant.getName () + "...");
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
		}
		return id;
	}

	@Override
	public boolean update (Restaurant restaurant) {
		boolean done = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "UPDATE Restaurant " +
						   "SET name = ?, " +
						   "address = ?, " +
						   "telephone = ?, " +
						   "gradesAverage = ?, " +
						   "city = ?, " +
						   "minPrice = ?, " +
						   "contactEmail = ?, " +
						   "maxPrice = ?, " +
						   "bikeFriendly = ?, " +
						   "available = ?, " +
						   "idu = ?, " +
						   "restaurantPic = ?" +
						   "WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setString (1, restaurant.getName ());
				stmt.setString (2, restaurant.getAddress ());
				stmt.setString (3, restaurant.getTelephone ());
				stmt.setDouble (4, restaurant.getGradesAverage ());
				stmt.setString (5, restaurant.getCity ());
				stmt.setInt (6, restaurant.getMinPrice ());
				stmt.setString (7, restaurant.getContactEmail ());
				stmt.setInt (8, restaurant.getMaxPrice ());
				stmt.setInt (9, restaurant.getBikeFriendly ());
				stmt.setInt (10, restaurant.getAvailable ());
				stmt.setLong (11, restaurant.getIdu ());
				stmt.setString (12, restaurant.getRestaurantPic ());
				stmt.setLong (13, restaurant.getId ());
				updatedRows = stmt.executeUpdate ();
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
	public boolean delete (long id) {
		boolean deletedRow = false;
		boolean updatedRow = false;
		int deletedRows = -1;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "DELETE FROM Restaurant WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, id);
				deletedRows = stmt.executeUpdate ();
				logger.info ("Deleting restaurant: " + id);
				if (deletedRows == 1) {
					deletedRow = true;
				}
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
			
			query = "UPDATE sqlite_sequence SET seq = (SELECT count (*) FROM Restaurant) WHERE name = 'Restaurant'";
			try {
				stmt = this.conn.prepareStatement (query);
				updatedRows = stmt.executeUpdate ();
				if (updatedRows == 1) {
					updatedRow = true;
				}
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
		}
		return (deletedRow && updatedRow);
	}

	@Override
	public void setConnection (Connection conn) {
		this.conn = conn;
	}
}
