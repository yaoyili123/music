package dao;

import entities.User;
import java.util.List;

public interface UserDao {
    public boolean save(User message);
    public boolean update(User message);
    public boolean delete(String username);
    public List findAll();
    public User findItem(User user);
}
