package utils;

import entities.DataBean;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    //分页显示
    public static void showPage(HttpServletRequest request, List<DataBean> dataBeans) {

        int pageSize = 7;
        int rowCount;       //总行数
        int pageCount;      //总页数
        int pageNo;         //当前页
        String strPage = request.getParameter("page");
        if (strPage==null)
            pageNo = 1;
        else {
            pageNo = Integer.parseInt(strPage);
            if (pageNo<1)
                pageNo = 1;
        }

        rowCount = dataBeans.size();
        pageCount = (int)Math.ceil((double)rowCount / (double)pageSize);
        int first=(pageNo-1)*pageSize;

        List<DataBean> dataset = new ArrayList<>();
        for (int i = 0; i < pageSize && first + i < rowCount; i++) {
            dataset.add(dataBeans.get(first+i));
        }
        request.setAttribute("dataset", dataset);
        request.setAttribute("pageNo", new Integer(pageNo));
        request.setAttribute("pageCount", new Integer(pageCount));
    }
    //删除图片
    public static void deleteImg(String filename) {
        File img = new File("J:/Program/JAVA/IDEA/fuck/src/main/webapp/static/images/"+
                filename);
        img.delete();
    }
}
