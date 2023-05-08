package es.unex.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.giiis.pi.model.Order;

public class JDBCOrderDAOImpl implements OrderDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCOrderDAOImpl.class.getName());

	@Override
	public List<Order> getAll() {

		logger.info("fetching order: connection with db?");
		
		if (conn == null) return null;
		
		logger.info("fetching order: connection with db ok");
				
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Pizzaorder");
			while ( rs.next() ) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setName(rs.getString("name"));
				order.setTel(rs.getString("tel"));
				order.setEmail(rs.getString("email"));
				order.setSize(rs.getString("size"));
				order.setType(rs.getString("type"));
				order.setDelivery(rs.getString("delivery"));
				order.setComments(rs.getString("comments"));
				
				orders.add(order);
				logger.info("fetching orders: "+order.getId()+" "+order.getName()+" "+order.getTel()+" "+
						order.getSize()+" "+order.getType()+" "+order.getDelivery()+" "+order.getComments());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public Order get(long id) {
		if (conn == null) return null;
		
		Order order = null;		
		
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Pizzaorder WHERE id ="+id);			 
			if (!rs.next()) return null; 
			order  = new Order();	 
			order.setId(rs.getLong("id"));
			order.setName(rs.getString("Name"));
			order.setTel(rs.getString("Tel"));
			order.setEmail(rs.getString("Email"));
			order.setSize(rs.getString("size"));
			order.setType(rs.getString("type"));
			order.setDelivery(rs.getString("delivery"));
			order.setComments(rs.getString("comments"));
			
			logger.info("fetching order: "+order.getId()+" "+order.getName()+" "+order.getTel()+" "+
					order.getSize()+" "+order.getType()+" "+order.getDelivery()+" "+order.getComments());
			
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	
	@Override
	public List<Order> get(String name) {
		logger.info("fetching order: connection with db?");
		
		if (conn == null) return null;
		logger.info("fetching order: connection with db?");
		
				
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Pizzaorder WHERE name ='" + name + "'");
			
			while ( rs.next() ) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setName(rs.getString("name"));
				order.setTel(rs.getString("tel"));
				order.setEmail(rs.getString("email"));
				order.setSize(rs.getString("size"));
				order.setType(rs.getString("type"));
				order.setDelivery(rs.getString("delivery"));
				order.setComments(rs.getString("comments"));
				
				orders.add(order);
				logger.info("fetching orders: "+order.getId()+" "+order.getName()+" "+order.getTel()+" "+
						order.getSize()+" "+order.getType()+" "+order.getDelivery()+" "+order.getComments());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orders;
	}
	

	@Override
	public long add(Order order) {
		long id=-1;
		long lastid=-1;
		if (conn != null){

			
			Statement stmt;
			try {
				
				ResultSet rs;
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='PizzaOrder'");			 
				if (!rs.next()) return -1; 
				lastid=rs.getLong("seq");
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO PIZZAORDER (name,tel,email,size,type,delivery,comments) VALUES('"
						+order.getName()+"','"
						+order.getTel()+"','"
						+order.getEmail()+"','"
						+order.getSize()+"','"
						+order.getType()+"','"
						+order.getDelivery()+"','"
						+order.getComments()+"')");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				ResultSet rs;
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='PizzaOrder'");
				if (!rs.next()) return -1; 
				id=rs.getLong("seq");
				if (id<=lastid) return -1;
											
				logger.info("creating order: "+order.getName()+" "+order.getTel()+" "+
						order.getSize()+" "+order.getType()+" "+order.getDelivery()+" "+order.getComments());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return id;
	}


	@Override
	public boolean save(Order order) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE PIZZAORDER SET Name='"+order.getName()+"', Tel='"+order.getTel()+"', Email='"+order.getEmail()+"', Size='"+order.getSize()+"', Type='"+order.getType()+"', Delivery='"+order.getDelivery()+"', Comments ='"+order.getComments()+"' WHERE id = "+order.getId());
				logger.info("updating order: "+order.getId()+" "+order.getName()+" "+order.getTel()+" "+
						order.getSize()+" "+order.getType()+" "+order.getDelivery()+" "+order.getComments());
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
				stmt.executeUpdate("DELETE FROM PIZZAORDER WHERE id ="+id);
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

		this.conn = conn;
	}
}
