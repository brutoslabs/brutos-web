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

import java.util.List;
import org.brandao.brutos.xml.FilterEntity;

/**
 *
 * @author Brandao
 */
public class ConfigurationEntry {

	private List<String> basePackage;

	private String scannerClassName;

	private List<FilterEntity> excludeFilters;

	private List<FilterEntity> includeFilters;

	private List<Class<?>> allClazz;

	private List<Class<?>> configClass;

	private boolean useDefaultfilter;

	public List<String> getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(List<String> basePackage) {
		this.basePackage = basePackage;
	}

	public String getScannerClassName() {
		return scannerClassName;
	}

	public void setScannerClassName(String scannerClassName) {
		this.scannerClassName = scannerClassName;
	}

	public List<FilterEntity> getExcludeFilters() {
		return excludeFilters;
	}

	public void setExcludeFilters(List<FilterEntity> excludeFilters) {
		this.excludeFilters = excludeFilters;
	}

	public List<FilterEntity> getIncludeFilters() {
		return includeFilters;
	}

	public void setIncludeFilters(List<FilterEntity> includeFilters) {
		this.includeFilters = includeFilters;
	}

	public List<Class<?>> getAllClazz() {
		return allClazz;
	}

	public void setAllClazz(List<Class<?>> allClazz) {
		this.allClazz = allClazz;
	}

	public List<Class<?>> getConfigClass() {
		return configClass;
	}

	public void setConfigClass(List<Class<?>> configClass) {
		this.configClass = configClass;
	}

	public boolean isUseDefaultfilter() {
		return useDefaultfilter;
	}

	public void setUseDefaultfilter(boolean useDefaultfilter) {
		this.useDefaultfilter = useDefaultfilter;
	}

}
