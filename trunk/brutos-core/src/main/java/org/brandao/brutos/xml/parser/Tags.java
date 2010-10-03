/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brand�o. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.xml.parser;

import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.xml.parser.tag.AliasTag;
import org.brandao.brutos.xml.parser.tag.BeanConstructorArgTag;
import org.brandao.brutos.xml.parser.tag.BeanPropertyTag;
import org.brandao.brutos.xml.parser.tag.BeanRefTag;
import org.brandao.brutos.xml.parser.tag.BeanTag;
import org.brandao.brutos.xml.parser.tag.BeanValueTag;
import org.brandao.brutos.xml.parser.tag.BeanValueNullTag;
import org.brandao.brutos.xml.parser.tag.BeansTag;
import org.brandao.brutos.xml.parser.tag.BrutosTag;
import org.brandao.brutos.xml.parser.tag.BrutosPropertyTag;
import org.brandao.brutos.xml.parser.tag.ContextParamTag;
import org.brandao.brutos.xml.parser.tag.ContextParamsTag;
import org.brandao.brutos.xml.parser.tag.ExtendConfigurationTag;
import org.brandao.brutos.xml.parser.tag.InterceptorRefTag;
import org.brandao.brutos.xml.parser.tag.InterceptorStackTag;
import org.brandao.brutos.xml.parser.tag.InterceptorTag;
import org.brandao.brutos.xml.parser.tag.InterceptorsTag;
import org.brandao.brutos.xml.parser.tag.ListTag;
import org.brandao.brutos.xml.parser.tag.MapTag;
import org.brandao.brutos.xml.parser.tag.MapEntryTag;
import org.brandao.brutos.xml.parser.tag.MapKeyTag;
import org.brandao.brutos.xml.parser.tag.MappingCollectionTag;
import org.brandao.brutos.xml.parser.tag.MappingKeyTag;
import org.brandao.brutos.xml.parser.tag.MappingMapTag;
import org.brandao.brutos.xml.parser.tag.MappingRefTag;
import org.brandao.brutos.xml.parser.tag.MappingTag;
import org.brandao.brutos.xml.parser.tag.MethodParamTag;
import org.brandao.brutos.xml.parser.tag.MethodTag;
import org.brandao.brutos.xml.parser.tag.ParamTag;
import org.brandao.brutos.xml.parser.tag.PropertyMappingTag;
import org.brandao.brutos.xml.parser.tag.PropsPropTag;
import org.brandao.brutos.xml.parser.tag.PropsTag;
import org.brandao.brutos.xml.parser.tag.ProviderTag;
import org.brandao.brutos.xml.parser.tag.SetTag;
import org.brandao.brutos.xml.parser.tag.ThrowSafeTag;
import org.brandao.brutos.xml.parser.tag.TypeTag;
import org.brandao.brutos.xml.parser.tag.TypesTag;
import org.brandao.brutos.xml.parser.tag.WebFrameTag;
import org.brandao.brutos.xml.parser.tag.WebFramesTag;

/**
 *
 * @author Afonso Brandao
 */
public class Tags {

    private static Map<String,Class<? extends Tag>> tags;

    static{
        tags = new HashMap<String,Class<? extends Tag>>();
        tags.put(XMLBrutosConstants.XML_BRUTOS, BrutosTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_CONTEXT_PARAMS, ContextParamsTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_CONTEXT_PARAM, ContextParamTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_EXTEND_CONFIGURATION, ExtendConfigurationTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_PROVIDER, ProviderTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_WEB_FRAMES, WebFramesTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_WEB_FRAME, WebFrameTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_THROWS, ThrowSafeTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_ALIAS, AliasTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_MAPPING, MappingTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_MAPPING_COLLECTION, MappingCollectionTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_MAPPING_REF, MappingRefTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_MAPPING_MAP, MappingMapTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_MAPPING_MAP_KEY, MappingKeyTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_PROPERTY_MAPPING, PropertyMappingTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_PROPERTY, BrutosPropertyTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_BEAN_PROPERY, BeanPropertyTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_BEAN_CONSTRUCTOR_ARG, BeanConstructorArgTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_BEAN_VALUE_NULL, BeanValueNullTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_BEAN_VALUE, BeanValueTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_MAP_KEY, MapKeyTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_BEAN_REF, BeanRefTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_MAP, MapTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_MAP_ENTRY, MapEntryTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_SET, SetTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_LIST, ListTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_METHOD, MethodTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_METHOD_PARAM, MethodParamTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_BEANS, BeansTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_BEAN, BeanTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_PROPS, PropsTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_PROPS_PROP, PropsPropTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_INTERCEPTORS, InterceptorsTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR, InterceptorTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR_REF, InterceptorRefTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_PARAM, ParamTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR_STACK, InterceptorStackTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_TYPES, TypesTag.class);
        tags.put(XMLBrutosConstants.XML_BRUTOS_TYPE, TypeTag.class);
    }

    public static Tag getTag( String tagName ){
        Class<? extends Tag> clazz = tags.get( tagName );
        if( clazz == null )
            throw new BrutosException( "unknown tag: " + tagName );
        else
            return getInstance( clazz );
    }

    private static <T> T getInstance( Class<T> clazz ){
        try{
            Object instance = clazz.newInstance();
            return (T)instance;
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

}
