package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import es.unex.pi.model.Category;


public class JDBCCategoryDAOImpl implements CategoryDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCCategoryDAOImpl.class.getName());
	
	@Override
	public Category get (long id) {
		if (conn == null) {
			return null;
		}
		
		Category category = null;
		String query = "SELECT * FROM Category WHERE id = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, id);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				category = new Category ();
				category.setId (rs.getLong ("id"));
				category.setName (rs.getString ("name"));
				category.setDescription (rs.getString ("description"));
				logger.info ("fetching Category by id: " + id + " -> " + category.getId () + " " + category.getName () + " " + category.getDescription ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return category;
	}
	
	@Override
	public Category get (String name) {
		if (conn == null) {
			return null;
		}
		
		Category category = null;		
		String query = "SELECT * FROM Category WHERE name = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setString (1, name);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				category = new Category ();
				category.setId (rs.getLong ("id"));
				category.setName (rs.getString ("name"));
				category.setDescription (rs.getString ("description"));
				logger.info ("fetching Category by name: " + category.getId () + " " + category.getName () + " " + category.getDescription ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return category;
	}
	
	@Override
	public ArrayList <Category> getAll () {
		if (conn == null) {
			return null;
		}
		
		Category category = null;
		ArrayList <Category> categories = null;
		String query = "SELECT * FROM Category";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			ResultSet rs = stmt.executeQuery ();
			categories = new ArrayList <Category> ();
			while (rs.next ()) {
				category = new Category ();
				category.setId (rs.getLong ("id"));
				category.setName (rs.getString ("name"));
				category.setDescription (rs.getString ("description"));
				categories.add (category);
				logger.info ("fetching Categories: " + category.getId () + " " + category.getName () + " " + category.getDescription ());						
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return categories;
	}
	
	@Override
	public ArrayList <Category> getAllBySearchName (String search) {
		if (conn == null) {
			return null;
		}
		
		search = search.toUpperCase ();
		search = "%" + search + "%";
		Category category = null;
		ArrayList <Category> categories = null;
		String query = "SELECT * FROM Category WHERE UPPER (name) LIKE ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setString (1, search);
			ResultSet rs = stmt.executeQuery ();
			categories = new ArrayList <Category> ();
			while (rs.next()) {
				category = new Category ();
				category.setId (rs.getLong ("id"));
				category.setName (rs.getString ("name"));
				category.setDescription (rs.getString ("description"));
				categories.add (category);
				logger.info ("fetching Categories by text in the name: " + search + ": " + category.getId () + " " + category.getName () + " " + category.getDescription ());	
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}
	
	@Override
	public long add (Category category) {
		long id = -1;
		if (conn != null) {
			long lastid = -1;
			int updatedRows = -1;
			String query = "SELECT * FROM sqlite_sequence WHERE name = 'Category'";
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
			
			String query2 = "INSERT INTO Category (name, description) VALUES (?, ?)";
			try {
				stmt = this.conn.prepareStatement (query2);
				stmt.clearParameters ();
				stmt.setString (1, category.getName ());
				stmt.setString (2, category.getDescription ());
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
				logger.info ("CREATING Category (" + id + "): " + category.getName () + ", " + category.getDescription ());
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
		}
		return id;
	}

	@Override
	public boolean update (Category category) {
		boolean done = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "UPDATE Category SET name = ?, description = ? WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setString (1, category.getName ());
				stmt.setString (2, category.getDescription ());
				stmt.setLong (3, category.getId ());
				updatedRows = stmt.executeUpdate ();
				logger.info ("Updating Category (" + category.getId () + "): " + category.getName () + ", " + category.getDescription ());
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
			String query = "DELETE FROM Category WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, id);
				deletedRows = stmt.executeUpdate ();
				logger.info ("Deleting Category: " + id);
				if (deletedRows == 1) {
					deletedRow = true;
				}
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
			
			query = "UPDATE sqlite_sequence SET seq = (SELECT count (*) FROM Category) WHERE name = 'Category'";
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
