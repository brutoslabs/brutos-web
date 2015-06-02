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

package org.brandao.brutos.annotation.configuration;

import org.brandao.brutos.*;
import org.brandao.brutos.annotation.AnyElementCollection;
import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.mapping.MappingException;

/**
 *
 * @author Brandao
 */
@Stereotype(target=ElementCollection.class, executeAfter=Bean.class)
public class ElementCollectionAnnotationConfig 
    extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof ElementEntry;
    }

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try{
			return this.applyConfiguration0(source, builder, componentRegistry);
		} catch (Exception e) {
			throw new MappingException("can't create element of collection: "
					+ ((ElementEntry) source).getName(), e);
		}
	}
    
    public Object applyConfiguration0(Object source, Object builder, 
            ComponentRegistry componentRegistry) {
        
        ElementEntry element = (ElementEntry)source;
        
        if(!element.isAnnotationPresent(AnyElementCollection.class) && AnnotationUtil.isBuildEntity(componentRegistry, element.getMappingType(), element.getClassType()))
            buildElement(element, builder, componentRegistry);
        else
            addElement(element, (BeanBuilder)builder, componentRegistry);
        

        return builder;
    }
    
    protected void addElement(ElementEntry elementEntry, BeanBuilder builder, 
            ComponentRegistry componentRegistry){
        
        String element = elementEntry.getName();
        EnumerationType enumType = elementEntry.getEnumerated();
        String tempType = elementEntry.getTemporal();
        ScopeType scope = elementEntry.getScopeType();
        org.brandao.brutos.type.Type type = 
                elementEntry.getType() == null? 
            		null : 
        			AnnotationUtil.getTypeInstance(elementEntry.getType());
        
        Object classType;
        
        if(elementEntry.isAnnotationPresent(AnyElementCollection.class))
        	classType = Object.class;
        else{
        	classType = 
        			elementEntry.getTarget() == null? 
    					elementEntry.getGenericType() : 
						elementEntry.getTarget();
        }
        
        ElementBuilder elementBuilder = builder.setElement(
            element, enumType, tempType, null, scope, null, false, type, classType);
        
        super.applyInternalConfiguration(
        		elementEntry, 
                elementBuilder, 
                componentRegistry);
    }
    
    protected void buildElement(ElementEntry element, Object builder, 
            ComponentRegistry componentRegistry){
        super.applyInternalConfiguration(
                element, 
                builder, 
                componentRegistry);
    }
    
    
}
