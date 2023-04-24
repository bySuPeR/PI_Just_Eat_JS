package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;

/**
 * Servlet implementation class DeleteDishServlet
 */
public class DeleteDishServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private long dish = -1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDishServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		RestaurantDAO resDAO = new JDBCRestaurantDAOImpl();
		DishDAO dishDAO = new JDBCDishDAOImpl ();
		resDAO.setConnection (conn);
		dishDAO.setConnection (conn);
		
		request.setAttribute ("delete", 2);
		this.dish = Long.parseLong (request.getParameter ("id"));
		request.setAttribute("dishID", this.dish);
		request.setAttribute ("resID", dishDAO.get (dish).getIdr ());
		
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/ConfirmDelete.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Eliminar el plato de la Base de Datos. Creo que habrá que coger el ID del plato en el doGet,
		 * indicarlo en un atributo privado de la clase y volver a cogerlo aquí para eliminarlo.
		 */
		DishDAO dishBD = new JDBCDishDAOImpl();
		Connection conn = (Connection)getServletContext().getAttribute("dbConn");
		dishBD.setConnection(conn); //debes hacer una conexion por cada DAO!
		dishBD.delete(dish);
		response.sendRedirect (request.getContextPath () + "/MyRestaurantsServlet.do");
	}
}
