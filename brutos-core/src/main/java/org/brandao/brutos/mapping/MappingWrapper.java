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

package org.brandao.brutos.mapping;

import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.old.programatic.WebFrameManager;

/**
 * @deprecated 
 * @author Afonso Brandao
 */
public class MappingWrapper extends Mapping{

    protected Mapping mapping;
    
    public MappingWrapper( Mapping mapping ){
        this.mapping = mapping;
        throw new UnsupportedOperationException( "deprecated: use DefaultContextWrapper" );
    }

    public void destroy() {
        mapping.destroy();
    }

    public void loadIOCManager(IOCManager iocManager) {
        mapping.loadIOCManager(iocManager);
    }

    public void loadWebFrameManager(WebFrameManager webFrameManager) {
        mapping.loadWebFrameManager(webFrameManager);
    }

    public void loadInterceptorManager(InterceptorManager interceptorManager) {
        mapping.loadInterceptorManager(interceptorManager);
    }

}
