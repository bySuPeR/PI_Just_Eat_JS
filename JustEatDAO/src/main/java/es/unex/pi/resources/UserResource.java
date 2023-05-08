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

@Path ("/User")
public class UserResource {
	@Context
	ServletContext sc;
	
	@Context
	UriInfo uriInfo;
	
	
	@GET
	@Path ("/loadUser")
	@Produces(MediaType.APPLICATION_JSON)
	public User loadUserData (@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute ("usuario");
	}
	
	
	@DELETE //Para los DELETE, POST y PUT se tiene que devolver un objeto Response.
	@Path ("/deleteUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCurrentUser (@Context HttpServletRequest request) {
//		Connection conn = (Connection) sc.getAttribute("dbConn"); //Abrir DB
//		
//		UserDAO userDAO = new JDBCUserDAOImpl(); //Conecta a tabla users
//		HostingDAO hostDAO = new JDBCHostingDAOImpl ();
//		UserLikesDAO usrLkDAO = new JDBCUserLikesDAOImpl ();
//		HostingCategoriesDAO hostCatDAO = new JDBCHostingCategoriesDAOImpl ();
//		userDAO.setConnection(conn);
//		usrLkDAO.setConnection (conn);
//		hostDAO.setConnection (conn);
//		hostCatDAO.setConnection (conn);
//		
//		HttpSession currentSession = request.getSession();
//		User currentUser = (User) currentSession.getAttribute("usuario");
//		
//		//Borrar todos los likes que ha dado el usuario.
//		List <UserLikes> user_likes = usrLkDAO.getAllByLike (currentUser.getId ());
//		for (UserLikes singleUserLike: user_likes) {
//			usrLkDAO.delete (singleUserLike.getIdhost (), singleUserLike.getIdLike ());
//			Hosting auxHost = hostDAO.get (singleUserLike.getIdhost ());
//			auxHost.setLikes (auxHost.getLikes () - 1);
//			hostDAO.save (auxHost);
//		}
//		
//		//Borrar todos los alojamientos del usuario.
//		List <Hosting> user_hosts = hostDAO.getAllByUser (currentUser.getId ());
//		for (Hosting auxHost: user_hosts) {
//			
//			//Borrar los likes de los alojamientos del usuario.
//			List <UserLikes> userHosts_likes = usrLkDAO.getAllByHosting (auxHost.getId ());
//			for (UserLikes auxUserLikes: userHosts_likes) {
//				usrLkDAO.delete (auxUserLikes.getIdhost (), auxUserLikes.getIdLike ());
//				//Los likes de este alojamiento no se actualizan porque �stos se van a borrar de su tabla.
//			}
//			
//			//Borrar las categor�as de los alojamientos.
//			List <HostingCategories> hostCat = hostCatDAO.getAllByHosting (auxHost.getId ());
//			for (HostingCategories auxHostCat: hostCat) {
//				hostCatDAO.delete (auxHostCat.getIdh (), auxHostCat.getIdct ());
//			}
//			
//			hostDAO.delete (auxHost.getId ());
//		}
//		
//		userDAO.delete(currentUser.getId());
				
		return Response.accepted (uriInfo.getAbsolutePathBuilder ().build ())
                .contentLocation (uriInfo.getAbsolutePathBuilder ().build ()).build ();
	}
	
	@PUT
	@Path ("/UpdateUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUserData (@Context HttpServletRequest request, User currentUser) {
//		Connection conn = (Connection) sc.getAttribute("dbConn"); //Abrir DB.
//		UserDAO userDAO = new JDBCUserDAOImpl(); //Conecta a tabla users
//		userDAO.setConnection(conn);
//		
//		//El usuario actual se elimina de la lista de usuarios para que no se encuentre
//		List <User> userList = userDAO.getAll();
//		int userListLength = userList.size ();
//		boolean found = false;
//		HttpSession sesion = request.getSession ();
//		User thisUser = (User) sesion.getAttribute ("usuario");
//		for (int i = 0; (i < userListLength) && (!found); i++) {
//			if (thisUser.getUsername ().equals (userList.get (i).getUsername ())) {
//				userList.remove (i);
//				found = true;
//			}
//		}
//		
//		userListLength = userList.size ();
//		found = false;
//		for (int i = 0; (i < userListLength) && (!found); i++) {
//			System.out.println (userList.get (i).getUsername ());
//			if (currentUser.getUsername ().equals (userList.get (i).getUsername ())) {
//				found = true;
//			}
//		}
//		if (found) {throw new WebApplicationException (Response.Status.BAD_GATEWAY);}
//		
//		userListLength = userList.size ();
//		found = false;
//		for (int i = 0; (i < userListLength) && (!found); i++) {
//			if (currentUser.getEmail ().equals (userList.get (i).getEmail ())) {
//				found = true;
//			}
//		}
//		if (found) {throw new WebApplicationException (Response.Status.BAD_REQUEST);}
//		
//		String validation = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=\\S+$).{8,20}$";
//		if (!currentUser.getPassword ().matches(validation)) {
//			throw new WebApplicationException (Response.Status.CONFLICT);
//		}
//		
//		userDAO.save (currentUser);
//		
//		return Response.accepted (uriInfo.getAbsolutePathBuilder ().build ()).contentLocation (uriInfo.getAbsolutePathBuilder ().build ()).build ();
		return null;
	}
}
