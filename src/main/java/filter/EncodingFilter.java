package filter;

import dao.AlbumDaoImpl;
import entities.Album;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//对请求进行编码，并进行点击量统计
public class EncodingFilter implements Filter {

    private ServletContext context;
    private AlbumDaoImpl albumDao = new AlbumDaoImpl();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            HttpServletResponse response = (HttpServletResponse)servletResponse;

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            //统计热度
            String type = request.getParameter("class");
            String tmp = request.getParameter("id");
            if (type != null && tmp != null) {
                int id = Integer.parseInt(tmp);
                context = request.getServletContext();
                Map<Integer, Integer> albums = (Map<Integer, Integer>) context.getAttribute("albumClick");
                Map<Integer, Integer> sheets = (Map<Integer, Integer>) context.getAttribute("sheetClick");
                Map<Integer, Integer> singers = (Map<Integer, Integer>) context.getAttribute("singerClick");

                //只能由一个线程访问, 因为递增不是原子性操作
                synchronized (this) {
                    switch (type) {
                        case "sheet":
                            int value = sheets.get(id);
                            sheets.put(id, ++value);
                            break;
                        case "album":
                            Album album = albumDao.findItem(id);
                            //改变该专辑歌手的点击量
                            int singerClick = singers.get(album.getSingerid());
                            singers.put(album.getSingerid(), ++singerClick);
                            //改变专辑的点击量
                            int albumClick = albums.get(id);
                            albums.put(id, ++albumClick);
                            break;
                        case "singer":
                            int value2 = singers.get(id);
                            singers.put(id, ++value2);
                            break;
                    }
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
