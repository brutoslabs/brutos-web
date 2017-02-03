package org.brandao.brutos.javassist;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.proxy.AbstractProxyFactory;

public class JavassistProxyFactory extends AbstractProxyFactory {

	private ClassPool pool = null;
	private ProxyFactory factory;

	public JavassistProxyFactory(Class superClass, ClassPool pool)
			throws Exception {
		super(superClass);
		this.pool = pool;
		pool.insertClassPath(new ClassClassPath(superClass));
		proxyClass = createProxyClass(superClass);
	}

	public Object getNewProxy(Object resource, Controller form,
			ConfigurableApplicationContext context, Invoker invoker)
			throws BrutosException {

		MethodHandler handler = new JavassistActionHandler(resource, form,
				context, invoker);

		try {
			ProxyObject instance = (ProxyObject) ClassUtil
					.getInstance(proxyClass);
			instance.setHandler(handler);
			return instance;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	private Class createProxyClass(Class clazz) throws Exception {
		factory = new ProxyFactory();
		factory.setSuperclass(clazz);
		return factory.createClass();
	}

}
