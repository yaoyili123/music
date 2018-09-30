package controller;

import dao.AlbumDaoImpl;
import dao.SingerDaoImpl;
import entities.Album;
import entities.DataBean;
import utils.UploadUtil;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//TODO:专辑管理
@WebServlet(
        name = "AlbumManagerServlet",
        urlPatterns = "/album"
)
public class AlbumManagerServlet extends HttpServlet {

    private AlbumDaoImpl albumDao = new AlbumDaoImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null)
            action = "show";
        switch (action) {
            case "delete": delete(request, response);
                break;
            case "update": getAlbum(request, response); //获取数据并转发到修改页面
                break;
            case "show": show(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "update": update(request, response);
                break;
            case "insert": insert(request, response);
                break;
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int albumid = Integer.parseInt(request.getParameter("albumid"));
        Album old = albumDao.findItem(albumid); //旧数据
        if (albumDao.delete(albumid)) {
            request.setAttribute("message", "删除成功");
            request.setAttribute("successed", true);
        }
        else {
            request.setAttribute("message", "删除失败");
            request.setAttribute("successed", false);
        }
        request.setAttribute("singerid", old.getSingerid());
        request.getRequestDispatcher("/WEB-INF/view/reminder.jsp")
                .forward(request, response);
    }

    public void insert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取上传文件以及表单
        int singerid = Integer.parseInt(request.getParameter("singerid"));
        Map<String, String> params = UploadUtil.upload(request);
        String name = params.get("name");
        String company = params.get("company");
        String type = params.get("type");
        String image = params.get("image");
        String detail = params.get("detail");
        Date publishtime = Date.valueOf(params.get("publishtime"));
        //没图就用默认图
        if (image == null) {
            image = "zhihu.jpg";
        }

        //构造MODEL
        SingerDaoImpl singerDao = new SingerDaoImpl();
        String singer = singerDao.findItem(singerid).getName();
        Album album = new Album();
        album.setSingerid(singerid);
        album.setName(name);
        album.setPublishtime(publishtime);
        album.setSinger(singer);
        album.setCompany(company);
        album.setType(type);
        album.setDetail(detail);
        album.setImage(image);

        if (albumDao.save(album)) {
            request.setAttribute("message", "插入成功");
            request.setAttribute("successed", true);
        }
        else {
            request.setAttribute("message", "插入失败");
            request.setAttribute("successed", false);
        }
        request.setAttribute("singerid", singerid);
        request.getRequestDispatcher("/WEB-INF/view/reminder.jsp")
                .forward(request, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int albumid = Integer.parseInt(request.getParameter("albumid"));
        Album old = albumDao.findItem(albumid); //旧数据

        //获取上传文件以及表单
        Map<String, String> params = UploadUtil.upload(request);
        String name = params.get("name");
        String company = params.get("company");
        String type = params.get("type");
        String image = params.get("image");
        String detail = params.get("detail");
        Date publishtime = Date.valueOf(params.get("publishtime"));
        //没图就用老图
        if (image == null) {
            image = old.getImage();
        }

        //构造MODEL
        Album album = new Album();
        album.setSingerid(old.getSingerid());
        album.setName(name);
        album.setPublishtime(publishtime);
        album.setSinger(old.getSinger());
        album.setCompany(company);
        album.setType(type);
        album.setDetail(detail);
        album.setImage(image);
        album.setAlbumid(albumid);

        if (albumDao.update(album)) {
            request.setAttribute("message", "更新成功");
            request.setAttribute("successed", true);
        }
        else {
            request.setAttribute("message", "更新失败");
            request.setAttribute("successed", false);
        }
        request.setAttribute("singerid", old.getSingerid());
        request.getRequestDispatcher("/WEB-INF/view/reminder.jsp")
                .forward(request, response);
    }

    public void getAlbum(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int albumid = Integer.parseInt(request.getParameter("albumid"));
        Album data = new Album();
        data = albumDao.findItem(albumid);
        request.setAttribute("data", data);
        request.getRequestDispatcher("/WEB-INF/view/update_album.jsp").forward(request, response);
    }

    public void show(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tmp = (String) request.getParameter("singerid");
        if (tmp != null) {
            int singerid = Integer.parseInt(tmp);
            request.setAttribute("singerid", singerid);
            List<DataBean> dataset = albumDao.findBySinger(singerid);
            Utils.showPage(request, dataset);
        }
        request.getRequestDispatcher("/WEB-INF/view/admini_album.jsp").forward(request, response);
    }

}
