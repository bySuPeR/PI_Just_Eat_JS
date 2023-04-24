package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.Connection;
import java.util.ArrayList;

import es.unex.pi.model.User;
import es.unex.pi.model.Order_Dish;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Dish;
import es.unex.pi.dao.OrderDAO;
import es.unex.pi.dao.Order_DishDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.JDBCOrderDAOImpl;
import es.unex.pi.dao.JDBCOrder_DishDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;

/**
 * Servlet implementation class MyOrdersServlet
 */
public class MyOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyOrdersServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		OrderDAO orderDAO = new JDBCOrderDAOImpl ();
		
		HttpSession session = request.getSession ();
		User currentUser = (User) session.getAttribute ("user");
		
		orderDAO.setConnection (conn);
		request.setAttribute ("orderList", orderDAO.getAllByUser (currentUser.getId ()));
		
    	request.setAttribute ("user", currentUser);
		
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/MyOrders.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		OrderDAO orderDAO = new JDBCOrderDAOImpl ();
		Order_DishDAO orderDishDAO = new JDBCOrder_DishDAOImpl ();
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		orderDAO.setConnection (conn);
		orderDishDAO.setConnection (conn);
		dishDAO.setConnection (conn);
		restaurantDAO.setConnection (conn);
		Dish dish = new Dish();
		long idRestaurant=-1;
		String restaurant="";
		Restaurant rest = new Restaurant(); 
		
		ArrayList <Order_Dish> orderDishList = orderDishDAO.getAllByOrder (Long.parseLong (request.getParameter ("Or")));
		ArrayList <Dish> dishList = new ArrayList <Dish> ();
		for (Order_Dish od: orderDishList) {
			dish = dishDAO.get (od.getIddi ());
			dishList.add (dish);
			idRestaurant=dish.getIdr();
			rest = restaurantDAO.get(idRestaurant);
			restaurant=rest.getName();
		}
		request.setAttribute ("dishList", dishList);
		request.setAttribute ("restaurant", restaurant);
		
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/CheckOrder.jsp");
		view.forward (request, response);
	}
}
