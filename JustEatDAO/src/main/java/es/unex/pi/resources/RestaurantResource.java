package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Category;
import es.unex.pi.model.Favorito;
import es.unex.pi.model.Restaurant_Category;
import es.unex.pi.model.User;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.dao.FavoritoDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
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

@Path ("/Restaurant")
public class RestaurantResource {
	@Context
	ServletContext sc;
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path ("/loadAllCategories")
	@Produces (MediaType.APPLICATION_JSON)
	public ArrayList <Category> loadAllCategoriesResource (@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl ();
		categoryDAO.setConnection (conn);
		return categoryDAO.getAll ();
	}
	
	@GET
	@Path ("/searchAmongRestaurants/{searchPrompt}")
	@Produces (MediaType.APPLICATION_JSON)
	public ArrayList <Restaurant> searchAmongRestaurantsResource (@Context HttpServletRequest request, @PathParam ("searchPrompt") String searchPrompt) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		restaurantDAO.setConnection (conn);
		return restaurantDAO.searchAmongRestaurants (searchPrompt);
	}
	
	@GET
	@Path ("/searchByCategory/{searchCategoryID}")
	@Produces (MediaType.APPLICATION_JSON)
	public ArrayList <Restaurant> searchByCategoryResource (@Context HttpServletRequest request, @PathParam ("searchCategoryID") long searchCategoryID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		restaurantDAO.setConnection (conn);
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl ();
		categoryDAO.setConnection (conn);
		Restaurant_CategoryDAO restCatDAO = new JDBCRestaurant_CategoryDAOImpl ();
		restCatDAO.setConnection (conn);
		ArrayList <Restaurant_Category> restCatList = restCatDAO.getAllByCategory (searchCategoryID);
		ArrayList <Restaurant> restList = new ArrayList <Restaurant> ();
		for (Restaurant_Category rc: restCatList) {
			restList.add (restaurantDAO.get (rc.getIdr ()));
		}
		return restList;
	}
	
	@GET
	@Path ("/searchByState/{restState}")
	@Produces (MediaType.APPLICATION_JSON)
	public ArrayList <Restaurant> searchByStateResource (@Context HttpServletRequest request, @PathParam ("restState") String restState) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		restaurantDAO.setConnection (conn);
		ArrayList <Restaurant> restListDB = restaurantDAO.getAll ();
		ArrayList <Restaurant> restList = null;
		if (restState.equals ("all-restaurants")) {
			restList = restListDB;
		}
		else {
			if (restState.equals ("reviews")) {
				Comparator <Restaurant> resComparator = new RestaurantComparator ();
				restList = restListDB;
				Collections.sort (restList, resComparator);
			}
			else {
				restList = new ArrayList <Restaurant> ();
				if (restState.equals ("take-orders")) {
					for (Restaurant r: restListDB) {
						if (r.getAvailable () == 1) {
							restList.add (r);
						}
					}
				}
				if (restState.equals ("no-take-orders")) {
					for (Restaurant r: restListDB) {
						if (r.getAvailable () == 0) {
							restList.add (r);
						}
					}
				}
				if (restState.equals ("myfavorites")) {
					FavoritoDAO favDAO = new JDBCFavoritoDAOImpl ();
					favDAO.setConnection (conn);
					HttpSession session = request.getSession ();
			    	User currentUser = (User) session.getAttribute ("user");
			    	ArrayList <Favorito> favList = favDAO.getAllByUser (currentUser.getId ());
			    	for (Favorito f: favList) {
			    		restList.add (restaurantDAO.get (f.getidRestaurant ()));
			    	}
				}
			}
		}
		return restList;
	}
}
