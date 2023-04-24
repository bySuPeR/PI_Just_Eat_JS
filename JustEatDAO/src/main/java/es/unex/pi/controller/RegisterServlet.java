package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean EmailUsed = false;
       
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
		RequestDispatcher view = request.getRequestDispatcher ("WEB-INF/Register.jsp");
		view.forward (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");  //creas la conexion
		User newUser = new User();												    //creas usuario
		UserDAO userDAO = new JDBCUserDAOImpl();									//creas usuario BD
		userDAO.setConnection(conn);
		String name = request.getParameter("name");									//coges parametros puesto por el usuario
		String surname = request.getParameter("surname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		newUser.setName(name);
		newUser.setSurname(surname);
		newUser.setEmail(email);
		newUser.setPassword(password);
		
		List<User> UserList = userDAO.getAll();										//Lista Usuarios de BD
		Iterator<User> Iterator = UserList.iterator();								//Iterador de la lista
		while (Iterator.hasNext()) {												//si hay siguiente
			User userIt = (User) Iterator.next();									//lo coges
			if (userIt.getEmail().equals(email)) {									//y compruebas si se repite el email
				String error = "El correo ya está siendo utilizado";
				EmailUsed=true;
				request.setAttribute("error", error);
			}
		}

		if (password.length() >= 8 && !EmailUsed) {

			int numeros = 0;
			int mayusculas = 0;
			int minusculas = 0;
			int especial = 0;
			for (int x = 0; x < password.length(); x++) {
				if (password.charAt(x) > 47 && password.charAt(x) < 58) {
					numeros++;
				}
				if (password.charAt(x) > 64 && password.charAt(x) < 91 || password.charAt(x) == 165) {
					mayusculas++;
				}
				if (password.charAt(x) > 96 && password.charAt(x) < 123 || password.charAt(x) == 164) {
					minusculas++;
					}
				if ((password.charAt(x) > 32 && password.charAt(x) < 48)
						|| (password.charAt(x) > 57 && password.charAt(x) < 65)
						|| (password.charAt(x) > 90 && password.charAt(x) < 97)
						|| (password.charAt(x) > 122 && password.charAt(x) <= 126)) {
					especial++;
				}
				}
			if (numeros < 1 || mayusculas < 1 || minusculas < 1 || especial < 1) {
				String error = "La contraseña no es válida";
				request.setAttribute("error", error);
				RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/Register.jsp");
				vista.forward(request, response);
			} else {
				userDAO.add(newUser);
				response.sendRedirect(request.getContextPath() + "/LoginServlet.do");
			}
		} else {
			if(password.length() < 8) {
				String error = "La contraseña no llega a 8 caracteres";
				request.setAttribute("error", error);
			}
			RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/Register.jsp");
			vista.forward(request, response);
		}
	}	
		
}
