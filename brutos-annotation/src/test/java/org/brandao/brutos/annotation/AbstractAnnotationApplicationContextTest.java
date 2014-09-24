/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.util.Properties;
import junit.framework.TestCase;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.test.MockObjectFactory;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.validator.DefaultValidatorFactory;
import org.brandao.brutos.web.WebControllerManager;

/**
 *
 * @author Brandao
 */
public abstract class AbstractAnnotationApplicationContextTest extends TestCase{

    protected abstract ConfigurableApplicationContext getApplication(Class[] clazz);
    
    /*protected AnnotationApplicationContext getApplication(Class[] clazz){
        AnnotationApplicationContext 
            annotationApplicationContext = 
                new AnnotationApplicationContext(
                        clazz
                );
        
        Properties prop = new Properties();
        prop.setProperty("org.brandao.brutos.ioc.provider",
                MockObjectFactory.class.getName());
        
        prop.setProperty("org.brandao.brutos.view.provider",
                MockRenderView.class.getName());

        prop.setProperty("org.brandao.brutos.view.prefix",
                "/WEB-INF/");

        prop.setProperty("org.brandao.brutos.view.suffix",
                ".jsp");

        prop.setProperty("org.brandao.brutos.view.separator",
                "/");
        
        prop.setProperty("org.brandao.brutos.view.index",
                "index");
        
        prop.setProperty(BrutosConstants.WEB_APPLICATION_CLASS, "");
        
        prop.setProperty(BrutosConstants.ACTION_TYPE, ActionType.HIERARCHY.name());

        prop.setProperty( BrutosConstants.CONTROLLER_MANAGER_CLASS,
                              WebControllerManager.class.getName() );
        
        prop.setProperty(BrutosConstants.VALIDATOR_FACTORY_CLASS, 
                DefaultValidatorFactory.class.getName());

        prop.setProperty(BrutosConstants.OBJECT_FACTORY_CLASS,
                MockObjectFactory.class.getName());
        
        annotationApplicationContext.setConfiguration(prop);
        annotationApplicationContext.flush();
        return annotationApplicationContext;
    }
    */
    
}
