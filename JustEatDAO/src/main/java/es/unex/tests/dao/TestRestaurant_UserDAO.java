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

import es.unex.pi.dao.Restaurant_UserDAO;
import es.unex.pi.dao.JDBCRestaurant_UserDAOImpl;
import es.unex.pi.model.Restaurant_User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestRestaurant_UserDAO {

	static DBConn dbConn;
	static Restaurant_UserDAO RestaurantUserDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		dbConn = new DBConn ();
		conn = dbConn.create ();
	    RestaurantUserDAO = new JDBCRestaurant_UserDAOImpl ();
		RestaurantUserDAO.setConnection (conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass () throws Exception {
		dbConn.destroy (conn);
	}

	@Before
	public void setUpBeforeMethod () throws Exception {}

	@Test
	public void test1BaseData() {
		ArrayList <Restaurant_User> restaurantUserList = RestaurantUserDAO.getAll ();
		assertEquals (3, restaurantUserList.size ());
		
		restaurantUserList = RestaurantUserDAO.getAllByUser (1);
		assertEquals (1, restaurantUserList.size ());
		for (Restaurant_User ru: restaurantUserList) {
			assertEquals (1, ru.getIdu ());
		}
		
		restaurantUserList = RestaurantUserDAO.getAllByRestaurant (1);
		assertEquals (1, restaurantUserList.size ());
		for (Restaurant_User ru: restaurantUserList) {
			assertEquals (1, ru.getIdr ());
		}
		
		Restaurant_User restaurantUser = RestaurantUserDAO.get (1, 3);
		assertEquals (1, restaurantUser.getIdr ());
		assertEquals (3, restaurantUser.getIdu ());
		assertEquals ("Mejores hamburguesas de la ciudad", restaurantUser.getReview ());			
	}
	
	@Test
	public void test2Add () {
		Restaurant_User restaurantUser01 = new Restaurant_User ();
		restaurantUser01.setIdr (5);
		restaurantUser01.setIdu (3);
		restaurantUser01.setReview ("newRestaurantUserReview");
		restaurantUser01.setGrade (-1.0);
		boolean result = RestaurantUserDAO.add (restaurantUser01);
		assertEquals (true, result);
		
		Restaurant_User restaurantUser02 = RestaurantUserDAO.get (5, 3);
		assertEquals (restaurantUser01.getIdr (), restaurantUser02.getIdr ());
		assertEquals (restaurantUser01.getIdu (), restaurantUser02.getIdu ());
		assertEquals (restaurantUser01.getReview (), restaurantUser02.getReview ());
		System.out.println (restaurantUser01.getGrade () + ", " + restaurantUser02.getGrade ());
	}
	
	@Test
	public void test3Modify () {
		Restaurant_User restaurantUser01 = RestaurantUserDAO.get (5, 3);
		restaurantUser01.setReview ("newRestaurantUserReviewUpdated");
		boolean result = RestaurantUserDAO.update (restaurantUser01);
		assertEquals (true, result);
		
		Restaurant_User restaurantUser02 = RestaurantUserDAO.get (5, 3);
		assertEquals (restaurantUser01.getIdr (), restaurantUser02.getIdr ());
		assertEquals (restaurantUser01.getIdu (), restaurantUser02.getIdu ());
		assertEquals (restaurantUser01.getReview (), restaurantUser02.getReview ());
		System.out.println (restaurantUser01.getGrade () + ", " + restaurantUser02.getGrade ());
	}
	
	@Test
	public void test4Delete () {
		Restaurant_User restaurantUser01 = RestaurantUserDAO.get (5, 3);
		boolean result = RestaurantUserDAO.delete (5, 3);
		assertEquals (true, result);
		
		Restaurant_User restaurantUser02 = RestaurantUserDAO.get (5, 3);
		assertEquals (null, restaurantUser02);
		
		ArrayList <Restaurant_User> restaurantUserList = RestaurantUserDAO.getAll ();
		for (Restaurant_User ru: restaurantUserList) {
			assertNotEquals (restaurantUser01, ru);
		}
	}
}
