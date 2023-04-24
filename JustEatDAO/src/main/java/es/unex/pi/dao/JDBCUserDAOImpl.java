package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import es.unex.pi.model.User;

public class JDBCUserDAOImpl implements UserDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());
	
	@Override
	public User get(long id) {
		if (conn == null) {
			return null;
		}
		
		User user = null;
		String query = "SELECT * FROM User WHERE id = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setLong (1, id);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				user = new User ();
				user.setId (rs.getLong ("id"));
				user.setEmail (rs.getString ("email"));
				user.setPassword (rs.getString ("password"));
				user.setName (rs.getString ("name"));
				user.setSurname (rs.getString ("surname"));
				logger.info ("Fetching User by id: " + id + " -> " + user.getId () + " " + user.getName () + " " + user.getEmail () + " " + user.getPassword ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return user;
	}
	
	@Override
	public User get (String email) {
		if (conn == null) {
			return null;
		}
		
		User user = null;
		String query = "SELECT * FROM User WHERE email = ?";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			stmt.clearParameters ();
			stmt.setString (1, email);
			ResultSet rs = stmt.executeQuery ();
			if (rs.next ()) {
				user = new User ();
				user.setId (rs.getLong ("id"));
				user.setEmail (rs.getString ("email"));
				user.setPassword (rs.getString ("password"));
				user.setName (rs.getString ("name"));
				user.setSurname (rs.getString ("surname"));
				logger.info ("Fetching User by email (" + email + "): " + user.getId () + ",  " + user.getEmail () + " " + user.getPassword ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return user;
	}
	
	@Override
	public ArrayList <User> getAll () {
		if (conn == null) {
			return null;
		}
		User user = null;
		ArrayList <User> users = null;
		String query = "SELECT * FROM User";
		
		try {
			PreparedStatement stmt = this.conn.prepareStatement (query);
			ResultSet rs = stmt.executeQuery ();
			users = new ArrayList <User> ();
			while (rs.next()) {
				user = new User ();
				user.setId (rs.getLong ("id"));
				user.setEmail (rs.getString ("email"));
				user.setPassword (rs.getString ("password"));
				user.setName (rs.getString ("name"));
				user.setSurname (rs.getString ("surname"));
				
				users.add (user);
				logger.info ("Fetching users: " + user.getId () + " " + user.getName () + " " + user.getEmail () + " " + user.getPassword ());
			}
		}
		catch (SQLException e) {
			e.printStackTrace ();
		}
		return users;
	}
	
	@Override
	public long add (User user) {
		long id = -1;
		if (conn != null) {
			long lastid = -1;
			int updatedRows = -1;
			String query = "SELECT * FROM sqlite_sequence WHERE name = 'User'";
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
			
			String query2 = "INSERT INTO User (email, password, name, surname) VALUES (?, ?, ?, ?)";
			try {
				stmt = this.conn.prepareStatement (query2);
				stmt.clearParameters ();
				stmt.setString (1, user.getEmail ());
				stmt.setString (2, user.getPassword ());
				stmt.setString (3, user.getName ());
				stmt.setString (4, user.getSurname ());
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
				logger.info ("CREATING User (" + id + "): " + user.getName () + " " + user.getEmail () + " " + user.getPassword ());
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
		}
		return id;
	}

	@Override
	public boolean update(User user) {
		boolean done = false;
		int updatedRows = -1;
		if (conn != null) {
			PreparedStatement stmt = null;
			String query = "UPDATE User SET email = ?, password = ?, name = ?, surname = ? WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setString (1, user.getEmail ());
				stmt.setString (2, user.getPassword ());
				stmt.setString (3, user.getName ());
				stmt.setString (4, user.getSurname ());
				stmt.setLong (5, user.getId ());
				updatedRows = stmt.executeUpdate ();
				logger.info ("Updating User: " + user.getId () + " " + user.getName () + " " + user.getEmail () + " " + user.getPassword ());
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
			String query = "DELETE FROM User WHERE id = ?";
			try {
				stmt = this.conn.prepareStatement (query);
				stmt.clearParameters ();
				stmt.setLong (1, id);
				deletedRows = stmt.executeUpdate ();
				logger.info ("Deleting User: " + id);
				if (deletedRows == 1) {
					deletedRow = true;
				}
			}
			catch (SQLException e) {
				e.printStackTrace ();
			}
			
			query = "UPDATE sqlite_sequence SET seq = (SELECT count (*) FROM User) WHERE name = 'User'";
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
