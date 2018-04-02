package org.brandao.brutos.hibernatemysql.entityaccess.hibernate;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.brandao.brutos.hibernatemysql.entity.User;
import org.brandao.brutos.hibernatemysql.entityaccess.EntityAccessException;
import org.brandao.brutos.hibernatemysql.entityaccess.UserEntityAccess;
import org.hibernate.Session;

@RequestScoped
public class UserEntityAccessImp 
	implements UserEntityAccess{
	
	private Session session;
	
	public UserEntityAccessImp(){
	}
	
	@Inject
	public UserEntityAccessImp(Session session){
		this.session = session;
	}
		
	@Override
	public void save(User user) throws EntityAccessException{
		try{
			this.session.save(user);
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

	@Override
	public void update(User user) throws EntityAccessException{
		try{
			user = (User) this.session.merge(user);
			this.session.update(user);
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

	@Override
	public void delete(int id) throws EntityAccessException{
		try{
			User user = this.findById(id);
			if(user != null){
				this.session.delete(user);
			}
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

	@Override
	public User findById(int id) throws EntityAccessException{
		try{
			return (User)this.session.get(User.class, id);
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() throws EntityAccessException{
		try{
			return (List<User>)this.session.createCriteria(User.class).list();
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

	@Override
	public void flush() throws EntityAccessException{
		try{
			this.session.flush();
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

}
