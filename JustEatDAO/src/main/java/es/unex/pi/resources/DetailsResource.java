
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
import es.unex.pi.model.Dish;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.dao.FavoritoDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_CategoryDAOImpl;
import es.unex.pi.dao.JDBCFavoritoDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
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

@Path("/restaurantDetails")
public class DetailsResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{restID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getRestaurant(@Context HttpServletRequest request, @PathParam("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restBD = new JDBCRestaurantDAOImpl();
		restBD.setConnection(conn);
		System.out.println(restID);
		return restBD.get(restID);
	}
	
	@GET
	@Path ("/loadDetails/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public ArrayList <Category> loadDetailsResource (@Context HttpServletRequest request, @PathParam ("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CategoryDAO catDAO = new JDBCCategoryDAOImpl ();
		Restaurant_CategoryDAO restCatDAO = new JDBCRestaurant_CategoryDAOImpl ();
		restCatDAO.setConnection (conn);
		catDAO.setConnection (conn);
		ArrayList <Restaurant_Category> restCatList = restCatDAO.getAllByRestaurant (restID);
		ArrayList <Category> catList = new ArrayList <Category> ();
		for (Restaurant_Category rc: restCatList) {
			catList.add (catDAO.get (rc.getIdct ()));
		}
		return catList;
	}
	
	@GET
	@Path ("/loadBelongsToMe/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public boolean loadBelongsToMeResource (@Context HttpServletRequest request, @PathParam ("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restDAO = new JDBCRestaurantDAOImpl ();
		restDAO.setConnection (conn);
		HttpSession session = request.getSession ();
		User currentUser = (User) session.getAttribute ("user");
		return (currentUser.getId () == restDAO.get (restID).getIdu ());
	}
	
	@GET
	@Path ("/loadAllDishes/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public ArrayList <Dish> loadAllDishesResource (@Context HttpServletRequest request, @PathParam ("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		dishDAO.setConnection (conn);
		return dishDAO.getAllByRestaurant (restID);
	}
	
	@DELETE
	@Path("/deleteRestaurant/{restID}")
	@Produces(MediaType.APPLICATION_JSON)
	public void borrarApartamento(@PathParam("restID") long restID , @Context HttpServletRequest request) {
		RestaurantDAO restBD = new JDBCRestaurantDAOImpl();
		Connection conn = (Connection)sc.getAttribute("dbConn");
		restBD.setConnection(conn); //debes hacer una conexion por cada DAO!
		restBD.delete(restID);
		}
	
	@POST
	@Path("/AddRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public long addRestaurant(@Context HttpServletRequest request,Restaurant rest) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		RestaurantDAO restaurantBD = new JDBCRestaurantDAOImpl(); // base de datos
		restaurantBD.setConnection(conn);
		
		rest.setAvailable (1);
		rest.setGradesAverage (0.0);
		rest.setMinPrice (0);
		rest.setMaxPrice (0);
		
		if(rest.getRestaurantPic().equals("")|| rest.getRestaurantPic() == null) {
			rest.setRestaurantPic ("default.png");
		}
		
		String option = request.getParameter("bike-friendly");
		
		if(option.equals("yes")) {
			rest.setBikeFriendly (1);
		}else {
			rest.setBikeFriendly (0);
		}
		
		String availability = request.getParameter ("available");
		if (availability.equals ("yes")) {
			rest.setAvailable (1);
		}
		else if (availability.equals ("no")) {
			rest.setAvailable (0);}
	
		
		HttpSession sesion = request.getSession();
		User currentUser = (User)sesion.getAttribute("user");
		rest.setIdu ((int)currentUser.getId ()); //El nuevo alojamiento pertenece al usuario que ha iniciado sesión y que ha creado el nuevo alojamiento.
		long newId = restaurantBD.add(rest); //Se añade a la BD.
		rest.setId (newId);
		return newId;
		}
	

	
}
