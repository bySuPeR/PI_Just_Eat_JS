package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.unex.pi.model.Order;
import es.unex.pi.dao.OrderDAO;
import es.unex.pi.dao.JDBCOrderDAOImpl;
import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestOrderDAO {

	static DBConn dbConn;
	static OrderDAO orderDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		dbConn = new DBConn ();
		conn = dbConn.create ();
	    orderDAO = new JDBCOrderDAOImpl ();
		orderDAO.setConnection (conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass () throws Exception {
		dbConn.destroy (conn);
	}

	@Before
	public void setUpBeforeMethod () throws Exception {}

	@Test
	public void test1BaseData () {
		Order order01 = orderDAO.get (1);
		assertEquals (order01.getId (), 1);
		assertEquals (order01.getTotalPrice (), 100);
		assertEquals (order01.getIdu (), 2);
		Order order11 = orderDAO.get (2, 100);
		assertEquals (order11.getId (), 1);
		assertEquals (order11.getTotalPrice (), 100);
		assertEquals (order11.getIdu (), 2);
		
		Order order02 = orderDAO.get (2);
		assertEquals (order02.getId (), 2);
		assertEquals (order02.getTotalPrice (), 14);
		assertEquals (order02.getIdu (), 2);
		Order order12 = orderDAO.get (2, 14);
		assertEquals (order12.getId (), 2);
		assertEquals (order12.getTotalPrice (), 14);
		assertEquals (order12.getIdu (), 2);
		
		Order order03 = orderDAO.get (3);
		assertEquals (order03.getId (), 3);
		assertEquals (order03.getTotalPrice (), 5);
		assertEquals (order03.getIdu (), 3);
		Order order13 = orderDAO.get (3, 5);
		assertEquals (order13.getId (), 3);
		assertEquals (order13.getTotalPrice (), 5);
		assertEquals (order13.getIdu (), 3);
		
		ArrayList <Order> orderList = orderDAO.getAll ();
		assertEquals (orderList.size (), 3);
	}
	
	@Test
	public void test2Add () {
		Order order01 = new Order ();
		order01.setTotalPrice (-1);
		order01.setIdu (1);
		long newID = orderDAO.add (order01);
		
		Order order02 = orderDAO.get (newID);
		assertEquals (newID, order02.getId ()); 
		assertEquals (order01.getTotalPrice (), order02.getTotalPrice ());
		assertEquals (order01.getIdu (), order02.getIdu ());
		
		Order order03 = orderDAO.get (order01.getIdu (), order01.getTotalPrice ());
		assertEquals (newID, order03.getId ());
		assertEquals (order01.getTotalPrice (), order03.getTotalPrice ());
		assertEquals (order01.getIdu (), order03.getIdu ());
	}
	
	@Test
	public void test3Modify () {
		Order order01 = orderDAO.get (1, -1);
		order01.setTotalPrice (-2);
		boolean result = orderDAO.update (order01);
		assertEquals (result, true);
		
		Order order02 = orderDAO.get (1, -2);
		assertEquals (order01.getId (), order02.getId ());
		assertEquals (order01.getTotalPrice (), order02.getTotalPrice ());
		assertEquals (order01.getIdu (), order02.getIdu ());
	}
	
	@Test
	public void test4Delete(){
		 Order order01 = orderDAO.get (1, -2);
		 boolean result = orderDAO.delete (order01.getId ());
		 assertEquals (result, true);
		 
		 Order order02 = orderDAO.get (order01.getId ());
		 assertEquals (order02, null);
	}
}
