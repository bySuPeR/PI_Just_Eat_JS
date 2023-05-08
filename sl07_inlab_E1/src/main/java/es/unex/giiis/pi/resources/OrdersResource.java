package es.unex.giiis.pi.resources;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import es.unex.giiis.pi.dao.JDBCOrderDAOImpl;
import es.unex.giiis.pi.dao.OrderDAO;
import es.unex.giiis.pi.model.Order;
import es.unex.giiis.pi.model.User;
import es.unex.giiis.pi.resources.exceptions.CustomBadRequestException;
import es.unex.giiis.pi.resources.exceptions.CustomNotFoundException;

@Path("/orders")
public class OrdersResource {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Order> getOrdersJSON(@Context HttpServletRequest request) {

		List<Order> orders=null;
		  
		  //TODO: Complete the code to implement this method.
		  
		  //1. You must connect to the database by using an OrderDAO object.
		 
		  //2. You must obtain the user that has logged into the system
		  
		  //3. If the user is a Manager, return all the orders.
		  //   otherwise, return the orders of the current user
		  
	
		return orders; 
	  }
	  
	  @GET
	  @Path("")	  //TODO Comlete the path
	  @Produces(MediaType.APPLICATION_JSON)
	  public Order getOrderJSON(@PathParam("orderid") long orderid,
			  					@Context HttpServletRequest request) {

		Order order = null;
		  //TODO: Complete the code to implement this method.
		  
		  //1. You must connect to the database by using an OrderDAO object.
		 
		  //2. You must obtain the user that has logged into the system
		  
		  //3. If ((the order exists) && ((it belongs to the current user) || (user is a manager)))
		  //       return the order
		  //   otherwise 
		  //       throw a CustomNotFoundException with the id of the order not found
		 
		return order;
	  }
	  
	 
	  
	  @POST	  	  
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response post(Order newOrder, @Context HttpServletRequest request) throws Exception {	
		
		  OrderDAO orderDao = null;
		  User user= null;
		
		  //TODO: Complete the code to implement this method.
		  
		  //1. You must connect to the database by using an OrderDAO object.
		 
		  //2. You must obtain the user that has logged into the system
		
		
		Response res;
		
		Map<String, String> messages = new HashMap<String, String>();

		if ((!newOrder.validate(messages))
			||((!user.getName().equals(newOrder.getName()))
			  &&(!user.getRole().equals("Manager"))))
			    throw new CustomBadRequestException("Errors in parameters");


		//save order in DB
		long id = orderDao.add(newOrder);

		res = Response 								//return 201 and Location: /orders/newid
			   .created(
				uriInfo.getAbsolutePathBuilder()
					   .path(Long.toString(id))
					   .build())
			   .contentLocation(					//Content Location: /orders/newid
				uriInfo.getAbsolutePathBuilder()
				       .path(Long.toString(id))
				       .build())
				.build();
		
		return res; 
	  }
	  
	  
	   //POST that receives a new order via webform
	  @POST	  	 
	  @Consumes("application/x-www-form-urlencoded")
	  public Response post(MultivaluedMap<String, String> formParams,
			               @Context HttpServletRequest request) {	

		  OrderDAO orderDao = null;
		  User user= null;
		
		  //TODO: Complete the code to implement this method.
		  
		  //1. You must connect to the database by using an OrderDAO object.
		 
		  //2. You must obtain the user that has logged into the system
		  
		Response res;
		
        Order order = new Order();
		order.setName(formParams.getFirst("name"));
		order.setEmail(formParams.getFirst("email"));
		order.setTel(formParams.getFirst("tel"));
		order.setSize(formParams.getFirst("size"));
		order.setType(formParams.getFirst("type"));
		order.setDelivery(formParams.getFirst("delivery"));
		order.setComments(formParams.getFirst("comments"));

		Map<String, String> messages = new HashMap<String, String>();
		if ((!order.validate(messages))
		    ||((!user.getName().equals(order.getName()))
		      &&(!user.getRole().equals("Manager"))))
			   throw new CustomBadRequestException("Errors in parameters");
		
		//save order in DB
		long id = orderDao.add(order);

		res = Response //return 201 and Location: /orders/newid
				   .created(
					uriInfo.getAbsolutePathBuilder()
						   .path(Long.toString(id))
						   .build())
				   .contentLocation(
					uriInfo.getAbsolutePathBuilder()
					       .path(Long.toString(id))
					       .build())
					.build();
		 return res;  
	  }
	  
	  
	  
	  @PUT
	  @Path("") //TODO Comlete the path
		@Consumes(MediaType.APPLICATION_JSON)
		public Response put(Order orderUpdate,
							@PathParam("orderid") long orderid,
							@Context HttpServletRequest request) throws Exception{
		
		  OrderDAO orderDao = null;
		  User user= null;
		
		  //TODO: Complete the code to implement this method.
		  
		  //1. You must connect to the database by using an OrderDAO object.
		 
		  //2. You must obtain the user that has logged into the system
			
		  Response response = null;
					
		  //We check that the order exists
		  Order order = orderDao.get(orderUpdate.getId());
		  if ((order != null)
		      &&((user.getName().equals(order.getName()))
		        ||(user.getRole().equals("Manager")))){
					if (order.getId()!=orderid) throw new CustomBadRequestException("Error in id");
					else {
						
						// 3. If the name of the order is valid
						//       update the order
						//    otherwise
						//       throw a CustomBadRequestException   
					}
				}
		  else throw new WebApplicationException(Response.Status.NOT_FOUND);			
		  
		  return response;
		}
	  
	  

	@DELETE
	  @Path("") //TODO Comlete the path	  
	  public Response deleteOrder(@PathParam("orderid") long orderid,
			  					  @Context HttpServletRequest request) {
		  
		  OrderDAO orderDao = null;
		  User user= null;

		  //TODO: Complete the code to implement this method.
		  
		  //1. You must connect to the database by using an OrderDAO object.
		 
		  //2. You must obtain the user that has logged into the system
		
		Order order = orderDao.get(orderid);
		if ((order != null)
			&&((user.getName().equals(order.getName()))
			  ||(user.getRole().equals("Manager")))){
				
				//3. Delete the order
			
				return Response.noContent().build(); //204 no content 
		}
		else throw new CustomBadRequestException("Error in user or id");		
			
	  }
	  
} 
