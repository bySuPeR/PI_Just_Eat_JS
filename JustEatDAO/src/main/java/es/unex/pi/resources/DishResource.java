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
import es.unex.pi.dao.JDBCFavoritoDAOImpl;
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

@Path ("/manageDishes")
public class DishResource {
	@Context
	ServletContext sc;
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path ("/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public ArrayList <Dish> getRestaurantDishes (@Context HttpServletRequest request,  @PathParam("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		RestaurantDAO restaurantBD = new JDBCRestaurantDAOImpl ();
		DishDAO dishBD = new JDBCDishDAOImpl ();
		restaurantBD.setConnection (conn);
		Restaurant currentRestaurant = restaurantBD.get (restID);
		System.out.println("Restaurant:" + currentRestaurant.getId());
		dishBD.setConnection (conn);
		ArrayList <Dish> dishList = dishBD.getAllByRestaurant (currentRestaurant.getId ());
		return dishList;
	}

}
