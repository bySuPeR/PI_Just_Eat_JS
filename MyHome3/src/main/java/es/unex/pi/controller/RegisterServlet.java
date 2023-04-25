package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;


/**
 * Servlet implementation class RegisterServlet
 */

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public RegisterServlet () {
        super ();
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher inicio = request.getRequestDispatcher ("/WEB-INF/Register.jsp");
		inicio.forward (request, response);
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Se inicia la conexión con la Base de Datos.
		Connection conn = (Connection) getServletContext ().getAttribute ("dbConn");
		
		//Se establece la conexión con la tabla de usuario.s
		UserDAO userDAO = new JDBCUserDAOImpl ();
		userDAO.setConnection (conn);
		
		//Se recoge toda la información introducida por el usuario.
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//Si cumple todas las condiciones, se registrará en la página web.
		User username = userDAO.get(name);
		if (username != null) {
			userExists ("El nombre de usuario ya existe.", request, response);
		}
		else {
			List <User> userList = userDAO.getAll ();
			int userListSize = userList.size ();
			boolean found = false;
			for (int i = 0; (i < userListSize) && (!found); i++) {
				if (userList.get(i).getEmail().equals(email)) {
					found = true;
				}
			}
			if (found) {
				userExists ("El correo electrónico ya existe.", request, response);
			}
			else {
				String validationString = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=\\S+$).{8,20}$"; //Mínimo una minúscula, una mayúscula, un número y longitud 8.
				if (password.matches(validationString)) {
					User newUser = new User ();
					newUser.setUsername(name);
					newUser.setEmail(email);
					newUser.setPassword(password);
					userDAO.add (newUser);
					response.sendRedirect ("LoginServlet.do");
				}
				else {
					String error = "La contraseña no es válida.";
					request.setAttribute("error", error);
					RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/Register.jsp");
					vista.forward (request, response);
				}
			}
		}
	}
	
	
	/**
	 * Indica al cliente que el usuario ya existe, ya sea por el nombre de usuario o por la dirección
	 * de correo electrónico.
	 */
	
	public void userExists (String error, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", error);
		RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/Register.jsp");
		vista.forward(request, response);
	}
}
