/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
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

        mappingBean.setBean( bean );
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
