package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import java.sql.Connection;

import es.unex.pi.model.Dish;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.User;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;

/**
 * Servlet implementation class UpdateDishesServlet
 */
public class UpdateDishesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private long restaurant = -1;
	private long dish = -1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDishesServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		
		restaurantDAO.setConnection (conn);
		this.restaurant = Long.parseLong (request.getParameter ("idR"));
		Restaurant currentRestaurant = restaurantDAO.get (this.restaurant);
		request.setAttribute ("restaurant", currentRestaurant);
		HttpSession session = request.getSession ();
		User currentUser = (User) session.getAttribute ("user");
		request.setAttribute ("user", currentUser);
		
		dishDAO.setConnection (conn);
		request.setAttribute ("dishList", dishDAO.getAllByRestaurant (currentRestaurant.getId ()));
		
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/UpdateDishes.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Actualiza la información de los platos en la Base de Datos
		 * Existe un atributo privado en esta clase que mantiene el ID del restaurante al que pertenece el plato.
		 */
		
		/*Actualizar los valores en la Base de Datos*/
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		this.dish = Long.parseLong (request.getParameter ("P"));
		DishDAO dishBD = new JDBCDishDAOImpl();
		RestaurantDAO resDAO = new JDBCRestaurantDAOImpl ();
		dishBD.setConnection (conn);
		resDAO.setConnection (conn);
		Dish currentDish = dishBD.get (this.dish);
		currentDish.setName(request.getParameter("name"));
		currentDish.setPrice(Integer.parseInt(request.getParameter("price")));
		currentDish.setDescription(request.getParameter("description"));
		
		
		String dishPic = request.getParameter("image");
		if(!dishPic.equals("")&& dishPic != null) {
			currentDish.setDishPic(dishPic);
		}else {
			currentDish.setDishPic ("default.png");
		}
		
		
		
		Restaurant res = resDAO.get (restaurant);
		if (res.getMinPrice () > currentDish.getPrice ()) {
			res.setMinPrice (currentDish.getPrice ());
		}
		if (res.getMaxPrice () < currentDish.getPrice ()) {
			res.setMaxPrice (currentDish.getPrice ());
		}
		resDAO.update (res);
		User currentUser = (User) request.getSession ().getAttribute ("user");
		
		dishBD.update(currentDish);
		response.sendRedirect (request.getContextPath () + "/UpdateDishesServlet.do?idR=" + this.restaurant + "&idU=" + currentUser.getId());
	}
}
