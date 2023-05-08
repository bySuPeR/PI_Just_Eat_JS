package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;

import es.unex.pi.model.Restaurant;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;

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

@Path ("/Hosting")
public class RestaurantResource {
	@Context
	ServletContext sc;
	
	@Context
	UriInfo uriInfo;
	
	
	@GET
	@Path ("/loadAllRestaurants")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList <Restaurant> loadAllRestaurantsResource (@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl ();
        restaurantDAO.setConnection (conn);
        return restaurantDAO.getAll();
	}
	
	
	@GET
	@Path ("/searchedHosts/{nameDescription}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList <Hosting> searchNameDescriptionResource (@Context HttpServletRequest request, @PathParam ("nameDescription") String nameDescription) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
        HostingDAO hostDAO = new JDBCHostingDAOImpl ();
        hostDAO.setConnection(conn);
        return hostDAO.getAllBySearchAllPlusLocation (nameDescription);
	}
	
	
	@GET
	@Path ("/reversedOrder")
	@Produces(MediaType.APPLICATION_JSON)
	public List <Hosting> reverseFavouritesOrder (@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl(); //Conectar a la tabla de alojamientos
		hostingDAO.setConnection (conn);
		
		Operations op = new Operations ();
		OrderSingleton os = OrderSingleton.getInstance ();
		List <Hosting> hostList = hostingDAO.getAll ();
		os.switchOrder ();
		op.compareOrder (hostList, os.getOrder ());
		return hostList;
	}
	
	
	/**
	 * It selects all hosts that are available, or not, depending on the parameter's value.
	 *
	 */
	public List <Hosting> getAvailability (boolean availability) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		HostingDAO hostingDAO = new JDBCHostingDAOImpl(); //Conectar a la tabla de alojamientos
		hostingDAO.setConnection(conn);
		
		List <Hosting> hostList = hostingDAO.getAll();
		List <Hosting> resultHostList = new ArrayList <Hosting> ();
		
		//1 means the host is available
		if (availability) {
			for (Hosting h: hostList) {
				if (h.getAvailable() == 1) {
					resultHostList.add(h);
				}
			}
		}
		else {
			for (Hosting h: hostList) {
				if (h.getAvailable () == 0) {
					resultHostList.add(h);
				}
			}
		}
		
		return resultHostList;
	}
	
	
	@GET
	@Path ("/status/{hostStatus}")
	@Produces(MediaType.APPLICATION_JSON)
	public List <Hosting> showStatus (@Context HttpServletRequest request, @PathParam ("hostStatus") String showStatus) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		HostingDAO hostDAO = new JDBCHostingDAOImpl ();
		hostDAO.setConnection (conn);
		
		List <Hosting> hostList = null;
		
		if (showStatus.equals ("All hosts")) {
			hostList = hostDAO.getAll ();
		}
		if (showStatus.equals ("Reserved")) {
			hostList = getAvailability (false);
		}
		if (showStatus.equals ("Available")) {
			hostList = getAvailability (true);
		}
		return hostList;
	}
	
	
	@GET
	@Path ("/MyHosts")
	@Produces(MediaType.APPLICATION_JSON)
	public List <Hosting> loadMyHosts (@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		HostingDAO hostDAO = new JDBCHostingDAOImpl ();
		hostDAO.setConnection (conn);
		
		HttpSession currentSesion = request.getSession();
		User currentUser = (User) currentSesion.getAttribute ("usuario");
		
		return hostDAO.getAllByUser (currentUser.getId ());
	}
	
	//Add categories
	public boolean addCategories (Hosting newHost, String categoryStr) {
		boolean result = true;
		if (!categoryStr.equals ("")) { //Si est� a nulo, la lista estar� vac�a y se debe elegir.
			Connection conn = (Connection) sc.getAttribute("dbConn");
			
			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl(); //Conectar a la tabla de category
			categoryDAO.setConnection(conn);
			
			HostingCategoriesDAO hostcatDAO = new JDBCHostingCategoriesDAOImpl ();
			hostcatDAO.setConnection (conn);
			
			String[] hostCategoriesList = categoryStr.split("_");
			Category categAux;
			HostingCategories hostCat = new HostingCategories ();
			for (String category : hostCategoriesList) {
				categAux = categoryDAO.get (category);
				hostCat.setIdh (newHost.getId ());
				hostCat.setIdct (categAux.getId ());
				hostcatDAO.add (hostCat);
			}
		}
		else {
			result = false;
		}
		return result;
	}
	
	
	@POST
	@Path ("/newHost/{categoryStr}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNewHost (@Context HttpServletRequest request, Hosting newHost, @PathParam ("categoryStr") String categoryStr) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		HostingDAO hostingDAO = new JDBCHostingDAOImpl(); //Conectar a la tabla de alojamientos
		hostingDAO.setConnection(conn);
		
		newHost.setAvailable (1);
		newHost.setLikes (0);
		newHost.setFoto ("defaultHouse.jpg");
		
		HttpSession currentSesion = request.getSession();
		User currentUser = (User) currentSesion.getAttribute("usuario");
		newHost.setIdu ((int) currentUser.getId ()); //El nuevo alojamiento pertenece al usuario que ha iniciado sesi�n y que ha creado el nuevo alojamiento.
		
		long newId = hostingDAO.add (newHost); //Se a�ade a la BD.
		newHost.setId (newId);
		
		if (!addCategories (newHost, categoryStr)) {
			hostingDAO.delete (newId);
			throw new WebApplicationException (Response.Status.BAD_GATEWAY);
		}
		
		return Response.accepted (uriInfo.getAbsolutePathBuilder ().build ())
                .contentLocation (uriInfo.getAbsolutePathBuilder ().build ()).build ();
	}
	
	
	@POST
	@Path ("/newHost")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNewHostFailed (@Context HttpServletRequest request, Hosting newHost) {
		throw new WebApplicationException (Response.Status.BAD_GATEWAY);
	}
	
	
	@GET
	@Path ("/loadServiceHosts/{selectedServices}")
	@Produces(MediaType.APPLICATION_JSON)
	public List <Hosting> filterHostsServices (@Context HttpServletRequest request, @PathParam ("selectedServices") String selectedServices) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl(); //Conectar a la tabla de alojamientos
		hostingDAO.setConnection(conn);
		
		String [] serviceList = selectedServices.split ("_");
		
		List <Hosting> hostList = null;
		if (serviceList.length != 0) { //No se ha seleccionado ning�n servicio a filtrar, as� que se recarga la p�gina.
			for (int i = 0; i < serviceList.length; i++) {
				hostList = addServiceToHostList (hostList, serviceList [i], conn);
			}
		}
		else {
			hostList = hostingDAO.getAll ();
		}
        
        return hostList;
	}
	
	@GET
	@Path ("/loadServiceHosts")
	@Produces(MediaType.APPLICATION_JSON)
	public List <Hosting> failedHostServices (@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl(); //Conectar a la tabla de alojamientos
		hostingDAO.setConnection(conn);
        
        return hostingDAO.getAll ();
	}
	
	public List <Hosting> addServiceToHostList (List <Hosting> hostList, String service, Connection conn) {
		HostingDAO hostDAO = new JDBCHostingDAOImpl ();
		hostDAO.setConnection (conn);
		
		if (hostList == null) {
			hostList = new ArrayList <Hosting> ();
		}
		List <Hosting> fullList = hostDAO.getAll ();
		Operations op = new Operations ();
		for (int index = 0; index < fullList.size (); index++) {
			if (hostHasService (fullList.get (index), service) && !op.listContains (hostList, fullList.get (index))) { //Si el host tiene el servicio y no est� ya incluido
				hostList.add (fullList.get (index));
			}
		}
		return hostList;
	}
	
	
	/**
	 * Indicates if a host offers certain service.
	 * @param host to check if offers service
	 * @param service that needs to be checked if it is offered by the host
	 * @return boolean indicating whether the host offers said service.
	 */
	
	public boolean hostHasService (Hosting host, String service) {
		boolean found = false;
		if (host != null) {
			String [] servArray = host.getServicios ().split ("/");
			for (int i = 0; (i < servArray.length) && (!found); i++) {
				if (servArray [i].equals (service)) {
					found = true;
				}
			}
		}
		return found;
	}
	
	
	@GET
	@Path ("/loadCategoryHosts/{pickedCategory}")
	@Produces(MediaType.APPLICATION_JSON)
	public List <Hosting> searchHostByCategory (@Context HttpServletRequest request, @PathParam ("pickedCategory") String pickedCategory) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		
		HostingDAO hostDAO = new JDBCHostingDAOImpl ();
		HostingCategoriesDAO hostCatDAO = new JDBCHostingCategoriesDAOImpl ();
		CategoryDAO catDAO = new JDBCCategoryDAOImpl ();
		hostDAO.setConnection (conn);
		hostCatDAO.setConnection (conn);
		catDAO.setConnection (conn);
		
		List <Hosting> hostList = new ArrayList <Hosting> ();
		
		Category auxCat = catDAO.get (pickedCategory); // Se coge la categor�a concreta
		List <HostingCategories> hostCatList = hostCatDAO.getAllByCategory (auxCat.getId ()); //Solo los hosts con dicha categor�a
		
		for (HostingCategories aux: hostCatList) {
			hostList.add (hostDAO.get (aux.getIdh ())); //Se a�aden los hosts con esa categor�a
		}
		
		return hostList;
	}
	
	
	@GET
	@Path ("/loadPriceHosts/{selectedPrice}")
	@Produces(MediaType.APPLICATION_JSON)
	public List <Hosting> searchHostByPrice (@Context HttpServletRequest request, @PathParam ("selectedPrice") int selectedPrice) {
		Connection conn = (Connection) sc.getAttribute ("dbConn");
		
		HostingDAO hostDAO = new JDBCHostingDAOImpl ();
		hostDAO.setConnection (conn);
		
		List <Hosting> hostList = hostDAO.getAll ();
		for (int index = 0; index < hostList.size (); index++) {
			if (hostList.get (index).getPrecio () > selectedPrice) {
				hostList.remove (index);
				index--;
			}
		}
		
		return hostList;
	}
}
