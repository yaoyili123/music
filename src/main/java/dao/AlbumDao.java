package dao;

import entities.Album;

import java.util.ArrayList;
import java.util.List;

public interface AlbumDao {
	public boolean save(Album album);
	public boolean update(Album album);
	public boolean delete(int id);
	public List findAll();
	public Album findItem(int id) ;
}
