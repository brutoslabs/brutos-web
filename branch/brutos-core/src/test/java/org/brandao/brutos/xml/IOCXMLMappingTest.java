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

package org.brandao.brutos.xml;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.ioc.PicoContainerIOCProvider;
import org.brandao.brutos.ioc.picocontainer.PicoContainerScopes;
import org.brandao.brutos.ioc.picocontainer.SingletonScope;
import org.brandao.brutos.programatic.IOCManager;
import org.brandao.brutos.xml.TestHelper.MyBean;
import org.brandao.brutos.xml.TestHelper.MyBeanFactory;
import org.brandao.brutos.xml.TestHelper.MyBeanTypes;
import org.brandao.brutos.xml.TestHelper.MyEnum;
import org.brandao.brutos.xml.TestHelper.MyType;

/**
 *
 * @author Afonso Brandao
 */
public class IOCXMLMappingTest  extends TestCase implements Test{

    public void testBean() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myType\" class=\""+MyType.class.getName()+"\" singleton=\"true\"/>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );
        
        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        Object instance = xml.getIocManager().getInstance( "myType" );
        assertEquals( MyType.class, instance.getClass() );
    }

    public void testConstructorInject() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg>" +
        "              <value>123456</value>" +
        "           </constructor-arg>" +
        "           <constructor-arg>" +
        "              <value>String test</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBean instance = (MyBean) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 123456, instance.getProperty1() );
        assertEquals( "String test", instance.getProperty2() );
    }

    public void testPropertyInject() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property1\">" +
        "              <value>123456</value>" +
        "           </property>" +
        "           <property name=\"property2\">" +
        "              <value>String test</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBean instance = (MyBean) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 123456, instance.getProperty1() );
        assertEquals( "String test", instance.getProperty2() );
    }

    public void testConstructorPropertyInject() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg>" +
        "              <value>123456</value>" +
        "           </constructor-arg>" +
        "           <constructor-arg>" +
        "              <null/>" +
        "           </constructor-arg>" +
        "           <property name=\"property2\">" +
        "              <value>String test</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBean instance = (MyBean) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 123456, instance.getProperty1() );
        assertEquals( "String test", instance.getProperty2() );
    }

    public void testBeanFactory() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBeanFactory\" class=\""+MyBeanFactory.class.getName()+"\" singleton=\"true\"/>" +
        "       <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" factory-method=\"createInstance\" factory-bean=\"myBeanFactory\" singleton=\"true\"/>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());
        
        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBean instance = (MyBean) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 123456, instance.getProperty1() );
        assertEquals( "String test", instance.getProperty2() );
    }

    public void testEnum() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyEnum.class.getName()+"\" factory-method=\"valueOf\" singleton=\"true\">" +
        "           <constructor-arg>" +
        "               <value>VALUE2</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());
        
        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        Object instance = xml.getIocManager().getInstance( "myBean" );
        assertEquals( MyEnum.VALUE2, instance );
    }

    public void testInnerBean() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property21\">" +
        "               <bean class=\""+MyEnum.class.getName()+"\" factory-method=\"valueOf\" singleton=\"true\">" +
        "                   <constructor-arg>" +
        "                       <value>VALUE2</value>" +
        "                   </constructor-arg>" +
        "               </bean>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( MyEnum.VALUE2, instance.getProperty21() );
    }

    public void testDependencyPropertyClass() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property1\">" +
        "               <value>java.lang.Integer</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( java.lang.Integer.class, instance.getProperty1() );
    }

    public void testDependencyPropertyBoolean() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property2\">" +
        "               <value>true</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( true, instance.isProperty2() );
    }

    public void testDependencyPropertyByte() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property3\">" +
        "               <value>127</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( (byte)127, instance.getProperty3() );
    }

    public void testDependencyPropertyChar() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property4\">" +
        "               <value>A</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 'A', instance.getProperty4() );
    }

    public void testDependencyPropertyDouble() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property5\">" +
        "               <value>2.3</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 2.3, instance.getProperty5() );
    }

    public void testDependencyPropertyFloat() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property6\">" +
        "               <value>2.3</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 2.3f, instance.getProperty6() );
    }

    public void testDependencyPropertyInt() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property7\">" +
        "               <value>100</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 100, instance.getProperty7() );
    }

    public void testDependencyPropertyLong() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property8\">" +
        "               <value>123456789</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 123456789L, instance.getProperty8() );
    }

    public void testDependencyPropertyShort() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property9\">" +
        "               <value>123</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( (short)123, instance.getProperty9() );
    }

    public void testDependencyPropertyString() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property10\">" +
        "               <value>String Test</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( "String Test", instance.getProperty10() );
    }

    /*--------------------------------------------------------------------------
     *
     *
     *
     *-------------------------------------------------------------------------*/

    public void testDependencyPropertyWrapperBoolean() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property11\">" +
        "               <value>true</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Boolean.valueOf( true ), instance.getProperty11() );
    }

    public void testDependencyPropertyWrapperByte() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property12\">" +
        "               <value>127</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Byte.valueOf( (byte)127 ), instance.getProperty12() );
    }

    public void testDependencyPropertyWrapperChar() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property13\">" +
        "               <value>A</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Character.valueOf( 'A' ), instance.getProperty13() );
    }

    public void testDependencyPropertyWrapperDouble() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property14\">" +
        "               <value>2.3</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Double.valueOf( 2.3 ), instance.getProperty14() );
    }

    public void testDependencyPropertyWrapperFloat() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property15\">" +
        "               <value>2.3</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Float.valueOf( 2.3f ), instance.getProperty15() );
    }

    public void testDependencyPropertyWrapperInt() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property16\">" +
        "               <value>100</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Integer.valueOf( 100 ), instance.getProperty16() );
    }

    public void testDependencyPropertyWrapperLong() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property17\">" +
        "               <value>123456789</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Long.valueOf( 123456789L ), instance.getProperty17() );
    }

    public void testDependencyPropertyWrapperShort() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property18\">" +
        "               <value>123</value>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Short.valueOf( (short)123 ), instance.getProperty18() );
    }

    public void testDependencyPropertyList() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property19\">" +
        "               <list>" +
        "               <value>100</value>" +
        "               <value>200</value>" +
        "               <value>10</value>" +
        "               <value>-10</value>" +
        "               </list>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertNotNull( instance.getProperty19() );
        assertTrue( Arrays.equals( new Object[]{"100", "200", "10", "-10" } , instance.getProperty19().toArray() ) );
    }

    public void testDependencyPropertySet() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property20\">" +
        "               <set>" +
        "               <value>100</value>" +
        "               <value>200</value>" +
        "               <value>10</value>" +
        "               <value>-10</value>" +
        "               </set>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertNotNull( instance.getProperty20() );
        assertTrue( instance.getProperty20().contains( -10 ) );
        assertTrue( instance.getProperty20().contains( 10 ) );
        assertTrue( instance.getProperty20().contains( 200 ) );
        assertTrue( instance.getProperty20().contains( 100 ) );
    }

    public void testDependencyPropertyEnum() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myEnum\" class=\""+MyEnum.class.getName()+"\" factory-method=\"valueOf\" singleton=\"true\">" +
        "           <constructor-arg>" +
        "               <value>VALUE2</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property21\">" +
        "               <ref bean=\"myEnum\"/>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( MyEnum.VALUE2, instance.getProperty21() );
    }

    public void testDependencyPropertyProperties() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property23\">" +
        "               <props>" +
        "                   <prop name=\"id1\" value=\"value1\"/>" +
        "                   <prop name=\"id2\"><value>value2</value></prop>" +
        "               </props>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Properties props = instance.getProperty23();
        assertEquals( "value1", props.getProperty( "id1" ) );
        assertEquals( "value2", props.getProperty( "id2" ) );
    }

    public void testDependencyPropertyMapPrimitiveObject() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property22\">" +
        "               <map>" +
        "                   <entry>" +
        "                    <key><value>1</value></key>" +
        "                     <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                         <constructor-arg>" +
        "                            <value>1</value>" +
        "                         </constructor-arg>" +
        "                         <constructor-arg>" +
        "                            <value>um</value>" +
        "                         </constructor-arg>" +
        "                     </bean>" +
        "                   </entry>" +
        "                   <entry>" +
        "                    <key><value>2</value></key>" +
        "                     <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                         <constructor-arg>" +
        "                            <value>2</value>" +
        "                         </constructor-arg>" +
        "                         <constructor-arg>" +
        "                            <value>dois</value>" +
        "                         </constructor-arg>" +
        "                     </bean>" +
        "                   </entry>" +
        "                   <entry>" +
        "                    <key><value>3</value></key>" +
        "                     <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                         <constructor-arg>" +
        "                            <value>3</value>" +
        "                         </constructor-arg>" +
        "                         <constructor-arg>" +
        "                            <value>tres</value>" +
        "                         </constructor-arg>" +
        "                     </bean>" +
        "                   </entry>" +
        "               </map>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<String,MyBean> map = instance.getProperty22();
        assertNotNull( map );
        MyBean key1 = map.get( "1" );
        assertNotNull( key1 );
        assertEquals( 1, key1.getProperty1() );
        assertEquals( "um", key1.getProperty2() );

        MyBean key2 = map.get( "2" );
        assertNotNull( key2 );
        assertEquals( 2, key2.getProperty1() );
        assertEquals( "dois", key2.getProperty2() );

        MyBean key3 = map.get( "3" );
        assertNotNull( key3 );
        assertEquals( 3, key3.getProperty1() );
        assertEquals( "tres", key3.getProperty2() );
    }

    public void testDependencyPropertyMapObjectObject() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property24\">" +
        "               <map>" +
        "                   <entry>" +
        "                    <key>" +
        "                     <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                         <constructor-arg>" +
        "                            <value>1</value>" +
        "                         </constructor-arg>" +
        "                         <constructor-arg>" +
        "                            <null/>" +
        "                         </constructor-arg>" +
        "                     </bean>" +
        "                    </key>" +
        "                     <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                         <constructor-arg>" +
        "                            <value>1</value>" +
        "                         </constructor-arg>" +
        "                         <constructor-arg>" +
        "                            <value>um</value>" +
        "                         </constructor-arg>" +
        "                     </bean>" +
        "                   </entry>" +
        "               </map>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,MyBean> map = instance.getProperty24();
        assertNotNull( map );
        MyBean key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertEquals( 1, key1.getProperty1() );
        assertEquals( "um", key1.getProperty2() );
    }

    public void testDependencyPropertyMapObjectList() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property25\">" +
        "               <map>" +
        "                   <entry>" +
        "                        <key>" +
        "                            <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <null/>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </key>" +
        "                        <list>" +
        "                             <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <value>um</value>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                             <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>2</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <value>dois</value>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </list>" +
        "                   </entry>" +
        "               </map>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,List<MyBean>> map = instance.getProperty25();
        assertNotNull( map );
        List<MyBean> key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertTrue( key1.size() == 2 );
        assertEquals( 1, key1.get(0).getProperty1() );
        assertEquals( "um", key1.get(0).getProperty2() );
        assertEquals( 2, key1.get(1).getProperty1() );
        assertEquals( "dois", key1.get(1).getProperty2() );
    }

    public void testDependencyPropertyMapObjectMap() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property26\">" +
        "               <map>" +
        "                   <entry>" +
        "                        <key>" +
        "                            <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <null/>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </key>" +
        "                        <map>" +
        "                            <entry>" +
        "                                <key>" +
        "                                    <value>1</value>" +
        "                                </key>" +
        "                                <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                    <constructor-arg>" +
        "                                       <value>2</value>" +
        "                                    </constructor-arg>" +
        "                                    <constructor-arg>" +
        "                                       <value>dois</value>" +
        "                                    </constructor-arg>" +
        "                                </bean>" +
        "                            </entry>" +
        "                        </map>" +
        "                   </entry>" +
        "               </map>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,Map<Integer,MyBean>> map = instance.getProperty26();
        assertNotNull( map );

        Map<Integer,MyBean> key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertTrue( key1.size() == 1 );

        assertEquals( 2, key1.get(1).getProperty1() );
        assertEquals( "dois", key1.get(1).getProperty2() );
    }

    public void testDependencyPropertyMapObjectSet() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property27\">" +
        "               <map>" +
        "                   <entry>" +
        "                        <key>" +
        "                            <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <null/>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </key>" +
        "                        <set>" +
        "                             <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <value>um</value>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                             <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>2</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <value>dois</value>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </set>" +
        "                   </entry>" +
        "               </map>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,Set<MyBean>> map = instance.getProperty27();
        assertNotNull( map );
        Set<MyBean> key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertTrue( key1.size() == 2 );
        MyBean[] objs = key1.toArray( new MyBean[]{} );
        assertEquals( 1, objs[0].getProperty1() );
        assertEquals( "um", objs[0].getProperty2() );
        assertEquals( 2, objs[1].getProperty1() );
        assertEquals( "dois", objs[1].getProperty2() );
    }

    public void testDependencyPropertyMapObjectProperties() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <property name=\"property28\">" +
        "               <map>" +
        "                   <entry>" +
        "                        <key>" +
        "                            <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <null/>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </key>" +
        "                        <props>" +
        "                            <prop name=\"id1\" value=\"value1\"/>" +
        "                            <prop name=\"id2\"><value>value2</value></prop>" +
        "                        </props>" +
        "                   </entry>" +
        "               </map>" +
        "           </property>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,Properties> map = instance.getProperty28();
        assertNotNull( map );
        Properties key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertTrue( key1.size() == 2 );
        assertEquals( "value1", key1.getProperty( "id1" ) );
        assertEquals( "value2", key1.getProperty( "id2" ) );
    }

    // Constructor

    public void testDependenceContructorInnerBean() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\""+MyEnum.class.getName()+"\">" +
        "               <bean class=\""+MyEnum.class.getName()+"\" factory-method=\"valueOf\" singleton=\"true\">" +
        "                   <constructor-arg>" +
        "                       <value>VALUE2</value>" +
        "                   </constructor-arg>" +
        "               </bean>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( MyEnum.VALUE2, instance.getProperty21() );
    }

    public void testDependencyConstructorArgClass() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.Class\">" +
        "               <value>java.lang.Integer</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( java.lang.Integer.class, instance.getProperty1() );
    }

    public void testDependencyConstructorArgBoolean() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"boolean\">" +
        "               <value>true</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( true, instance.isProperty2() );
    }

    public void testDependencyConstructorArgByte() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"byte\">" +
        "               <value>127</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( (byte)127, instance.getProperty3() );
    }

    public void testDependencyConstructorArgChar() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"char\">" +
        "               <value>A</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 'A', instance.getProperty4() );
    }

    public void testDependencyConstructorArgDouble() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"double\">" +
        "               <value>2.3</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 2.3, instance.getProperty5() );
    }

    public void testDependencyConstructorArgFloat() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"float\">" +
        "               <value>2.3</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 2.3f, instance.getProperty6() );
    }

    public void testDependencyConstructorArgInt() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"int\">" +
        "               <value>100</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 100, instance.getProperty7() );
    }

    public void testDependencyConstructorArgLong() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"long\">" +
        "               <value>123456789</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( 123456789L, instance.getProperty8() );
    }

    public void testDependencyConstructorArgShort() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"short\">" +
        "               <value>123</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( (short)123, instance.getProperty9() );
    }

    public void testDependencyConstructorArgString() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.String\">" +
        "               <value>String Test</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( "String Test", instance.getProperty10() );
    }

    /*--------------------------------------------------------------------------
     *
     *
     *
     *-------------------------------------------------------------------------*/

    public void testDependencyConstructorArgWrapperBoolean() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.Boolean\">" +
        "               <value>true</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Boolean.valueOf( true ), instance.getProperty11() );
    }

    public void testDependencyConstructorArgWrapperByte() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.Byte\">" +
        "               <value>127</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Byte.valueOf( (byte)127 ), instance.getProperty12() );
    }

    public void testDependencyConstructorArgWrapperChar() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.Character\">" +
        "               <value>A</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Character.valueOf( 'A' ), instance.getProperty13() );
    }

    public void testDependencyConstructorArgWrapperDouble() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.Double\">" +
        "               <value>2.3</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Double.valueOf( 2.3 ), instance.getProperty14() );
    }

    public void testDependencyConstructorArgWrapperFloat() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.Float\">" +
        "               <value>2.3</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Float.valueOf( 2.3f ), instance.getProperty15() );
    }

    public void testDependencyConstructorArgWrapperInt() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.Integer\">" +
        "               <value>100</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Integer.valueOf( 100 ), instance.getProperty16() );
    }

    public void testDependencyConstructorArgWrapperLong() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.Long\">" +
        "               <value>123456789</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Long.valueOf( 123456789L ), instance.getProperty17() );
    }

    public void testDependencyConstrucotrArgWrapperShort() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.lang.Short\">" +
        "               <value>123</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( Short.valueOf( (short)123 ), instance.getProperty18() );
    }

    public void testDependencyConstructorArgList() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.util.List\">" +
        "               <list>" +
        "               <value>100</value>" +
        "               <value>200</value>" +
        "               <value>10</value>" +
        "               <value>-10</value>" +
        "               </list>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertNotNull( instance.getProperty19() );
        assertTrue( Arrays.equals( new Object[]{"100", "200", "10", "-10" } , instance.getProperty19().toArray() ) );
    }

    public void testDependencyConstrucotrArgSet() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.util.Set\">" +
        "               <set>" +
        "               <value>100</value>" +
        "               <value>200</value>" +
        "               <value>10</value>" +
        "               <value>-10</value>" +
        "               </set>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertNotNull( instance.getProperty20() );
        assertTrue( instance.getProperty20().contains( -10 ) );
        assertTrue( instance.getProperty20().contains( 10 ) );
        assertTrue( instance.getProperty20().contains( 200 ) );
        assertTrue( instance.getProperty20().contains( 100 ) );
    }

    public void testDependencyConstrucotrArgEnum() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myEnum\" class=\""+MyEnum.class.getName()+"\" factory-method=\"valueOf\" singleton=\"true\">" +
        "           <constructor-arg>" +
        "               <value>VALUE2</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\""+MyEnum.class.getName()+"\">" +
        "               <ref bean=\"myEnum\"/>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        assertEquals( MyEnum.VALUE2, instance.getProperty21() );
    }

    public void testDependencyConstrucotrArgProperties() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.util.Properties\">" +
        "               <props>" +
        "                   <prop name=\"id1\" value=\"value1\"/>" +
        "                   <prop name=\"id2\"><value>value2</value></prop>" +
        "               </props>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Properties props = instance.getProperty23();
        assertEquals( "value1", props.getProperty( "id1" ) );
        assertEquals( "value2", props.getProperty( "id2" ) );
    }

    public void testDependencyConstrucotrArgMapPrimitiveObject() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "\n" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'\n" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >\n" +
        "\n" +
        "   <beans>\n" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">\n" +
        "           <constructor-arg type=\"java.util.Map\">\n" +
        "               <map>\n" +
        "                   <entry>\n" +
        "                    <key><value>1</value></key>\n" +
        "                     <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">\n" +
        "                         <constructor-arg>\n" +
        "                            <value>1</value>\n" +
        "                         </constructor-arg>\n" +
        "                         <constructor-arg>\n" +
        "                            <value>um</value>\n" +
        "                         </constructor-arg>\n" +
        "                     </bean>\n" +
        "                   </entry>\n" +
        "                   <entry>\n" +
        "                    <key><value>2</value></key>\n" +
        "                     <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">\n" +
        "                         <constructor-arg>\n" +
        "                            <value>2</value>\n" +
        "                         </constructor-arg>\n" +
        "                         <constructor-arg>\n" +
        "                            <value>dois</value>\n" +
        "                         </constructor-arg>\n" +
        "                     </bean>\n" +
        "                   </entry>\n" +
        "                   <entry>\n" +
        "                    <key><value>3</value></key>\n" +
        "                     <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">\n" +
        "                         <constructor-arg>\n" +
        "                            <value>3</value>\n" +
        "                         </constructor-arg>\n" +
        "                         <constructor-arg>\n" +
        "                            <value>tres</value>\n" +
        "                         </constructor-arg>\n" +
        "                     </bean>\n" +
        "                   </entry>\n" +
        "               </map>\n" +
        "           </constructor-arg>\n" +
        "           <constructor-arg type=\"int\">\n" +
        "              <value>0</value>\n" +
        "           </constructor-arg>\n" +
        "       </bean>\n" +
        "   </beans>\n" +
        "</brutos-configuration>\n";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<String,MyBean> map = instance.getProperty22();
        assertNotNull( map );
        MyBean key1 = map.get( "1" );
        assertNotNull( key1 );
        assertEquals( 1, key1.getProperty1() );
        assertEquals( "um", key1.getProperty2() );

        MyBean key2 = map.get( "2" );
        assertNotNull( key2 );
        assertEquals( 2, key2.getProperty1() );
        assertEquals( "dois", key2.getProperty2() );

        MyBean key3 = map.get( "3" );
        assertNotNull( key3 );
        assertEquals( 3, key3.getProperty1() );
        assertEquals( "tres", key3.getProperty2() );
    }

    public void testDependencyConstrucotrArgMapObjectObject() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.util.Map\">" +
        "               <map>" +
        "                   <entry>" +
        "                    <key>" +
        "                     <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                         <constructor-arg>" +
        "                            <value>1</value>" +
        "                         </constructor-arg>" +
        "                         <constructor-arg>" +
        "                            <null/>" +
        "                         </constructor-arg>" +
        "                     </bean>" +
        "                    </key>" +
        "                     <bean name=\"myBean\" class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                         <constructor-arg>" +
        "                            <value>1</value>" +
        "                         </constructor-arg>" +
        "                         <constructor-arg>" +
        "                            <value>um</value>" +
        "                         </constructor-arg>" +
        "                     </bean>" +
        "                   </entry>" +
        "               </map>" +
        "           </constructor-arg>" +
        "           <constructor-arg type=\"double\">" +
        "              <value>0.0</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,MyBean> map = instance.getProperty24();
        assertNotNull( map );
        MyBean key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertEquals( 1, key1.getProperty1() );
        assertEquals( "um", key1.getProperty2() );
    }

    public void testDependencyConstrucotrArgMapObjectList() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.util.Map\">" +
        "               <map>" +
        "                   <entry>" +
        "                        <key>" +
        "                            <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <null/>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </key>" +
        "                        <list>" +
        "                             <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <value>um</value>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                             <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>2</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <value>dois</value>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </list>" +
        "                   </entry>" +
        "               </map>" +
        "           </constructor-arg>" +
        "           <constructor-arg type=\"java.lang.String\">" +
        "              <value>0</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,List<MyBean>> map = instance.getProperty25();
        assertNotNull( map );
        List<MyBean> key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertTrue( key1.size() == 2 );
        assertEquals( 1, key1.get(0).getProperty1() );
        assertEquals( "um", key1.get(0).getProperty2() );
        assertEquals( 2, key1.get(1).getProperty1() );
        assertEquals( "dois", key1.get(1).getProperty2() );
    }

    public void testDependencyConstrucotrArgMapObjectMap() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.util.Map\">" +
        "               <map>" +
        "                   <entry>" +
        "                        <key>" +
        "                            <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <null/>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </key>" +
        "                        <map>" +
        "                            <entry>" +
        "                                <key>" +
        "                                    <value>1</value>" +
        "                                </key>" +
        "                                <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                    <constructor-arg>" +
        "                                       <value>2</value>" +
        "                                    </constructor-arg>" +
        "                                    <constructor-arg>" +
        "                                       <value>dois</value>" +
        "                                    </constructor-arg>" +
        "                                </bean>" +
        "                            </entry>" +
        "                        </map>" +
        "                   </entry>" +
        "               </map>" +
        "           </constructor-arg>" +
        "           <constructor-arg type=\"long\">" +
        "              <value>0</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,Map<Integer,MyBean>> map = instance.getProperty26();
        assertNotNull( map );

        Map<Integer,MyBean> key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertTrue( key1.size() == 1 );

        assertEquals( 2, key1.get(1).getProperty1() );
        assertEquals( "dois", key1.get(1).getProperty2() );
    }

    public void testDependencyConstrucotrArgMapObjectSet() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.util.Map\">" +
        "               <map>" +
        "                   <entry>" +
        "                        <key>" +
        "                            <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <null/>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </key>" +
        "                        <set>" +
        "                             <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <value>um</value>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                             <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>2</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <value>dois</value>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </set>" +
        "                   </entry>" +
        "               </map>" +
        "           </constructor-arg>" +
        "           <constructor-arg type=\"float\">" +
        "              <value>0</value>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,Set<MyBean>> map = instance.getProperty27();
        assertNotNull( map );
        Set<MyBean> key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertTrue( key1.size() == 2 );
        MyBean[] objs = key1.toArray( new MyBean[]{} );
        assertEquals( 1, objs[0].getProperty1() );
        assertEquals( "um", objs[0].getProperty2() );
        assertEquals( 2, objs[1].getProperty1() );
        assertEquals( "dois", objs[1].getProperty2() );
    }

    public void testDependencyConstrucotrArgMapObjectProperties() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <beans>" +
        "       <bean name=\"myBean\" class=\""+MyBeanTypes.class.getName()+"\" singleton=\"true\">" +
        "           <constructor-arg type=\"java.util.Map\">" +
        "               <map>" +
        "                   <entry>" +
        "                        <key>" +
        "                            <bean class=\""+MyBean.class.getName()+"\" singleton=\"true\">" +
        "                                 <constructor-arg>" +
        "                                    <value>1</value>" +
        "                                 </constructor-arg>" +
        "                                 <constructor-arg>" +
        "                                    <null/>" +
        "                                 </constructor-arg>" +
        "                             </bean>" +
        "                        </key>" +
        "                        <props>" +
        "                            <prop name=\"id1\" value=\"value1\"/>" +
        "                            <prop name=\"id2\"><value>value2</value></prop>" +
        "                        </props>" +
        "                   </entry>" +
        "               </map>" +
        "           </constructor-arg>" +
        "       </bean>" +
        "   </beans>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );

        IOCXMLMapping xml =
                new IOCXMLMapping( new IOCManager( new PicoContainerIOCProvider() ) );

        PicoContainerScopes.register("singleton", new SingletonScope());

        xml.setBeans( (Map<String,Map<String,Object>>)data.get( "beans" ) );
        MyBeanTypes instance = (MyBeanTypes) xml.getIocManager().getInstance( "myBean" );
        Map<MyBean,Properties> map = instance.getProperty28();
        assertNotNull( map );
        Properties key1 = map.get( new MyBean(1,"") );
        assertNotNull( key1 );
        assertTrue( key1.size() == 2 );
        assertEquals( "value1", key1.getProperty( "id1" ) );
        assertEquals( "value2", key1.getProperty( "id2" ) );
    }

}
