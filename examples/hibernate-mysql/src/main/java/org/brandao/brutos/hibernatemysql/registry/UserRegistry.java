package org.brandao.brutos.hibernatemysql.registry;

import java.util.List;

import org.brandao.brutos.hibernatemysql.entity.User;

public interface UserRegistry {

	void registerUser(User user) throws UserRegistryException;
	
	void removeUser(int id) throws UserRegistryException;
	
	User findById(int id) throws UserRegistryException;
	
	List<User> findAll() throws UserRegistryException;
	
}
