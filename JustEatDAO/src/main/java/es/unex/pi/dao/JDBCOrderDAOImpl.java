package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import es.unex.pi.model.Order;


public class JDBCOrderDAOImpl implements OrderDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCOrderDAOImpl.class.getName());
	
	@Override
	public Order get (long id) {
		if (conn == null) {
			return null;
		}
		
		Order order = null;
		String query = "SELECT * FROM 'Order' WHERE id = ?";

		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, id);
			ResultSet rs = stmt.executeQuery ();			 
			if (rs.next ()) {
				order = new Order ();
				order.setId (rs.getLong ("id"));
				order.setIdu (rs.getLong ("idu"));
				order.setTotalPrice (rs.getInt ("totalPrice"));
				logger.info ("fetching Order by id: " + id + " -> " + order.getId () + ", " + order.getIdu () + ", " + order.getTotalPrice ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return order;
	}
	
	@Override
	public Order get (long idu, int totalPrice) {
		if (conn == null) {
			return null;
		}
		
		Order order = null;
		String query = "SELECT * FROM 'Order' WHERE idu = ? AND totalPrice = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idu);
			stmt.setInt (2, totalPrice);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				order = new Order ();
				order.setId (rs.getLong ("id"));
				order.setIdu (rs.getLong ("idu"));
				order.setTotalPrice (rs.getInt ("totalPrice"));
				logger.info ("Fetching Order (" + order.getId () + "): " + order.getIdu () + ", " + order.getTotalPrice ());
			} 			
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return order;
	}
	
	@Override
	public ArrayList <Order> getAll () {
		if (conn == null) {
			return null;
		}
		
		Order order = null;
		ArrayList <Order> orders = null;
		String query = "SELECT * FROM 'Order'";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			ResultSet rs = stmt.executeQuery ();
			orders = new ArrayList <Order> ();
			while (rs.next ()) {
				order = new Order ();
				order.setId (rs.getLong ("id"));
				order.setIdu (rs.getLong ("idu"));
				order.setTotalPrice (rs.getInt ("totalPrice"));
				
				orders.add (order);
				logger.info ("fetching Orders: " + order.getId () + ", " + order.getIdu () + ", " + order.getTotalPrice ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return orders;
	}
	
	@Override
	public ArrayList <Order> getAllByUser (long idu) {
		if (conn == null) {
			return null;
		}
		Order order = null;
		ArrayList <Order> orders = null;
		String query = "SELECT * FROM 'Order' WHERE idu = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, idu);
			ResultSet rs = stmt.executeQuery ();
			orders = new ArrayList <Order> ();
			while (rs.next ()) {
				order = new Order ();
				order.setId (rs.getLong ("id"));
				order.setIdu (rs.getLong ("idu"));
				order.setTotalPrice (rs.getInt ("totalPrice"));
				orders.add (order);
				logger.info ("Fetching Orders by User (" + idu + "): " + order.getId ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return orders;
	}
	
	@Override
	public long add (Order order) {
		long id = -1;
		if (conn != null) {
			long lastid = -1;
			int updatedRows = -1;
			String query = "SELECT * FROM sqlite_sequence WHERE name = 'Order'";
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
			
			String query2 = "INSERT INTO 'Order' (totalPrice, idu) VALUES (?, ?)";
			try {
				stmt = this.conn.prepareStatement (query2);
				stmt.clearParameters ();
				stmt.setInt (1, order.getTotalPrice ());
				stmt.setLong (2, order.getIdu ());
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
				logger.info ("CREATING Order (" + id + "): " + order.getIdu () + ", " + order.getTotalPrice ());
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
		}
		return id;
	}

	@Override
	public boolean update (Order order) {
		boolean done = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "UPDATE 'Order' SET totalPrice = ?, idu = ? WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setInt (1, order.getTotalPrice ());
				stmt.setLong (2, order.getIdu ());
				stmt.setLong (3, order.getId ());
				updatedRows = stmt.executeUpdate ();				
				logger.info ("Updating Order: " + order.getId() + ", " + order.getTotalPrice () + ", " + order.getIdu ());				
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
			String query = "DELETE FROM 'Order' WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, id);
				deletedRows = stmt.executeUpdate ();
				logger.info("deleting Order: " + id);
				if (deletedRows == 1) {
					deletedRow = true;
				}
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
			
			query = "UPDATE sqlite_sequence SET seq = (SELECT count (*) FROM 'Order') WHERE name = 'Order'";
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
