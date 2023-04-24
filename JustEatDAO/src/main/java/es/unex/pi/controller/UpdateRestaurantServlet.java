package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant_Category;
import es.unex.pi.model.User;
import es.unex.pi.model.Dish;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_CategoryDAOImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class UpdateRestaurantServlet
 */
public class UpdateRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private long restaurant = -1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRestaurantServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		Restaurant_CategoryDAO resCatDAO = new JDBCRestaurant_CategoryDAOImpl ();
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl ();
		
		restaurantDAO.setConnection (conn);
		this.restaurant = Long.parseLong (request.getParameter ("idR"));
		Restaurant currentRestaurant = restaurantDAO.get (this.restaurant);
		request.setAttribute ("restaurant", currentRestaurant);
		
		User currentUser = (User) request.getSession ().getAttribute ("user");
		request.setAttribute ("currentUser", currentUser);
		
		if (currentRestaurant.getBikeFriendly () == 1) {
			request.setAttribute ("yesbikefriendly", "checked");
		}
		else if (currentRestaurant.getBikeFriendly () == 0) {
			request.setAttribute ("nobikefriendly", "checked");
		}
		
		if (currentRestaurant.getAvailable () == 1) {
			request.setAttribute ("yesavailable", "checked");
		}
		else if (currentRestaurant.getAvailable () == 0) {
			request.setAttribute ("noavailable", "checked");
		}
		
		resCatDAO.setConnection (conn);
		ArrayList <Restaurant_Category> resCatList = resCatDAO.getAllByRestaurant (currentRestaurant.getId ());
		
		categoryDAO.setConnection (conn);
		ArrayList <Category> categoryList = categoryDAO.getAll ();
		request.setAttribute ("categoryList", categoryList);
		for (Restaurant_Category res_cat: resCatList) {
			request.setAttribute (categoryDAO.get (res_cat.getIdct ()).getName (), "checked");
		}
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/UpdateRestaurant.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Actualizar los valores en la Base de Datos*/
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		Restaurant rest = new Restaurant();
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		restaurantDAO.setConnection (conn);
		Restaurant currentRestaurant = restaurantDAO.get (this.restaurant);
		rest.setId(restaurant);
		rest.setName(request.getParameter("name"));
		rest.setAddress(request.getParameter("address"));
		rest.setTelephone(request.getParameter("telephone"));
		rest.setCity(request.getParameter("city"));
		rest.setContactEmail(request.getParameter("contactEmail"));
		
		String restaurantPic = request.getParameter("image");
		if(!restaurantPic.equals("")&& restaurantPic != null) {
			rest.setRestaurantPic(restaurantPic);
		}else {
			rest.setRestaurantPic ("default.png");
		}
		
		
		String option = request.getParameter("bike-friendly");
		if(option.equals("yes-bike-friendly-value")) {
			rest.setBikeFriendly (1);
		}else {
			rest.setBikeFriendly (0);
		}
		String availability = request.getParameter ("available");
		if (availability.equals ("yes-available")) {
			rest.setAvailable (1);
		}
		else if (availability.equals ("no-available")) {
			rest.setAvailable (0);
		}
		
		dishDAO.setConnection (conn);
		ArrayList <Dish> dishList = dishDAO.getAllByRestaurant (this.restaurant);
		int minPrice = Integer.MAX_VALUE;
		int maxPrice = 0;
		for (Dish d: dishList) {
			if (d.getPrice () > maxPrice) {
				maxPrice = d.getPrice ();
			}
			if (d.getPrice () < minPrice) {
				minPrice = d.getPrice ();
			}
		}
		if (minPrice == Integer.MAX_VALUE) {
			minPrice = 0;
		}
		rest.setMinPrice (minPrice);
		rest.setMaxPrice (maxPrice);
		rest.setGradesAverage(currentRestaurant.getGradesAverage());

		rest.setIdu (currentRestaurant.getIdu ());
		
		if (request.getParameterValues("selectedCategory") != null) {
			CategoryDAO categoryBD = new JDBCCategoryDAOImpl();
			categoryBD.setConnection(conn);
			Restaurant_CategoryDAO categoryRestBD = new JDBCRestaurant_CategoryDAOImpl();
			categoryRestBD.setConnection(conn);
			String[] vectorCat = request.getParameterValues("selectedCategory");
			String catB = "";
			List<Restaurant_Category> CategoriesDeleted = categoryRestBD.getAllByRestaurant(rest.getId());
			for(Restaurant_Category restaurantDeleted : CategoriesDeleted) {
				categoryRestBD.delete(restaurantDeleted.getIdr(), restaurantDeleted.getIdct());
				
			}
			for (int i = 0; i < vectorCat.length; i++) {
				catB = vectorCat[i];
				Category categoria = categoryBD.get(catB);
				Restaurant_Category restCategory = new Restaurant_Category();
				restCategory.setIdr(rest.getId());
				restCategory.setIdct(categoria.getId());
				categoryRestBD.add(restCategory);
			}
			RestaurantDAO restaurantBD = new JDBCRestaurantDAOImpl();
			
			restaurantBD.setConnection(conn);
			restaurantBD.update(rest);
			response.sendRedirect(request.getContextPath() + "/MyRestaurantsServlet.do?id="  + this.restaurant);

		} else {
			String error = "No se ha elegido una categoría para este restaurante";
			request.setAttribute("error", error);
			response.sendRedirect(request.getContextPath() + "/UpdateRestaurantServlet.do?id="  + this.restaurant);
		}
		//Por el momento, vuelve a la página de los detalles del restaurante.
	}
}
