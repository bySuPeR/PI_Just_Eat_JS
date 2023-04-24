package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.unex.pi.model.Category;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import java.util.ArrayList;

@FixMethodOrder (MethodSorters.NAME_ASCENDING)

public class TestCategoryDAO {

	static DBConn dbConn;
	static CategoryDAO categoryDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		dbConn = new DBConn ();
		conn = dbConn.create ();
	    categoryDAO = new JDBCCategoryDAOImpl ();
		categoryDAO.setConnection (conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass () throws Exception {
		dbConn.destroy (conn);
	}

	@Before
	public void setUpBeforeMethod () throws Exception {}

	@Test
	public void test1BaseData () {
		Category category01 = categoryDAO.get (1);
		assertEquals (category01.getId (), 1);
		assertEquals(category01.getName (), "Burguer");
		assertEquals (category01.getDescription (), "Burguer restaurants");
		Category category11 = categoryDAO.get ("Burguer");
		assertEquals (category11.getId (), 1);
		assertEquals(category11.getName (), "Burguer");
		assertEquals (category11.getDescription (), "Burguer restaurants");
		
		Category category02 = categoryDAO.get (2);
		assertEquals (category02.getId (), 2);
		assertEquals(category02.getName (), "Oriental");
		assertEquals (category02.getDescription (), "Asian food");
		Category category12 = categoryDAO.get ("Oriental");
		assertEquals (category12.getId (), 2);
		assertEquals(category12.getName (), "Oriental");
		assertEquals (category12.getDescription (), "Asian food");
		
		Category category03 = categoryDAO.get (3);
		assertEquals (category03.getId (), 3);
		assertEquals(category03.getName (), "Italian");
		assertEquals (category03.getDescription (), "Italian food");
		Category category13 = categoryDAO.get ("Italian");
		assertEquals (category13.getId (), 3);
		assertEquals(category13.getName (), "Italian");
		assertEquals (category13.getDescription (), "Italian food");
		
		Category category04 = categoryDAO.get (4);
		assertEquals (category04.getId (), 4);
		assertEquals(category04.getName (), "Mexican");
		assertEquals (category04.getDescription (), "Mexican food");
		Category category14 = categoryDAO.get ("Mexican");
		assertEquals (category14.getId (), 4);
		assertEquals(category14.getName (), "Mexican");
		assertEquals (category14.getDescription (), "Mexican food");
		
		Category category05 = categoryDAO.get (5);
		assertEquals (category05.getId (), 5);
		assertEquals(category05.getName (), "American");
		assertEquals (category05.getDescription (), "American food");
		Category category15 = categoryDAO.get ("American");
		assertEquals (category15.getId (), 5);
		assertEquals(category15.getName (), "American");
		assertEquals (category15.getDescription (), "American food");
		
		Category category06 = categoryDAO.get (6);
		assertEquals (category06.getId (), 6);
		assertEquals(category06.getName (), "Pizza");
		assertEquals (category06.getDescription (), "Pizza food");
		Category category16 = categoryDAO.get ("Pizza");
		assertEquals (category16.getId (), 6);
		assertEquals(category16.getName (), "Pizza");
		assertEquals (category16.getDescription (), "Pizza food");
		
		Category category07 = categoryDAO.get (7);
		assertEquals (category07.getId (), 7);
		assertEquals(category07.getName (), "Deluxe");
		assertEquals (category07.getDescription (), "Michelin stars");
		Category category17 = categoryDAO.get ("Deluxe");
		assertEquals (category17.getId (), 7);
		assertEquals(category17.getName (), "Deluxe");
		assertEquals (category17.getDescription (), "Michelin stars");
		
		ArrayList <Category> categoriesList = categoryDAO.getAll ();
		assertEquals (categoriesList.size (), 7);
		
		ArrayList <Category> categoriesNameList = categoryDAO.getAllBySearchName ("an");
		assertEquals (categoriesNameList.size (), 3);
		
		Category cat = categoryDAO.get (10);
		assertEquals (cat, null);
	}
	
	@Test
	public void test2Add () {
		Category category01 = new Category ();
		category01.setName ("newCategory");
		category01.setDescription ("newDescription");
		long newID = categoryDAO.add (category01);
		
		Category category02 = categoryDAO.get (newID);
		assertEquals (newID, category02.getId ());
		assertEquals (category01.getName (), category02.getName());
		assertEquals (category01.getDescription (), category02.getDescription ());
		
		Category category03 = categoryDAO.get ("newCategory");
		assertEquals (newID, category03.getId ());
		assertEquals (category01.getName (), category03.getName());
		assertEquals (category01.getDescription (), category03.getDescription ());
	}
	
	@Test
	public void test3Modify () {
		Category category01 = categoryDAO.get ("newCategory");
		category01.setName ("newCategoryUpdated");
		boolean result = categoryDAO.update (category01);
		assertEquals (result, true);
		
		Category category02 = categoryDAO.get ("newCategoryUpdated");
		assertEquals (category01.getId (), category01.getId ());
		assertEquals (category01.getName (), category02.getName ());
		assertEquals (category01.getDescription (), category02.getDescription ());
	}
	
	@Test
	public void test4Delete () {
		 Category category01 = categoryDAO.get ("newCategoryUpdated");
		 boolean result = categoryDAO.delete (category01.getId ());
		 assertEquals (result, true);
		 
		 Category category02 = categoryDAO.get ("newCategoryUpdated");
 		 assertEquals (null, category02);
	}
}
