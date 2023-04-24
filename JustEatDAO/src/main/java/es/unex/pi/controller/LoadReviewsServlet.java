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

import es.unex.pi.util.Triplet;

import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Restaurant_User;
import es.unex.pi.model.User;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.Restaurant_UserDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_UserDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;

/**
 * Servlet implementation class LoadReviewsServlet
 */
public class LoadReviewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private long resID = -1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadReviewsServlet() {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		Restaurant_UserDAO restaurantUserDAO = new JDBCRestaurant_UserDAOImpl ();
		UserDAO userDAO = new JDBCUserDAOImpl ();
		
		User currentUser = (User) request.getSession ().getAttribute ("user");
		request.setAttribute ("currentUser", currentUser);
		
		restaurantDAO.setConnection (conn);
		resID = Long.parseLong (request.getParameter ("idR"));
		Restaurant currentRestaurant = restaurantDAO.get (resID);
		request.setAttribute ("restaurant", currentRestaurant);
		
		Triplet <User, Restaurant_User, Restaurant> avoid = null;
		Triplet <User, Restaurant_User, Restaurant> singleComment = null;
		restaurantUserDAO.setConnection (conn);
		userDAO.setConnection (conn);
		ArrayList <Restaurant_User> restaurantUserList = restaurantUserDAO.getAllByRestaurant (resID);
		ArrayList <Triplet <User, Restaurant_User, Restaurant>> triplet = new ArrayList <Triplet <User, Restaurant_User, Restaurant>> ();
		for (Restaurant_User ru: restaurantUserList) {
			singleComment = new Triplet <User, Restaurant_User, Restaurant> (userDAO.get (ru.getIdu ()), ru, restaurantDAO.get (ru.getIdr ()));
			if (ru.getIdu () == currentUser.getId ()) {
				avoid = singleComment;
			}
			triplet.add (singleComment);
		}
		request.setAttribute ("reviewList", triplet);
		
		if (triplet.contains (avoid)) {
			request.setAttribute ("addreview", false);
		}
		else {
			request.setAttribute ("addreview", true);
		}
		
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/LoadReviews.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*TODO Coger con el getParameter el ID del restaurante y el ID del usuario es que el está en sesión*/
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		Restaurant_UserDAO reviewBD = new JDBCRestaurant_UserDAOImpl();
		reviewBD.setConnection(conn);
		Restaurant_User newReview = new Restaurant_User();
		newReview.setIdr(resID);
		HttpSession sesion = request.getSession();
		User SessionUser = (User) sesion.getAttribute("user"); 
		newReview.setIdu(SessionUser.getId());
		String reviewText = request.getParameter("review");
		double grade = Double.parseDouble(request.getParameter("grade"));
		newReview.setReview(reviewText);
		newReview.setGrade(grade);
		reviewBD.add (newReview);
		
		RestaurantDAO resDAO = new JDBCRestaurantDAOImpl ();
		resDAO.setConnection (conn);
		Restaurant r = resDAO.get (resID);
		ArrayList <Restaurant_User> reviews = reviewBD.getAllByRestaurant (resID);
		double comments = 0.0;
		for (Restaurant_User ru: reviews) {
			comments += ru.getGrade ();
		}
		r.setGradesAverage (truncate (comments / reviews.size (), 1)); //Truncar con una cifra decimal
		resDAO.update (r);
		
		//Devolver a la página de reviews para ver que se ha añadido.
		response.sendRedirect (request.getContextPath () + "/LoadReviewsServlet.do?idR=" + request.getParameter ("idR"));
	}
	
	private double truncate (double number, int places) {
		number = number * Math.pow (10, places);
		number = Math.floor (number);
		number = number / Math.pow (10, places);
		return number;
	}
}
