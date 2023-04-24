package es.unex.pi.filter;

import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.Restaurant_UserDAO;
import es.unex.pi.dao.JDBCRestaurant_UserDAOImpl;
import es.unex.pi.model.Restaurant_User;
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

@WebFilter(urlPatterns = { "/UpdateReviewServlet.do", "/DeleteReviewServlet.do" })

public class ReviewFilter implements jakarta.servlet.Filter {
	private FilterConfig filtro;

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public ReviewFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Connection conn = (Connection) filtro.getServletContext().getAttribute("dbConn");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		long restid = Long.parseLong(req.getParameter("idR"));
		HttpSession sesion = req.getSession(true);
		User user = (User) sesion.getAttribute("user");
		long userid = Long.parseLong(req.getParameter("idU"));
		Restaurant_UserDAO restUserBD = new JDBCRestaurant_UserDAOImpl();
		restUserBD.setConnection(conn);
		Restaurant_User RestUser = new Restaurant_User();
		RestUser = restUserBD.get(restid, userid);
		
		if( user.getId() == userid && user!=null && RestUser!=null) {
			chain.doFilter(request, response);
		} else {
			res.sendError (403);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		filtro = fConfig;
	}

}
