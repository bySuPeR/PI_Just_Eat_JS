package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.JDBCRestaurant_UserDAOImpl;
import es.unex.pi.dao.Restaurant_UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class DeleteReviewServlet
 */
public class DeleteReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private long currentRestaurantID=-1;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReviewServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute ("delete", 3);
		currentRestaurantID = Long.parseLong (request.getParameter ("idR"));
		request.setAttribute ("resID", currentRestaurantID);
		User currentUser = (User) request.getSession ().getAttribute ("user");
		request.setAttribute ("user", currentUser);
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/ConfirmDelete.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Borrar la review de la Base de Datos. El ID del usuario es el que tiene la sesión iniciada*/
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		Restaurant_UserDAO reviewBD = new JDBCRestaurant_UserDAOImpl();
		reviewBD.setConnection(conn);
		HttpSession sesion = request.getSession();
		User SessionUser = (User) sesion.getAttribute("user"); 
		request.setAttribute("user",SessionUser );
		reviewBD.delete(currentRestaurantID, SessionUser.getId());
		//Al hacerlo, vuelve a cargar la página de reviews del restaurante para ver que se ha actualizado.
		response.sendRedirect (request.getContextPath () + "/LoadReviewsServlet.do?idR=" + request.getParameter ("idR"));
	}
}
