package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.unex.pi.model.User;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestUserDAO {

	static DBConn dbConn;
	static UserDAO UserDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    UserDAO = new JDBCUserDAOImpl();
		UserDAO.setConnection(conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dbConn.destroy(conn);   
	}

	@Before
	public void setUpBeforeMethod() throws Exception {}

	@Test
	public void test1BaseData() {
		User user01 = UserDAO.get (1);
		assertEquals (user01.getId (), 1);
		assertEquals (user01.getEmail (), "cc@cc.com");
		assertEquals (user01.getPassword (), "cc123");
		assertEquals (user01.getName (), "Chema");
		assertEquals (user01.getSurname (), "Conejero");
		User user11 = UserDAO.get ("cc@cc.com");
		assertEquals (user11.getId (), 1);
		assertEquals (user11.getEmail (), "cc@cc.com");
		assertEquals (user11.getPassword (), "cc123");
		assertEquals (user11.getName (), "Chema");
		assertEquals (user11.getSurname (), "Conejero");
		
		User user02 = UserDAO.get (2);
		assertEquals (user02.getId (), 2);
		assertEquals (user02.getEmail (), "tt@tt.com");
		assertEquals (user02.getPassword (), "tt123");
		assertEquals (user02.getName (), "Tote");
		assertEquals (user02.getSurname (), "Sancho");
		User user12 = UserDAO.get ("tt@tt.com");
		assertEquals (user12.getId (), 2);
		assertEquals (user12.getEmail (), "tt@tt.com");
		assertEquals (user12.getPassword (), "tt123");
		assertEquals (user12.getName (), "Tote");
		assertEquals (user12.getSurname (), "Sancho");
		
		User user03 = UserDAO.get (3);
		assertEquals (user03.getId (), 3);
		assertEquals (user03.getEmail (), "pp@pp.com");
		assertEquals (user03.getPassword (), "pp123");
		assertEquals (user03.getName (), "Toño");
		assertEquals (user03.getSurname (), "Pérez");
		User user13 = UserDAO.get ("pp@pp.com");
		assertEquals (user13.getId (), 3);
		assertEquals (user13.getEmail (), "pp@pp.com");
		assertEquals (user13.getPassword (), "pp123");
		assertEquals (user13.getName (), "Toño");
		assertEquals (user13.getSurname (), "Pérez");
		
		ArrayList <User> userList = UserDAO.getAll ();
		assertEquals (3, userList.size ());
	}
	
	@Test
	public void test2Add(){
		User user01 = new User ();
		user01.setEmail ("newUserEmail");
		user01.setPassword ("newUserPassword");
		user01.setName ("newUserName");
		user01.setSurname ("newUserSurname");
		long newID = UserDAO.add (user01);
		
		User user02 = UserDAO.get (newID);
		assertEquals (user02.getId (), newID);
		assertEquals (user02.getEmail (), "newUserEmail");
		assertEquals (user02.getPassword (), "newUserPassword");
		assertEquals (user02.getName (), "newUserName");
		assertEquals (user02.getSurname (), "newUserSurname");
		
		User user03 = UserDAO.get ("newUserEmail");
		assertEquals (user03.getId (), newID);
		assertEquals (user03.getEmail (), "newUserEmail");
		assertEquals (user03.getPassword (), "newUserPassword");
		assertEquals (user03.getName (), "newUserName");
		assertEquals (user03.getSurname (), "newUserSurname");
	}
	
	@Test
	public void test3Modify(){
		User user01 = UserDAO.get ("newUserEmail");
		user01.setEmail ("newUserEmailUpdated");
		boolean result = UserDAO.update (user01);
		assertEquals (true, result);
		
		User user02 = UserDAO.get ("newUserEmailUpdated");
		assertEquals (user01.getId (), user02.getId ());
		assertEquals (user01.getEmail (), user02.getEmail ());
		assertEquals (user01.getPassword (), user02.getPassword ());
		assertEquals (user01.getName (), user02.getName ());
		assertEquals (user01.getSurname (), user02.getSurname ());
	}
	
	@Test
	public void test4Delete(){
		User user01 = UserDAO.get ("newUserEmailUpdated");
		boolean result = UserDAO.delete (user01.getId ());
		assertEquals (true, result);
		
		User user02 = UserDAO.get ("newUserEmailUpdated");
		assertEquals (null, user02);
	}
}
