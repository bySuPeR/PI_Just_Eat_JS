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
import java.util.HashMap;

import es.unex.pi.model.Restaurant;
import es.unex.pi.model.User;
import es.unex.pi.model.Category;
import es.unex.pi.model.Favorito;
import es.unex.pi.model.Restaurant_Category;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.FavoritoDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_CategoryDAOImpl;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCFavoritoDAOImpl;

/**
 * Servlet implementation class RestaurantDetailsServlet
 */
public class RestaurantDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Restaurant currentRestaurant;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantDetailsServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl ();
		Restaurant_CategoryDAO restaurantCategoryDAO = new JDBCRestaurant_CategoryDAOImpl ();
		restaurantDAO.setConnection (conn);
		restaurantCategoryDAO.setConnection (conn);
		categoryDAO.setConnection (conn);
		
		currentRestaurant = restaurantDAO.get (Long.parseLong (request.getParameter ("id")));
		request.setAttribute ("restaurant", currentRestaurant);
		User currentUser = (User) request.getSession ().getAttribute ("user");
		request.setAttribute ("currentUser", currentUser);
		
		FavoritoDAO favoritoBD = new JDBCFavoritoDAOImpl();
		favoritoBD.setConnection(conn);
		Favorito fav = favoritoBD.get(currentRestaurant.getId(),currentUser.getId());
		if(fav == null) {
			request.setAttribute ("like", "emptyH");
		}
		
		dishDAO.setConnection (conn);
		request.setAttribute ("dishList", dishDAO.getAllByRestaurant (currentRestaurant.getId ()));
		
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/Res_details.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn"); // conectar con bd
		FavoritoDAO favoritoBD = new JDBCFavoritoDAOImpl();
		favoritoBD.setConnection(conn);
		HttpSession sesion = request.getSession();
		User usuario = (User) sesion.getAttribute("user");
		Favorito fav = favoritoBD.get(currentRestaurant.getId(),usuario.getId());
		if(fav!=null) {
			favoritoBD.delete(currentRestaurant.getId(), usuario.getId());
		}
		else {
			Favorito favorito = new Favorito();
			favorito.setidRestaurant(currentRestaurant.getId());
			favorito.setidUser(usuario.getId());
			favoritoBD.add(favorito);
		}
		
		response.sendRedirect(request.getContextPath() + "/RestaurantDetailsServlet.do?id="+ currentRestaurant.getId());
		
		
		
	}
}
