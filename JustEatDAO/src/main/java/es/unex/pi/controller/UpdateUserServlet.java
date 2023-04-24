package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class UserServlet
 */
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet () {
        super ();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession ();
		User user = (User) session.getAttribute ("user");
		request.setAttribute ("user", user); // precargar los datos del usuario que ha iniciado sesión.
		RequestDispatcher vista = request.getRequestDispatcher ("/WEB-INF/UpdateUser.jsp");
		vista.forward (request, response); // siempre al final del DOGET con el jsp necesario
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession sesion = request.getSession();
		User user = (User) sesion.getAttribute("user");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User userUpdated = new User();
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String error;
		userUpdated.setName(name);
		userUpdated.setSurname(surname);
		userUpdated.setEmail(email);
		userUpdated.setPassword(password);
		List<User> UserList = userDAO.getAll();
		boolean enc = false;
		for (User usu : UserList) {
			if (user.getId() != usu.getId() && usu.getEmail().equals(userUpdated.getEmail())
					&& usu != null) {
				enc = true;
			}
		}
		if (enc) {
			error = "El correo ya existe";
			request.setAttribute("error", error);
			request.setAttribute("usuario", user);
			RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/UpdateUser.jsp");
			vista.forward(request, response);
		}

		// Validar contraseña
		else if (password.length() >= 8) {

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
				error = "La contraseña no es válida";
				request.setAttribute("error", error);
				request.setAttribute("usuario", user);
				RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/UpdateUser.jsp");
				vista.forward(request, response);
			} else {
				userUpdated.setId(user.getId());
				sesion.removeAttribute("usuario");
				sesion.setAttribute("user", userUpdated);
				userDAO.update(userUpdated);
				response.sendRedirect(request.getContextPath() + "/UpdateUserServlet.do");

			}
		} else {
			error = "La contraseña no llega a 8 caracteres";
			request.setAttribute("error", error);
			request.setAttribute("usuario", user);
			RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/UpdateUser.jsp");
			vista.forward(request, response);
		}
	}
}

