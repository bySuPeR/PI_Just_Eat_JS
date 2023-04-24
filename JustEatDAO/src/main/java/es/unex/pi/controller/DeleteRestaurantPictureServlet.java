package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Restaurant;

/**
 * Servlet implementation class DeleteRestaurantPictureServlet
 */
public class DeleteRestaurantPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private long restaurantID=-1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRestaurantPictureServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		restaurantID = Long.parseLong (request.getParameter ("idR"));
		
		RestaurantDAO restDAO = new JDBCRestaurantDAOImpl ();
		restDAO.setConnection (conn);
		Restaurant rest = new Restaurant();
		rest = restDAO.get(restaurantID);
		rest.setRestaurantPic("default.png");
		restDAO.update(rest);
		
		response.sendRedirect (request.getContextPath () + "/MyRestaurantsServlet.do");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
