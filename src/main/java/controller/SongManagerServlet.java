package controller;

import dao.*;
import entities.Album;
import entities.DataBean;
import entities.Singer;
import entities.Song;
import utils.UploadUtil;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(
        name = "SongManagerServlet",
        urlPatterns = "/song"
)
//TODO:歌曲管理
public class SongManagerServlet extends HttpServlet {

    private SongDaoImpl songDao = new SongDaoImpl();
    private AlbumDaoImpl albumDao = new AlbumDaoImpl();
    private SingerDaoImpl singerDao = new SingerDaoImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null)
            action = "show";
        switch(action)
        {
            case "delete": delete(request, response);	//删除操作
                break;
            case "update": getSong(request, response);	//编辑操作
                break;
            case "show":
            default: show(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch(action)
        {
            case "insert":insert(request, response);
                break;
            case "update":update(request, response);
                break;

        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        int songid = Integer.parseInt(request.getParameter("songid"));
        Song old = songDao.findItem(songid);
        if (songDao.delete(songid)) {
            request.setAttribute("message", "删除成功");
            request.setAttribute("successed", true);
        }
        else {
            request.setAttribute("message", "删除失败");
            request.setAttribute("successed", false);
        }
        request.setAttribute("albumid", old.getAlbumid());
        request.getRequestDispatcher("/WEB-INF/view/reminder.jsp")
                .forward(request, response);
    }

    private void getSong(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        String songid = request.getParameter("songid");
        Song data = songDao.findItem(Integer.parseInt(songid));

        request.setAttribute("data", data);
        request.getRequestDispatcher("/WEB-INF/view/update_song.jsp").forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        int songid = Integer.parseInt(request.getParameter("songid"));
        int albumid = Integer.parseInt(request.getParameter("albumid"));
        Map<String, String> params = UploadUtil.upload(request);
        Song song = new Song();

        String name = params.get("name");
        String lyric = params.get("lyric");
        String time = params.get("time");
        song.setName(name);
        song.setSongid(songid);
        song.setLyric(lyric);
        song.setTime(time);

        if (songDao.update(song)) {
            request.setAttribute("message", "更新成功");
            request.setAttribute("successed", true);
        }
        else {
            request.setAttribute("message", "更新失败");
            request.setAttribute("successed", false);
        }
        request.setAttribute("albumid", albumid);
        request.getRequestDispatcher("/WEB-INF/view/reminder.jsp")
                .forward(request, response);
    }
    //处理插入
    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{

        int albumid = Integer.parseInt(request.getParameter("albumid"));
        Map<String, String> params = UploadUtil.upload(request);
        Song song = new Song();
        String name = params.get("name");

        if (name.isEmpty()) {
            response.getWriter().println("信息不完整，不授予修改");
            response.getWriter().append("<a href=\"showall.jsp\">返回</a>");
        }
        else {

            Album album = albumDao.findItem(albumid);
            Singer singer = singerDao.findItem(album.getSingerid());
            String lyric = params.get("lyric");
            String time = params.get("time");
            song.setName(name);
            song.setAlbumid(albumid);
            song.setSingerid(album.getSingerid());
            song.setAlbum(singer.getName());
            song.setAuther(album.getName());
            song.setLyric(lyric);
            song.setTime(time);

            if (songDao.save(song)) {
                request.setAttribute("message", "添加成功");
                request.setAttribute("successed", true);
            }
            else {
                request.setAttribute("message", "添加失败");
                request.setAttribute("successed", false);
            }
            request.setAttribute("albumid", albumid);
            request.getRequestDispatcher("/WEB-INF/view/reminder.jsp")
                    .forward(request, response);
        }
    }
    //显示全部
    private void show(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        String tmp = request.getParameter("albumid");
        if (tmp != null) {
            int albumid = Integer.parseInt(tmp);
            request.setAttribute("albumid", albumid);
            List<DataBean> dataset = songDao.findByAlbum(albumid);
            Utils.showPage(request, dataset);
        }
        request.getRequestDispatcher("/WEB-INF/view/admini_song.jsp").forward(request, response);
    }
}
