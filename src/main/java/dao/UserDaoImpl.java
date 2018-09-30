package dao;

import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private BaseDao baseDao = new BaseDao();

    @Override
    public boolean save(User user) {
        String sql = "insert into user values(?, ?, ?)";
        Object[] params = {user.getUsername(), user.getNickname(), user.getPassword()};
        if (baseDao.update(sql, params) == 0)
            return false;
        return true;
    }

    @Override
    public boolean update(User user) {
        String sql = "update user set password = ? where username = ?";
        Object[] params = {user.getPassword(), user.getUsername()};
        if (baseDao.update(sql, params) == 0)
            return false;
        return true;
    }

    @Override
    public boolean delete(String username) {
        String sql = "delete from user where username = ?";
        Object[] params = {username};
        if (baseDao.update(sql, params) == 0)
            return false;
        return true;
    }

    @Override
    public List findAll() {
        String sql = "select * from user";
        List<User> users = baseDao.query(sql, null, User.class);

        if (users.size() > 0)
            return users;
        else
            return new ArrayList<User>();
    }

    @Override
    public User findItem(User user) {
        String sql = "select * from user where username = ? and password = ?";
        Object[] params = {user.getUsername(), user.getPassword()};
        List<User> users = baseDao.query(sql, params, User.class);

        if (users.size() > 0)
            return users.get(0);
        else
            return null;
    }

    public User getUser(String username) {
        String sql = "select * from user where username = ?";
        Object[] params = {username};
        List<User> users = baseDao.query(sql, params, User.class);

        if (users.size() > 0)
            return users.get(0);
        else
            return null;
    }

    //模糊查询
    public List findLike(String key) {
        String sql = "select * from user where nickname like ?";
        Object[] params = {key};
        List<User> users = baseDao.query(sql, params, User.class);

        if (users.size() > 0)
            return users;
        else
            return new ArrayList<User>();
    }
}
