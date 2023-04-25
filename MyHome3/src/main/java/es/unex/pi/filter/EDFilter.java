package es.unex.pi.filter;

import java.io.IOException;
import java.sql.Connection;

import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter(urlPatterns = { "/DeleteHostServlet.do", "/EditHostServlet.do" })

/**
 * Servlet Filter implementation class EditFilter
 */
public class EDFilter implements Filter {
    
	private FilterConfig customFilter;
	
    /**
     * @see HttpFilter#HttpFilter()
     */
    public EDFilter() {
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
		Connection conn = (Connection) customFilter.getServletContext().getAttribute("dbConn");
		HostingDAO hostDAO = new JDBCHostingDAOImpl ();
		hostDAO.setConnection (conn);
		
		HttpServletRequest requ = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        HttpSession currentSession = requ.getSession (true);
        User currentUser = (User) currentSession.getAttribute ("usuario");
        Hosting currentHost = hostDAO.get (Long.parseLong (requ.getParameter ("id")));
        
        if (currentUser == null || currentUser.getId () != currentHost.getIdu ()) {
        	RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/NotAvailable.jsp");
            vista.forward (request, response);
        }
        else {
        	chain.doFilter (requ, resp);
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.customFilter = fConfig;
	}

}
