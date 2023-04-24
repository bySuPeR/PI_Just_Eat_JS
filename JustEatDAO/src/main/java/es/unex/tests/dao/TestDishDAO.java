package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.unex.pi.model.Dish;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestDishDAO {

	static DBConn dbConn;
	static DishDAO dishDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		dbConn = new DBConn ();
		conn = dbConn.create ();
	    dishDAO = new JDBCDishDAOImpl ();
		dishDAO.setConnection (conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass () throws Exception {
		dbConn.destroy (conn);
	}

	@Before
	public void setUpBeforeMethod () throws Exception {}

	@Test
	public void test1BaseData () {
		Dish dish01 = dishDAO.get (1);
		assertEquals (dish01.getId (), 1);
		assertEquals (dish01.getName (), "Whopper");
		assertEquals (dish01.getPrice (), 5);
		assertEquals (dish01.getDescription (), "Whopper burguer");
		assertEquals (dish01.getIdr (), 3);
		Dish dish11 = dishDAO.get ("Whopper");
		assertEquals (dish11.getId (), 1);
		assertEquals (dish11.getName (), "Whopper");
		assertEquals (dish11.getPrice (), 5);
		assertEquals (dish11.getDescription (), "Whopper burguer");
		assertEquals (dish11.getIdr (), 3);
		
		Dish dish02 = dishDAO.get (2);
		assertEquals (dish02.getId (), 2);
		assertEquals (dish02.getName (), "Arroz frito");
		assertEquals (dish02.getPrice (), 6);
		assertEquals (dish02.getDescription (), "Arroz de la casa");
		assertEquals (dish02.getIdr (), 1);
		Dish dish12 = dishDAO.get ("Arroz frito");
		assertEquals (dish12.getId (), 2);
		assertEquals (dish12.getName (), "Arroz frito");
		assertEquals (dish12.getPrice (), 6);
		assertEquals (dish12.getDescription (), "Arroz de la casa");
		assertEquals (dish12.getIdr (), 1);
		
		Dish dish03 = dishDAO.get (3);
		assertEquals (dish03.getId (), 3);
		assertEquals (dish03.getName (), "Careta de cerdo");
		assertEquals (dish03.getPrice (), 40);
		assertEquals (dish03.getDescription (), "Delicia de cerdo ibérico");
		assertEquals (dish03.getIdr (), 1);
		Dish dish13 = dishDAO.get ("Careta de cerdo");
		assertEquals (dish13.getId (), 3);
		assertEquals (dish13.getName (), "Careta de cerdo");
		assertEquals (dish13.getPrice (), 40);
		assertEquals (dish13.getDescription (), "Delicia de cerdo ibérico");
		assertEquals (dish13.getIdr (), 1);
		
		ArrayList <Dish> dishesList = dishDAO.getAll ();
		assertEquals (dishesList.size (), 3);
		
		ArrayList <Dish> dishesNameList = dishDAO.getAllBySearchName ("o");
		assertEquals (dishesNameList.size (), 3);
		
		Dish d = dishDAO.get (10);
		assertEquals (d, null);
	}
	
	
	@Test
	public void test2Add () {
		Dish dish01 = new Dish ();
		dish01.setName ("newDish");
		dish01.setPrice (-1);
		dish01.setDescription ("newDescription");
		dish01.setIdr (1);
		long newID = dishDAO.add (dish01);
		
		Dish dish02 = dishDAO.get (newID);
		assertEquals (newID, dish02.getId ());
		assertEquals (dish01.getName (), dish02.getName ());
		assertEquals (dish01.getPrice (), dish02.getPrice ());
		assertEquals (dish01.getDescription (), dish02.getDescription ());
		assertEquals (dish01.getIdr (), dish02.getIdr ());
		
		Dish dish03 = dishDAO.get ("newDish");
		assertEquals (newID, dish03.getId ());
		assertEquals (dish01.getName (), dish03.getName ());
		assertEquals (dish01.getPrice (), dish03.getPrice ());
		assertEquals (dish01.getDescription (), dish03.getDescription ());
		assertEquals (dish01.getIdr (), dish03.getIdr ());
	}
	
	@Test
	public void test3Modify () {
		Dish dish01 = dishDAO.get ("newDish");
		dish01.setName ("newDishUpdated");
		boolean result = dishDAO.update (dish01);
		assertEquals (result, true);
		
		Dish dish02 = dishDAO.get ("newDishUpdated");
		assertEquals (dish01.getId (), dish02.getId ());
		assertEquals (dish01.getName (), dish02.getName ());
		assertEquals (dish01.getPrice (), dish02.getPrice ());
		assertEquals (dish01.getDescription (), dish02.getDescription ());
		assertEquals (dish01.getIdr (), dish02.getIdr ());
	}
	
	@Test
	public void test4Delete(){
		 Dish dish01 = dishDAO.get ("newDishUpdated");
		 boolean result = dishDAO.delete (dish01.getId ());
		 assertEquals (result, true);
		 
		 Dish dish02 = dishDAO.get ("newDishUpdated");
		 assertEquals (dish02, null);
	}
}
