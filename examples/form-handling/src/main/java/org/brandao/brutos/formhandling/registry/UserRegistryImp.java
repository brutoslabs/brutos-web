package org.brandao.brutos.formhandling.registry;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.brutos.formhandling.entity.User;
import org.brandao.brutos.formhandling.entityaccess.UserEntityAccess;

@Singleton
public class UserRegistryImp 
	implements UserRegistry{

	@Inject
	private UserEntityAccess entityAccess;
	
	@Override
	public void registerUser(User user) {

		if(user.getId() == null){
			this.entityAccess.save(user);
		}
		else{
			this.entityAccess.update(user);
		}
		
	}

	@Override
	public void removeUser(int id) {
		this.entityAccess.delete(id);
	}

	@Override
	public User findById(int id) {
		return this.entityAccess.findById(id);
	}

	@Override
	public List<User> findAll() {
		return this.entityAccess.findAll();
	}

}
