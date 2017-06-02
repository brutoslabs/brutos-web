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

package org.brandao.brutos.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.configuration.AbstractAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ActionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.AnnotationConfigEntry;
import org.brandao.brutos.annotation.configuration.AnnotationUtil;
import org.brandao.brutos.annotation.configuration.AnyAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ApplyAnnotationConfig;
import org.brandao.brutos.annotation.configuration.BeanAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ConfigurationEntry;
import org.brandao.brutos.annotation.configuration.Configurer;
import org.brandao.brutos.annotation.configuration.ControllerAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ElementCollectionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ExtendedScopeAnnotationConfig;
import org.brandao.brutos.annotation.configuration.BasicAnnotationConfig;
import org.brandao.brutos.annotation.configuration.InterceptedByAnnotationConfig;
import org.brandao.brutos.annotation.configuration.InterceptsAnnotationConfig;
import org.brandao.brutos.annotation.configuration.InterceptsStackAnnotationConfig;
import org.brandao.brutos.annotation.configuration.KeyCollectionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.RestrictionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.RestrictionsAnnotationConfig;
import org.brandao.brutos.annotation.configuration.RootAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ThrowSafeAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ThrowSafeListAnnotationConfig;
import org.brandao.brutos.annotation.configuration.TypeDefAnnotationConfig;
import org.brandao.brutos.annotation.scanner.Scanner;
import org.brandao.brutos.annotation.scanner.filter.ConfigurationTypeFilter;
import org.brandao.brutos.annotation.scanner.filter.ControllerFilter;
import org.brandao.brutos.annotation.scanner.filter.InterceptorFilter;
import org.brandao.brutos.annotation.scanner.filter.StereotypeTypeFilter;
import org.brandao.brutos.annotation.scanner.filter.TypeTypeFilter;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;

/**
 *
 * @author Brandao
 */
public class ComponentConfigurer {

	public static final org.brandao.brutos.annotation.scanner.TypeFilter[] DEFAULT_FILTERS = 
			new org.brandao.brutos.annotation.scanner.TypeFilter[] {
				new StereotypeTypeFilter(), 
				new ConfigurationTypeFilter(),
				new ControllerFilter(), 
				new InterceptorFilter(),
				new TypeTypeFilter() };

	public static final List<Class<?>> defaultAnnotationConfig;

	static {
		List<Class<?>> defaultList = new ArrayList<Class<?>>();
		defaultAnnotationConfig = Collections.unmodifiableList(defaultList);
		
		defaultList.add(RootAnnotationConfig.class);
		defaultList.add(ActionAnnotationConfig.class);
		defaultList.add(InterceptsStackAnnotationConfig.class);
		defaultList.add(BeanAnnotationConfig.class);
		defaultList.add(KeyCollectionAnnotationConfig.class);
		defaultList.add(ElementCollectionAnnotationConfig.class);
		defaultList.add(ControllerAnnotationConfig.class);
		defaultList.add(InterceptedByAnnotationConfig.class);
		defaultList.add(InterceptsAnnotationConfig.class);
		defaultList.add(RestrictionAnnotationConfig.class);
		defaultList.add(RestrictionsAnnotationConfig.class);
		defaultList.add(ThrowSafeAnnotationConfig.class);
		defaultList.add(ThrowSafeListAnnotationConfig.class);
		defaultList.add(TypeDefAnnotationConfig.class);
		defaultList.add(BasicAnnotationConfig.class);
		defaultList.add(ExtendedScopeAnnotationConfig.class);
		defaultList.add(AnyAnnotationConfig.class);
		// defaultAnnotationConfig.add(AnyElementCollectionAnnotationConfig.class);
		// defaultAnnotationConfig.add(AnyKeyCollectionAnnotationConfig.class);
	}

	private ConfigurationEntry configuration;

	private ConfigurableApplicationContext applicationContext;

	private List<Class<?>> baseAnnotation;
	
	@SuppressWarnings("unused")
	private Logger logger;

	public ComponentConfigurer(ConfigurableApplicationContext applicationContext) {
		this(applicationContext, defaultAnnotationConfig);
	}
	
	public ComponentConfigurer(ConfigurableApplicationContext applicationContext,
			List<Class<?>> baseAnnotation) {
		this.baseAnnotation     = baseAnnotation;
		this.applicationContext = applicationContext;
		this.logger             = 
			LoggerProvider.getCurrentLoggerProvider().getLogger(AnnotationApplicationContext.class);
	}

	public void init(ComponentRegistry componentRegistry) {

		Set<Class<?>> resultClassList = new HashSet<Class<?>>();
		Set<Class<?>> firstClassList = new HashSet<Class<?>>();

		for (Class<?> clazz : this.baseAnnotation)
			resultClassList.add(clazz);

		if (this.configuration.getConfigClass() == null)
			this.loadClassList(this.configuration, firstClassList);
		else {
			for (Class<?> clazz : this.configuration.getConfigClass())
				firstClassList.add(clazz);
		}

		resultClassList.addAll(firstClassList);

		for (Class<?> clazz : firstClassList) {

			if (!clazz.isAnnotationPresent(Configuration.class))
				continue;

			if (clazz.isAnnotationPresent(ComponentScan.class)) {
				ComponentScan componentScan = (ComponentScan) clazz
						.getAnnotation(ComponentScan.class);

				ConfigurationEntry configurationEntry = AnnotationUtil
						.createConfigurationEntry(componentScan);

				this.loadClassList(configurationEntry, resultClassList);
			}

		}

		List<Class<?>> classList = new ArrayList<Class<?>>(resultClassList);
		List<Object> objectList = new ArrayList<Object>(resultClassList);

		AnnotationConfig rootAnnotationConfig = AnnotationUtil
				.createAnnotationTree(applicationContext, classList);

		AnnotationConfig init = new StartConfiguration(
				(ApplyAnnotationConfig) rootAnnotationConfig);

		init.applyConfiguration(objectList, null, componentRegistry);

		for (Class<?> clazz : firstClassList) {

			if (!clazz.isAnnotationPresent(Configuration.class))
				continue;

			if (Configurer.class.isAssignableFrom(clazz)) {
				Configurer configurer = null;
				try {
					configurer = (Configurer) ClassUtil.getInstance(clazz);
				} catch (Throwable e) {
					throw new BrutosException(e);
				}

				configurer.addControllers(componentRegistry);
				configurer.addInterceptors(componentRegistry);
				configurer.addScopes(componentRegistry);
				configurer.addTypes(componentRegistry);

			}

		}

	}

	public ConfigurationEntry getConfiguration() {
		return configuration;
	}

	public void setConfiguration(ConfigurationEntry configuration) {
		this.configuration = configuration;
	}

	private void loadClassList(ConfigurationEntry configurationEntry,
			Set<Class<?>> classList) {
		Scanner scanner = AnnotationUtil.createScanner(configurationEntry,
				DEFAULT_FILTERS);
		scanner.scan();

		List<Class<?>> tmplist = scanner.getClassList();

		for (Class<?> clazz : tmplist)
			classList.add(clazz);
	}

	private class StartConfiguration extends AbstractAnnotationConfig {

		@SuppressWarnings("unused")
		private ApplyAnnotationConfig root;

		public StartConfiguration(ApplyAnnotationConfig root) {
			this.root = root;
			AnnotationConfigEntry entry = new AnnotationConfigEntry();
			entry.setAnnotationConfig(null);
			entry.setNextAnnotationConfig(Arrays.asList(root.getConfiguration()));
			super.setConfiguration(entry);
		}

		public boolean isApplicable(Object source) {
			return true;
		}

		public Object applyConfiguration(Object source, Object builder,
				ComponentRegistry componentRegistry) {
			return super.applyInternalConfiguration(source, builder,
					componentRegistry);
		}

	}

}
