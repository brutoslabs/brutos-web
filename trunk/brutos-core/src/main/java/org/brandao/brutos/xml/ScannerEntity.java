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

package org.brandao.brutos.xml;

import java.util.List;

/**
 * 
 * @author Brandao
 */
public class ScannerEntity {

	private String scannerClassName;

	private String[] basePackage;

	private boolean useDefaultfilter;

	private List<FilterEntity> excludeFilters;

	private List<FilterEntity> includeFilters;

	public String getScannerClassName() {
		return scannerClassName;
	}

	public void setScannerClassName(String scannerClassName) {
		this.scannerClassName = scannerClassName;
	}

	public String[] getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String[] basePackage) {
		this.basePackage = basePackage;
	}

	public boolean isUseDefaultfilter() {
		return useDefaultfilter;
	}

	public void setUseDefaultfilter(boolean useDefaultfilter) {
		this.useDefaultfilter = useDefaultfilter;
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

}
