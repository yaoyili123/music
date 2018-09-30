package dao;

import entities.Singer;
import java.util.List;

public interface SingerDao {
    boolean save(Singer music);
    boolean delete(int singerid);
    boolean update(Singer music);
    Singer findItem(int singerid);
    List findAll();
}
