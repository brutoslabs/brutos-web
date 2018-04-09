package org.brandao.brutos.hibernatemysql.entityaccess;

import java.util.List;

public interface EntityAccess<T> {

	void save(T user) throws EntityAccessException;
	
	void update(T user) throws EntityAccessException;
	
	void delete(int id) throws EntityAccessException;
	
	T findById(int id) throws EntityAccessException;
	
	List<T> findAll() throws EntityAccessException;

	void flush() throws EntityAccessException;
	
}
