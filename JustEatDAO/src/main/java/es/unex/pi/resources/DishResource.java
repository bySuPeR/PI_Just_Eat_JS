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
		DishDAO dishBD = new JDBCDishDAOImpl ();
		dishBD.setConnection (conn);
		ArrayList <Dish> dishList = dishBD.getAllByRestaurant (restID);
		return dishList;
	}
	
	@DELETE
	@Path("/deleteDish/{dishID}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteDish(@PathParam("dishID") long dishID , @Context HttpServletRequest request) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		DishDAO dishBD = new JDBCDishDAOImpl();
		RestaurantDAO resDAO = new JDBCRestaurantDAOImpl ();
		dishBD.setConnection(conn);
		resDAO.setConnection (conn);
		Dish dish = dishBD.get (dishID);
		dishBD.delete (dishID);
		Restaurant res = resDAO.get (dish.getIdr ());
		ArrayList <Dish> allDishes = dishBD.getAllByRestaurant (dish.getIdr ());
		int minPrice = Integer.MAX_VALUE;
		int maxPrice = Integer.MIN_VALUE;
		for (Dish d: allDishes) {
			if (d.getPrice () < minPrice) {
				minPrice = d.getPrice ();
			}
			if (d.getPrice () > maxPrice) {
				maxPrice = d.getPrice ();
			}
		}
		res.setMinPrice (minPrice);
		res.setMaxPrice (maxPrice);
		resDAO.update (res);
	}
	
	@PUT
	@Path ("/UpdateDish/{dishID}")
	@Produces (MediaType.APPLICATION_JSON)
	public Response UpdateDishResoure (@Context HttpServletRequest request, Dish dish, @PathParam ("dishID") long dishID) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		RestaurantDAO resDAO = new JDBCRestaurantDAOImpl ();
		dishDAO.setConnection (conn);
		resDAO.setConnection (conn);
		dishDAO.update (dish);
		Restaurant res = resDAO.get (dish.getIdr ());
		ArrayList <Dish> allDishes = dishDAO.getAllByRestaurant (dish.getIdr ());
		int minPrice = Integer.MAX_VALUE;
		int maxPrice = Integer.MIN_VALUE;
		for (Dish d: allDishes) {
			if (d.getPrice () < minPrice) {
				minPrice = d.getPrice ();
			}
			if (d.getPrice () > maxPrice) {
				maxPrice = d.getPrice ();
			}
		}
		res.setMinPrice (minPrice);
		res.setMaxPrice (maxPrice);
		resDAO.update (res);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build()).contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	@POST
	@Path ("/AddDish")
	@Produces (MediaType.APPLICATION_JSON)
	public Response AddDishResource (@Context HttpServletRequest request, Dish dish) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		RestaurantDAO resDAO = new JDBCRestaurantDAOImpl ();
		dishDAO.setConnection (conn);
		resDAO.setConnection (conn);
		dish.setDishPic ("default.png");
		dishDAO.add (dish);
		Restaurant res = resDAO.get (dish.getIdr ());
		if ((res.getMinPrice () > dish.getPrice ()) || (res.getMinPrice () == -1)) {
			res.setMinPrice (dish.getPrice ());
		}
		if (res.getMaxPrice () < dish.getPrice ()) {
			res.setMaxPrice (dish.getPrice ());
		}
		resDAO.update (res);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build()).contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	@GET
	@Path ("/getDish/{dishID}")
	@Produces (MediaType.APPLICATION_JSON)
	public Dish getDishResource (@Context HttpServletRequest request, @PathParam ("dishID") long dishID) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		dishDAO.setConnection (conn);
		return dishDAO.get (dishID);
	}
}
