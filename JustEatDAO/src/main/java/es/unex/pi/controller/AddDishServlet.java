package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Restaurant;

/**
 * Servlet implementation class AddDishServlet
 */
public class AddDishServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private long restaurant = -1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDishServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.restaurant = Long.parseLong (request.getParameter ("idR"));
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/AddDish.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		DishDAO dishBD = new JDBCDishDAOImpl(); // base de datos
		RestaurantDAO resDAO = new JDBCRestaurantDAOImpl ();
		resDAO.setConnection(conn);
		dishBD.setConnection(conn);
		
		String name = request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		String description = request.getParameter("description");
		
		Dish dish = new Dish();
		dish.setName(name);
		dish.setPrice(price);
		dish.setDescription(description);
		dish.setIdr(restaurant);
		
		

		String dishPic = request.getParameter("image");
		if(!dishPic.equals("")&& dishPic != null) {
			dish.setDishPic(dishPic);
		}else {
			dish.setDishPic ("default.png");
		}
		
		
		
		
		dishBD.add(dish);
		
		Restaurant res = resDAO.get (restaurant);
		if (res.getMinPrice () > dish.getPrice ()) {
			res.setMinPrice (dish.getPrice ());
		}
		if (res.getMaxPrice () < dish.getPrice ()) {
			res.setMaxPrice (dish.getPrice ());
		}
		resDAO.update (res);
		
		response.sendRedirect(request.getContextPath() + "/UpdateDishesServlet.do?idR=" + restaurant);
	}
}

