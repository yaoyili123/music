package dao;

import entities.Song;
import java.util.ArrayList;
import java.util.List;


public class SongDaoImpl implements SongDao {

    private BaseDao baseDao = new BaseDao();

    @Override
	public boolean save(Song song) {
        String sql = "insert into song(singerid, albumid, name,auther,album,lyric,time) values (?,?,?,?,?,?,?)";
        Object[] params = {song.getSingerid(), song.getAlbumid(), song.getName(), song.getAuther(), song.getAlbum(),
                song.getLyric(), song.getTime()};
        if (baseDao.update(sql, params) == 0)
            return false;
        return true;
	}

    @Override
	public boolean delete(int songid) {
        String sql = "delete from song where songid = ?";
        Object[] params = {songid};
        if (baseDao.update(sql, params) == 0)
            return false;
        return true;
	}

    @Override
	public boolean update(Song song) {
        String sql = "update song set name = ?, time = ?, lyric = ? " +
                "where songid = ?";
        Object[] params = {song.getName(), song.getTime(), song.getLyric(), song.getSongid()};
        if (baseDao.update(sql, params) == 0)
            return false;
        return true;
    }

    @Override
    public Song findItem(int songid) {
        String sql = "select * from song where songid = ?";
        Object[] params = {songid};
        List<Song> songs = baseDao.query(sql, params, Song.class);

        if (songs.size() > 0)
            return songs.get(0);
        else
            return null;
	}

    @Override
	public List findAll() {
        String sql = "select * from song";
        List<Song> songs = baseDao.query(sql, null, Song.class);

        if (songs.size() > 0)
            return songs;
        else
            return new ArrayList<Song>();
	}

    //根据歌单查找歌曲
    public List findBySheet(int sheetid) {
        String sql = "select * from song natural join sheet_song where sheetid = ?";
        Object[] params = {sheetid};
        List<Song> songs = baseDao.query(sql, params, Song.class);

        if (songs.size() > 0)
            return songs;
        else
            return new ArrayList<Song>();
    }

    //根据专辑查找歌曲
    public List findByAlbum(int albumid) {
        String sql = "select * from song where albumid = ?";
        Object[] params = {albumid};
        List<Song> songs = baseDao.query(sql, params, Song.class);

        if (songs.size() > 0)
            return songs;
        else
            return new ArrayList<Song>();
    }

    //根据歌手查找歌曲
    public List findBySinger(int singerid) {
        String sql = "select * from song where singerid = ?";
        Object[] params = {singerid};
        List<Song> songs = baseDao.query(sql, params, Song.class);

        if (songs.size() > 0)
            return songs;
        else
            return new ArrayList<Song>();
    }

    //模糊查询
    public List findLike(String key) {
        String sql = "select * from song where name like ?";
        Object[] params = {key};
        List<Song> songs = baseDao.query(sql, params, Song.class);

        if (songs.size() > 0)
            return songs;
        else
            return new ArrayList<Song>();
    }
}
