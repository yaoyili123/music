package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.SheetDaoImpl;
import dao.SongDao;
import dao.SongDaoImpl;
import dao.UserDaoImpl;
import entities.Sheet;
import entities.Song;
import entities.User;
import utils.EncoderHandler;
import utils.JsonUtil;

import java.util.HashMap;
import java.util.List;

@WebServlet(
        name = "UserServlet",
        urlPatterns = {"/user"}
)
/*TODO:处理与用户相关的请求
* */
public class UserServlet extends HttpServlet {

	private String administrator = "yaoyili";	//管理员账号
	private String key = "yao123456";			//管理员密码
	private UserDaoImpl userDao = new UserDaoImpl();
	private JsonUtil jsonUtil = new JsonUtil();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String action = request.getParameter("action");
			if (action == null) {
				action = "show";
			}
			switch (action) {
				case "login" : jumpAdminiPage(request, response);	//获取管理员页面
					break;
				case "show":
				default: response.sendRedirect("/main");
					break;
			}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String action = request.getParameter("action");
			if (action == null)
				action = "show";
			switch (action) {
				case "login" : login(request, response);			//登录表单提交
					break;
				case "register" : register(request, response);	//注册表单提交
					break;
				case "modify" : modifyPassword(request, response);	//修改密码
					break;
				case "show":
				default : response.sendRedirect("/main");
					break;
			}
	}

	//登录
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String isAdmini = request.getParameter("isAdmini");
		HashMap<String, Object> data = new HashMap<String, Object>();
		PrintWriter content = response.getWriter();

		//若是管理员
		if (isAdmini.equals("true")) {
			if (username.equals(administrator) && password.equals(key)){
				//设置会话
				HttpSession session = request.getSession();
				session.setAttribute("identity", "administrator");
				//跳转到管理员页面
				data.put("status", "successed");
				content.print(jsonUtil.mapToJson(data));
			} else {
				data.put("status", "failed");
				content.print(jsonUtil.mapToJson(data));
			}
		} else {
			//构建Model
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);

			//登录失败
			if (userDao.findItem(user) == null) {	//用户密码错误
				data.put("status", "failed");
				content.print(jsonUtil.mapToJson(data));
			}
			else {
				//登录成功创建一个加密cookie:username-password-key
				Cookie cookie = new Cookie("music_user", username + "-" + password + "-" +
						EncoderHandler.sha1(username + "$$" + password));
				cookie.setMaxAge(3 * 60 * 60);
                cookie.setPath("/");
				response.addCookie(cookie);

				//设置会话用于辨别身份
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setAttribute("isLogined", true);

				//设置响应
				data.put("status", "successed");
				content.print(jsonUtil.mapToJson(data));
			}
		}
	}
	//注册
	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		HashMap<String, Object> data = new HashMap<String, Object>();
		PrintWriter content = response.getWriter();

		//调用DAO
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setNickname(nickname);
		//判断是否已经有重复的用户
		if (userDao.findItem(user) != null) {
			data.put("status", "failed");
			content.print(jsonUtil.mapToJson(data));
		} else {
			//注册成功创建一个加密cookie:username-password-key
			Cookie cookie = new Cookie("music_user", username + "-" + password + "-" +
					EncoderHandler.sha1(username + "$$" + password));
			cookie.setMaxAge(3 * 60 * 60);
			cookie.setPath("/");
			response.addCookie(cookie);
			//设置会话用于辨别身份
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("isLogined", true);
			userDao.save(user);
			data.put("status", "successed");
			content.print(jsonUtil.mapToJson(data));
		}
	}

	//切换到管理员页面
	private void jumpAdminiPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("identity").equals("administrator")) {
			request.getRequestDispatcher("/WEB-INF/view/admini.jsp").forward(request, response);
		}
	}

	//修改密码
	private void modifyPassword(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		User olduser = (User) session.getAttribute("user");
		String oldpw = request.getParameter("oldpw");
		String newpw = request.getParameter("newpw");
		HashMap<String, Object> data = new HashMap<String, Object>();
		PrintWriter content = response.getWriter();

		//构建Model
		User user = new User();
		user.setUsername(olduser.getUsername());
		user.setPassword(oldpw);

		//登录失败
		if (userDao.findItem(user) == null) {	//原用户密码错误
			data.put("status", "failed");
			data.put("info", "原用户名或者密码错误");
			content.print(jsonUtil.mapToJson(data));
		}
		else {
			user.setPassword(newpw);
			userDao.update(user);

			//消除登录cookie
			logout(request, response);
			//设置响应
			data.put("status", "successed");
			content.print(jsonUtil.mapToJson(data));
		}
	}

    //注销
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//销毁cookie
		Cookie cookie = new Cookie("music_user", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
	}
}
