package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Category;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Favorito;
import es.unex.pi.model.Order;
import es.unex.pi.model.Order_Dish;
import es.unex.pi.model.Restaurant_Category;
import es.unex.pi.model.User;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.dao.FavoritoDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_CategoryDAOImpl;
import es.unex.pi.dao.OrderDAO;
import es.unex.pi.dao.Order_DishDAO;
import es.unex.pi.dao.JDBCFavoritoDAOImpl;
import es.unex.pi.dao.JDBCOrderDAOImpl;
import es.unex.pi.dao.JDBCOrder_DishDAOImpl;
import es.unex.pi.util.RestaurantComparator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path ("/MyOrders")
public class OrderResource {
	@Context
	ServletContext sc;
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Order> getMyOrders(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		OrderDAO orderBD = new JDBCOrderDAOImpl();
		orderBD.setConnection(conn);
		return orderBD.getAllByUser(currentUser.getId());
	}	
	@GET
	@Path("/checkOrder/{orderID}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Dish> getOrderDishes(@Context HttpServletRequest request, @PathParam ("orderID") long orderID) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		OrderDAO orderBD = new JDBCOrderDAOImpl ();
		Order_DishDAO orderDishBD = new JDBCOrder_DishDAOImpl ();
		DishDAO dishBD = new JDBCDishDAOImpl ();
		RestaurantDAO restaurantBD = new JDBCRestaurantDAOImpl ();
		orderBD.setConnection (conn);
		orderDishBD.setConnection (conn);
		dishBD.setConnection (conn);
		restaurantBD.setConnection (conn);
		
		Dish dish = new Dish(); 
		
		ArrayList <Order_Dish> orderDishList = orderDishBD.getAllByOrder (orderID);
		ArrayList <Dish> dishList = new ArrayList <Dish> ();
		for (Order_Dish od: orderDishList) {
			dish = dishBD.get (od.getIddi ());
			dishList.add (dish);
		}
		return dishList;
	}
	
	@POST
	@Path ("/makeOrder/{orderedDishes}")
	@Produces (MediaType.APPLICATION_JSON)
	public Response makeOrderResource (@Context HttpServletRequest request, @PathParam ("orderedDishes") String orderedDishes) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		OrderDAO orderDAO = new JDBCOrderDAOImpl ();
		Order_DishDAO orderDishDAO = new JDBCOrder_DishDAOImpl ();
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		orderDAO.setConnection (conn);
		orderDishDAO.setConnection (conn);
		dishDAO.setConnection (conn);
		Order newOrder = new Order ();
		HttpSession session = request.getSession ();
		User user = (User) session.getAttribute ("user");
		int totalPrice = 0;
		newOrder.setIdu (user.getId ());
		newOrder.setTotalPrice (totalPrice);
		long orderID = orderDAO.add (newOrder);
		String [] selectedDishes = orderedDishes.split ("_");
		Dish dish = null;
		Order_Dish orderDish = new Order_Dish ();
		for (int i = 0; i < selectedDishes.length; i++) {
			if (!selectedDishes [i].equals ("")) {
				orderDish.setIddi (Long.parseLong (selectedDishes [i]));
				orderDish.setIdo (orderID);
				orderDishDAO.add (orderDish);
				dish = dishDAO.get (Long.parseLong (selectedDishes [i]));
				totalPrice += dish.getPrice ();
			}
		}
		newOrder.setTotalPrice (totalPrice);
		newOrder.setId(orderID);
		orderDAO.update (newOrder);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build()).contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
}