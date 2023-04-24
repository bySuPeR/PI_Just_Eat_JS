package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurant_CategoryDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.Restaurant_CategoryDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Restaurant_Category;
import es.unex.pi.model.User;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Servlet implementation class AddRestaurantServlet
 */
public class AddRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRestaurantServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl ();
		categoryDAO.setConnection (conn);
		ArrayList <Category> categoryList = categoryDAO.getAll ();
		request.setAttribute ("categoryList", categoryList);
		RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/AddRestaurant.jsp");
		vista.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Estas en el post");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		RestaurantDAO restaurantBD = new JDBCRestaurantDAOImpl(); // base de datos
		restaurantBD.setConnection(conn);
		
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String telephone = request.getParameter("telephone");
		String city = request.getParameter("city");
		String contactEmail = request.getParameter("contactEmail");
		Restaurant newRest = new Restaurant();
		newRest.setName(name);
		newRest.setAddress(address);
		newRest.setTelephone(telephone);
		newRest.setCity(city);
		newRest.setContactEmail(contactEmail);
		newRest.setGradesAverage (0.0);
		newRest.setMinPrice (0);
		newRest.setMaxPrice (0);
		
		String restaurantPic = request.getParameter("image");
		if(!restaurantPic.equals("")&& restaurantPic != null) {
			newRest.setRestaurantPic(restaurantPic);
		}else {
			newRest.setRestaurantPic ("default.png");
		}
		
		String option = request.getParameter("bike-friendly");
		
		if(option.equals("yes-bike-friendly-value")) {
			newRest.setBikeFriendly (1);
			logger.info("1");
		}else {
			newRest.setBikeFriendly (0);
			logger.info("0");
		}
		
		String availability = request.getParameter ("available");
		if (availability.equals ("yes-available")) {
			newRest.setAvailable (1);
		}
		else if (availability.equals ("no-available")) {
			newRest.setAvailable (0);
		}
				
		logger.info("Todos los parametros");
		HttpSession sesion = request.getSession();
		User user = (User) sesion.getAttribute("user");
		newRest.setIdu(user.getId());
		logger.info("Id usuario "+ user);
		String[] vectorCat = request.getParameterValues("selectedCategory");
		if (vectorCat != null) {
			long idRest = restaurantBD.add(newRest);
			CategoryDAO cat = new JDBCCategoryDAOImpl();
			cat.setConnection(conn);
			Restaurant_CategoryDAO categoriasRest = new JDBCRestaurant_CategoryDAOImpl();
			categoriasRest.setConnection(conn);
			String catB="";
			for(int i=0;i< vectorCat.length ;i++) {
				catB = vectorCat[i];
				Category categoria = cat.get(catB);
				Restaurant_Category cateRest = new Restaurant_Category();
				cateRest.setIdr(idRest);
				cateRest.setIdct(categoria.getId());
				categoriasRest.add(cateRest);
				logger.info("Categoria checked "+ catB );
			}
			
			
			logger.info("Restaurante añadido");
			response.sendRedirect(request.getContextPath() + "/MyRestaurantsServlet.do");
		}
		else {
			String error = "No se ha elegido una categoría para este restaurante";
			request.setAttribute("error", error);
			doGet(request, response);
		}
	}

}
