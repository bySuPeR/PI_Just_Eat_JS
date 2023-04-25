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
import es.unex.pi.controller.SingleHostList;
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

@Path ("/Details")
public class DetailsResource {
	@Context
	ServletContext sc;
	
	@Context
	UriInfo uriInfo;
	
	
	@GET
	@Path ("/loadHostDetails/{HostID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Hosting loadHostDetails (@Context HttpServletRequest request, @PathParam ("HostID") long HostID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
        HostingDAO hostDAO = new JDBCHostingDAOImpl ();
        hostDAO.setConnection(conn);
        return hostDAO.get (HostID);
	}
	
	
	@GET
	@Path ("/loadHostCategoryData/{HostID}")
	@Produces(MediaType.APPLICATION_JSON)
	public List <String> loadHostCategoryData (@Context HttpServletRequest request, @PathParam ("HostID") long HostID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		HostingDAO hostingDAO = new JDBCHostingDAOImpl(); //Conectar a la tabla de alojamientos
		HostingCategoriesDAO hostCatDAO = new JDBCHostingCategoriesDAOImpl (); //Conectarse a la tabla de HostingCategories
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl (); //Conectarse a la base de categorias
		hostingDAO.setConnection(conn);
		hostCatDAO.setConnection (conn);
		categoryDAO.setConnection (conn);
		
		List <HostingCategories> hostCatList = hostCatDAO.getAllByHosting (HostID);
		List <String> categoryList = new ArrayList <String> ();
		
		for (HostingCategories hostCat : hostCatList) {
			Category category = categoryDAO.get (hostCat.getIdct ());
			categoryList.add (category.getName ());
		}
        
        return categoryList;
	}
	
	
	@GET
	@Path ("/loadHostLikeDetails/{HostID}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean loadHostLikeDetails (@Context HttpServletRequest request, @PathParam ("HostID") long HostID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
        HostingDAO hostDAO = new JDBCHostingDAOImpl ();
        UserLikesDAO userLikesDAO = new JDBCUserLikesDAOImpl ();
        hostDAO.setConnection(conn);
        userLikesDAO.setConnection (conn);
        
        HttpSession currentSession = request.getSession();
		User currentUser = (User) currentSession.getAttribute("usuario");
        
		UserLikes userLikes = userLikesDAO.get (HostID, currentUser.getId ());
		boolean result;
		if (userLikes != null) {
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}
	
	
	@GET
	@Path ("/loadHostBelongingData/{HostID}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean itsMine (@Context HttpServletRequest request, @PathParam ("HostID") long HostID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
        HostingDAO hostDAO = new JDBCHostingDAOImpl ();
        hostDAO.setConnection(conn);
        Hosting currentHost = hostDAO.get( HostID);
        
        HttpSession currentSession = request.getSession();
		User currentUser = (User) currentSession.getAttribute("usuario");
        
		return (currentUser.getId() == currentHost.getIdu ());
	}
	
	
	@DELETE
	@Path ("/deleteHost/{HostID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteHost (@Context HttpServletRequest request, @PathParam ("HostID") long HostID) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		HostingDAO hostDAO = new JDBCHostingDAOImpl ();
		UserLikesDAO usrLkDAO = new JDBCUserLikesDAOImpl ();
		HostingCategoriesDAO hostCatDAO = new JDBCHostingCategoriesDAOImpl ();
		hostDAO.setConnection (conn);
		usrLkDAO.setConnection (conn);
		hostCatDAO.setConnection (conn);
		
		Hosting thisHost = hostDAO.get (HostID);
		
		//Borrar los likes del alojamiento.
		List <UserLikes> usrLkList = usrLkDAO.getAllByHosting (thisHost.getId ());
		for (UserLikes auxUsrLk: usrLkList) {
			usrLkDAO.delete (thisHost.getId (), auxUsrLk.getIdLike());
		}
		
		//Borras las categorías del alojamiento.
		List <HostingCategories> hostCatList = hostCatDAO.getAllByHosting (thisHost.getId ());
		for (HostingCategories auxHostCat: hostCatList) {
			hostCatDAO.delete (thisHost.getId (), auxHostCat.getIdct ());
		}
		
		//Borrar el alojamiento de la base de datos
		hostDAO.delete (thisHost.getId ());
		
		return Response.accepted (uriInfo.getAbsolutePathBuilder ().build ())
                .contentLocation (uriInfo.getAbsolutePathBuilder ().build ()).build ();
	}
	
	
	//Add categories
	public boolean editCategories (Hosting host, String categoryStr) {
		boolean result = true;
		if (!categoryStr.equals ("")) { //Si está a nulo, la lista estará vacía y se debe elegir.
			Connection conn = (Connection) sc.getAttribute("dbConn");
			
			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl(); //Conectar a la tabla de category
			categoryDAO.setConnection(conn);
			
			HostingCategoriesDAO hostcatDAO = new JDBCHostingCategoriesDAOImpl ();
			hostcatDAO.setConnection (conn);
			
			List <HostingCategories> eliminate = hostcatDAO.getAllByHosting(host.getId());
			for (HostingCategories hcl: eliminate) {
				hostcatDAO.delete(hcl.getIdh (), hcl.getIdct ());
			}
			
			String[] hostCategoriesList = categoryStr.split("_");
			Category categAux;
			HostingCategories hostCat = new HostingCategories ();
			for (String category : hostCategoriesList) {
				categAux = categoryDAO.get (category);
				hostCat.setIdh (host.getId ());
				hostCat.setIdct (categAux.getId ());
				hostcatDAO.add (hostCat); //Actualiza los registros
			}
		}
		else {
			result = false;
		}
		return result;
	}
	
	
	@PUT
	@Path ("/editHost/{categoryStr}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editHost (@Context HttpServletRequest request, Hosting host, @PathParam ("categoryStr") String categoryStr) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
			
		HostingDAO hostingDAO = new JDBCHostingDAOImpl(); //Conectar a la tabla de alojamientos
		hostingDAO.setConnection(conn);
			
		if (editCategories (host, categoryStr)) {
			hostingDAO.save (host); //Se actualiza su valor en la DB.
		}
		
		return Response.accepted (uriInfo.getAbsolutePathBuilder ().build ())
                .contentLocation (uriInfo.getAbsolutePathBuilder ().build ()).build ();
	}
	
	
	@PUT
	@Path ("/editHost")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNewHostFailed (@Context HttpServletRequest request, Hosting newHost) {
		throw new WebApplicationException (Response.Status.BAD_GATEWAY);
	}
	
	
	@PUT
	@Path ("/updateAvailability")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bookHost (@Context HttpServletRequest request, Hosting host) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		
		HostingDAO hostingDAO = new JDBCHostingDAOImpl(); //Conectar a la tabla de alojamientos
		hostingDAO.setConnection(conn);
		
		if (host.getAvailable () == 1) {
			host.setAvailable (0);
		}
		else  if (host.getAvailable () == 0){
			host.setAvailable (1);
		}
		hostingDAO.save (host);
		
		return Response.accepted (uriInfo.getAbsolutePathBuilder ().build ())
                .contentLocation (uriInfo.getAbsolutePathBuilder ().build ()).build ();
	}
	
	
	@PUT
	@Path ("/likeHost")
	@Produces(MediaType.APPLICATION_JSON)
	public Response likeHost (@Context HttpServletRequest request, Hosting host) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		HostingDAO hostingDAO = new JDBCHostingDAOImpl(); //Conectar a la tabla de alojamientos
		UserLikesDAO userLikesDAO = new JDBCUserLikesDAOImpl (); //Conectarse a la tabla de userLikes.
		hostingDAO.setConnection(conn);
		userLikesDAO.setConnection (conn);
		
		HttpSession sesion = request.getSession();
		User currentUser = (User) sesion.getAttribute("usuario");
		
		UserLikes userLikes = userLikesDAO.get (host.getId (), currentUser.getId ());
		
		if (userLikes != null) { //Como ya está dado like, hay que quitárselo.
			userLikesDAO.delete (host.getId (), currentUser.getId ());
			host.setLikes (host.getLikes () - 1);
			hostingDAO.save (host);
		}
		else {
			UserLikes auxUserLikes = new UserLikes ();
			auxUserLikes.setIdhost (host.getId ());
			auxUserLikes.setIdLike (currentUser.getId ());
			userLikesDAO.add (auxUserLikes);
			host.setLikes (host.getLikes () + 1);
			hostingDAO.save (host);
		}
		
		return Response.accepted (uriInfo.getAbsolutePathBuilder ().build ())
                .contentLocation (uriInfo.getAbsolutePathBuilder ().build ()).build ();
	}
}
