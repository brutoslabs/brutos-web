/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.cdi;

import org.brandao.brutos.ObjectFactory;
import java.lang.annotation.Annotation;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;

/**
 * 
 * @author Brandao
 */
public class JSR299ObjectFactory implements ObjectFactory {

	private BeanManager beanManager;
	private Properties config;

	public JSR299ObjectFactory() {
	}

	public Object getBean(String name) {

		if (name == null)
			return null;
		else {
			Set beans = beanManager.getBeans(name);
			return this.getInstance(beans, null);
		}
	}

	public Object getBean(Class clazz) {
		Set beans = beanManager.getBeans(clazz, new Annotation[] {});
		return this.getInstance(beans, clazz);
	}

	protected Object getInstance(Set beans, Class clazz) {

		if (beans.isEmpty())
			return null;

		Bean bean = (Bean) beans.iterator().next();

		CreationalContext ctx = beanManager.createCreationalContext(bean);
		return beanManager.getReference(bean,
				clazz == null ? bean.getBeanClass() : clazz, ctx);
	}

	public void configure(Properties properties) {
		this.config = properties;
		initBeanManager(config);
	}

	public void destroy() {
		this.beanManager = null;
	}

	public void initBeanManager(Properties config) {
		String beanManagerName = null;

		try {
			String jndiClass = config.getProperty(BrutosConstants.JNDI_CLASS);
			String jndiURL = config.getProperty(BrutosConstants.JNDI_URL);

			Hashtable args = new Hashtable();

			if (jndiClass != null)
				args.put(Context.INITIAL_CONTEXT_FACTORY, jndiClass);

			if (jndiURL != null)
				args.put(Context.PROVIDER_URL, jndiURL);

			beanManagerName = config.getProperty(
					BrutosConstants.CDI_BEAN_MANAGER,
					BrutosConstants.DEFAULT_CDI_BEAN_MANAGER);
			InitialContext initialContext = new InitialContext(args);
			this.beanManager = (BeanManager) initialContext
					.lookup(beanManagerName);
		} catch (NamingException e) {
			throw new BrutosException("Could not get the BeanManager: "
					+ beanManagerName, e);
		}
	}
}
