package es.unex.pi.filter;

import java.io.IOException;

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


@WebFilter(urlPatterns = { "/AddHostServlet.do", "/BookServlet.do", "/CategoryServlet.do", "/DeleteHostServlet.do", "/DeleteProfile.do",
        "/DetailsFilterServlet.do", "/DetailsServlet.do", "/EditHostServlet.do", "/GraphicsServlet.do", "/LikesFilterServlet.do",
        "/LogoutServlet.do", "/MyHostsServlet.do", "/PriceRangeServlet.do", "/ProfileServlet.do", "/ServiceServlet.do", "/StatusServlet",
        "/WebpageServlet" })

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
    
	private FilterConfig fc;
	
    /**
     * @see HttpFilter#HttpFilter()
     */
    public LoginFilter() {
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
        HttpServletRequest requ = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = requ.getSession (true);

        if (session.getAttribute("usuario") == null) {
            resp.sendRedirect (requ.getContextPath () + "/LoginServlet.do");
        } else {
            chain.doFilter (request, response);
        }
    }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
