package dao;

import entities.Sheet;

import java.util.ArrayList;
import java.util.List;

public class SheetDaoImpl implements SheetDao {

	private BaseDao baseDao = new BaseDao();

	public boolean save(Sheet sheet) {
		String sql = "insert into sheet(username, nickname,name,detail,type,image) "
				+ "values(?,?,?,?,?,?)";
		Object[] params = {sheet.getUsername(), sheet.getNickname(),sheet.getName(),
				sheet.getDetail(),sheet.getType(), sheet.getImage()};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	public boolean update(Sheet sheet) {
		String sql = "update sheet set nickname = ?,name = ?,detail = ?,type = ?,image = ?"
				+ "where sheetid = ?";
		Object[] params = {sheet.getNickname(),sheet.getName(),sheet.getDetail(),sheet.getType(),
				sheet.getImage(),sheet.getSheetid()};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	public boolean delete(int id) {
		String sql = "delete from sheet where sheetid = ?";
		Object[] params = {id};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	public List findAll() {
		String sql = "select * from sheet";
		List<Sheet> sheets = baseDao.query(sql, null, Sheet.class);

		if (sheets.size() > 0)
			return sheets;
		else
			return new ArrayList<Sheet>();
	}

	public Sheet findItem(int id) {
		String sql = "select * from sheet where sheetid = ?";
		Object[] params = {id};
		List<Sheet> sheets = baseDao.query(sql, params, Sheet.class);

		if (sheets.size() > 0)
			return sheets.get(0);
		else
			return null;
	}

	//查询所有类型（不重复）
	public List findTypes() {
		String sql = "select DISTINCT type from sheet";
		List<Sheet> sheets = baseDao.query(sql, null, Sheet.class);
		List<String> types = new ArrayList<String>();
		for(Sheet sheet : sheets){
			types.add(sheet.getType());
		}
		if (types.size() > 0)
			return types;
		else
			return new ArrayList<String>();
	}

	//根据类型查询所有歌单信息
	public List findByType(String type) {
		String sql = "select * from sheet where type = ?";
		Object[] params = {type};
		List<Sheet> sheets = baseDao.query(sql, params, Sheet.class);
		if (sheets.size() > 0)
			return sheets;
		else
			return new ArrayList<Sheet>();
	}

	//其他歌单
	public List findOthers(int id) {
		String sql = "select * from sheet where sheetid <> ? limit 0, 5";
		Object[] params = {id};
		List<Sheet> Sheets = baseDao.query(sql, params, Sheet.class);

		if (Sheets.size() > 0)
			return Sheets;
		else
			return new ArrayList<Sheet>();
	}

	//根据用户名查询该用户所有歌单
	public List findByUsername(String username) {
		String sql = "select * from sheet where username = ? ";
		Object[] params = {username};
		List<Sheet> sheets = baseDao.query(sql, params, Sheet.class);
		if (sheets.size() > 0)
			return sheets;
		else
			return new ArrayList<Sheet>();
	}

	//添加歌曲到歌单
	public boolean addSongToSheet(int sheetid, int songid){
		String sql = "insert into sheet_song values(?,?)";
		Object[] params = {songid, sheetid};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	//查看歌单中是否已有歌曲
	public boolean checkSongInSheet(int sheetid, int songid) {
		String sql = "select * from sheet_song where sheetid = ? and songid = ?";
		Object[] params = {sheetid, songid};
		return baseDao.check(sql, params);
	}
	//从歌单中删除歌曲
	public boolean deleteSongFromSheet(int sheetid, int songid){
		String sql = "delete from sheet_song where sheetid = ? and songid = ?";
		Object[] params = {sheetid, songid};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	//更新专辑热度
	public boolean updateClick(int id, int click) {
		String sql = "update sheet set click = ? where sheetid = ?";
		Object[] params = {click, id};
		if (baseDao.update(sql, params) == 0)
			return false;
		return true;
	}

	//模糊查询
	public List findLike(String key) {
		String sql = "select * from sheet where name like ?";
		Object[] params = {key};
		List<Sheet> sheets = baseDao.query(sql, params, Sheet.class);

		if (sheets.size() > 0)
			return sheets;
		else
			return new ArrayList<Sheet>();
	}
}

