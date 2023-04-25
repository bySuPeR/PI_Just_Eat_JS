package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    /**
     * @see HttpServlet#HttpServlet()
     * Permite que un usuario ya registrado inicie sesión en la aplicación.
     */
	
    public LoginServlet () {
        super ();
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {;
		//Se inicia la sesión con el usuario indicado.
		HttpSession sesion = request.getSession ();
        if (sesion.getAttribute("usuario") != null) {
        	//Si se ha iniciado sesión, se carga la página principal.
        	response.sendRedirect(request.getContextPath() + "/pages/index.html");
        }
        else {
        	//Si no se inicia sesión, no se carga la página principal.
            RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/Login.jsp");
            vista.forward (request, response);
        }
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Se inicia la conexión a la Base de Datos.
		Connection conn = (Connection)getServletContext().getAttribute("dbConn");
		
		//Se establece la conexión a la tabla de Usuarios.
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//Permitirá iniciar sesión si el usuario introduce la información correcta.
		User user = userDAO.get(username);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				HttpSession sesion = request.getSession();
	            sesion.setAttribute("usuario", user);
	            response.sendRedirect(request.getContextPath()+"/pages/index.html");
			}
			else {
				String error = "La contraseña no es correcta.";
				request.setAttribute("error", error);
				RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/Login.jsp");
	            vista.forward(request, response);
			}
		}
		else {
			String error = "El usuario no existe.";
			request.setAttribute("error", error);
			RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/Login.jsp");
            vista.forward(request, response);
		}
	}

}
