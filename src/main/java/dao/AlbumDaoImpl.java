package dao;

import entities.Album;
import entities.Singer;

import java.util.ArrayList;
import java.util.List;

public class AlbumDaoImpl implements AlbumDao {

	BaseDao baseDao = new BaseDao();

	@Override
	public boolean save(Album album) {
		String sql = "insert into album(singerid, name, publishtime, singer, company, type, detail, image) "
				+ "values(?, ?,?,?,?,?,?,?)";
		Object[] params = {album.getSingerid(), album.getName(),album.getPublishtime(),
				album.getSinger(),album.getCompany(), album.getType(),album.getDetail(),
				album.getImage()};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	@Override
	public boolean update(Album album) {
		String sql = "update album set singerid = ?, name = ?,publishtime = ?,singer = ?,company = ?,type = ?,detail = ?,image = ?"
				+ "where albumid = ?";
		Object[] params = {album.getSingerid(), album.getName(),album.getPublishtime(),
				album.getSinger(),album.getCompany(), album.getType(),album.getDetail(),
				album.getImage(),album.getAlbumid()};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from album where albumid = ?";
		Object[] params = {id};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	@Override
	public List findAll() {
		String sql = "select * from album";
		List<Album> albums = baseDao.query(sql, null, Album.class);

		if (albums.size() > 0)
			return albums;
		else
			return new ArrayList<Album>();
	}

	@Override
	public Album findItem(int id) {
		String sql = "select * from album where albumid = ?";
		Object[] params = {id};
		List<Album> albums = baseDao.query(sql, params, Album.class);

		if (albums.size() > 0)
			return albums.get(0);
		else
			return null;
	}

	//查询所有类型（不重复）
	public List findTypes() {
		String sql = "select DISTINCT type from album";
		List<Album> albums = baseDao.query(sql, null, Album.class);
		List<String> types = new ArrayList<String>();
		for(Album album : albums){
			types.add(album.getType());
		}

		if (types.size() > 0)
			return types;
		else
			return new ArrayList<String>();
	}

	//根据语言查询所有专辑信息
	public List findByType(String type) {
		String sql = "select * from album where type = ?";
		Object[] params = {type};
		List<Album> albums = baseDao.query(sql, params, Album.class);

		if (albums.size() > 0)
			return albums;
		else
			return new ArrayList<Album>();
	}

	//其他专辑
	public List findOthers(int id) {
		String sql = "select * from album where albumid <> ? limit 0, 5";
		Object[] params = {id};
		List<Album> albums = baseDao.query(sql, params, Album.class);

		if (albums.size() > 0)
			return albums;
		else
			return new ArrayList<Album>();
	}

	//查询歌手拥有的专辑
	public List findBySinger(int singerid) {
		String sql = "select * from album where singerid = ?";
		Object[] params = {singerid};
		List<Album> albums = baseDao.query(sql, params, Album.class);

		if (albums.size() > 0)
			return albums;
		else
			return new ArrayList<Album>();
	}

	//更新专辑热度
	public boolean updateClick(int id, int click) {
		String sql = "update album set click = ? where albumid = ?";
		Object[] params = {click, id};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	//模糊查询
	public List findLike(String key) {
		String sql = "select * from album where name like ?";
		Object[] params = {key};
		List<Album> albums = baseDao.query(sql, params, Album.class);

		if (albums.size() > 0)
			return albums;
		else
			return new ArrayList<Album>();
	}
}

