package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;

/**
 * Servlet implementation class DeleteRestaurantServlet
 */
public class DeleteRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private long restaurant = -1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRestaurantServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute ("delete", 1);
		this.restaurant = Long.parseLong (request.getParameter ("idR"));
		request.setAttribute("resID", this.restaurant);
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/ConfirmDelete.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Elimina el restaurant de la Base de Datos, así como todos sus platos*/
		//Hay un atributo privado en esta clase que es el ID del restaurante.
		RestaurantDAO restBD = new JDBCRestaurantDAOImpl();
		Connection conn = (Connection)getServletContext().getAttribute("dbConn");
		restBD.setConnection(conn); //debes hacer una conexion por cada DAO!
		restBD.delete(restaurant);
		response.sendRedirect (request.getContextPath () + "/MyRestaurantsServlet.do");
	}

}