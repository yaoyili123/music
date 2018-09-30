package dao;

import entities.Sheet;

import java.util.ArrayList;
import java.util.List;

public interface SheetDao {
	public boolean save(Sheet sheet);
	public boolean update(Sheet sheet);
	public boolean delete(int id);
	public List findAll();
	public Sheet findItem(int id);
}
