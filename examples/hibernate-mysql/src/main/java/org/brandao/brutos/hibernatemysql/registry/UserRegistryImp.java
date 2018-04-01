package org.brandao.brutos.hibernatemysql.registry;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.brutos.hibernatemysql.entity.User;
import org.brandao.brutos.hibernatemysql.entityaccess.UserEntityAccess;

@Singleton
public class UserRegistryImp 
	implements UserRegistry{

	@Inject
	private UserEntityAccess entityAccess;
	
	@Override
	public void registerUser(User user) throws UserRegistryException{

		try{
			if(user.getId() == null){
				this.entityAccess.save(user);
			}
			else{
				this.entityAccess.update(user);
			}
			
			this.entityAccess.flush();
		}
		catch(Throwable e){
			throw new UserRegistryException(e);
		}
		
	}

	@Override
	public void removeUser(int id) throws UserRegistryException{
		try{
			this.entityAccess.delete(id);
			this.entityAccess.flush();
		}
		catch(Throwable e){
			throw new UserRegistryException(e);
		}
	}

	@Override
	public User findById(int id) throws UserRegistryException{
		try{
			return this.entityAccess.findById(id);
		}
		catch(Throwable e){
			throw new UserRegistryException(e);
		}
	}

	@Override
	public List<User> findAll() throws UserRegistryException{
		try{
			return this.entityAccess.findAll();
		}
		catch(Throwable e){
			throw new UserRegistryException(e);
		}
	}

}
