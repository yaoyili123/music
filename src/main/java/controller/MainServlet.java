package controller;

import dao.AlbumDaoImpl;
import dao.SheetDaoImpl;
import dao.SingerDaoImpl;
import entities.Album;
import entities.DataBean;
import entities.Sheet;
import entities.Singer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@WebServlet(
        name = "MainServlet",
        urlPatterns = {"/main"}
)
//TODO:响应主页访问请求
public class MainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取热度
        ServletContext context = request.getServletContext();
        Map<Integer, Integer> albumClick = (Map<Integer, Integer>) context.getAttribute("albumClick");
        Map<Integer, Integer> sheetClick = (Map<Integer, Integer>) context.getAttribute("sheetClick");
        Map<Integer, Integer> singerClick = (Map<Integer, Integer>) context.getAttribute("singerClick");
        //获取歌单数据
        SheetDaoImpl sheetDao = new SheetDaoImpl();
        List<Sheet> dataset = sheetDao.findAll();
        if (dataset.size() > 0) {
            //根据热度排序
            Collections.sort(dataset, new RankComparator<Sheet>(sheetClick));
            Stream<Sheet> sheetStream = dataset.stream().limit(8);
            List<Sheet> sheets = new ArrayList<Sheet>();
            sheetStream.forEach(sheets::add);       //转换成集合
            request.setAttribute("sheets", sheets);
        }
        //获取专辑数据
        AlbumDaoImpl albumDao = new AlbumDaoImpl();
        List<Album> dataset2 = albumDao.findAll();
        if (dataset2.size() > 0) {
            Collections.sort(dataset2, new RankComparator<Album>(albumClick));
            Stream<Album> albumStream = dataset2.stream().limit(8);
            List<Album> albums = new ArrayList<Album>();
            albumStream.forEach(albums::add);       //转换成集合
            request.setAttribute("albums", albums);
        }
        //获取歌手数据
        SingerDaoImpl singerDao = new SingerDaoImpl();
        List<Singer> dataset3 = singerDao.findAll();
        if (dataset3.size() > 0) {
            Collections.sort(dataset3, new RankComparator<Singer>(singerClick));
            Stream<Singer> singerStream = dataset3.stream().limit(5);
            List<Singer> singers = new ArrayList<Singer>();
            singerStream.forEach(singers::add);       //转换成集合
            request.setAttribute("singers", singers);
        }

        request.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(request, response);
    }
}

//TODO:热度排序对象
class RankComparator<T extends DataBean> implements Comparator<T> {

    private Map<Integer, Integer> rank;

    public RankComparator(Map map) {
        this.rank = map;
    }

    @Override
    public int compare(T obj1, T obj2) {
        if (rank.get(obj1.getId()) == rank.get(obj2.getId()))
            return 0;
        else if(rank.get(obj1.getId()) > rank.get(obj2.getId()))
            return -1;
        else
            return 1;
    }
}

