package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import es.unex.pi.model.Order_Dish;

public class JDBCOrder_DishDAOImpl implements Order_DishDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCOrder_DishDAOImpl.class.getName());

	@Override
	public ArrayList <Order_Dish> getAll () {
		if (conn == null) {
			return null;
		}
		Order_Dish orderDish = null;
		ArrayList <Order_Dish> orderDishes = null;
		String query = "SELECT * FROM Order_Dish";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			ResultSet rs = stmt.executeQuery ();
			orderDishes = new ArrayList <Order_Dish> ();
			while (rs.next ()) {
				orderDish = new Order_Dish ();
				orderDish.setIdo (rs.getLong ("ido"));
				orderDish.setIddi (rs.getLong ("iddi"));
				
				orderDishes.add (orderDish);
				logger.info ("Fetching all OrderDishes: " + orderDish.getIdo () + ", " + orderDish.getIddi ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return orderDishes;
	}

	@Override
	public ArrayList <Order_Dish> getAllByDish (long iddi) {
		if (conn == null) {
			return null;
		}
		Order_Dish orderDish = null;
		ArrayList <Order_Dish> orderDishes = null;
		String query = "SELECT * FROM Order_Dish WHERE iddi = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, iddi);
			ResultSet rs = stmt.executeQuery ();
			orderDishes = new ArrayList <Order_Dish> ();
			while (rs.next ()) {
				orderDish = new Order_Dish ();
				orderDish.setIdo (rs.getLong ("ido"));
				orderDish.setIddi (rs.getLong ("iddi"));

				orderDishes.add (orderDish);
				logger.info ("Fetching all OrderDishes by Dish: " + orderDish.getIddi () + " -> " + orderDish.getIddi ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return orderDishes;
	}
	
	@Override
	public ArrayList <Order_Dish> getAllByOrder (long ido) {
		if (conn == null) {
			return null;
		}
		Order_Dish orderDish = null;				
		ArrayList <Order_Dish> orderDishes = null;
		String query = "SELECT * FROM Order_Dish WHERE ido = ?";
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, ido);
			ResultSet rs = stmt.executeQuery ();
			orderDishes = new ArrayList <Order_Dish> ();
			while (rs.next ()) {
				orderDish = new Order_Dish ();
				orderDish.setIdo (rs.getLong ("ido"));
				orderDish.setIddi (rs.getLong ("iddi"));
							
				orderDishes.add (orderDish);
				logger.info ("Fetching all OrderDishes by ido: " + orderDish.getIdo () + " -> " + orderDish.getIdo ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return orderDishes;
	}
	
	@Override
	public Order_Dish get (long ido, long iddi) {
		if (conn == null) {
			return null;
		}
		
		Order_Dish orderDish = null;
		String query = "SELECT * FROM Order_Dish WHERE ido = ? AND iddi = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, ido);
			stmt.setLong (2, iddi);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				orderDish = new Order_Dish ();
				orderDish.setIdo (rs.getLong ("ido"));
				orderDish.setIddi (rs.getLong ("iddi"));
			}
			logger.info ("Fetching OrderDish by ido: " + orderDish.getIdo () + " and iddi: " + orderDish.getIddi ());
		}
		catch (SQLException e) {
			e.printStackTrace ();			
		}
		return orderDish;
	}
	
	@Override
	public boolean add (Order_Dish orderDish) {
		boolean done = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "INSERT INTO Order_Dish VALUES (?, ?)";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, orderDish.getIdo ());
				stmt.setLong (2, orderDish.getIddi ());
				updatedRows = stmt.executeUpdate ();
				logger.info ("Creating OrderDish: (" + orderDish.getIdo () + ", " + orderDish.getIddi () + ")");
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
	public boolean update (Order_Dish previousOrderDish, Order_Dish newOrderDish) {
		boolean done = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "UPDATE Order_Dish SET ido = ?, iddi = ? WHERE ido = ? AND iddi = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, newOrderDish.getIdo ());
				stmt.setLong (2, newOrderDish.getIddi ());
				stmt.setLong (3, previousOrderDish.getIdo ());
				stmt.setLong (4, previousOrderDish.getIddi ());
				updatedRows = stmt.executeUpdate ();
				logger.info ("Updating OrderDishes: (" + previousOrderDish.getIdo () + ", " + previousOrderDish.getIddi () + ")");
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
	public boolean delete (long ido, long iddi) {
		boolean deletedRow = false;
		int deletedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "DELETE FROM Order_Dish WHERE ido = ? AND iddi = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, ido);
				stmt.setLong (2, iddi);
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
