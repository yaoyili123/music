package controller;

import dao.SheetDaoImpl;
import dao.SongDaoImpl;
import dao.UserDaoImpl;
import entities.Sheet;
import entities.Song;
import entities.User;
import utils.JsonUtil;
import utils.UploadUtil;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@WebServlet(
        name = "SheetManagerServlet",
        urlPatterns = {"/userSheet"}
)
//TODO:用户歌单管理
public class SheetManagerServlet extends HttpServlet {

    private SheetDaoImpl sheetDao = new SheetDaoImpl();
    private SongDaoImpl songDao = new SongDaoImpl();
    private UserDaoImpl userDao = new UserDaoImpl();
    private JsonUtil jsonUtil = new JsonUtil();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setAttribute("action", action); //记住响应参数
        if (action == null) {
            action = "show";
        }

        try {
            switch (action) {
                case "userPage" : userPage(request, response);	//用户主页
                    break;
                case "userMusic" :
                case "update" : userMusic(request, response);	//用户歌单页面
                    break;
                case "delete": deleteSheet(request, response); //删除歌单
                    break;
                case "addSong": showSheetList(request, response);//添加歌曲到歌单功能中，获取歌单数据
                    break;
                case "deleteSong": deleteSongFromSheet(request, response);
                case "show":
                default: //response.sendRedirect("/main");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setAttribute("action", action); //记住响应参数
        if (action == null)
            action = "show";

        try {
            switch (action) {
                case "update": updateSheet(request, response);  //提交修改
                    break;
                case "addSheet": createSheet(request, response); //创建新歌单
                    break;
                case "addSong": addSongToSheet(request, response); //添加歌曲到歌单中
                    break;
                case "show":
                default : response.sendRedirect("/main");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //用户主页
    private void userPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        //没有用户名，则是从个人主页跳过来的
        if (username == null) {
            HttpSession session = request.getSession(false);
            //未登录直接跳转
            if (session == null || !(boolean)session.getAttribute("isLogined")) {
                request.getRequestDispatcher("/WEB-INF/view/user_main.jsp")
                        .forward(request, response);
            }
            //获取用户当前拥有的歌单
            username = (String) session.getAttribute("username");
        } else {
            request.setAttribute("isOtherPage", true);
        }

        List<Sheet> sheets = sheetDao.findByUsername(username);
        User user = userDao.getUser(username);
        request.setAttribute("sheets", sheets);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/view/user_main.jsp")
                .forward(request, response);
    }

    //我的音乐
    private void userMusic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        HttpSession session = request.getSession(false);

        //未登录直接跳转
        if (session == null || !(boolean)session.getAttribute("isLogined")) {
            request.getRequestDispatcher("/WEB-INF/view/user_sheet.jsp")
                    .forward(request, response);
        }

        //已经登录,获取用户的歌单并发送
        List<Sheet> sheets = sheetDao.findByUsername((String) session.getAttribute("username"));
        List<Song> songs = null;

        if (sheets.size() > 0) {
            //有id
            if (id != null) {
                int sheetid = Integer.parseInt(id);
                songs = songDao.findBySheet(sheetid);
                //使用流进行过滤
                List<Sheet> tmp = sheets.stream().filter(sheet -> sheet.getId() == sheetid)
                        .collect(Collectors.toList());
                if (tmp.size() > 0) {
                    Sheet nowSheet = tmp.get(0);
                    request.setAttribute("now", nowSheet);
                }
            } else {
                songs = songDao.findBySheet(sheets.get(0).getSheetid());
                request.setAttribute("now", sheets.get(0));
            }
            request.setAttribute("sheets", sheets);
            request.setAttribute("songs", songs);
        }
        request.getRequestDispatcher("/WEB-INF/view/user_sheet.jsp").
                forward(request, response);
    }

    //提交修改
    private void updateSheet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        //获取上传文件以及表单
        Map<String, String> params = UploadUtil.upload(request);
        String name = params.get("name");
        String detail = params.get("detail");
        String type = params.get("type");
        String image = params.get("image");
        Sheet old = sheetDao.findItem(id);

        //没新图片就使用老图片
        if (image == null) {
            image = old.getImage();
        }

        //第一次创建表单时把用户信息放入会话
        User user = null;
        HttpSession session = request.getSession(false);
        if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
        } else {
            user = userDao.getUser((String) session.getAttribute("username"));
            session.setAttribute("user", user);
        }

        //设置MODEL
        Sheet sheet = new Sheet();
        sheet.setNickname(user.getNickname());
        sheet.setUsername(user.getUsername());
        sheet.setName(name);
        sheet.setDetail(detail);
        sheet.setType(type);
        sheet.setImage(image);
        sheet.setSheetid(id);

        //设置结果
        Map<String, Object> result = new HashMap<>();
        if (sheetDao.update(sheet)) {
            result.put("status", "successed");
        } else {
            result.put("status", "failed");
        }
        response.getWriter().print(jsonUtil.mapToJson(result));
    }

    //删除歌单
    private void deleteSheet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        //设置结果
        Map<String, Object> result = new HashMap<>();
        //删除文件
        Sheet sheet = sheetDao.findItem(id);
        Utils.deleteImg(sheet.getImage());
        //删除元组
        if (sheetDao.delete(id)) {
            result.put("status", "successed");
        } else {
            result.put("status", "failed");
        }
        response.getWriter().print(jsonUtil.mapToJson(result));
    }

    //创建新歌单
    private void createSheet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //获取上传文件以及表单
            Map<String, String> params = UploadUtil.upload(request);
            String name = params.get("name");
            String detail = params.get("detail");
            String type = params.get("type");
            String image = params.get("image");
            if (image == null) {    //没上传图片就使用默认图片
                image = "zhihu.jpg";
            }

            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            //设置MODEL
            Sheet sheet = new Sheet();
            sheet.setNickname(user.getNickname());
            sheet.setUsername(user.getUsername());
            sheet.setName(name);
            sheet.setDetail(detail);
            sheet.setType(type);
            sheet.setImage(image);

            //设置结果
            Map<String, Object> result = new HashMap<>();
            if (sheetDao.save(sheet)) {
                result.put("status", "successed");
            } else {
                result.put("status", "failed");
            }
            response.getWriter().print(jsonUtil.mapToJson(result));
    }

    //返回歌单列表
    private void showSheetList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SheetDaoImpl sheetDao = new SheetDaoImpl();
        HttpSession session = request.getSession(false);
        List<Sheet> dataset = sheetDao.findByUsername((String) session.getAttribute("username"));
        response.getWriter().print(jsonUtil.ObjectsToJson(dataset));
    }

    //添加歌曲到歌单中
    private void addSongToSheet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sheetid = Integer.parseInt(request.getParameter("sheetid"));
        int songid = Integer.parseInt(request.getParameter("songid"));

        Map<String, Object> result = new HashMap<>();
        //是否已经存在于歌单
        if(sheetDao.checkSongInSheet(sheetid, songid)) {
            result.put("status", "failed");
        }else {
            sheetDao.addSongToSheet(sheetid, songid);
            result.put("status", "successed");
        }
        response.getWriter().print(jsonUtil.mapToJson(result));
    }

    //从歌单中删除歌曲
    private void deleteSongFromSheet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sheetid = Integer.parseInt(request.getParameter("sheetid"));
        int songid = Integer.parseInt(request.getParameter("songid"));

        Map<String, Object> result = new HashMap<>();
        if(sheetDao.deleteSongFromSheet(sheetid, songid)) {
            result.put("status", "successed");
        }else {
            result.put("status", "failed");
        }
        response.getWriter().print(jsonUtil.mapToJson(result));
    }
}
