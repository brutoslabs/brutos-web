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

package org.brandao.brutos.annotation.configuration;

import java.lang.annotation.Annotation;
import java.util.*;

import org.brandao.brutos.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.annotation.scanner.DefaultScanner;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.TypeUtil;
import org.brandao.brutos.web.BrutosWebConstants;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.xml.FilterEntity;
import org.brandao.brutos.annotation.scanner.Scanner;
import org.brandao.brutos.xml.XMLBrutosConstants;

/**
 *
 * @author Brandao
 */
public class AnnotationUtil {

	public static List<ThrowSafe> toList(ThrowSafeList value) {
		return Arrays.asList(value.value());
	}

	public static ThrowableEntry toEntry(ThrowSafe value) {
		return new ThrowableEntry(value);
	}

	public static List<ThrowableEntry> toList(List<ThrowSafe> list) {

		List<ThrowableEntry> result = new ArrayList<ThrowableEntry>();

		for (ThrowSafe t : list)
			result.add(toEntry(t));

		return result;
	}

	public static boolean isInterceptor(Class<?> clazz) {
		boolean isInterceptor = clazz.getSimpleName().endsWith(
				"InterceptorController")
				|| clazz.isAnnotationPresent(Intercepts.class);

		return isInterceptor && !isTransient(clazz);
	}

	public static boolean isInterceptorStack(Class<?> clazz) {
		boolean isInterceptor = isInterceptor(clazz)
				&& (clazz.isAnnotationPresent(InterceptsStackList.class) || clazz
						.isAnnotationPresent(InterceptsStack.class));

		return isInterceptor;
	}

	public static boolean isController(Class<?> clazz) {
		boolean isController = clazz.getSimpleName().endsWith("Controller")
				|| clazz.isAnnotationPresent(Controller.class);

		return isController && !isTransient(clazz) && !isInterceptor(clazz);
	}

	public static boolean isScope(Class<?> clazz) {
		boolean isScope = clazz.getSimpleName().endsWith("Scope")
				|| clazz.isAnnotationPresent(ExtendedScope.class);

		return isScope && !isTransient(clazz);
	}

	public static boolean isTransient(Class<?> clazz) {
		return clazz.isAnnotationPresent(Transient.class);
	}

	public static org.brandao.brutos.type.Type getTypeInstance(Any value) {
		if (value != null) {
			return value.metaTypeDef() == org.brandao.brutos.type.Type.class ? null
					: (org.brandao.brutos.type.Type) getTypeInstance(value
							.metaTypeDef());
		} else
			return null;
	}

	public static org.brandao.brutos.type.Type getTypeInstance(Type value) {
		if (value != null) {
			return value.value() == org.brandao.brutos.type.Type.class ? null
					: (org.brandao.brutos.type.Type) getTypeInstance(value
							.value());
		} else
			return null;
	}

	public static org.brandao.brutos.type.Type getTypeInstance(Class<?> value) {
		try {
			if (value != null)
				return (org.brandao.brutos.type.Type) ClassUtil
						.getInstance(value);
			else
				return null;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	public static String getTemporalProperty(Any value) {
		return value == null ? null /*BrutosConstants.DEFAULT_TEMPORALPROPERTY*/ : value
				.metaTemporal();
	}

	public static String getTemporalProperty(Temporal value) {
		if (value != null)
			return value.value();
		else
			return null;//BrutosConstants.DEFAULT_TEMPORALPROPERTY;
	}

	public static org.brandao.brutos.EnumerationType getEnumerationType(
			Any value) {
		return getEnumerationType(value == null ? null : value.metaEnumerated());
	}

	public static org.brandao.brutos.EnumerationType getEnumerationType(
			Enumerated value) {
		return getEnumerationType(value == null ? null : value.value());
	}

	public static org.brandao.brutos.EnumerationType getEnumerationType(
			EnumerationType value) {
		if (value != null) {
			return org.brandao.brutos.EnumerationType.valueOf(value.name()
					.toLowerCase());
		} else
			return null;//BrutosConstants.DEFAULT_ENUMERATIONTYPE;
	}

	public static org.brandao.brutos.ScopeType getScope(Basic value) {

		if (value != null) {
			String scope = StringUtil.adjust(value.scope());
			if (!StringUtil.isEmpty(scope))
				return org.brandao.brutos.ScopeType.valueOf(value.scope());
		}

		return BrutosConstants.DEFAULT_SCOPETYPE;
	}

	public static boolean isBuildEntity(TypeRegistry typeRegistry,
			Boolean build, Class<?> type) {

		if (type == null)
			return false;
		else
			return build == null ? !typeRegistry.isStandardType(type) : build
					.booleanValue();
	}

	public static boolean isBuildEntity(TypeRegistry typeRegistry,
			MappingTypes mappingType, Class<?> type) {
		return isBuildEntity(typeRegistry,
				mappingType == null || mappingType == MappingTypes.AUTO ? null
						: mappingType.equals(MappingTypes.OBJECT), type);
	}

	public static boolean isComplexBean(Class<?> type) {
		return isUseDefaultMapping(type)
				|| type.isAnnotationPresent(Bean.class);
	}

	public static boolean isUseDefaultMapping(Class<?> type) {
		return type == Map.class || type == List.class || type == Set.class || type == Collection.class;
	}

	public static boolean isBuildEntity(TypeRegistry typeRegistry,
			KeyCollection identify, Class<?> type) {
		return isBuildEntity(
				typeRegistry,
				identify == null || identify.mappingType() == MappingTypes.AUTO ? null
						: identify.mappingType().equals(MappingTypes.OBJECT),
				type);
	}

	public static boolean isBuildEntity(TypeRegistry typeRegistry,
			ElementCollection identify, Class<?> type) {
		return isBuildEntity(
				typeRegistry,
				identify == null || identify.mappingType() == MappingTypes.AUTO ? null
						: identify.mappingType().equals(MappingTypes.OBJECT),
				type);
	}

	public static boolean isBuildEntity(TypeRegistry typeRegistry,
			Basic identify, Class<?> type) {
		return isBuildEntity(
				typeRegistry,
				identify == null || identify.mappingType() == MappingTypes.AUTO ? null
						: identify.mappingType().equals(MappingTypes.OBJECT),
				type);
	}

	public static boolean isBuildEntity(TypeRegistry typeRegistry,
			Result resultAction, Class<?> type) {
		return isBuildEntity(
				typeRegistry,
				resultAction == null || resultAction.mappingType() == MappingTypes.AUTO ? null
						: resultAction.mappingType().equals(MappingTypes.OBJECT),
				type);
	}
	
	public static Object getKeyType(Object type) {

		if (type == null)
			return null;

		Class<?> rawType = TypeUtil.getRawType(type);
		Object keyType = TypeUtil.getKeyType(type);

		if (keyType != null)
			return keyType;

		if (isMap(rawType))
			return getKeyType(rawType.getGenericSuperclass());

		return null;
	}

	public static Object getCollectionType(Object type) {

		if (type == null)
			return null;

		Class<?> rawType = TypeUtil.getRawType(type);
		Object elementType = TypeUtil.getCollectionType(type);

		if (elementType != null)
			return elementType;

		if (isMap(rawType) || isCollection(rawType))
			return getCollectionType(rawType.getGenericSuperclass());
		else
			return null;

	}

	public static boolean isCollection(Class<?> clazz) {
		return isMap(clazz) || Collection.class.isAssignableFrom(clazz);
	}

	public static boolean isMap(Class<?> clazz) {
		return Map.class.isAssignableFrom(clazz);
	}

	public static boolean isWebApplication(ApplicationContext applicationContext) {
		Properties config = applicationContext.getConfiguration();
		return config.getProperty(BrutosWebConstants.WEB_APPLICATION_CLASS) != null;
	}

	public static boolean isWebApplication(ComponentRegistry componentRegistry) {
		return componentRegistry instanceof WebApplicationContext;
	}

	public static AnnotationConfig createAnnotationTree(
			ConfigurableApplicationContext applicationContext,
			List<Class<?>> list) {
		return createAnnotationTree(null, applicationContext, list);
	}

	public static AnnotationConfig createAnnotationTree(
			AnnotationConfig rootAnnotationConfig,
			ConfigurableApplicationContext applicationContext,
			List<Class<?>> list) {

		Logger logger = LoggerProvider.getCurrentLoggerProvider().getLogger(
				AnnotationApplicationContext.class);

		Map<Class<?>, AnnotationConfigEntry> configMap = getAnnotationConfigEntry(
				applicationContext, list);
		List<AnnotationConfigEntry> root = new LinkedList<AnnotationConfigEntry>();

		AnnotationConfigEntry config = null;

		if (rootAnnotationConfig != null) {
			config = rootAnnotationConfig.getConfiguration();
			config.setNextAnnotationConfig(root);
		}

		for (AnnotationConfigEntry ace : configMap.values()) {
			Stereotype stereotype = ace.getStereotype();

			if (stereotype.target() == org.brandao.brutos.annotation.Configuration.class) {
				if (config == null) {
					ace.setNextAnnotationConfig(root);
					config = ace;
				}
				continue;
			}

			Class<? extends Annotation>[] after = stereotype.executeAfter();

			for (Class<? extends Annotation> an : after) {
				if (an == org.brandao.brutos.annotation.Configuration.class) {
					logger.warn("property after ignored: " + ace.getClass());
					continue;
				}

				AnnotationConfigEntry afterEntry = configMap.get(an);

				if (afterEntry == null)
					throw new BrutosException("not found: " + an.getName());

				afterEntry.getNextAnnotationConfig().add(ace);
			}

			if (after.length == 0)
				root.add(ace);
		}

		if (config == null)
			throw new BrutosException(
					"not found: @"
							+ org.brandao.brutos.annotation.Configuration.class
									.getName());

		printConfigurationRoute(logger, config);
		return config.getAnnotationConfig();
	}

	protected static Map<Class<?>, AnnotationConfigEntry> getAnnotationConfigEntry(
			ConfigurableApplicationContext applicationContext,
			List<Class<?>> list) {
		Map<Class<?>, AnnotationConfigEntry> map = new HashMap<Class<?>, AnnotationConfigEntry>();

		for (Class<?> clazz : list) {

			if (!clazz.isAnnotationPresent(Stereotype.class)
					|| !AnnotationConfig.class.isAssignableFrom(clazz))
				continue;

			Stereotype st = (Stereotype) clazz.getAnnotation(Stereotype.class);

			AnnotationConfigEntry current = map.get(st.target());
			AnnotationConfigEntry newConfig = getAnnotationConfig(
					applicationContext, st, clazz);

			if (current != null) {
				boolean override = newConfig.getStereotype().majorVersion() > current
						.getStereotype().majorVersion()
						|| (newConfig.getStereotype().majorVersion() == current
								.getStereotype().majorVersion() && newConfig
								.getStereotype().minorVersion() > current
								.getStereotype().minorVersion());
				if (override)
					map.put(st.target(), newConfig);
			} else
				map.put(st.target(), newConfig);
		}

		return map;
	}

	protected static AnnotationConfigEntry getAnnotationConfig(
			ConfigurableApplicationContext applicationContext,
			Stereotype stereotype, Class<?> clazz) {

		try {
			AnnotationConfig ac = (AnnotationConfig) ClassUtil
					.getInstance(clazz);
			ac.setApplicationContext(applicationContext);
			AnnotationConfigEntry r = new AnnotationConfigEntry();
			r.setAnnotationConfig(ac);
			ac.setConfiguration(r);
			r.setStereotype(stereotype);
			r.setNextAnnotationConfig(new LinkedList<AnnotationConfigEntry>());

			Class<?> sourceConverterClass = stereotype.sourceConverter();

			if (sourceConverterClass != Converter.class) {
				Converter valueConverter = (Converter) ClassUtil
						.getInstance(sourceConverterClass);
				ac.setSourceConverter(valueConverter);
			}

			return r;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	private static void printConfigurationRoute(Logger logger,
			AnnotationConfigEntry root) {
		nextPart(logger, root, new StringBuilder());
	}

	private static void nextPart(Logger logger, AnnotationConfigEntry entry,
			StringBuilder prefix) {

		prefix.append(entry.getStereotype().target().getSimpleName());

		for (AnnotationConfigEntry next : entry.getNextAnnotationConfig()) {
			String node = entry.getStereotype().target().getSimpleName()
					+ " -> " + next.getStereotype().target().getSimpleName();

			if (prefix.indexOf(node) != -1)
				continue;

			nextPart(logger, next, new StringBuilder(prefix).append(" -> "));
		}

		if (entry.getNextAnnotationConfig().isEmpty())
			logger.info("route config detected: " + prefix);
	}

	public static Scanner createScanner(ConfigurationEntry configuration,
			org.brandao.brutos.annotation.scanner.TypeFilter[] defaultFilters) {

		Scanner scanner;
		try {
			String scannerClassName = configuration.getScannerClassName();
			scanner = StringUtil.isEmpty(scannerClassName) ? new DefaultScanner()
					: (org.brandao.brutos.annotation.scanner.Scanner) ClassUtil
							.getInstance(scannerClassName);
		} catch (Throwable e) {
			throw new BrutosException("failed to create scanner instance", e);
		}

		List<String> basePackage = configuration.getBasePackage();

		scanner.setBasePackage(basePackage == null || basePackage.isEmpty() ? new String[] { "" }
				: basePackage.toArray(new String[] {}));

		if (configuration.isUseDefaultfilter()) {
			org.brandao.brutos.annotation.scanner.TypeFilter[] filters = defaultFilters;
			for (org.brandao.brutos.annotation.scanner.TypeFilter filter : filters) {
				scanner.addIncludeFilter(filter);
			}
		}

		List<FilterEntity> excludeFilter = configuration.getExcludeFilters();

		if (excludeFilter != null) {
			for (FilterEntity filterDef : excludeFilter) {
				List<org.brandao.brutos.annotation.scanner.TypeFilter> filter = getTypeFilter(
						filterDef.getExpression(), filterDef.getType());
				for (org.brandao.brutos.annotation.scanner.TypeFilter t : filter) {
					scanner.addExcludeFilter(t);
				}
			}
		}

		List<FilterEntity> includeFilter = configuration.getIncludeFilters();

		if (includeFilter != null) {
			for (FilterEntity filterDef : includeFilter) {
				List<org.brandao.brutos.annotation.scanner.TypeFilter> filter = getTypeFilter(
						filterDef.getExpression(), filterDef.getType());
				for (org.brandao.brutos.annotation.scanner.TypeFilter t : filter) {
					scanner.addIncludeFilter(t);
				}
			}
		}

		return scanner;
	}

	public static List<org.brandao.brutos.annotation.scanner.TypeFilter> getTypeFilter(
			List<String> expression, String type) {
		try {
			List<String> classNames = getFilterClassName(expression, type);
			List<org.brandao.brutos.annotation.scanner.TypeFilter> filterList = new ArrayList<org.brandao.brutos.annotation.scanner.TypeFilter>();
			for (String cn : classNames) {
				Class<?> scannerFilterClass = ClassUtil.get(cn);
				org.brandao.brutos.annotation.scanner.TypeFilter filter = (org.brandao.brutos.annotation.scanner.TypeFilter) ClassUtil
						.getInstance(scannerFilterClass);
				filter.setExpression(expression);
				filterList.add(filter);
			}
			return filterList;
		} catch (Throwable ex) {
			throw new BrutosException("can't initialize the scanner: " + type,
					ex);
		}
	}

	public static ConfigurationEntry createDefaultConfiguration() {
		ConfigurationEntry config = new ConfigurationEntry();
		config.setUseDefaultfilter(true);
		config.setBasePackage(Arrays.asList(new String[] { "" }));
		return config;
	}

	private static List<String> getFilterClassName(List<String> expression,
			String type) {
		if (XMLBrutosConstants.XML_BRUTOS_CUSTOM_FILTER.equals(type))
			return expression;
		else {
			String name = type.length() > 1 ? Character.toUpperCase(type
					.charAt(0)) + type.substring(1) : type;
			return Arrays
					.asList(new String[] { "org.brandao.brutos.annotation.scanner.filter."
							+ name + "TypeFilter" });
		}
	}

	public static FilterEntity toFilterEntity(TypeFilter typeFilter) {
		String type = typeFilter.type().getName();
		List<String> expression;

		if (typeFilter.type() == FilterType.REGEX)
			expression = Arrays.asList(typeFilter.pattern());
		else {
			expression = new ArrayList<String>();
			for (Class<?> c : typeFilter.value())
				expression.add(c.getName());
		}

		return new FilterEntity(type, expression);
	}

	public static ConfigurationEntry createConfigurationEntry(
			ComponentScan componentScan) {

		Class<?>[] basePackageClass = componentScan.basePackage();
		String[] basePackage = componentScan.value();
		TypeFilter[] excludeFilter = componentScan.excludeFilters();
		TypeFilter[] includeFilters = componentScan.includeFilters();
		boolean useDefaultFilters = componentScan.useDefaultFilters();
		Class<?> scannerClass = componentScan.scanner();

		ConfigurationEntry result = new ConfigurationEntry();
		result.setUseDefaultfilter(useDefaultFilters);
		result.setScannerClassName(scannerClass == Scanner.class ? null
				: scannerClass.getName());

		List<String> basePackageList = new ArrayList<String>();

		for (Class<?> c : basePackageClass)
			basePackageList.add(c.getPackage().getName());

		basePackageList.addAll(Arrays.asList(basePackage));

		result.setBasePackage(basePackageList);

		List<FilterEntity> excludeFilterList = new ArrayList<FilterEntity>();
		result.setExcludeFilters(excludeFilterList);
		for (TypeFilter e : excludeFilter) {
			excludeFilterList.add(AnnotationUtil.toFilterEntity(e));
		}

		List<FilterEntity> includeFilterslist = new ArrayList<FilterEntity>();
		result.setIncludeFilters(includeFilterslist);
		for (TypeFilter e : includeFilters) {
			includeFilterslist.add(AnnotationUtil.toFilterEntity(e));
		}

		return result;
	}

	public static String getBeanName(BeanPropertyAnnotation property) {
		Basic basic = property.getAnnotation(Basic.class);

		if (basic != null) {
			String bean = StringUtil.adjust(basic.bean());
			if (!StringUtil.isEmpty(bean))
				return basic.bean();
		}

		// return property.getName();
		return null;
	}

	public static String getBeanName(Class<?> type) {

		Bean bean = (Bean) type.getAnnotation(Bean.class);
		String name = bean == null ? null : StringUtil.adjust(bean.value());
		name = StringUtil.isEmpty(name) ? StringUtil.toVariableFormat(type
				.getSimpleName()) : name;

		return name;
	}

	public static Annotation[] getAnnotations(KeyCollection annotation) {
		return annotation == null || !existAnnotation(annotation.any()) ? new Annotation[] {}
				: new Annotation[] { annotation.any() };
	}

	public static Annotation[] getAnnotations(ElementCollection annotation) {
		return annotation == null || !existAnnotation(annotation.any()) ? new Annotation[] {}
				: new Annotation[] { annotation.any() };
	}

	public static boolean existAnnotation(Any annotation) {

		if (annotation == null)
			return false;

		boolean hasAny = !StringUtil.isEmpty(annotation.metaBean().bean());
		hasAny = hasAny
				|| annotation.metaTypeDef() != org.brandao.brutos.type.Type.class;
		hasAny = hasAny || annotation.metaType() != void.class;
		hasAny = hasAny || annotation.metaValues().length != 0;
		hasAny = hasAny
				|| annotation.metaValuesDefinition() != MetaValuesDefinition.class;

		return hasAny;
	}
	
	public static boolean isObject(ElementEntry element, ComponentRegistry componentRegistry){
		return !element.isAnnotationPresent(Any.class) && 
				AnnotationUtil.isBuildEntity(componentRegistry,element.getMappingType(), element.getClassType());		
	}
	
	public static boolean isObject(KeyEntry element, ComponentRegistry componentRegistry){
		return !element.isAnnotationPresent(Any.class) && 
				AnnotationUtil.isBuildEntity(componentRegistry,element.getMappingType(), element.getClassType());		
	}
	
}
