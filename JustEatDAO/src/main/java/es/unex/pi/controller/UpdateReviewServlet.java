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

import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Restaurant_User;
import es.unex.pi.model.User;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.Restaurant_UserDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_UserDAOImpl;

/**
 * Servlet implementation class UpdateReviewServlet
 */
public class UpdateReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private long currentRestaurantID = -1;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateReviewServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		Restaurant_UserDAO restaurantUserDAO = new JDBCRestaurant_UserDAOImpl ();
		restaurantDAO.setConnection (conn);
		restaurantUserDAO.setConnection (conn);
		
		currentRestaurantID = Long.parseLong (request.getParameter ("idR"));
		request.setAttribute ("restaurant", restaurantDAO.get (currentRestaurantID));
		User currentUser = (User) request.getSession ().getAttribute ("user");
		
		request.setAttribute ("comment", restaurantUserDAO.get (currentRestaurantID, currentUser.getId ()));
		request.setAttribute ("user", currentUser);
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/UpdateReview.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Actualiza la review. En la URL está el ID del restaurante al que pertenece la review
		 * y el usuario que la hace es el que tiene la sesión.
		 */
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		Restaurant_UserDAO reviewBD = new JDBCRestaurant_UserDAOImpl();
		reviewBD.setConnection(conn);
		HttpSession sesion = request.getSession();
		User SessionUser = (User) sesion.getAttribute("user"); 
		Restaurant_User reviewUpdated =reviewBD.get(currentRestaurantID, SessionUser.getId());
		String reviewText = request.getParameter("review");
		double grade = Double.parseDouble(request.getParameter("grade"));
		reviewUpdated.setReview(reviewText);
		reviewUpdated.setGrade(grade);
		reviewBD.update(reviewUpdated);
		
		RestaurantDAO resDAO = new JDBCRestaurantDAOImpl ();
		resDAO.setConnection (conn);
		Restaurant r = resDAO.get (currentRestaurantID);
		ArrayList <Restaurant_User> reviews = reviewBD.getAllByRestaurant (currentRestaurantID);
		double comments = 0.0;
		for (Restaurant_User ru: reviews) {
			comments += ru.getGrade ();
		}
		r.setGradesAverage (truncate (comments / reviews.size (), 1));
		resDAO.update (r);
		
		//Por el momento, vuelve a la página de los detalles del restaurante.
	
	//Tras actualizar, vuelve a cargar las reviews del restaurante.
		response.sendRedirect (request.getContextPath () + "/LoadReviewsServlet.do?idR=" + request.getParameter ("idR"));
	}
	
	private double truncate (double number, int places) {
		number = number * Math.pow (10, places);
		number = Math.floor (number);
		number = number / Math.pow (10, places);
		return number;
	}
}
