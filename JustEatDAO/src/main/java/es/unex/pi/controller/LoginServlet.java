package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;

import java.sql.Connection;
import es.unex.pi.model.User;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.JDBCUserDAOImpl;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession ();
		if (sesion.getAttribute ("user") != null) {
			response.sendRedirect (request.getContextPath () + "/SearchServlet.do");
        }
        else {
        	RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/Login.jsp");
    		view.forward (request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn"); 
		UserDAO userDAO = new JDBCUserDAOImpl ();
		userDAO.setConnection (conn);
		
		String email = request.getParameter ("email");
		String password = request.getParameter ("password");
		
		if (email.equals ("") || password.equals ("")) {
			reloadLogin (request, response, "Type in an email and a password");
		}
		
		User user = userDAO.get (email);
		if (user != null) {
			if (password.equals (user.getPassword ())) {
				HttpSession session = request.getSession ();
				session.setAttribute ("user", user);
				response.sendRedirect (request.getContextPath () + "/SearchServlet.do");
			}
			else {
				reloadLogin (request, response, "The password is not correct");
			}
		}
		else {
			reloadLogin (request, response, "The user is not registered");
		}
	}
	
	private void reloadLogin (HttpServletRequest request, HttpServletResponse response, String errorMsg) throws ServletException, IOException {
		request.setAttribute ("error", errorMsg);
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/Login.jsp");
		view.forward (request, response);
	}
}
