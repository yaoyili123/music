package controller;

import dao.SingerDaoImpl;
import entities.DataBean;
import entities.Singer;
import utils.UploadUtil;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(
        name = "SingerManagerServlet",
        urlPatterns = "/singer"
)
//TODO:歌手管理
public class SingerManagerServlet extends HttpServlet {

    private SingerDaoImpl singerDao = new SingerDaoImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String type = request.getParameter("type");
        if(action == null)
            action = "show";
        switch(action)
        {
            case "delete": delete(request, response);	//删除操作
                break;
            case "update": getSinger(request, response);	//将数据发送的修改页面
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
            case "insert": insert(request, response);	//插入操作
                break;
            case "update": update(request, response);	//编辑处理
                break;
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int singerid = Integer.parseInt(request.getParameter("singerid"));

        if (singerDao.delete(singerid)) {
            request.setAttribute("message", "删除成功");
            request.setAttribute("successed", true);
        }
        else {
            request.setAttribute("message", "删除失败");
            request.setAttribute("successed", false);
        }
        request.getRequestDispatcher("/WEB-INF/view/reminder.jsp")
                .forward(request, response);
    }
    //获取歌手信息并发送到修改表单
    private void getSinger(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        String singerid = request.getParameter("singerid");
        Singer data = singerDao.findItem(Integer.parseInt(singerid));
        //设置数据模型，传给视图层
        request.setAttribute("data", data);
        //转发请求，发送数据到jsp
        request.getRequestDispatcher("/WEB-INF/view/update_singer.jsp").forward(request, response);
    }

    //获取歌手信息并发送到修改表单
    private void show(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        List<DataBean> dataset = singerDao.findAll();
        Utils.showPage(request, dataset);
        request.getRequestDispatcher("/WEB-INF/view/admini_singer.jsp").forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        int singerid = Integer.parseInt((String)request.getParameter("singerid"));
        Map<String, String> params = UploadUtil.upload(request);
        Singer old = singerDao.findItem(singerid);

        Singer singer = new Singer();
        singer.setSingerid(singerid);
        String name = params.get("name");
        singer.setName(name);

        if (name.isEmpty()) {
            response.getWriter().println("信息不完整，不授予修改");
            response.getWriter().append("<a href=\"showall.jsp\">返回</a>");
        }
        else {
            String detail = params.get("detail");
            String type = params.get("type");
            String image = params.get("image");
            String city = params.get("city");
            singer.setDetail(detail);
            singer.setCity(city);
            singer.setType(type);
            singer.setImage(image);
            //没图就用老图
            if (image == null) {
                image = old.getImage();
            }

            if (singerDao.update(singer)) {
                request.setAttribute("message", "更新成功");
                request.setAttribute("successed", true);
            }
            else {
                request.setAttribute("message", "更新失败");
                request.setAttribute("successed", false);
            }
            request.getRequestDispatcher("/WEB-INF/view/reminder.jsp")
                    .forward(request, response);
        }
    }
    //处理插入
    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        Map<String, String> params = UploadUtil.upload(request);
        Singer singer = new Singer();
        String name = params.get("name");
        singer.setName(name);

        if (name.isEmpty()) {
            response.getWriter().println("信息不完整，不授予修改");
            response.getWriter().append("<a href=\"showall.jsp\">返回</a>");
        }
        else {
            String detail = params.get("detail");
            String type = params.get("type");
            String image = params.get("image");
            String city = params.get("city");
            singer.setDetail(detail);
            singer.setCity(city);
            singer.setType(type);
            singer.setImage(image);
            //没图就用默认图
            if (image == null) {
                image = "zhihu.jpg";
            }

            if (singerDao.save(singer)) {
                request.setAttribute("message", "插入成功");
                request.setAttribute("successed", true);
            }
            else {
                request.setAttribute("message", "插入失败");
                request.setAttribute("successed", false);
            }
            request.getRequestDispatcher("/WEB-INF/view/reminder.jsp")
                    .forward(request, response);
        }
    }
}
