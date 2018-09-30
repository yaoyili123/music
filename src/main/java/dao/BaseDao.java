package dao;

import org.apache.commons.beanutils.BeanUtils;
import java.sql.*;
import java.util.*;

public class BaseDao {

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private static String URL="jdbc:mysql://localhost:3306/music";
    private static String DB_USER="music";
    private static String DB_PASS="yao123456";

    /**
     * 更新的通用方法
     * @param sql   sql语句(update/insert/delete)
     * @param paramsValue  sql语句中占位符对应的值(如果没有占位符，传入null)
     */
    public int update(String sql,Object[] paramsValue){

        try {
            // 获取连接
            con = this.getConnection();
            // 创建执行命令的stmt对象
            pstmt = con.prepareStatement(sql);
            // 参数元数据, 可以查看结果集的行数、列属性名等元数据
            int count = pstmt.getParameterMetaData().getParameterCount();
            // 设置占位符参数的值
            if (paramsValue != null && paramsValue.length > 0) {
                // 循环给参数赋值
                for(int i = 0; i < count; i++) {
                    pstmt.setObject(i + 1, paramsValue[i]);
                }
            }
            // 执行更新
            int row = pstmt.executeUpdate();
            return row;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.closeAll(con, pstmt, null);
        }
    }

    /**
     * 查询的通用方法
     * @param sql  sql语句
     * @param paramsValue  占位参数
     * @param clazz 返回类型
     */
    public <T> List<T> query(String sql, Object[] paramsValue, Class<T> clazz){

        try {
            // 返回的集合
            List<T> list = new ArrayList<T>();
            // 对象
            T object = null;
            // 1. 获取连接
            con = this.getConnection();
            // 2. 创建stmt对象
            pstmt = con.prepareStatement(sql);
            // 3. 获取占位符参数的个数， 并设置每个参数的值
            int count = pstmt.getParameterMetaData().getParameterCount();
            if (paramsValue != null && paramsValue.length > 0) {
                for (int i = 0; i < paramsValue.length; i++) {
                    pstmt.setObject(i + 1, paramsValue[i]);
                }
            }
            // 4. 执行查询
            rs = pstmt.executeQuery();
            // 5. 获取结果集元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // ---> 获取列的个数
            int columnCount = rsmd.getColumnCount();

            // 6. 遍历rs
            while (rs.next()) {
                // 通过Class对象生成实例
                object = clazz.newInstance();

                // 7. 遍历每一行的每一列, 封装数据
                for (int i = 0; i < columnCount; i++) {
                    // 获取每一列的列名称
                    String columnName = rsmd.getColumnName(i + 1);
                    // 获取每一列的列名称, 对应的值
                    Object value = rs.getObject(columnName);
                    //BeanUtils使用反射来实现的，他用于处理JAVABEAN的属性集合
                    //API文档解释：Copy the specified property value to the specified destination bean,
                    //performing any type conversion that is required.
                    //建议Bean中的基本数据类型的属性使用包装类
                    BeanUtils.copyProperty(object, columnName, value);
                }

                // 把封装完毕的对象，添加到list集合中
                list.add(object);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.closeAll(con, pstmt, rs);
        }
    }
    //仅仅用与判断一条元素是否存在
    public boolean check(String sql, Object[] paramsValue) {
        con = this.getConnection();
        try{
            pstmt = con.prepareStatement(sql);
            int count = pstmt.getParameterMetaData().getParameterCount();
            if (paramsValue != null && paramsValue.length > 0) {
                for (int i = 0; i < paramsValue.length; i++) {
                    pstmt.setObject(i + 1, paramsValue[i]);
                }
            }
            rs = pstmt.executeQuery();
            if (rs.next())
                return true;
            else
                return false;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            this.closeAll(con, pstmt, rs);
        }
    }

    private void closeAll(Connection con, PreparedStatement pstmt, ResultSet rs) {
        try {
            if(rs!=null) rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(URL,DB_USER,DB_PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return con;
    }
}
