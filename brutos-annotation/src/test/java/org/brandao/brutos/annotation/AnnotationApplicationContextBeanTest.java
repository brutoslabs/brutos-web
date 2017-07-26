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

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.helper.*;
import org.brandao.brutos.annotation.helper.bean.BeanConstructorTest;
import org.brandao.brutos.annotation.helper.bean.CustomArrayList;
import org.brandao.brutos.annotation.helper.bean.NewArrayList;
import org.brandao.brutos.annotation.helper.bean.NewHashMap;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.type.IntegerWrapperType;
import org.brandao.brutos.type.StringType;
import org.brandao.brutos.type.TypeUtil;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextBeanTest 
    extends AbstractWebAnnotationApplicationContextTest{

    public AnnotationApplicationContextBeanTest(){
    }
    
    @Override
    public ConfigurableApplicationContext getApplication(Class[] clazz, String complement){
        ConfigurableApplicationContext context = super.getApplication(clazz, complement);
        return context;
    }
    
    public void testBean1() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyA");
        assertEquals(int.class, property.getClassType());
        assertEquals("propertyA", property.getName());
        assertEquals("propertyA", property.getParameterName());
        assertNull(property.getMapping());
        
    }

    public void testBean2() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyB");
        assertEquals(String.class, property.getClassType());
        assertEquals("propertyB", property.getName());
        assertEquals("prop", property.getParameterName());
        assertNull(property.getMapping());
        
    }

    public void testBean3() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyC");
        assertEquals(Date.class, property.getClassType());
        assertEquals("propertyC", property.getName());
        assertEquals("propertyC", property.getParameterName());
        assertEquals(annotationApplicationContext.getTemporalProperty(), property.getTemporalType());
        assertNull(property.getMapping());
        
    }

    public void testBean4() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyD");
        assertEquals(Date.class, property.getClassType());
        assertEquals("propertyD", property.getName());
        assertEquals("propertyD", property.getParameterName());
        assertEquals("yyyy-MM-dd", property.getTemporalType());
        assertNull(property.getMapping());
        
    }

    public void testBean5() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyE");
        assertEquals(EnumTest.class, property.getClassType());
        assertEquals("propertyE", property.getName());
        assertEquals("propertyE", property.getParameterName());
        assertEquals(annotationApplicationContext.getEnumerationType(), property.getEnumProperty());
        assertNull(property.getMapping());
        
    }

    public void testBean6() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyF");
        assertEquals(EnumTest.class, property.getClassType());
        assertEquals("propertyF", property.getName());
        assertEquals("propertyF", property.getParameterName());
        assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, property.getEnumProperty());
        assertNull(property.getMapping());
        
    }

    public void testBean7() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyG");
        assertEquals(EnumTest.class, property.getClassType());
        assertEquals("propertyG", property.getName());
        assertEquals("propertyG", property.getParameterName());
        assertEquals(org.brandao.brutos.EnumerationType.STRING, property.getEnumProperty());
        assertNull(property.getMapping());
        
    }

    public void testBean8() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyH");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("myKey", key.getParameterName());
        assertEquals(Integer.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(EnumerationType.STRING, key.getEnumProperty());
        assertEquals("yyyy-MM-dd", key.getTemporalType());
        assertEquals(IntegerWrapperType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(String.class, element.getClassType());
        assertEquals(ScopeType.REQUEST, element.getScopeType());
        assertEquals(EnumerationType.STRING, element.getEnumProperty());
        assertEquals("yyyy-MM-dd", element.getTemporalType());
        assertEquals(StringType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean9() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyI");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(NewHashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean10() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyJ");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(BeanConstructorTest.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNotNull(key.getMapping());
        assertNull(key.getType());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean11() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyK");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean12() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyL");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean13() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;

        ConfigurableApplicationContext annotationApplicationContext =
            getApplication(new Class[]{clazz});
        
        
        org.brandao.brutos.mapping.Controller
            controller = annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyM");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
    }

    public void testBean14() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyN");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(NewArrayList.class, beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean15() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyO");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean16() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyP");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean17() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyQ");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertEquals("beanConstructorTest", element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
    }

    public void testBean18() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyR");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("myKey", key.getParameterName());
        assertEquals(BeanConstructorTest.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNotNull(key.getMapping());
        assertNull(key.getType());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean19() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyS");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean20() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyT");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(TypeUtil.getDefaultListType(), element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        assertEquals("element", subElement.getParameterName());
        assertEquals(BeanConstructorTest.class, subElement.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        assertNotNull(subElement.getMapping());
        assertNotNull(subElement.getType());
        assertNull(subElement.getValue());
        
    }

    public void testBean21() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyU");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(CustomArrayList.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        assertEquals("myElement", subElement.getParameterName());
        assertEquals(BeanConstructorTest.class, subElement.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        assertNotNull(subElement.getMapping());
        assertNotNull(subElement.getType());
        assertNull(subElement.getValue());
        
    }

    public void testBean22() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyV");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement2", element.getParameterName());
        assertEquals(CustomArrayList.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        assertEquals("myElement", subElement.getParameterName());
        assertEquals(BeanConstructorTest.class, subElement.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        assertNotNull(subElement.getMapping());
        assertNotNull(subElement.getType());
        assertNull(subElement.getValue());
        
    }
    
    
    public void testBean22X() throws NoSuchMethodException{
        Class<?> clazz = BeanTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyX");
        assertEquals(BeanConstructorTest.class, property.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, property.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, property.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, property.getTemporalType());
        assertNotNull(property.getMapping());
        assertNotNull(property.getType());
        assertNull(property.getValue());
        
    }
    
    public void testBean23() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyA");
        assertEquals(int.class, property.getClassType());
        assertEquals("propertyA", property.getName());
        assertEquals("propertyA", property.getParameterName());
        assertNull(property.getMapping());
        
    }

    public void testBean24() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyB");
        assertEquals(String.class, property.getClassType());
        assertEquals("propertyB", property.getName());
        assertEquals("prop", property.getParameterName());
        assertNull(property.getMapping());
        
    }

    public void testBean25() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyC");
        assertEquals(Date.class, property.getClassType());
        assertEquals("propertyC", property.getName());
        assertEquals("propertyC", property.getParameterName());
        assertEquals("dd/MM/yyyy", property.getTemporalType());
        assertNull(property.getMapping());
        
    }

    public void testBean26() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyD");
        assertEquals(Date.class, property.getClassType());
        assertEquals("propertyD", property.getName());
        assertEquals("propertyD", property.getParameterName());
        assertEquals("yyyy-MM-dd", property.getTemporalType());
        assertNull(property.getMapping());
        
    }

    public void testBean27() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyE");
        assertEquals(EnumTest.class, property.getClassType());
        assertEquals("propertyE", property.getName());
        assertEquals("propertyE", property.getParameterName());
        assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, property.getEnumProperty());
        assertNull(property.getMapping());
        
    }

    public void testBean28() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyF");
        assertEquals(EnumTest.class, property.getClassType());
        assertEquals("propertyF", property.getName());
        assertEquals("propertyF", property.getParameterName());
        assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, property.getEnumProperty());
        assertNull(property.getMapping());
        
    }

    public void testBean29() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyG");
        assertEquals(EnumTest.class, property.getClassType());
        assertEquals("propertyG", property.getName());
        assertEquals("propertyG", property.getParameterName());
        assertEquals(org.brandao.brutos.EnumerationType.STRING, property.getEnumProperty());
        assertNull(property.getMapping());
        
    }

    public void testBean30() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyH");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("myKey", key.getParameterName());
        assertEquals(Integer.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(EnumerationType.STRING, key.getEnumProperty());
        assertEquals("yyyy-MM-dd", key.getTemporalType());
        assertEquals(IntegerWrapperType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(String.class, element.getClassType());
        assertEquals(ScopeType.REQUEST, element.getScopeType());
        assertEquals(EnumerationType.STRING, element.getEnumProperty());
        assertEquals("yyyy-MM-dd", element.getTemporalType());
        assertEquals(StringType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean31() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyI");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(NewHashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean32() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyJ");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(BeanConstructorTest.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNotNull(key.getMapping());
        assertNull(key.getType());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean33() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyK");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean34() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyL");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean35() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyM");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean36() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyN");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(NewArrayList.class, beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean37() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyO");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testBean38() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyP");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean39() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyQ");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertEquals("beanConstructorTest",element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean40() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyR");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("myKey", key.getParameterName());
        assertEquals(BeanConstructorTest.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNotNull(key.getMapping());
        assertNull(key.getType());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean41() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyS");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testBean42() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyT");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(TypeUtil.getDefaultListType(), element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        assertEquals("element", subElement.getParameterName());
        assertEquals(BeanConstructorTest.class, subElement.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        assertNotNull(subElement.getMapping());
        assertNotNull(subElement.getType());
        assertNull(subElement.getValue());
        
    }

    public void testBean43() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyU");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(CustomArrayList.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        assertEquals("myElement", subElement.getParameterName());
        assertEquals(BeanConstructorTest.class, subElement.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        assertNotNull(subElement.getMapping());
        assertNotNull(subElement.getType());
        assertNull(subElement.getValue());
        
    }

    public void testBean44() throws NoSuchMethodException{
        Class<?> clazz = BeanTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();

        PropertyBean property = bean.getProperty("propertyV");
        org.brandao.brutos.mapping.Bean beanProperty = property.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement2", element.getParameterName());
        assertEquals(CustomArrayList.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        assertEquals("myElement", subElement.getParameterName());
        assertEquals(BeanConstructorTest.class, subElement.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        assertNotNull(subElement.getMapping());
        assertNotNull(subElement.getType());
        assertNull(subElement.getValue());
        
    }
    
    public void testConstructorBean() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest3Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        assertEquals(int.class, arg.getClassType());
        assertEquals("arg0", arg.getParameterName());
        assertNull(arg.getMapping());
    }

    public void testConstructoBean2() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest4Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        assertEquals(String.class, arg.getClassType());
        assertEquals("prop", arg.getParameterName());
        assertNull(arg.getMapping());
    }

    public void testConstructoBean3() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest5Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        assertEquals(Date.class, arg.getClassType());
        assertEquals("arg0", arg.getParameterName());
        assertEquals("dd/MM/yyyy", arg.getTemporalType());
        assertNull(arg.getMapping());
    }

    public void testConstructoBean4() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest6Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        assertEquals(Date.class, arg.getClassType());
        assertEquals("arg0", arg.getParameterName());
        assertEquals("yyyy-MM-dd", arg.getTemporalType());
        assertNull(arg.getMapping());
    }

    public void testConstructoBean5() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest7Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        assertEquals(EnumTest.class, arg.getClassType());
        assertEquals("arg0", arg.getParameterName());
        assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, arg.getEnumProperty());
        assertNull(arg.getMapping());
    }

    public void testConstructoBean6() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest8Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        assertEquals(EnumTest.class, arg.getClassType());
        assertEquals("arg0", arg.getParameterName());
        assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, arg.getEnumProperty());
        assertNull(arg.getMapping());
    }

    public void testConstructoBean7() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest9Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        assertEquals(EnumTest.class, arg.getClassType());
        assertEquals("arg0", arg.getParameterName());
        assertEquals(org.brandao.brutos.EnumerationType.STRING, arg.getEnumProperty());
        assertNull(arg.getMapping());
    }

    public void testConstructoBean8() throws NoSuchMethodException{
        
        try{
            Class<?> clazz = BeanTest10Controller.class;
            getApplication(new Class[]{clazz});
            fail();
        }
        catch(BrutosException e){
        }
        
    }

    public void testConstructoBean9() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest11Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        assertEquals(EnumTest.class, arg.getClassType());
        assertEquals("arg0", arg.getParameterName());
        assertEquals(org.brandao.brutos.EnumerationType.STRING, arg.getEnumProperty());
        assertNull(arg.getMapping());
    }

    public void testConstructoBean10() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest12Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        
        assertEquals(int.class, arg.getClassType());
        assertEquals("arg0", arg.getParameterName());
        assertNull(arg.getMapping());
    }

    //Não aplicavel
    public void testConstructoBean11() throws NoSuchMethodException{
        
        try{
            Class<?> clazz = BeanTest13Controller.class;
            getApplication(new Class[]{clazz});
            //fail();
        }
        catch(BrutosException e){
        }
    }

    public void testConstructoBean12() throws NoSuchMethodException{
        
        Class<?> clazz = BeanTest14Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("myKey", key.getParameterName());
        assertEquals(Integer.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(EnumerationType.STRING, key.getEnumProperty());
        assertEquals("yyyy-MM-dd", key.getTemporalType());
        assertEquals(IntegerWrapperType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(String.class, element.getClassType());
        assertEquals(ScopeType.REQUEST, element.getScopeType());
        assertEquals(EnumerationType.STRING, element.getEnumProperty());
        assertEquals("yyyy-MM-dd", element.getTemporalType());
        assertEquals(StringType.class,element.getType().getClass());
        assertNull(element.getValue());
    }

    public void testConstructoBean13() throws NoSuchMethodException{
        Class<?> clazz = BeanTest15Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(NewHashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean14() throws NoSuchMethodException{
        Class<?> clazz = BeanTest16Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(BeanConstructorTest.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNotNull(key.getMapping());
        assertNull(key.getType());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean15() throws NoSuchMethodException{
        Class<?> clazz = BeanTest17Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean16() throws NoSuchMethodException{
        Class<?> clazz = BeanTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean17() throws NoSuchMethodException{
        Class<?> clazz = BeanTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean18() throws NoSuchMethodException{
        Class<?> clazz = BeanTest20Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(NewArrayList.class, beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean19() throws NoSuchMethodException{
        Class<?> clazz = BeanTest21Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(Integer.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNull(element.getMapping());
        assertEquals(IntegerWrapperType.class,element.getType().getClass());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean20() throws NoSuchMethodException{
        Class<?> clazz = BeanTest22Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean21() throws NoSuchMethodException{
        Class<?> clazz = BeanTest23Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertEquals("beanConstructorTest", element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean22() throws NoSuchMethodException{
        Class<?> clazz = BeanTest24Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("myKey", key.getParameterName());
        assertEquals(BeanConstructorTest.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNotNull(key.getMapping());
        assertNull(key.getType());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean23() throws NoSuchMethodException{
        Class<?> clazz = BeanTest25Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        assertEquals("myElement", element.getParameterName());
        assertEquals(BeanConstructorTest.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
    }

    public void testConstructoBean24() throws NoSuchMethodException{
        Class<?> clazz = BeanTest26Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(TypeUtil.getDefaultListType(), element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        assertEquals("element", subElement.getParameterName());
        assertEquals(BeanConstructorTest.class, subElement.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        assertNotNull(subElement.getMapping());
        assertNotNull(subElement.getType());
        assertNull(subElement.getValue());
        
    }

    public void testConstructoBean25() throws NoSuchMethodException{
        Class<?> clazz = BeanTest27Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("element", element.getParameterName());
        assertEquals(CustomArrayList.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        assertEquals("myElement", subElement.getParameterName());
        assertEquals(BeanConstructorTest.class, subElement.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        assertNotNull(subElement.getMapping());
        assertNotNull(subElement.getType());
        assertNull(subElement.getValue());
        
    }

    public void testConstructoBean26() throws NoSuchMethodException{
        Class<?> clazz = BeanTest28Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ParameterAction param = action.getParameter(0);
        
        org.brandao.brutos.mapping.Bean bean = param.getMapping();
        
        ConstructorBean constructor = bean.getConstructor();
        assertEquals(1,constructor.size());
        ConstructorArgBean arg = constructor.getConstructorArg(0);
        org.brandao.brutos.mapping.Bean beanProperty = arg.getBean();
        assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        assertEquals("key", key.getParameterName());
        assertEquals(String.class, key.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        assertNull(key.getMapping());
        assertEquals(StringType.class,key.getType().getClass());
        assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        assertEquals("myElement2", element.getParameterName());
        assertEquals(CustomArrayList.class, element.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        assertNotNull(element.getMapping());
        assertNotNull(element.getType());
        assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        assertEquals("myElement", subElement.getParameterName());
        assertEquals(BeanConstructorTest.class, subElement.getClassType());
        assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        assertNotNull(subElement.getMapping());
        assertNotNull(subElement.getType());
        assertNull(subElement.getValue());
        
    }
    
}
