package es.unex.giiis.pi.listener;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Logger;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;



/**
 * Application Lifecycle Listener implementation class ServletContextListener
 *
 */

@WebListener
public class ServletContextListener implements jakarta.servlet.ServletContextListener {

	
	private static final Logger logger = Logger.getLogger(ServletContextListener.class.getName());
	
	
	public void contextInitialized(ServletContextEvent event) {

		
		logger.info("Connecting DB");
		Connection conn = null;
		
		try {
          Class.forName("org.sqlite.JDBC");
          String dbURL = "jdbc:sqlite:file:"+System.getProperty("user.home")+"/sqlite_dbs/pizzeria.db";
          conn = DriverManager.getConnection(dbURL);
          if (conn != null) {
        	  logger.info("Connected to the database");
              DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
              logger.info("Driver name: " + dm.getDriverName());
              logger.info("Driver version: " + dm.getDriverVersion());
              logger.info("Product name: " + dm.getDatabaseProductName());
              logger.info("Product version: " + dm.getDatabaseProductVersion());
              ServletContext sc = event.getServletContext();
              sc.setAttribute("dbConn", conn);
          }
      } catch (ClassNotFoundException ex) {
          ex.printStackTrace();
      } catch (SQLException ex) {
          ex.printStackTrace();
      }
		
		logger.info("DB connected");

		
	}
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
		
		logger.info("Destroying DB");
		try {
			logger.info("DB shutdown start");
			//Descomentar si se se va a usar un Listener para destruir la conexi�n:
	   		ServletContext sc = arg0.getServletContext();
	   		Connection conn = (Connection) sc.getAttribute("dbConn");
			conn.close();
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				logger.info("DB deregistering drivers");
				Driver driver = drivers.nextElement();
				try {
					DriverManager.deregisterDriver(driver);
					logger.info(String.format("deregistering jdbc driver: %s", driver));
				} catch (SQLException e) {
					logger.severe(String.format("Error deregistering driver %s %s", driver, e));
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("DB destroyed");
	}
	
}

