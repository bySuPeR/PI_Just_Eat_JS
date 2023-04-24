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

import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.dao.JDBCRestaurant_CategoryDAOImpl;
import es.unex.pi.model.Restaurant_Category;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestRestaurant_CategoryDAO {

	static DBConn dbConn;
	static Restaurant_CategoryDAO RestaurantCategoryDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		dbConn = new DBConn ();
		conn = dbConn.create ();
	    RestaurantCategoryDAO = new JDBCRestaurant_CategoryDAOImpl ();
		RestaurantCategoryDAO.setConnection (conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass () throws Exception {
		dbConn.destroy (conn);
	}

	@Before
	public void setUpBeforeMethod() throws Exception {}

	@Test
	public void test1BaseData () {
		ArrayList <Restaurant_Category> restaurantCategoryList = RestaurantCategoryDAO.getAll ();
		assertEquals (3, restaurantCategoryList.size ());
		
		restaurantCategoryList = RestaurantCategoryDAO.getAllByCategory (5);
		assertEquals (1, restaurantCategoryList.size ());
		for (Restaurant_Category rc: restaurantCategoryList) {
			assertEquals (5, rc.getIdct ());
		}
		
		restaurantCategoryList = RestaurantCategoryDAO.getAllByRestaurant (1);
		assertEquals (2, restaurantCategoryList.size ());
		for (Restaurant_Category rc: restaurantCategoryList) {
			assertEquals (1, rc.getIdr ());
		}
		
		Restaurant_Category restaurantCategory = RestaurantCategoryDAO.get (1, 1);
		assertEquals (1, restaurantCategory.getIdr ());
		assertEquals (1, restaurantCategory.getIdct ());
	}
	
	@Test
	public void test2Add () {
		Restaurant_Category restaurantCategory01 = new Restaurant_Category ();
		restaurantCategory01.setIdr (2);
		restaurantCategory01.setIdct (2);
		RestaurantCategoryDAO.add (restaurantCategory01);
		
		Restaurant_Category restaurantCategory02 = RestaurantCategoryDAO.get (2, 2);
		
		assertEquals (2,restaurantCategory02.getIdr ());
		assertEquals (2,restaurantCategory02.getIdct ());
	}
	
	@Test
	public void test3Modify () {
		Restaurant_Category restaurantCategory01 = RestaurantCategoryDAO.get (2, 2);
		Restaurant_Category restaurantCategory02 = RestaurantCategoryDAO.get (2, 2);
		restaurantCategory02.setIdct (1);
		RestaurantCategoryDAO.update (restaurantCategory01, restaurantCategory02);
		
		Restaurant_Category restaurantCategory03 = RestaurantCategoryDAO.get (2, 1);
		assertEquals (2, restaurantCategory03.getIdr ());
		assertEquals (1, restaurantCategory03.getIdct ());
	}
	
	@Test
	public void test4Delete () {
		Restaurant_Category restaurantCategory01 = RestaurantCategoryDAO.get (2, 1);
		boolean result = RestaurantCategoryDAO.delete (2, 1);
		assertEquals (true, result);
		
		Restaurant_Category restaurantCategory02 = RestaurantCategoryDAO.get (2, 1);
		assertEquals (null, restaurantCategory02);
		
		ArrayList <Restaurant_Category> restaurantCategoryList = RestaurantCategoryDAO.getAll ();
		for (Restaurant_Category rc: restaurantCategoryList) {
			assertNotEquals (restaurantCategory01, rc);
		}	 
	}
}
