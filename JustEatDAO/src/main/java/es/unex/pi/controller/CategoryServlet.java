package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Restaurant_Category;
import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_CategoryDAOImpl;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute("dbConn"); //conectar con bd
		CategoryDAO catDAO = new JDBCCategoryDAOImpl (); //objeto tipo Hosting con implementacion
		RestaurantDAO resDAO = new JDBCRestaurantDAOImpl ();
		Restaurant_CategoryDAO resCatDAO = new JDBCRestaurant_CategoryDAOImpl ();
		
		catDAO.setConnection (conn);//establecer conexion con la BD
		resDAO.setConnection (conn);
		resCatDAO.setConnection (conn);
		ArrayList <Restaurant_Category> resCatList = resCatDAO.getAllByCategory (Long.parseLong (request.getParameter ("idC")));
		ArrayList <Restaurant> resList = new ArrayList <Restaurant> ();
		for (Restaurant_Category rc: resCatList) {
			resList.add (resDAO.get (rc.getIdr ()));
		}
		request.setAttribute ("restaurantList", resList);
		
		//Recarga la página para ver los resultados.
		request.setAttribute ("categoryList", catDAO.getAll ());
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/Search.jsp");
		view.forward (request, response);
	}
}
