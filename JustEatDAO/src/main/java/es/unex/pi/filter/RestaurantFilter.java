package es.unex.pi.filter;

import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter(urlPatterns = { "/UpdateRestaurantServlet.do", "/DeleteRestaurantServlet.do", "/UpdateDishesServlet.do" })

public class RestaurantFilter implements jakarta.servlet.Filter{
	private FilterConfig filtro;

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public RestaurantFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Connection conn = (Connection) filtro.getServletContext().getAttribute("dbConn");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		long idR = Long.parseLong(req.getParameter("idR"));
		long idU = Long.parseLong(req.getParameter("idU"));
		HttpSession sesion = req.getSession(true);
		RestaurantDAO restaurantBD = new JDBCRestaurantDAOImpl();
		restaurantBD.setConnection(conn);

		User user = (User) sesion.getAttribute("user");
		Restaurant rest = restaurantBD.get(idR);
		if (user.getId() == rest.getIdu() && user != null && user.getId()== idU) {
			chain.doFilter(request, response);
		} else {
			res.sendError(403);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		filtro = fConfig;
	}

}