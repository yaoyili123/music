package controller;

import dao.*;
import entities.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "DataServlet",
        urlPatterns = {"/data"}
    )
//TODO:提供数据的显示
public class DataServlet extends HttpServlet {

    private SingerDaoImpl singerDao = new SingerDaoImpl();
    private AlbumDaoImpl albumDao = new AlbumDaoImpl();
    private SheetDaoImpl sheetDao = new SheetDaoImpl();
    private SongDaoImpl songDao = new SongDaoImpl();
    private UserDaoImpl userDao = new UserDaoImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "list" : showList(request, response);      //显示列表
                break;
            case "detail": showDetail(request, response);   //显示详情
                break;
            case "search": showResult(request, response);   //搜索引擎结构
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dataClass = request.getParameter("class");   //数据类型
        //字段类型
        String dataType = request.getParameter("type");
        List<String> types = new ArrayList<String>();

        if (dataType == null) { //没有字段类型要求，就查找全部信息并分页显示
            switch (dataClass) {
                case "singer" :
                List<Singer> singers = singerDao.findAll();
                types = singerDao.findTypes();
                request.setAttribute("types", types);
                request.setAttribute("singers", singers);
                request.getRequestDispatcher("/WEB-INF/view/singer_list.jsp").forward(request, response);
                break;
            case "album" :
                List<Album> albums = albumDao.findAll();
                types = albumDao.findTypes();
                request.setAttribute("types", types);
                request.setAttribute("albums", albums);
                request.getRequestDispatcher("/WEB-INF/view/album_list.jsp").forward(request, response);
                break;
            case "sheet" :
                List<Sheet> sheets = sheetDao.findAll();
                types = sheetDao.findTypes();
                request.setAttribute("types", types);
                request.setAttribute("sheets", sheets);
                request.getRequestDispatcher("/WEB-INF/view/sheet_list.jsp").forward(request, response);
                break;
            }
        } else {    //有类型要求, 显示特定类型
            URLDecoder.decode(dataType, "utf-8");
            request.setAttribute("dataType", dataType);
            switch (dataClass) {
                case "singer" :
                    List<Singer> singers = singerDao.findByType(dataType);
                    types = singerDao.findTypes();
                    request.setAttribute("types", types);
                    request.setAttribute("singers", singers);
                    request.getRequestDispatcher("/WEB-INF/view/singer_list.jsp").forward(request, response);
                    break;
                case "album" :
                    List<Album> albums = albumDao.findByType(dataType);
                    types = albumDao.findTypes();
                    request.setAttribute("types", types);
                    request.setAttribute("albums", albums);
                    request.getRequestDispatcher("/WEB-INF/view/album_list.jsp").forward(request, response);
                    break;
                case "sheet" :
                    List<Sheet> sheets = sheetDao.findByType(dataType);
                    types = sheetDao.findTypes();
                    request.setAttribute("types", types);
                    request.setAttribute("sheets", sheets);
                    request.getRequestDispatcher("/WEB-INF/view/sheet_list.jsp").forward(request, response);
                    break;
            }
        }
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dataClass = request.getParameter("class");   //数据类型
        int id = Integer.parseInt(request.getParameter("id"));     //id
        List<Song> songs = null;
        List<Album> albums = null;


        switch (dataClass) {
            case "singer":
                Singer singer = singerDao.findItem(id);
                List<Singer> singers = singerDao.findOthers(id);    //其他歌手
                songs = songDao.findBySinger(id);        //获取歌曲列表
                albums = albumDao.findBySinger(id);     //获取专辑列表
                request.setAttribute("songs", songs);
                request.setAttribute("albums", albums);
                request.setAttribute("others", singers);
                request.setAttribute("singer", singer);
                request.getRequestDispatcher("/WEB-INF/view/singer_detail.jsp").forward(request, response);
                break;
            case "album":
                Album album = albumDao.findItem(id);
                albums = albumDao.findOthers(id);
                songs = songDao.findByAlbum(id);        //获取歌曲列表
                request.setAttribute("songs", songs);
                request.setAttribute("others", albums);
                request.setAttribute("album", album);
                request.getRequestDispatcher("/WEB-INF/view/album_detail.jsp").forward(request, response);
                break;
            case "sheet":
                Sheet sheet = sheetDao.findItem(id);
                List<Sheet> sheets = sheetDao.findOthers(id);
                songs = songDao.findBySheet(id);        //获取歌曲列表
                request.setAttribute("songs", songs);
                request.setAttribute("others", sheets);
                request.setAttribute("sheet", sheet);
                request.getRequestDispatcher("/WEB-INF/view/sheet_detail.jsp").forward(request, response);
                break;
        }
    }

    private void showResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String key = request.getParameter("key");
        request.setAttribute("key", key);
        //转换成模式
        StringBuffer pattern = new StringBuffer(key);
        pattern.append('%');
        pattern.insert(0, '%');

        List<Singer> singers = singerDao.findLike(pattern.toString());
        List<Song> songs = songDao.findLike(pattern.toString());
        List<Sheet> sheets = sheetDao.findLike(pattern.toString());
        List<Album> albums = albumDao.findLike(pattern.toString());
        List<User> users = userDao.findLike(pattern.toString());
        request.setAttribute("singers", singers);
        request.setAttribute("songs", songs);
        request.setAttribute("sheets", sheets);
        request.setAttribute("albums", albums);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(request, response);
    }
}
