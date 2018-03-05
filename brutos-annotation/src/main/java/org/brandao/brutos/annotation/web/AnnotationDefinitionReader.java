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

package org.brandao.brutos.annotation.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.ComponentConfigurer;
import org.brandao.brutos.annotation.Configuration;
import org.brandao.brutos.annotation.FilterType;
import org.brandao.brutos.annotation.configuration.ConfigurationEntry;
import org.brandao.brutos.annotation.configuration.web.WebActionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.web.WebControllerAnnotationConfig;
import org.brandao.brutos.web.WebXMLComponentDefinitionReader;
import org.brandao.brutos.xml.FilterEntity;
import org.brandao.brutos.xml.ScannerEntity;

/**
 *
 * @author Brandão
 */
public class AnnotationDefinitionReader extends WebXMLComponentDefinitionReader {

	private ConfigurableApplicationContext applicationContext;

	public AnnotationDefinitionReader(
			ConfigurableApplicationContext applicationContext,
			ComponentRegistry componentRegistry) {
		super(componentRegistry);
		this.applicationContext = applicationContext;
	}

	public void loadDefinitions() {
		//contém as classes de configuração da aplicação.
		List<Class<?>> baseAnnotation = new ArrayList<Class<?>>();
		
		//adiciona as classes base de uma aplicação com anotação.
		baseAnnotation.addAll(ComponentConfigurer.defaultAnnotationConfig);
		
		//adiciona as classes base de uma aplicação web.
		baseAnnotation.add(WebControllerAnnotationConfig.class);
		baseAnnotation.add(WebActionAnnotationConfig.class);
		baseAnnotation.add(WebThrowSafeAnnotationConfig.class);
		
		ComponentConfigurer componentConfigurer = 
			new ComponentConfigurer(applicationContext, baseAnnotation);

		ConfigurationEntry config = new ConfigurationEntry();

		if (this.getScannerEntity() != null) {
			ScannerEntity scannerEntity = super.getScannerEntity();
			config.setBasePackage(Arrays.asList(scannerEntity.getBasePackage()));
			config.setExcludeFilters(scannerEntity.getExcludeFilters());
			config.setIncludeFilters(scannerEntity.getIncludeFilters());
			config.setScannerClassName(scannerEntity.getScannerClassName());
			config.setUseDefaultfilter(scannerEntity.isUseDefaultfilter());
		} else {
			config.setBasePackage(Arrays.asList(new String[] { "" }));
			config.setExcludeFilters(null);
			config.setIncludeFilters(Arrays
					.asList(new FilterEntity[] { new FilterEntity(
							FilterType.ANNOTATION.getName(), Arrays
									.asList(new String[] { Configuration.class
											.getName() })) }));
			config.setScannerClassName(null);
			config.setUseDefaultfilter(true);
		}

		componentConfigurer.setConfiguration(config);
		componentConfigurer.init(this.componentRegistry);
	}

}
