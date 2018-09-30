package test;


import dao.UserDaoImpl;
import entities.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UserDaoImplTest extends TestCase {

    private UserDaoImpl userDao = new UserDaoImpl();
    @Test
    public void save() {
        User user = new User();
        user.setPassword("123456");
        user.setUsername("yaoyili");
        user.setNickname("Abnormality");
        userDao.save(user);
    }

    @Test
    public void update() {
        User user = new User();
        user.setPassword("yao123456");
        user.setUsername("yaoyili");
        user.setNickname("Abnormality");
        userDao.update(user);
    }

    @Test
    public void delete() {
        userDao.delete("yaoyili");
    }

    @Test
    public void findAll() {
        ArrayList<User> users = (ArrayList<User>) userDao.findAll();
        System.out.print(users);
    }

    @Test
    public void findItem() {
        User user = new User();
        user.setPassword("yao123456");
        user.setUsername("yaoyili");
        user = userDao.findItem(user);
        System.out.print(user);
    }
}
