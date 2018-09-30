package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//TODO:管理员身份验证
public class AdministratorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("identity") == null
                || !session.getAttribute("identity").equals("administrator"))
            (response).sendRedirect("error.jsp");
        else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
