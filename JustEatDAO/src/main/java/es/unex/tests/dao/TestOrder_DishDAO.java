package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.unex.pi.dao.Order_DishDAO;
import es.unex.pi.dao.JDBCOrder_DishDAOImpl;
import es.unex.pi.model.Order_Dish;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestOrder_DishDAO {

	static DBConn dbConn;
	static Order_DishDAO OrderDishDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		dbConn = new DBConn ();
		conn = dbConn.create ();
	    OrderDishDAO = new JDBCOrder_DishDAOImpl ();
		OrderDishDAO.setConnection (conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass () throws Exception {
		dbConn.destroy (conn);
	}

	@Before
	public void setUpBeforeMethod () throws Exception {}

	@Test
	public void test1BaseData () {
		ArrayList <Order_Dish> orderDishesList = OrderDishDAO.getAll ();
		assertEquals (4, orderDishesList.size ());
		
		orderDishesList = OrderDishDAO.getAllByDish (1);
		assertEquals (2, orderDishesList.size ());
		for (Order_Dish od: orderDishesList) {
			assertEquals (1, od.getIddi ());
		}
		
		orderDishesList = OrderDishDAO.getAllByOrder (2);
		assertEquals (3, orderDishesList.size ());
		for (Order_Dish od: orderDishesList) {
			assertEquals (2, od.getIdo ());
		}
		
		Order_Dish orderDish = OrderDishDAO.get (2, 3);
		assertEquals (2, orderDish.getIdo ());
		assertEquals (3, orderDish.getIddi ());
	}
	
	@Test
	public void test2Add () {
		Order_Dish orderDish01 = new Order_Dish ();
		orderDish01.setIdo (3);
		orderDish01.setIddi (3);
		boolean result = OrderDishDAO.add (orderDish01);
		assertEquals (true, result);
		
		Order_Dish orderDish02 = OrderDishDAO.get (3, 3);
		assertEquals (3, orderDish02.getIdo ());
		assertEquals (3, orderDish02.getIddi ());		
	}
	
	@Test
	public void test3Modify () {
		Order_Dish orderDish01 = OrderDishDAO.get (3, 3);
		Order_Dish orderDish02 = OrderDishDAO.get (3, 3);
		orderDish02.setIdo (1);
		boolean result = OrderDishDAO.update (orderDish01, orderDish02);
		assertEquals (true, result);
		
		Order_Dish orderDish03 = OrderDishDAO.get (1, 3);
		assertEquals (1, orderDish03.getIdo ());
		assertEquals (3, orderDish03.getIddi ());
	}
	
	@Test
	public void test4Delete () {
		Order_Dish orderDish01 = OrderDishDAO.get (1, 3);
		boolean result = OrderDishDAO.delete (orderDish01.getIdo (), orderDish01.getIddi ());
		assertEquals (true, result);
		ArrayList <Order_Dish> orderDishList = OrderDishDAO.getAll ();
		for (Order_Dish od: orderDishList) {
			assertNotEquals (orderDish01, od);
		}
	}
}
