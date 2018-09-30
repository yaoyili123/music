package filter;

import dao.AlbumDaoImpl;
import dao.SheetDaoImpl;
import dao.SingerDaoImpl;
import entities.Album;
import entities.Sheet;
import entities.Singer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO：监听WEBapp
@WebListener
public class Configurator implements ServletContextListener {

    private SingerDaoImpl singerDao = new SingerDaoImpl();
    private AlbumDaoImpl albumDao = new AlbumDaoImpl();
    private SheetDaoImpl sheetDao = new SheetDaoImpl();

    //WEB容器上下文初始化
    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext context = event.getServletContext();

        //注册过滤器
        //注册用户身份验证过滤器
        FilterRegistration.Dynamic registration = context.addFilter(
                "UserLoginedFilter", new UserLoginedFilter()
        );
        registration.setAsyncSupported(true);
        registration.addMappingForUrlPatterns(null, false,
                "/main", "/user", "/data", "/userSheet"
        );

        //注册编码过滤器
        FilterRegistration.Dynamic registration1 = context.addFilter(
                "EncodingFilter", new EncodingFilter()
        );
        registration1.setAsyncSupported(true);
        registration1.addMappingForUrlPatterns(
                null, false, "/*"
        );

        //注册管理员身份验证过滤器
        FilterRegistration.Dynamic registration2 = context.addFilter(
                "AdministratorFilter", new AdministratorFilter()
        );
        registration2.setAsyncSupported(true);
        registration2.addMappingForUrlPatterns(
                null, false, "/song", "/album", "/singer"
        );

        //加载热度
        List<Sheet> dataset1 = sheetDao.findAll();
        List<Singer> dataset2 = singerDao.findAll();
        List<Album> dataset3 = albumDao.findAll();
        Map<Integer, Integer> albums = new HashMap<>();
        Map<Integer, Integer> sheets = new HashMap<>();
        Map<Integer, Integer> singers = new HashMap<>();
        for (Sheet sheet : dataset1) {
            sheets.put(sheet.getSheetid(), sheet.getClick());
        }
        for (Singer singer : dataset2) {
            singers.put(singer.getSingerid(), singer.getClick());
        }
        for (Album album : dataset3) {
            albums.put(album.getAlbumid(), album.getClick());
        }
        context.setAttribute("sheetClick", sheets);
        context.setAttribute("albumClick", albums);
        context.setAttribute("singerClick", singers);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        //更新热度
        ServletContext context = event.getServletContext();
        Map<Integer, Integer> albums = (Map<Integer, Integer>) context.getAttribute("albumClick");
        Map<Integer, Integer> sheets = (Map<Integer, Integer>) context.getAttribute("sheetClick");
        Map<Integer, Integer> singers = (Map<Integer, Integer>) context.getAttribute("singerClick");
        for (Integer key : albums.keySet()) {
            albumDao.updateClick(key, albums.get(key));
        }
        for (Integer key : sheets.keySet()) {
            sheetDao.updateClick(key, sheets.get(key));
        }
        for (Integer key : singers.keySet()) {
            singerDao.updateClick(key, singers.get(key));
        }
    }
}
