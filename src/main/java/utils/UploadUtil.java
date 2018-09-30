package utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/*TODO:上传工具包
 * */
public class UploadUtil {
    /*封装文件上传过程
    @param 请求对象
    @return 文件名数组
    */
    public static Map upload(HttpServletRequest request)
            throws ServletException, IOException {

        RequestContext requestContext = new ServletRequestContext(request);
        Map<String, String> forms = new HashMap<String, String>();

        //判断上传的东西是否是文件(MIME:Multipart/Xxx)
        if (FileUpload.isMultipartContent(requestContext)) {
            //生成fileitem实例的工厂，且可配置大小SizeThresdhold和文件目录Repository
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置一个目录用于临时储存
            factory.setRepository(new File("J:/temp/"));
            //获取请求中的文件
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(20000000);       //设置size
            List items = new ArrayList();
            try {
                items = upload.parseRequest(request);  //获取关联上传文件的表单的列表
            } catch (FileUploadException e1) {
                System.out.println("文件上传发生错误" + e1.getMessage());
            }

            Iterator it = items.iterator();
            //下一个元素是否存在
            while (it.hasNext()) {
                //生成FileItem
                FileItem fileItem = (FileItem) it.next();
                //判断表单是否是文件，是返回true，反之，false
                if (fileItem.isFormField()) {
                    //getFieldName()获取表单元素的name属性，getName()获取文件名
                    //获取表单值
                    forms.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("iso8859-1"),
                            "utf-8"));
                } else {
                    //临时文件不存在, 且表单列表仍有文件, 就将文件保存到临时目录
                    if (fileItem.getName() != null && fileItem.getSize() != 0) {
                        File newFile = new File("J:/Program/JAVA/IDEA/fuck/src/main/webapp/static/images/" +
                                fileItem.getName());
                        try {
                            fileItem.write(newFile);  //临时文件写入本地
                            forms.put(fileItem.getFieldName(), fileItem.getName());
                            return forms;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        System.out.println("文件没有选择 或 文件内容为空");
        return forms;
    }
}
