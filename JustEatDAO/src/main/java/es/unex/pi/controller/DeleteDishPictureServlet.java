package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.model.Dish;

/**
 * Servlet implementation class DeleteDishPictureServlet
 */
public class DeleteDishPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private long dishID = -1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDishPictureServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		dishID = Long.parseLong (request.getParameter ("P"));
		
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		dishDAO.setConnection (conn);
		Dish dish = new Dish();
		dish = dishDAO.get(dishID);
		dish.setDishPic("default.png");
		dishDAO.update(dish);
		
		long restaurantID = dish.getIdr();
		
		response.sendRedirect (request.getContextPath () + "/UpdateDishesServlet.do?idR=" + restaurantID);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
