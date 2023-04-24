package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import java.sql.Connection;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Order;
import es.unex.pi.model.Order_Dish;
import es.unex.pi.model.User;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.OrderDAO;
import es.unex.pi.dao.Order_DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCOrderDAOImpl;
import es.unex.pi.dao.JDBCOrder_DishDAOImpl;

/**
 * Servlet implementation class MakeOrderServlet
 */
public class MakeOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private long idRestaurant=-1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeOrderServlet () {
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
		dishDAO.setConnection (conn);
		idRestaurant = Long.parseLong (request.getParameter ("id"));
		request.setAttribute ("dishList", dishDAO.getAllByRestaurant (idRestaurant));
		request.setAttribute ("resID", idRestaurant);
		RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/NewOrder.jsp");
		vista.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		OrderDAO orderBD = new JDBCOrderDAOImpl(); // base de datos
		orderBD.setConnection(conn);
		Order newOrder = new Order();
		HttpSession sesion = request.getSession();
		User user = (User) sesion.getAttribute("user");
		int totalPrice=0;
		newOrder.setTotalPrice(totalPrice);
		newOrder.setIdu(user.getId());
		long idOrder = orderBD.add(newOrder);
	
		String[] idDished = request.getParameterValues("selectedDish");
		if (idDished != null) {
			DishDAO dishBD = new JDBCDishDAOImpl();
			dishBD.setConnection(conn);
			Dish dish = new Dish();
			Order_DishDAO orDishBD = new JDBCOrder_DishDAOImpl();
			orDishBD.setConnection(conn);
			Order_Dish newOrderDish = new Order_Dish();
			long idDish=-1;
			for(int i=0;i< idDished.length ;i++) {
				idDish = Long.parseLong(idDished[i]);
				System.out.println("idDish: " + idDish);
				dish = dishBD.get(idDish);
				newOrderDish.setIddi(idDish);
				newOrderDish.setIdo(idOrder);
				System.out.println("idOrder: " + idOrder);
				orDishBD.add(newOrderDish);
				totalPrice=totalPrice+ dish.getPrice(); 
				System.out.println("Total: " + totalPrice);
			}
			newOrder.setTotalPrice(totalPrice);
			System.out.println("Total introducido: " + totalPrice);
			newOrder.setId(idOrder);
			orderBD.update(newOrder);
		
		  	HttpSession session = request.getSession ();
	    	User currentUser = (User) session.getAttribute ("user");
	    	request.setAttribute ("user", currentUser);
	    	
			
			response.sendRedirect (request.getContextPath () + "/MyOrdersServlet.do?id=" + currentUser.getId());
		}
		else {
			String error = "No se ha escogido ningún plato para este pedido";
			request.setAttribute("error", error);
			doGet(request, response);
		}
		
	}
}
