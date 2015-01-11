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


package org.brandao.brutos.annotation.web.test;

import java.util.Properties;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.web.AnnotationWebApplicationContext;
import org.brandao.brutos.test.MockObjectFactory;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.test.MockScope;
import org.brandao.brutos.validator.JSR303ValidatorFactory;
import org.brandao.brutos.web.WebScopeType;
import org.brandao.brutos.web.test.MockWebInvoker;

/**
 *
 * @author Brandao
 */
public class MockAnnotationWebApplicationContext 
    extends AnnotationWebApplicationContext{

    @Override
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

    @Override
    protected void overrideConfig(){
        Properties config = this.getConfiguration();
        config.put(BrutosConstants.RENDER_VIEW_CLASS, MockRenderView.class.getName());
        config.put(BrutosConstants.INVOKER_CLASS, MockWebInvoker.class.getName());
        config.put(BrutosConstants.OBJECT_FACTORY_CLASS, MockObjectFactory.class.getName());
        config.put(BrutosConstants.VALIDATOR_FACTORY_CLASS, JSR303ValidatorFactory.class.getName());
        super.overrideConfig();
    }
    
}
