package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import java.sql.Connection;
import java.util.ArrayList;

import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.User;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCCategoryDAOImpl;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
    	RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
    	CategoryDAO categoryDAO = new JDBCCategoryDAOImpl ();
    	
    	restaurantDAO.setConnection (conn);
    	request.setAttribute ("restaurantList", restaurantDAO.getAll ());
    	request.setAttribute ("allrestaurants", "checked");
    	
    	categoryDAO.setConnection (conn);
    	request.setAttribute ("categoryList", categoryDAO.getAll ());
    	
    	HttpSession session = request.getSession ();
    	User currentUser = (User) session.getAttribute ("user");
    	request.setAttribute ("user", currentUser);
    	
    	RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/Search.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//cargar los resultados de las busquedas (formularios,modificados....)
				Connection conn = (Connection) getServletContext().getAttribute("dbConn"); //conectar con bd
				RestaurantDAO rest = new JDBCRestaurantDAOImpl();
				rest.setConnection(conn);//establecer conexion con la BD
				String search = request.getParameter("search"); //guardar lo del input
				CategoryDAO categoryDAO = new JDBCCategoryDAOImpl ();
				categoryDAO.setConnection (conn);
		    	request.setAttribute ("categoryList", categoryDAO.getAll ());
				ArrayList<Restaurant> RestaurantListBD = rest.searchAmongRestaurants(search); //lista de apartamentos de la BD con los parametro busqueda
				request.setAttribute("restaurantList", RestaurantListBD);
				RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/Search.jsp");
				view.forward (request, response);
			}
}