package org.brandao.brutos.formhandling.registry;

import java.util.List;

import org.brandao.brutos.formhandling.entity.User;

public interface UserRegistry {

	void registerUser(User user);
	
	void removeUser(int id);
	
	User findById(int id);
	
	List<User> findAll();
	
}
