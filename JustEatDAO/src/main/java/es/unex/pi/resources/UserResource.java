package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path ("/UpdateUser")
public class UserResource {
	@Context
	ServletContext sc;
	
	@Context
	UriInfo uriInfo;
	
	
	@GET
	@Path ("/")
	@Produces(MediaType.APPLICATION_JSON)
	public User loadUserResource (@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		return currentUser;
	}
	
	
	@DELETE //Para los DELETE, POST y PUT se tiene que devolver un objeto Response.
	@Path ("/deleteUser")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteCurrentUser (@Context HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		User usuarioSesion = (User) sesion.getAttribute("user"); 
		UserDAO usuarioBD = new JDBCUserDAOImpl();
		Connection conn = (Connection)sc.getAttribute("dbConn");
		usuarioBD.setConnection(conn); //debes hacer una conexion por cada DAO!
		usuarioBD.delete(usuarioSesion.getId());
	}
	
	
	@PUT
	@Path ("/{currentUserID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCurrentUser (@Context HttpServletRequest request, User newUser) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession sesion = request.getSession();
		User user = (User) sesion.getAttribute("user");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User userUpdated = new User();
		String password = newUser.getPassword();
		String error;
		userUpdated.setName(newUser.getName());
		userUpdated.setSurname(newUser.getSurname());
		userUpdated.setEmail(newUser.getEmail());
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
			throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
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
				throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
			} else {
				userUpdated.setId(user.getId());
				sesion.removeAttribute("user");
				sesion.setAttribute("user", userUpdated);
				userDAO.update(userUpdated);
				return Response.accepted(uriInfo.getAbsolutePathBuilder().build()).contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();

			}
		} else {
			throw new WebApplicationException(Response.Status.LENGTH_REQUIRED);
		}

	}
	
}
