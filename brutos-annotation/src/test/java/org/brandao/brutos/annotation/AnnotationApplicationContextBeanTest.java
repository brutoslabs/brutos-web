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

import java.util.*;
import junit.framework.Assert;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.annotation.helper.*;
import org.brandao.brutos.annotation.helper.bean.BeanConstructorTest;
import org.brandao.brutos.annotation.helper.bean.CustomArrayList;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextBeanTest 
    extends AbstractApplicationContextTest{
    
    public void testBean1() throws NoSuchMethodException{
        
        Class clazz = BeanTest1Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        PropertyBean property = bean.getProperty("propertyA");
        Assert.assertEquals(int.class, property.getClassType());
        Assert.assertEquals("propertyA", property.getName());
        Assert.assertEquals("propertyA", property.getParameterName());
        Assert.assertNull(property.getMapping());
        
        property = bean.getProperty("propertyB");
        Assert.assertEquals(String.class, property.getClassType());
        Assert.assertEquals("propertyB", property.getName());
        Assert.assertEquals("prop", property.getParameterName());
        Assert.assertNull(property.getMapping());
        
        property = bean.getProperty("propertyC");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyC", property.getName());
        Assert.assertEquals("propertyC", property.getParameterName());
        Assert.assertEquals("dd/MM/yyyy", property.getTemporalType());
        Assert.assertNull(property.getMapping());
        
        property = bean.getProperty("propertyD");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyD", property.getName());
        Assert.assertEquals("propertyD", property.getParameterName());
        Assert.assertEquals("yyyy-MM-dd", property.getTemporalType());
        Assert.assertNull(property.getMapping());
        
        property = bean.getProperty("propertyE");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyE", property.getName());
        Assert.assertEquals("propertyE", property.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, property.getEnumProperty());
        Assert.assertNull(property.getMapping());

        property = bean.getProperty("propertyF");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyF", property.getName());
        Assert.assertEquals("propertyF", property.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, property.getEnumProperty());
        Assert.assertNull(property.getMapping());

        property = bean.getProperty("propertyG");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyG", property.getName());
        Assert.assertEquals("propertyG", property.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.STRING, property.getEnumProperty());
        Assert.assertNull(property.getMapping());
        
        
        property = bean.getProperty("propertyH");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("myKey", key.getParameterName());
        Assert.assertEquals(Integer.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(EnumerationType.STRING, key.getEnumProperty());
        Assert.assertEquals("yyyy-MM-dd", key.getTemporalType());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(String.class, element.getClassType());
        Assert.assertEquals(ScopeType.REQUEST, element.getScope());
        Assert.assertEquals(EnumerationType.STRING, element.getEnumProperty());
        Assert.assertEquals("yyyy-MM-dd", element.getTemporalType());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyI");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(LinkedHashMap.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyJ");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyK");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        property = bean.getProperty("propertyL");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyM");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyN");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(LinkedList.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyO");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyP");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyQ");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyR");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("myKey", key.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyS");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyT");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(List.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getMappingBean()).getCollection();
        Assert.assertEquals("element", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());

        property = bean.getProperty("propertyU");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(CustomArrayList.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        subElement = ((CollectionBean)element.getMappingBean()).getCollection();
        Assert.assertEquals("myElement", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());

        property = bean.getProperty("propertyV");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement2", element.getParameterName());
        Assert.assertEquals(CustomArrayList.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        subElement = ((CollectionBean)element.getMappingBean()).getCollection();
        Assert.assertEquals("myElement", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());
        
    }

    public void testBean2() throws NoSuchMethodException{
        
        Class clazz = BeanTest2Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        PropertyBean property = bean.getProperty("propertyA");
        Assert.assertEquals(int.class, property.getClassType());
        Assert.assertEquals("propertyA", property.getName());
        Assert.assertEquals("propertyA", property.getParameterName());
        Assert.assertNull(property.getMapping());
        
        property = bean.getProperty("propertyB");
        Assert.assertEquals(String.class, property.getClassType());
        Assert.assertEquals("propertyB", property.getName());
        Assert.assertEquals("prop", property.getParameterName());
        Assert.assertNull(property.getMapping());
        
        property = bean.getProperty("propertyC");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyC", property.getName());
        Assert.assertEquals("propertyC", property.getParameterName());
        Assert.assertEquals("dd/MM/yyyy", property.getTemporalType());
        Assert.assertNull(property.getMapping());
        
        property = bean.getProperty("propertyD");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyD", property.getName());
        Assert.assertEquals("propertyD", property.getParameterName());
        Assert.assertEquals("yyyy-MM-dd", property.getTemporalType());
        Assert.assertNull(property.getMapping());
        
        property = bean.getProperty("propertyE");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyE", property.getName());
        Assert.assertEquals("propertyE", property.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, property.getEnumProperty());
        Assert.assertNull(property.getMapping());

        property = bean.getProperty("propertyF");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyF", property.getName());
        Assert.assertEquals("propertyF", property.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, property.getEnumProperty());
        Assert.assertNull(property.getMapping());

        property = bean.getProperty("propertyG");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyG", property.getName());
        Assert.assertEquals("propertyG", property.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.STRING, property.getEnumProperty());
        Assert.assertNull(property.getMapping());
        
        property = bean.getProperty("propertyH");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("myKey", key.getParameterName());
        Assert.assertEquals(Integer.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(EnumerationType.STRING, key.getEnumProperty());
        Assert.assertEquals("yyyy-MM-dd", key.getTemporalType());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(String.class, element.getClassType());
        Assert.assertEquals(ScopeType.REQUEST, element.getScope());
        Assert.assertEquals(EnumerationType.STRING, element.getEnumProperty());
        Assert.assertEquals("yyyy-MM-dd", element.getTemporalType());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyI");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(LinkedHashMap.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyJ");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyK");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        property = bean.getProperty("propertyL");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyM");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyN");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(LinkedList.class, beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyO");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyP");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyQ");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyR");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("myKey", key.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyS");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultListType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertNull(key);

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());

        property = bean.getProperty("propertyT");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(List.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getMappingBean()).getCollection();
        Assert.assertEquals("element", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());

        property = bean.getProperty("propertyU");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(CustomArrayList.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        subElement = ((CollectionBean)element.getMappingBean()).getCollection();
        Assert.assertEquals("myElement", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());

        property = bean.getProperty("propertyV");
        beanProperty = property.getMappingBean();
        Assert.assertEquals(TypeManager.getDefaultMapType(), beanProperty.getClassType());
        
        key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement2", element.getParameterName());
        Assert.assertEquals(CustomArrayList.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        subElement = ((CollectionBean)element.getMappingBean()).getCollection();
        Assert.assertEquals("myElement", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScope());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());
        
    }

    public void testConstructorBean() throws NoSuchMethodException{
        
        Class clazz = BeanTest3Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        Assert.assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        Assert.assertEquals(int.class, arg.getClassType());
        Assert.assertEquals("arg0", arg.getParameterName());
        Assert.assertNull(arg.getMapping());
    }

    public void testConstructoBean2() throws NoSuchMethodException{
        
        Class clazz = BeanTest4Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        Assert.assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        Assert.assertEquals(String.class, arg.getClassType());
        Assert.assertEquals("prop", arg.getParameterName());
        Assert.assertNull(arg.getMapping());
    }

    public void testConstructoBean3() throws NoSuchMethodException{
        
        Class clazz = BeanTest5Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        Assert.assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        Assert.assertEquals(Date.class, arg.getClassType());
        Assert.assertEquals("arg0", arg.getParameterName());
        Assert.assertEquals("dd/MM/yyyy", arg.getTemporalType());
        Assert.assertNull(arg.getMapping());
    }

    public void testConstructoBean4() throws NoSuchMethodException{
        
        Class clazz = BeanTest6Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        Assert.assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        Assert.assertEquals(Date.class, arg.getClassType());
        Assert.assertEquals("arg0", arg.getParameterName());
        Assert.assertEquals("yyyy-MM-dd", arg.getTemporalType());
        Assert.assertNull(arg.getMapping());
    }

    public void testConstructoBean5() throws NoSuchMethodException{
        
        Class clazz = BeanTest7Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        Assert.assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        Assert.assertEquals(EnumTest.class, arg.getClassType());
        Assert.assertEquals("arg0", arg.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, arg.getEnumProperty());
        Assert.assertNull(arg.getMapping());
    }

    public void testConstructoBean6() throws NoSuchMethodException{
        
        Class clazz = BeanTest8Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        Assert.assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        Assert.assertEquals(EnumTest.class, arg.getClassType());
        Assert.assertEquals("arg0", arg.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, arg.getEnumProperty());
        Assert.assertNull(arg.getMapping());
    }

    public void testConstructoBean7() throws NoSuchMethodException{
        
        Class clazz = BeanTest9Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        Assert.assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        Assert.assertEquals(EnumTest.class, arg.getClassType());
        Assert.assertEquals("arg0", arg.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.STRING, arg.getEnumProperty());
        Assert.assertNull(arg.getMapping());
    }

    public void testConstructoBean8() throws NoSuchMethodException{
        
        try{
            Class clazz = BeanTest10Controller.class;
            getApplication(new Class[]{clazz});
            Assert.fail();
        }
        catch(BrutosException e){
        }
        
    }

    public void testConstructoBean9() throws NoSuchMethodException{
        
        Class clazz = BeanTest11Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        Assert.assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        Assert.assertEquals(EnumTest.class, arg.getClassType());
        Assert.assertEquals("arg0", arg.getParameterName());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.STRING, arg.getEnumProperty());
        Assert.assertNull(arg.getMapping());
    }

    public void testConstructoBean10() throws NoSuchMethodException{
        
        Class clazz = BeanTest12Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getBean().getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        Assert.assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        Assert.assertEquals(int.class, arg.getClassType());
        Assert.assertEquals("arg0", arg.getParameterName());
        Assert.assertNull(arg.getMapping());
    }

    public void testConstructoBean11() throws NoSuchMethodException{
        
        try{
            Class clazz = BeanTest13Controller.class;
            getApplication(new Class[]{clazz});
            Assert.fail();
        }
        catch(BrutosException e){
        }
    }
    
}
