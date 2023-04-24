package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import es.unex.pi.model.Dish;


public class JDBCDishDAOImpl implements DishDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCDishDAOImpl.class.getName());
	
	@Override
	public Dish get (long id) {
		if (conn == null) {
			return null;
		}
		
		Dish dish = null;		
		String query = "SELECT * FROM Dish WHERE id = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, id);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				dish = new Dish ();
				dish.setId (rs.getLong ("id"));
				dish.setName (rs.getString ("name"));
				dish.setPrice (rs.getInt ("price"));
				dish.setDescription (rs.getString ("description"));
				dish.setIdr (rs.getLong ("idr"));
				dish.setDishPic (rs.getString ("dishPic"));
				logger.info ("fetching Dish by id: " + id + " -> " + dish.getId () + " " + dish.getName () + " " + dish.getDescription ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return dish;
	}
	
	@Override
	public Dish get (String name) {
		if (conn == null) {
			return null;
		}
		
		Dish dish = null;
		String query = "SELECT * FROM Dish WHERE name = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setString (1, name);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				dish = new Dish ();
				dish.setId (rs.getLong ("id"));
				dish.setName (rs.getString ("name"));
				dish.setPrice (rs.getInt ("price"));
				dish.setDescription (rs.getString ("description"));
				dish.setIdr (rs.getLong ("idr"));
				dish.setDishPic (rs.getString ("dishPic"));
				logger.info("fetching Dish by name: " + dish.getId () + " " + dish.getName () + " " + dish.getDescription ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return dish;
	}
	
	@Override
	public ArrayList <Dish> getAll () {
		if (conn == null) {
			return null;
		}
		
		Dish dish = null;
		ArrayList <Dish> dishes = null;
		String query = "SELECT * FROM Dish";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			ResultSet rs = stmt.executeQuery ();
			dishes = new ArrayList <Dish> ();
			while (rs.next ()) {
				dish = new Dish ();
				dish.setId (rs.getLong ("id"));
				dish.setName (rs.getString ("name"));
				dish.setPrice (rs.getInt ("price"));
				dish.setDescription (rs.getString ("description"));
				dish.setIdr (rs.getLong ("idr"));
				dish.setDishPic (rs.getString ("dishPic"));
				dishes.add(dish);
				logger.info("fetching Dishes: " + dish.getId () + " " + dish.getName () + " " + dish.getDescription ());								
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return dishes;
	}
	
	@Override
	public ArrayList <Dish> getAllBySearchName (String search) {
		if (conn == null) {
			return null;
		}
		
		search = search.toUpperCase ();
		search = "%" + search + "%";
		Dish dish = null;
		ArrayList <Dish> dishes = null;
		String query = "SELECT * FROM Dish WHERE UPPER (name) LIKE ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setString (1, search);
			ResultSet rs = stmt.executeQuery ();
			dishes = new ArrayList <Dish> ();
			while (rs.next ()) {
				dish = new Dish ();
				dish.setId (rs.getLong ("id"));
				dish.setName (rs.getString ("name"));
				dish.setPrice (rs.getInt ("price"));
				dish.setDescription (rs.getString ("description"));
				dish.setIdr (rs.getLong ("idr"));
				dish.setDishPic (rs.getString ("dishPic"));
				dishes.add (dish);
				logger.info ("fetching Dishes by text in the name: " + search + ": " + dish.getId () + " " + dish.getName () + " " + dish.getDescription ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return dishes;
	}
	
	@Override
	public ArrayList <Dish> getAllByRestaurant (long idr) {
		if (conn == null) {
			return null;
		}
		Dish dish = null;
		ArrayList <Dish> dishes = null;
		String query = "SELECT * FROM Dish WHERE idr = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idr);
			ResultSet rs = stmt.executeQuery ();
			dishes = new ArrayList <Dish> ();
			while (rs.next ()) {
				dish = new Dish ();
				dish.setId (rs.getLong ("id"));
				dish.setId (rs.getLong ("id"));
				dish.setName (rs.getString ("name"));
				dish.setPrice (rs.getInt ("price"));
				dish.setDescription (rs.getString ("description"));
				dish.setIdr (rs.getLong ("idr"));
				dish.setDishPic (rs.getString ("dishPic"));
				dishes.add (dish);
				logger.info ("Fetching Dishes by restaurant (" + idr + "): " + dish.getName ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return dishes;
	}
	
	@Override
	public long add (Dish dish) {
		long id = -1;
		if (conn != null) {
			long lastid = -1;
			int updatedRows = -1;
			String query = "SELECT * FROM sqlite_sequence WHERE name = 'Dish'";
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
			
			String query2 = "INSERT INTO Dish (name, price, description, idr, dishPic) VALUES (?, ?, ?, ?, ?)";
			try {
				stmt = this.conn.prepareStatement (query2);
				stmt.clearParameters ();
				stmt.setString (1, dish.getName ());
				stmt.setInt (2, dish.getPrice ());
				stmt.setString (3, dish.getDescription ());
				stmt.setLong (4, dish.getIdr ());
				stmt.setString (5, dish.getDishPic ());
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
				logger.info("CREATING Dish (" + id + "): " + dish.getName () + ", " + dish.getDescription ());
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
		}
		return id;
	}

	@Override
	public boolean update (Dish dish) {
		boolean done = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "UPDATE Dish SET name = ?, price = ?, description = ?, idr = ?, dishPic = ? WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setString (1, dish.getName ());
				stmt.setInt(2, dish.getPrice ());
				stmt.setString (3, dish.getDescription ());
				stmt.setLong (4, dish.getIdr ());
				stmt.setString (5, dish.getDishPic());
				stmt.setLong (6, dish.getId ());
				updatedRows = stmt.executeUpdate ();				
				logger.info ("Updating Dish: " + dish.getId () + " " + dish.getName () + " " + dish.getDescription () + " "+ dish.getDishPic());
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
			String query = "DELETE FROM Dish WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, id);
				updatedRows = stmt.executeUpdate ();
				logger.info ("deleting Dish: " + id);
				if (deletedRows == 1) {
					deletedRow = true;
				}
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
			query = "UPDATE sqlite_sequence SET seq = (SELECT count (*) FROM Dish) WHERE name = 'Dish'";
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
