package es.unex.pi.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/AddDishServlet.do", "/AddRestaurantServlet.do","/CategoryServlet.do","/DeleteDishPictureServlet.do",
		"/DeleteRestaurantPictureServlet.do","/DeleteDishServlet.do", "/DeleteRestaurantServlet.do", "/DeleteUserServlet.do",
		"/DeleteReviewServlet.do","/ListRestaurantServlet.do", "/LoadReviewsServlet.do","/LogOutServlet.do", 
		"/MakeOrderServlet.do","/MyRestaurantsServlet.do", "/MyOrdersServlet.do", "/MyRestaurantsServlet.do", "/RelatedRestaurantsServlet.do",
		"/RestaurantDetailsServlet.do", "/SearchServlet.do", "/StateServlet.do","/UpdateDishesServlet.do", 
		"/UpdateRestaurantServlet.do", "/UpdateReviewServlet.do","/UpdateUserServlet.do" })
																	

public class LoginFilter  implements jakarta.servlet.Filter {


	public void init(FilterConfig fc) {

		// TODO complete the code here

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession(true);

		if (session.getAttribute("user") == null) {
			res.sendRedirect(req.getContextPath() + "/LoginServlet.do");
		} else {
			chain.doFilter(request, response);
		}


	}
	public void destroy() {
		// do the cleanup staff
	}

}