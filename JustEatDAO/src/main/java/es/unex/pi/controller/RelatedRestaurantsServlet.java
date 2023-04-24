package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Favorito;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Restaurant_Category;
import es.unex.pi.model.User;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.FavoritoDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCFavoritoDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_CategoryDAOImpl;

/**
 * Servlet implementation class RelatedRestaurantsServlet
 */
public class RelatedRestaurantsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelatedRestaurantsServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl ();
		Restaurant_CategoryDAO restaurantCategoryDAO = new JDBCRestaurant_CategoryDAOImpl ();
		FavoritoDAO favoritoBD = new JDBCFavoritoDAOImpl();
		favoritoBD.setConnection(conn);
		restaurantDAO.setConnection (conn);
		restaurantCategoryDAO.setConnection (conn);
		categoryDAO.setConnection (conn);
		dishDAO.setConnection (conn);
		
		long resID = Long.parseLong (request.getParameter ("idR"));
		Restaurant currentRestaurant = restaurantDAO.get (resID);
		request.setAttribute ("restaurant", currentRestaurant);
		User currentUser = (User) request.getSession ().getAttribute ("user");
		request.setAttribute ("currentUser", currentUser);
		
		Favorito fav = favoritoBD.get (resID, currentUser.getId());
		if(fav == null) {
			request.setAttribute ("like", "emptyH");
		}
		
		request.setAttribute ("dishList", dishDAO.getAllByRestaurant (resID));
		
		String selection = request.getParameter ("state-value");
		ArrayList <Restaurant> relatedRestaurants = new ArrayList <Restaurant> ();
		ArrayList <Restaurant> allRestaurants = restaurantDAO.getAll ();
		if (selection.equals ("category-related")) {
			ArrayList <Category> allCategoryList = new ArrayList <Category> ();
			ArrayList <Category> thisCategoryList = null;
			ArrayList <Long> relatedIDs = new ArrayList <Long> ();
			HashMap <Long, ArrayList <Category>> restaurant_categoryList_Map = new HashMap <Long, ArrayList <Category>> ();
			ArrayList <Restaurant_Category> restaurant_Category_List = null;
			for (Restaurant r: allRestaurants) {
				restaurant_Category_List = restaurantCategoryDAO.getAllByRestaurant (r.getId ());
				for (Restaurant_Category rc: restaurant_Category_List) {
					allCategoryList.add (categoryDAO.get (rc.getIdct ()));
				}
				restaurant_categoryList_Map.put (r.getId (), allCategoryList);
				allCategoryList = new ArrayList <Category> ();
				if (r.getId () == currentRestaurant.getId ()) { //Se cargan las categorias de este restaurante y se elimina de los relacionados.
					thisCategoryList = restaurant_categoryList_Map.get (r.getId ());
					request.setAttribute ("categoryList", thisCategoryList);
					restaurant_categoryList_Map.remove (r.getId ());
				}
			}
			for (Long rID: restaurant_categoryList_Map.keySet ()) {
				for (Category c: restaurant_categoryList_Map.get (rID)) {
					for (Category d: thisCategoryList) {
						if ((d.getId () == c.getId ()) && (!relatedIDs.contains (rID))) {
							relatedIDs.add (rID);
						}
						if (relatedIDs.contains (rID)) break;
					}
					if (relatedIDs.contains (rID)) break;
				}
			}
			for (Long rID: relatedIDs) {
				relatedRestaurants.add (restaurantDAO.get (rID));
			}
			request.setAttribute ("categoryrelated", "checked");			
		}
		if (selection.equals ("location-related")) {
			for (Restaurant r: allRestaurants) {
				if (r.getCity ().equals (currentRestaurant.getCity ()) && !relatedRestaurants.contains (r) && (r.getId () != currentRestaurant.getId ())) {
					relatedRestaurants.add (r);
				}
			}
			request.setAttribute ("locationrelated", "checked");
		}
		if (selection.equals ("price-related")) {
			for (Restaurant r: allRestaurants) {
				if (this.priceRelated (currentRestaurant, r) && !relatedRestaurants.contains (r) && (r.getId () != currentRestaurant.getId ())) {
					relatedRestaurants.add (r);
				}
			}
			request.setAttribute ("pricerelated", "checked");
		}
		if (selection.equals ("grade-related")) {
			for (Restaurant r: allRestaurants) {
				if (this.gradeRelated (currentRestaurant, r) && !relatedRestaurants.contains (r) && (r.getId () != currentRestaurant.getId ())) {
					relatedRestaurants.add (r);
				}
			}
			request.setAttribute ("graderelated", "checked");
		}
		request.setAttribute ("relatedRestaurants", relatedRestaurants);
		
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/Res_details.jsp");
		view.forward (request, response);
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
