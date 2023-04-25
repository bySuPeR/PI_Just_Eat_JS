package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCUserLikesDAOImpl;
import es.unex.pi.dao.UserLikesDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.User;
import es.unex.pi.model.UserLikes;
import es.unex.pi.util.OrderSingleton;
import es.unex.pi.controller.Operations;
import jakarta.servlet.RequestDispatcher;
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

@Path ("/Graphics")
public class GraphicsResource {
	@Context
	ServletContext sc;
	
	@Context
	UriInfo uriInfo;
	
	
	public int calculateData (int code, HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		
		HostingDAO hostDAO = new JDBCHostingDAOImpl (); //Conectar a la tabla de alojamientos
		hostDAO.setConnection (conn);
		
		HttpSession sesion = request.getSession();
		User currentUser = (User) sesion.getAttribute("usuario");
		
		int result = -1;
		
		int n_ava_hosts = 0;
		int n_hosts = 0;
		int t_n_likes = 0;
		List <Hosting> fullList = hostDAO.getAll ();
		for (Hosting h: fullList) {
			if (h.getIdu () == currentUser.getId ()) {
				n_hosts++;
				t_n_likes += h.getLikes ();
				if (h.getAvailable () == 1) {
					n_ava_hosts++;
				}
			}
		}
		
		switch (code) {
			case 1:
				result = n_hosts;
			break;
			case 2:
				result = n_ava_hosts;
			break;
			case 3:
				result = t_n_likes;
			break;
			case 4:
				result = t_n_likes/n_hosts;
			break;
		}
		return result;
	}
	
	
	@GET
	@Path ("/loadNumberHosts")
	@Produces(MediaType.APPLICATION_JSON)
	public int loadGraphicsNumberHosts (@Context HttpServletRequest request) {
		return calculateData (1, request);
	}
	
	
	@GET
	@Path ("/loadNumberAvailableHosts")
	@Produces(MediaType.APPLICATION_JSON)
	public int loadGraphicsNumberAvailableHosts (@Context HttpServletRequest request) {
		return calculateData (2, request);
	}
	
	
	@GET
	@Path ("/loadNumberLikes")
	@Produces(MediaType.APPLICATION_JSON)
	public int loadGraphicsNumberLikes (@Context HttpServletRequest request) {
		return calculateData (3, request);
	}
	
	
	@GET
	@Path ("/loadNumberLikesHosts")
	@Produces(MediaType.APPLICATION_JSON)
	public int loadGraphicsAverageNumberLikesHost (@Context HttpServletRequest request) {
		return calculateData (4, request);
	}
}
