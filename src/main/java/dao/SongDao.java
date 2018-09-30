package dao;

import entities.Song;

import java.util.List;


public interface SongDao {
    boolean save(Song song);

    boolean delete(int singerid);

    boolean update(Song song);

    Song findItem(int singerid);

    List findAll();
}
