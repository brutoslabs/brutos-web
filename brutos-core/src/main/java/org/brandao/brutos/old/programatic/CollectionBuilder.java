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

package org.brandao.brutos.old.programatic;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.CollectionBean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Bean;

/**
 *
 * @author Afonso Brandao
 */
public class CollectionBuilder {

    CollectionBean mappingBean;
    Controller webFrame;
    WebFrameBuilder webFrameBuilder;

    public CollectionBuilder( CollectionBean mappingBean, Controller webFrame, WebFrameBuilder webFrameBuilder ) {
        this.mappingBean = mappingBean;
        this.webFrame = webFrame;
        this.webFrameBuilder = webFrameBuilder;
    }

    
    public CollectionBuilder beanRef( String name ){
        name = name == null || name.replace( " ", "" ).length() == 0? null : name;

        if( webFrame.getBean( name ) == null )
            throw new BrutosException( 
                    "mapping " + name + " not found: " +
                    webFrame.getClassType().getName() );

        Bean bean = (Bean) webFrame.getBean( name );
        
        if( !bean.isBean() )
            throw new BrutosException(
                    "not allowed: " +
                    webFrame.getClassType().getName() );

        //mappingBean.setBean( bean );
        return this;
    }
    
    public BeanBuilder bean( Class type ){
        mappingBean.setClassType(type);
        return new BeanBuilder( mappingBean, webFrame, webFrameBuilder );
        //return webFrameBuilder.addMappingBean(mappingBean.getName() + "#bean", type);
    }
    /*
    public CollectionBuilder collection( Class<? extends Collection> type ){
        CollectionBuilder cb =
                webFrameBuilder.addMappingCollection(mappingBean.getName() + "#bean", type);
        beanRef( mappingBean.getName() + "#bean" );
        return cb;
    }

    public MapBuilder map( Class<? extends Map> type ){
        MapBuilder mb = 
                webFrameBuilder.addMappingMap(mappingBean.getName() + "#bean", type);

        beanRef( mappingBean.getName() + "#bean" );

        return mb;
    }
    */
}
