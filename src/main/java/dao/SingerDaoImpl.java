package dao;

import java.util.ArrayList;
import java.util.List;
import entities.Singer;
import entities.Song;


public class SingerDaoImpl implements SingerDao {

	private BaseDao baseDao = new BaseDao();

	@Override
	public boolean save(Singer singer) {
		String sql = "insert into singer(name, detail, city, type, image) values (?,?,?,?,?)";
		Object[] params = {singer.getName(), singer.getDetail(), singer.getCity(),
				singer.getType(), singer.getImage()};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	@Override
	public boolean delete(int singerid) {
		String sql = "delete from singer where singerid = ?";
		Object[] params = {singerid};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	@Override
	public boolean update(Singer singer) {
		String sql = "update singer set name = ?, detail = ?, city = ?,type = ?, image = ? " +
				 "where singerid = ?";
		Object[] params = {singer.getName(), singer.getDetail(), singer.getCity(),
				singer.getType(), singer.getImage(), singer.getSingerid()};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	public List findTypes() {
		String sql = "select distinct (type) from singer";
		List<Singer> singers = baseDao.query(sql, null, Singer.class);
		List<String> types = new ArrayList<String>();
		for(Singer singer : singers){
			types.add(singer.getType());
		}

		if (types.size() > 0)
			return types;
		else
			return new ArrayList<String>();
	}

	@Override
	public Singer findItem(int singerid) {
		String sql = "select * from singer where singerid = ?";
		Object[] params = {singerid};
		List<Singer> singers = baseDao.query(sql, params, Singer.class);

		if (singers.size() > 0)
			return singers.get(0);
		else
			return null;
	}
	
	@Override
	public List findAll() {
		String sql = "select * from singer";
		List<Singer> singers = baseDao.query(sql, null, Singer.class);

		if (singers.size() > 0)
			return singers;
		else
			return new ArrayList<Singer>();
	}
	
	public List findByType(String type) {
		String sql = "select * from singer where type = ?";
		Object[] params = {type};
		List<Singer> singers = baseDao.query(sql, params, Singer.class);

		if (singers.size() > 0)
			return singers;
		else
			return new ArrayList<Singer>();
	}

	//其他歌手
	public List findOthers(int id) {
		String sql = "select * from singer where singerid <> ? limit 0, 5";
		Object[] params = {id};
		List<Singer> Singers = baseDao.query(sql, params, Singer.class);

		if (Singers.size() > 0)
			return Singers;
		else
			return new ArrayList<Singer>();
	}

	//更新专辑热度
	public boolean updateClick(int id, int click) {
		String sql = "update singer set click = ? where singerid = ?";
		Object[] params = {click, id};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	//模糊查询
	public List findLike(String key) {
		String sql = "select * from singer where name like ?";
		Object[] params = {key};
		List<Singer> singers = baseDao.query(sql, params, Singer.class);

		if (singers.size() > 0)
			return singers;
		else
			return new ArrayList<Singer>();
	}
}
