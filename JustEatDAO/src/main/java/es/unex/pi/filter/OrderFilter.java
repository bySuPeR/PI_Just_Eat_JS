package es.unex.pi.filter;


import java.io.IOException;
import java.sql.Connection;

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

@WebFilter(urlPatterns = { "/MyOrdersServlet.do" })

public class OrderFilter implements jakarta.servlet.Filter {

	private FilterConfig filtro;

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public OrderFilter() {
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
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		long id = Long.parseLong(req.getParameter("id"));
		HttpSession sesion = req.getSession(true);

		User user = (User) sesion.getAttribute("user");
		
		if (user.getId() == id && user != null) {
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

