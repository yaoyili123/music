package filter;

import dao.UserDaoImpl;
import entities.User;
import utils.EncoderHandler;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//TODO：用户身份验证, 确认用户身份
public class UserLoginedFilter implements Filter {

    //在这里定义了一个cookie变量，被赋值了连接着doFilter中对象引用的地址的附近的地址，突然变成了注销之前的cookie
    //private Cookie userCookie = null;
    //原因是：Filter整个APP生命周期只会被初始化一次，所以它的属性值在这个过程中是不变的

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();

        //获取用户cookie
        Cookie[] cookies = request.getCookies();
        Cookie userCookie = null;
        if (cookies == null) {
            session.setAttribute("isLogined", false);
            chain.doFilter(servletRequest, servletResponse);  //调用过滤器链上的下一个过滤器
        } else {
            for(Cookie cookie : cookies) {
                if (cookie.getName().equals("music_user")) {
                    userCookie = cookie;
                    break;
                }
            }
            //根据请求的servlet和aciton设置会话结果
            session.setAttribute("isLogined", checkIdentity(userCookie, session));
            if ((boolean)session.getAttribute("isLogined")) {
                //第一次创建表单时把用户信息放入会话
                UserDaoImpl userDao = new UserDaoImpl();
                User user = userDao.getUser((String) session.getAttribute("username"));
                session.setAttribute("user", user);
            }
            chain.doFilter(servletRequest, servletResponse);  //调用过滤器链上的下一个过滤器
        }
    }
    //验证用户cookie是否是伪造
    private boolean checkCookie(Cookie cookie, HttpSession session) {
        String value = cookie.getValue();
        String[] values = value.split("-");
        String username = values[0], password = values[1], key = values[2];
        if (key.equals(EncoderHandler.sha1(username + "$$" + password))) {
            session.setAttribute("username", username);
            return true;
        } else
            return false;
    }

    //根据cookie判断并设置用户身份
    private boolean checkIdentity(Cookie cookie, HttpSession session) {
        if (cookie != null && !cookie.getValue().isEmpty() && checkCookie(cookie, session))
            return true;
        else
            return false;
    }

    @Override
    public void init(FilterConfig config) throws ServletException { }

    @Override
    public void destroy() { }
}
