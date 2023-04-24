

package es.unex.pi.filter;

import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Dish;
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

@WebFilter(urlPatterns = { "/DeleteDishServlet.do" })

public class DishFilter implements jakarta.servlet.Filter{
	private FilterConfig filtro;

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public DishFilter() {
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
		long id = Long.parseLong(req.getParameter("id"));
		HttpSession sesion = req.getSession(true);
		DishDAO dishBD = new JDBCDishDAOImpl();
		dishBD.setConnection(conn);
		RestaurantDAO restBD = new JDBCRestaurantDAOImpl();
		restBD.setConnection(conn);
		User user = (User) sesion.getAttribute("user");
		Dish dish = dishBD.get(id);
		Restaurant rest = restBD.get(dish.getIdr());
		
		if (user.getId() == rest.getIdu() && user != null) {
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
