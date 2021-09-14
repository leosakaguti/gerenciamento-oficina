package com.gerenciamento.oficina.dao;

import java.util.List;

public interface DAO<T> {
	
	Object get(Long id);
	
	List<T> getAll();
	
	int save(T t);
	
	boolean update(T t, String[] params);
	
	boolean delete(T t);
}
