
package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Category;
import es.unex.pi.model.Favorito;
import es.unex.pi.model.Restaurant_Category;
import es.unex.pi.model.Restaurant_User;
import es.unex.pi.model.User;
import es.unex.pi.model.Dish;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.dao.Restaurant_UserDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.FavoritoDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_CategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_UserDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.JDBCFavoritoDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.util.RestaurantComparator;
import es.unex.pi.util.Triplet;
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
	public void deleteRestaurant(@PathParam("restID") long restID , @Context HttpServletRequest request) {
		RestaurantDAO restBD = new JDBCRestaurantDAOImpl();
		Connection conn = (Connection)sc.getAttribute("dbConn");
		restBD.setConnection(conn); //debes hacer una conexion por cada DAO!
		restBD.delete(restID);
		}
	
	@POST
	@Path("/AddRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public long addRestaurant (@Context HttpServletRequest request, Restaurant rest) {
		return 1;
	}
	
	@PUT
	@Path ("/addCategory/{catName}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList <Category> addCategoryResource (@Context HttpServletRequest request, ArrayList <Category> restaurantCategories, @PathParam ("catName") String catName) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CategoryDAO catDAO = new JDBCCategoryDAOImpl ();
		catDAO.setConnection (conn);
		if (restaurantCategories == null) {
			restaurantCategories = new ArrayList <Category> ();
		}
		restaurantCategories.add (catDAO.get (catName));
		return restaurantCategories;
	}
	
	@PUT
	@Path ("/deleteCategory/{pos}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList <Category> deleteCategoryResource (@Context HttpServletRequest request, ArrayList <Category> restaurantCategories, @PathParam ("pos") int pos) {
		restaurantCategories.remove (pos);
		return restaurantCategories;
	}
	
	@PUT
	@Path ("/updateCurrentRestaurant/{restaurantCategories}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCurrentRestaurantResource (@Context HttpServletRequest request, Restaurant restaurant, @PathParam ("restaurantCategories") String restaurantCategories) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restDAO = new JDBCRestaurantDAOImpl ();
		Restaurant_CategoryDAO restCatDAO = new JDBCRestaurant_CategoryDAOImpl ();
		restDAO.setConnection (conn);
		restCatDAO.setConnection (conn);
		ArrayList <Restaurant_Category> restCatList = restCatDAO.getAllByRestaurant (restaurant.getId ());
		for (Restaurant_Category rc: restCatList) {
			restCatDAO.delete (rc.getIdr (), rc.getIdct ());
		}
		Restaurant_Category rc = new Restaurant_Category ();
		String [] ids = restaurantCategories.split ("_");
		for (String cat: ids) {
			if (!cat.equals ("")) {
				rc.setIdr (restaurant.getId ());
				rc.setIdct (Long.parseLong (cat));
				restCatDAO.add (rc);
			}
		}
		restDAO.update (restaurant);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build()).contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	@GET
	@Path ("/rateRestaurant/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public Restaurant_User loadReviewUser (@Context HttpServletRequest request, @PathParam ("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		Restaurant_UserDAO restaurantUserBD = new JDBCRestaurant_UserDAOImpl ();
		restaurantUserBD.setConnection (conn);
		User currentUser = (User) request.getSession ().getAttribute ("user");
		Restaurant_User reviewUser = restaurantUserBD.get(restID, currentUser.getId());
		return reviewUser;
	}
	
	@DELETE
	@Path("/deleteReview/{restID}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteReview(@PathParam("restID") long restID, @Context HttpServletRequest request) {
		Connection conn = (Connection)sc.getAttribute("dbConn");
		Restaurant_UserDAO reviewBD = new JDBCRestaurant_UserDAOImpl();
		reviewBD.setConnection (conn);
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user"); 
		reviewBD.delete (restID, currentUser.getId());
	}
	
	@GET
	@Path ("/loadReviews/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public ArrayList <Restaurant_User> loadRestaurantReviews (@Context HttpServletRequest request, @PathParam ("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		Restaurant_UserDAO restaurantUserBD = new JDBCRestaurant_UserDAOImpl ();
		restaurantUserBD.setConnection (conn);
		ArrayList <Restaurant_User> restaurantUserList = restaurantUserBD.getAllByRestaurant (restID);
		return restaurantUserList;
	}

	@PUT
	@Path ("/reviewList")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList <User> loadUserReviews (@Context HttpServletRequest request,  ArrayList <Restaurant_User> reviewsList) {
		UserDAO userBD = new JDBCUserDAOImpl();
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		userBD.setConnection(conn);
		ArrayList <User> userList = new ArrayList <User> ();
		User user;
		for (Restaurant_User ru: reviewsList) {
			user=userBD.get(ru.getIdu());
			userList.add(user);
		}
		return userList;
	}
	
	@GET
	@Path ("/loadCurrentReview/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public Restaurant_User loadCurrentReviewResource (@Context HttpServletRequest request, @PathParam ("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		Restaurant_UserDAO restUserDAO = new JDBCRestaurant_UserDAOImpl ();
		restUserDAO.setConnection (conn);
		HttpSession session = request.getSession ();
		User currentUser = (User) session.getAttribute ("user");
		return restUserDAO.get (restID, currentUser.getId ());
	}

	@GET
	@Path("/like/{restID}")
	@Produces(MediaType.APPLICATION_JSON)
	public int getLikeApartamento(@PathParam("restID") long restID, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		int like;
		FavoritoDAO favBD = new JDBCFavoritoDAOImpl();
		favBD.setConnection(conn);
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		Favorito fav = favBD.get(restID,currentUser.getId());
		if(fav==null) {
			like=0;
		}
		else {
			like=1;
		}
		return like;
	}
	
	@PUT
	@Path("/giveLike/{restID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darLikeApartamento(@PathParam("restID") long restID, @Context HttpServletRequest request, Restaurant restaurant) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		FavoritoDAO favBD = new JDBCFavoritoDAOImpl();
		favBD.setConnection(conn);
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		Favorito fav = new Favorito();
		fav.setidRestaurant(restaurant.getId());
		fav.setidUser(currentUser.getId());
		favBD.add(fav);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build()).contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	@PUT
	@Path("/removeLike/{restID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response quitarLikeApartamento(@PathParam("restID") long restID, @Context HttpServletRequest request, Restaurant restaurant) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		FavoritoDAO favBD = new JDBCFavoritoDAOImpl();
		favBD.setConnection(conn);
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		favBD.delete(restaurant.getId(), currentUser.getId());
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build()).contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	
	@PUT
	@Path ("/updateCurrentReview/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public Response updateCurrentReviewResource (@Context HttpServletRequest request, Restaurant_User currentReview, @PathParam ("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		Restaurant_UserDAO restUserDAO = new JDBCRestaurant_UserDAOImpl ();
		restUserDAO.setConnection (conn);
		HttpSession session = request.getSession ();
		User currentUser = (User) session.getAttribute ("user");
		currentReview.setIdr (restID);
		currentReview.setIdu (currentUser.getId ());
		restUserDAO.update (currentReview);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build()).contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	@POST
	@Path ("/addReview/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public Response addReviewResource (@Context HttpServletRequest request, Restaurant_User currentReview, @PathParam ("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		Restaurant_UserDAO restUserDAO = new JDBCRestaurant_UserDAOImpl ();
		restUserDAO.setConnection (conn);
		HttpSession session = request.getSession ();
		User currentUser = (User) session.getAttribute ("user");
		currentReview.setIdr (restID);
		currentReview.setIdu (currentUser.getId ());
		restUserDAO.add (currentReview);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build()).contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	@GET
	@Path ("/isAlreadyReviewed/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public boolean isAlreadyReviewedResource (@Context HttpServletRequest request, @PathParam ("restID") long restID) {
		boolean found = false;
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		Restaurant_UserDAO restUserDAO = new JDBCRestaurant_UserDAOImpl ();
		restUserDAO.setConnection (conn);
		HttpSession session = request.getSession ();
		User currentUser = (User) session.getAttribute ("user");
		ArrayList <Restaurant_User> restUserList = restUserDAO.getAllByRestaurant (restID);
		for (int i = 0; (i < restUserList.size ()) && (!found); i++) {
			if (restUserList.get (i).getIdu () == currentUser.getId ()) {
				found = true;
			}
		}
		return found;
	}
	
	@PUT
	@Path ("/searchRelated/{restID}")
	@Produces (MediaType.APPLICATION_JSON)
	public ArrayList <Restaurant> searchRelatedResource (@Context HttpServletRequest request, String relateSearch, @PathParam ("restID") long restID) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl ();
		Restaurant_CategoryDAO restaurantCategoryDAO = new JDBCRestaurant_CategoryDAOImpl ();
		restaurantDAO.setConnection (conn);
		categoryDAO.setConnection (conn);
		restaurantCategoryDAO.setConnection (conn);
		
		Restaurant currentRestaurant = restaurantDAO.get (restID);
		ArrayList <Restaurant> relatedRestaurants = new ArrayList <Restaurant> ();
		ArrayList <Restaurant> allRestaurants = restaurantDAO.getAll ();
		if (relateSearch.equals ("category-related")) {
			ArrayList <Category> allCategoryList = new ArrayList <Category> ();
			ArrayList <Category> thisCategoryList = null;
			ArrayList <Long> relatedIDs = new ArrayList <Long> ();
			HashMap <Long, ArrayList <Category>> restaurant_categoryList_Map = new HashMap <Long, ArrayList <Category>> ();
			ArrayList <Restaurant_Category> restaurant_Category_List = null;
			for (Restaurant r: allRestaurants) {
				restaurant_Category_List = restaurantCategoryDAO.getAllByRestaurant (r.getId ());
				for (Restaurant_Category rc: restaurant_Category_List) {
					allCategoryList.add (categoryDAO.get (rc.getIdct()));
				}
				restaurant_categoryList_Map.put (r.getId (), allCategoryList);
				allCategoryList = new ArrayList <Category> ();
				if (r.getId () == restID) {
					thisCategoryList = restaurant_categoryList_Map.get (r.getId ());
					restaurant_categoryList_Map.remove (r.getId ());
				}
			}
			for (Long rID: restaurant_categoryList_Map.keySet ()) {
				for (Category c: restaurant_categoryList_Map.get (rID)) {
					for (Category d: thisCategoryList) {
						if ((d.getId () == c.getId ()) && (!relatedIDs.contains (rID))) {
							relatedIDs.add (rID);
						}
						if (relatedIDs.contains (rID)) {
							break;
						}
					}
				}
			}
			for (Long rID: relatedIDs) {
				relatedRestaurants.add (restaurantDAO.get (rID));
			}
		}
		if (relateSearch.equals ("location-related")) {
			for (Restaurant r: allRestaurants) {
				if (r.getCity ().equals (currentRestaurant.getCity ()) && !relatedRestaurants.contains (r) && (r.getId () != currentRestaurant.getId ())) {
					relatedRestaurants.add (r);
				}
			}
		}
		if (relateSearch.equals ("price-related")) {
			for (Restaurant r: allRestaurants) {
				if (this.priceRelated (currentRestaurant, r) && !relatedRestaurants.contains (r) && (r.getId () != currentRestaurant.getId ())) {
					relatedRestaurants.add (r);
				}
			}
		}
		if (relateSearch.equals ("grade-related")) {
			for (Restaurant r: allRestaurants) {
				if (this.gradeRelated (currentRestaurant, r) && !relatedRestaurants.contains (r) && (r.getId () != currentRestaurant.getId ())) {
					relatedRestaurants.add (r);
				}
			}
		}
		return relatedRestaurants;
	}
	
	private boolean priceRelated (Restaurant current, Restaurant r) {
		boolean result = false;
		int minPrice = current.getMinPrice () - 1;
		int maxPrice = current.getMaxPrice () + 1;
		if ((minPrice <= r.getMinPrice ()) && (maxPrice >= r.getMaxPrice ())) {
			result = true;
		}
		return result;
	}
	
	private boolean gradeRelated (Restaurant current, Restaurant r) {
		boolean result = false;
		double minGrade = current.getGradesAverage () - 0.5;
		double maxGrade = current.getGradesAverage () + 0.5;
		if ((minGrade <= r.getGradesAverage ()) && (maxGrade >= r.getGradesAverage ())) {
			result = true;
		}
		return result;
	}
}
