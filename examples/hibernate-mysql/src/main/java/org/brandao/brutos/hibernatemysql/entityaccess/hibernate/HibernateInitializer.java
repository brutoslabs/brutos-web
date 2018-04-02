package org.brandao.brutos.hibernatemysql.entityaccess.hibernate;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.brandao.brutos.annotation.scanner.DefaultScanner;
import org.brandao.brutos.annotation.scanner.filter.AnnotationTypeFilter;
import org.brandao.brutos.io.DefaultResourceLoader;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Singleton
public class HibernateInitializer {

	@Produces
	@RequestScoped
	public Session openSession(SessionFactory sessionFactory){
		Session session = sessionFactory.openSession();
		session.setFlushMode(FlushMode.COMMIT);
		return session;
	}
	
	public void close(@Disposes Session session) {
		if (session.isOpen()) {
            session.close();
        }
	}
	
	@Produces
	@Singleton
	@SuppressWarnings("unchecked")
	public SessionFactory createSessionFactory() throws PropertyVetoException, IOException {

		AnnotationTypeFilter filter = new AnnotationTypeFilter();
		
		filter.setExpression(Arrays
				.asList("javax.persistence.Entity"));

		DefaultScanner s = new DefaultScanner();
		s.setBasePackage(new String[] { "org.brandao.brutos.hibernatemysql.entity" });
		s.addIncludeFilter(filter);
		s.scan();
		List<Class<?>> list = s.getClassList();
		SessionFactory sessionFactory = this.createSessionFactory(list);
		
		return sessionFactory;
	}
	
	private Properties getConfiguration() throws IOException{
		
		ResourceLoader loader 	= new DefaultResourceLoader();
		Resource resource 		= loader.getResource("classpath:hibernate.properties");
		Properties config 		= new Properties();
		InputStream in 			= resource.getInputStream();
		
		try{
			config.load(resource.getInputStream());
		}
		finally{
			in.close();
		}
		
		return config;
	}
	
	public SessionFactory createSessionFactory(List<Class<?>> list) 
			throws PropertyVetoException, IOException {
		
		org.hibernate.cfg.Configuration config = 
				new org.hibernate.cfg.Configuration();
		
		config.addProperties(this.getConfiguration());
		
		for(Class<?> clazz: list){
			config.addAnnotatedClass(clazz);
		}
		
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
		return config.buildSessionFactory(builder.build());
	}
	
}
