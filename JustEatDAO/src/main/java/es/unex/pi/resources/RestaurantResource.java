package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;

import es.unex.pi.model.Restaurant;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;

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
	@Path ("/loadAllRestaurants")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList <Restaurant> loadAllRestaurantsResource (@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
        restaurantDAO.setConnection (conn);
        return restaurantDAO.getAll();
	}
}
