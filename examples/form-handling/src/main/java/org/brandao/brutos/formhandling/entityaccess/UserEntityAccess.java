package org.brandao.brutos.formhandling.entityaccess;

import java.util.List;

import org.brandao.brutos.formhandling.entity.User;

public interface UserEntityAccess {

	void save(User user);
	
	void update(User user);
	
	void delete(int id);
	
	User findById(int id);
	
	List<User> findAll();
	
}
