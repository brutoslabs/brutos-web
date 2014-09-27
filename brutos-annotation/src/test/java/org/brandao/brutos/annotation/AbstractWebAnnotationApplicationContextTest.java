/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.web.AnnotationWebApplicationContext;
import org.brandao.brutos.io.ByteArrayResource;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.test.MockObjectFactory;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.test.MockScope;
import org.brandao.brutos.validator.DefaultValidatorFactory;
import org.brandao.brutos.web.WebScopeType;
import org.brandao.brutos.web.test.MockWebInvoker;
import junit.framework.TestCase;

/**
 *
 * @author Brandao
 */
public abstract class AbstractWebAnnotationApplicationContextTest 
    extends TestCase 
    implements AnnotationApplicationContextTest{
    
    @Override
    public ConfigurableApplicationContext getApplication(Class[] clazz){
        String xml = "";
        xml +="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml +="<ns2:controllers";
        xml +="    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'";
        xml +="    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'";
        xml +="    xmlns:ns1='http://www.brutosframework.com.br/schema/context'";
        xml +="    xsi:schemaLocation='";
        xml +="    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd";
        xml +="    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>";
        xml +="<ns1:component-scan use-default-filters=\"false\">";
        
        for(Class c: clazz){
            xml +="        <ns1:include-filter type=\"regex\" expression=\""+c.getName().replace(".","\\.")+"\"/>";
        }
        
        xml +="</ns1:component-scan>";
        xml +="</ns2:controllers>";
        
        
        AnnotationWebApplicationContext context =
                new AnnotationWebApplicationContext(){
                  
                    protected void initScopes(){
                        getScopes().register( WebScopeType.APPLICATION.toString(),
                                new MockScope() );
                        getScopes().register( WebScopeType.FLASH.toString(),
                                new MockScope() );
                        getScopes().register( WebScopeType.IOC.toString(),
                                new MockScope() );
                        getScopes().register( WebScopeType.REQUEST.toString(),
                                new MockScope() );
                        getScopes().register( WebScopeType.SESSION.toString(),
                                new MockScope() );
                        getScopes().register( WebScopeType.PARAM.toString(),
                                new MockScope() );
                    }
                };
        
        Properties config = context.getConfiguration();
        
        config.setProperty(BrutosConstants.OBJECT_FACTORY_CLASS,
                MockObjectFactory.class.getName());

        config.setProperty(BrutosConstants.VALIDATOR_FACTORY_CLASS, 
                DefaultValidatorFactory.class.getName());

        config.setProperty(BrutosConstants.INVOKER_CLASS, 
                MockWebInvoker.class.getName());
        
        //config.setProperty(BrutosConstants.ACTION_TYPE, actionType.name());
        
        config.setProperty(BrutosConstants.RENDER_VIEW_CLASS,
                MockRenderView.class.getName());

        config.setProperty(BrutosConstants.VIEW_RESOLVER_AUTO, 
                "true");
        
        context
            .setResources(
                    new Resource[]{
                        new ByteArrayResource(xml.getBytes())});
        
        context.flush();
        return context;
    }
    
}
