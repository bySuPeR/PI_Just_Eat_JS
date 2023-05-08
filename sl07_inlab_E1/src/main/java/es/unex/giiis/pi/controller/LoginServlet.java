package es.unex.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import es.unex.giiis.pi.dao.JDBCUserDAOImpl;
import es.unex.giiis.pi.dao.UserDAO;
import es.unex.giiis.pi.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
		urlPatterns = { "/LoginServlet" }
		)
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if (session.getAttribute("user")!= null) 
				response.sendRedirect("orders/ListOrderServlet");
		else { 
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/login.jsp");
				view.forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");		
		
		logger.info("credentials: "+username+" - "+password);
		
		User user = userDao.get(username);
		
		if ((user != null) 
				&& (user.getPassword().equals(password)))
	    {
			HttpSession session = request.getSession();
			session.setAttribute("user",user);
			response.sendRedirect("pages/index.html");
			
		} 
		else {
			request.setAttribute("messages","Wrong username or password!!");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/login.jsp");
			view.forward(request,response);
		}	
	}

}

