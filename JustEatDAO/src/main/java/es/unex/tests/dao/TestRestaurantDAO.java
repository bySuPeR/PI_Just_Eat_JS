package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.model.Restaurant;
import java.util.ArrayList;

import org.junit.Test;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestRestaurantDAO {

	static DBConn dbConn;
	static RestaurantDAO RestaurantDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		dbConn = new DBConn ();
		conn = dbConn.create ();
	    RestaurantDAO = new JDBCRestaurantDAOImpl ();
		RestaurantDAO.setConnection (conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass () throws Exception {
		dbConn.destroy (conn);
	}

	@Before
	public void setUpBeforeMethod () throws Exception {}

	@Test
	public void test1BaseData () {
		Restaurant restaurant01 = RestaurantDAO.get (1);
		assertEquals (restaurant01.getId (), 1);
		assertEquals (restaurant01.getName (), "Burguer King");
		assertEquals (restaurant01.getAddress (), "Gómez Becerra");
		assertEquals (restaurant01.getTelephone (), "666666666");
		assertEquals (restaurant01.getCity (), "Cáceres");
		assertEquals (restaurant01.getMinPrice (), 5);
		assertEquals (restaurant01.getContactEmail (), "info@burguerkingencasa.com");
		assertEquals (restaurant01.getMaxPrice (), 15);
		assertEquals (restaurant01.getBikeFriendly (), 1);
		assertEquals (restaurant01.getAvailable (), 1);
		assertEquals (restaurant01.getIdu (), 1);

		Restaurant restaurant02 = RestaurantDAO.get (2);
		assertEquals (restaurant02.getId (), 2);
		assertEquals (restaurant02.getName (), "Atrio");
		assertEquals (restaurant02.getAddress (), "Hotel Relaix Chateau Atrio");
		assertEquals (restaurant02.getTelephone (), "111111111");
		assertEquals (restaurant02.getCity (), "Cáceres");
		assertEquals (restaurant02.getMinPrice (), 150);
		assertEquals (restaurant02.getContactEmail (), "info@atriovinos.com");
		assertEquals (restaurant02.getMaxPrice (), 300);
		assertEquals (restaurant02.getBikeFriendly (), 0);
		assertEquals (restaurant02.getAvailable (), 1);
		assertEquals (restaurant02.getIdu (), 2);
		
		Restaurant restaurant03 = RestaurantDAO.get (3);
		assertEquals (restaurant03.getId (), 3);
		assertEquals (restaurant03.getName (), "Big House");
		assertEquals (restaurant03.getAddress (), "Ruta de la Plata");
		assertEquals (restaurant03.getTelephone (), "333333333");
		assertEquals (restaurant03.getCity (), "Cáceres");
		assertEquals (restaurant03.getMinPrice (), 10);
		assertEquals (restaurant03.getContactEmail (), "info@bighouse.com");
		assertEquals (restaurant03.getMaxPrice (), 100);
		assertEquals (restaurant03.getBikeFriendly (), 1);
		assertEquals (restaurant03.getAvailable (), 0);
		assertEquals (restaurant03.getIdu (), 2);
		
		Restaurant restaurant04 = RestaurantDAO.get (4);
		assertEquals (restaurant04.getId (), 4);
		assertEquals (restaurant04.getName (), "Ciao ciao");
		assertEquals (restaurant04.getAddress (), "Av. Isabel de Moctezuma");
		assertEquals (restaurant04.getTelephone (), "444444444");
		assertEquals (restaurant04.getCity (), "Cáceres");
		assertEquals (restaurant04.getMinPrice (), 8);
		assertEquals (restaurant04.getContactEmail (), "info@ciaociao.com");
		assertEquals (restaurant04.getMaxPrice (), 15);
		assertEquals (restaurant04.getBikeFriendly (), 1);
		assertEquals (restaurant04.getAvailable (), 1);
		assertEquals (restaurant04.getIdu (), 2);
		
		Restaurant restaurant05 = RestaurantDAO.get (5);
		assertEquals (restaurant05.getId (), 5);
		assertEquals (restaurant05.getName (), "Ms. Sushi");
		assertEquals (restaurant05.getAddress (), "Castellana");
		assertEquals (restaurant05.getTelephone (), "123123123");
		assertEquals (restaurant05.getCity (), "Madrid");
		assertEquals (restaurant05.getMinPrice (), 12);
		assertEquals (restaurant05.getContactEmail (), "info@mssushi.com");
		assertEquals (restaurant05.getMaxPrice (), 40);
		assertEquals (restaurant05.getBikeFriendly (), 0);
		assertEquals (restaurant05.getAvailable (), 1);
		assertEquals (restaurant05.getIdu (), 3);
		
		ArrayList <Restaurant> restaurantsList = RestaurantDAO.getAll ();
		assertEquals (restaurantsList.size (), 5);
		
		ArrayList <Restaurant> restaurantsNameList = RestaurantDAO.searchAmongRestaurants ("r");
		assertEquals (restaurantsNameList.size (), 2);
		
		ArrayList <Restaurant> restaurantsUserList = RestaurantDAO.getAllByUser (2);
		assertEquals (restaurantsUserList.size (), 3);
	}
	
	@Test
	public void test2Add () {
		Restaurant restaurant01 = new Restaurant ();
		restaurant01.setName ("newRestaurantName");
		restaurant01.setAddress ("newRestaurantAddress");
		restaurant01.setTelephone ("newRestaurantTelephone");
		restaurant01.setGradesAverage (-1.0);
		restaurant01.setCity ("newRestaurantCity");
		restaurant01.setMinPrice (-1);
		restaurant01.setContactEmail ("newRestaurantEmail");
		restaurant01.setMaxPrice (-1);
		restaurant01.setBikeFriendly (0);
		restaurant01.setAvailable (0);
		restaurant01.setIdu (1);
		long newID = RestaurantDAO.add (restaurant01);
		
		Restaurant restaurant02 = RestaurantDAO.get (newID);
		assertEquals (restaurant02.getId (), newID);
		assertEquals (restaurant02.getName (), "newRestaurantName");
		assertEquals (restaurant02.getAddress (), "newRestaurantAddress");
		assertEquals (restaurant02.getTelephone (), "newRestaurantTelephone");
		assertEquals (restaurant02.getCity (), "newRestaurantCity");
		assertEquals (restaurant02.getMinPrice (), -1);
		assertEquals (restaurant02.getContactEmail (), "newRestaurantEmail");
		assertEquals (restaurant02.getMaxPrice (), -1);
		assertEquals (restaurant02.getBikeFriendly (), 0);
		assertEquals (restaurant02.getAvailable (), 0);
		assertEquals (restaurant02.getIdu (), 1);
	}
	
	@Test
	public void test3Modify () {
		Restaurant restaurant01 = RestaurantDAO.searchAmongRestaurants ("newRestaurantName").iterator ().next ();
		restaurant01.setName ("newRestaurantNameUpdated");
		boolean result = RestaurantDAO.update (restaurant01);
		assertEquals (true, result);
		
		Restaurant restaurant02 = RestaurantDAO.searchAmongRestaurants ("newRestaurantNameUpdated").iterator ().next ();
		assertEquals (restaurant01.getId (), restaurant02.getId ());
		assertEquals ("newRestaurantNameUpdated", restaurant02.getName ());
		assertEquals (restaurant01.getAddress (), restaurant02.getAddress ());
		assertEquals (restaurant01.getTelephone (), restaurant02.getTelephone ());
		assertEquals (restaurant01.getGradesAverage (), restaurant02.getGradesAverage ());
		assertEquals (restaurant01.getCity (), restaurant02.getCity ());
		assertEquals (restaurant01.getMinPrice (), restaurant02.getMinPrice ());
		assertEquals (restaurant01.getContactEmail (), restaurant02.getContactEmail ());
		assertEquals (restaurant01.getMaxPrice (), restaurant02.getMaxPrice ());
		assertEquals (restaurant01.getBikeFriendly (), restaurant02.getBikeFriendly ());
		assertEquals (restaurant01.getAvailable (), restaurant02.getAvailable ());
		assertEquals (restaurant01.getIdu (), restaurant02.getIdu ());
	}
	
	@Test
	public void test4Delete () {
		Restaurant restaurant01 = RestaurantDAO.searchAmongRestaurants ("newRestaurantNameUpdated").iterator ().next ();
		boolean result = RestaurantDAO.delete (restaurant01.getId ());
		assertEquals (true, result);
		
		ArrayList <Restaurant> restaurantList = RestaurantDAO.searchAmongRestaurants ("newRestaurantNameUpdated");
		assertEquals (restaurantList.size (), 0);
	}
}
