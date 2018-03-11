package org.brandao.brutos.formhandling.entityaccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.RequestScoped;

import org.brandao.brutos.formhandling.entity.Sex;
import org.brandao.brutos.formhandling.entity.User;

@RequestScoped
public class UserEntityAccessImp 
	implements UserEntityAccess{

	private static ConcurrentMap<Integer, User> users;
	
	private static AtomicInteger ids;
	
	static{
		users = new ConcurrentHashMap<Integer, User>();
		ids   = new AtomicInteger(1);
		
		User user = new User();
		user.setId(ids.getAndIncrement());
		user.setName("afonso");
		user.setSex(Sex.M);
		user.setNumber(1);
		user.setCountry("BR");
		user.setEmail("afonso@teste.com");
		user.setFramework(Arrays.asList("Brutos MVC", "GWT"));
		user.setSkill(Arrays.asList("Hibernate", "Groovy"));
		users.put(user.getId(), user);
		
		user = new User();
		user.setId(ids.getAndIncrement());
		user.setName("brandao");
		user.setSex(Sex.M);
		user.setNumber(3);
		user.setCountry("US");
		user.setEmail("teste@teste.com");
		user.setFramework(Arrays.asList("Brutos MVC", "GWT"));
		user.setSkill(Arrays.asList("Hibernate", "Groovy"));
		users.put(user.getId(), user);

		user = new User();
		user.setId(ids.getAndIncrement());
		user.setName("henrique");
		user.setSex(Sex.M);
		user.setNumber(4);
		user.setCountry("MY");
		user.setEmail("henrique@teste.com");
		user.setFramework(Arrays.asList("Brutos MVC", "GWT"));
		user.setSkill(Arrays.asList("Hibernate", "Groovy"));
		users.put(user.getId(), user);
		
	}
	
	@Override
	public void save(User user) {
		
		if(user.getId() != null){
			throw new IllegalStateException();
		}
		
		Integer id = ids.getAndIncrement();
		user.setId(id);
		users.put(id, user);
	}

	@Override
	public void update(User user) {
		
		if(user.getId() == null || !users.containsKey(user.getId())){
			throw new IllegalStateException();
		}
		
		users.put(user.getId(), user);
	}

	@Override
	public void delete(int id) {
		
		if(!users.containsKey(id)){
			throw new IllegalStateException();
		}
		users.remove(id);
	}

	@Override
	public User findById(int id) {
		return users.get(id);
	}

	@Override
	public List<User> findAll() {
		return new ArrayList<User>(users.values());
	}

}
