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

package org.brandao.brutos.scanner;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.brandao.brutos.ByteArrayXMLApplicationContext;
import org.brandao.brutos.helper.TestController;
import org.brandao.brutos.helper.TestIocProvider;
import org.brandao.brutos.helper.controller.Test2Controller;
import org.brandao.brutos.io.ByteArrayResource;
import org.brandao.brutos.xml.ContextDefinitionReader;

/**
 *
 * @author Brandao
 */
public class ScannerTest extends TestCase{
    
    public void test1(){
        String content ="";
        content +="<ns1:component-scan base-package=\"org.brandao.brutos.helper\">";
        content +="        <ns1:exclude-filter type=\"regex\" expression=\".*InterceptorController$\"/>";
        content +="        <ns1:exclude-filter type=\"annotation\" expression=\"org.brandao.brutos.annotation.Intercepts\"/>";
        content +="</ns1:component-scan>";
        ByteArrayXMLApplicationContext applicationContext = 
                new ByteArrayXMLApplicationContext(getXML(content).getBytes());
        
        applicationContext
            .getConfiguration().setProperty("org.brandao.brutos.ioc.provider", TestIocProvider.class.getName());
        ContextDefinitionReader cdr = 
            new ContextDefinitionReader( applicationContext, null, applicationContext );
        cdr.loadDefinitions(new ByteArrayResource(getXML(content).getBytes()));
        applicationContext.configure();
        applicationContext.setParent(cdr.getParent());
        
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test2(){
        String content ="<ns1:component-scan base-package=\"org.brandao.brutos.helper.controller\"/>";
        ByteArrayXMLApplicationContext applicationContext = 
                new ByteArrayXMLApplicationContext(getXML(content).getBytes());
        
        applicationContext
            .getConfiguration().setProperty("org.brandao.brutos.ioc.provider", TestIocProvider.class.getName());
        ContextDefinitionReader cdr = 
            new ContextDefinitionReader( applicationContext, null, applicationContext );
        cdr.loadDefinitions(new ByteArrayResource(getXML(content).getBytes()));
        applicationContext.configure();
        applicationContext.setParent(cdr.getParent());
        
        Assert.assertNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test3(){
        String content ="<ns1:component-scan base-package=\"org.brandao.brutos.helper.controller\"/>";
        ByteArrayXMLApplicationContext applicationContext = 
                new ByteArrayXMLApplicationContext(getXML(content).getBytes());
        
        applicationContext
            .getConfiguration().setProperty("org.brandao.brutos.ioc.provider", TestIocProvider.class.getName());
        ContextDefinitionReader cdr = 
            new ContextDefinitionReader( applicationContext, null, applicationContext );
        cdr.loadDefinitions(new ByteArrayResource(getXML(content).getBytes()));
        applicationContext.configure();
        applicationContext.setParent(cdr.getParent());
        
        Assert.assertNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test4(){
        String content = "";
        content +="<ns1:component-scan use-default-filters=\"false\">";
        content +="        <ns1:include-filter type=\"regex\" expression=\""+TestController.class.getName().replace(".","\\.")+"\"/>";
        content +="        <ns1:include-filter type=\"regex\" expression=\""+Test2Controller.class.getName().replace(".","\\.")+"\"/>";
        /*
        content +="        <ns1:exclude-filter type=\"regex\" expression=\".*InterceptorController$\"/>";
        content +="        <ns1:exclude-filter type=\"annotation\" expression=\"org.brandao.brutos.annotation.Intercepts\"/>";
        content +="        <ns1:exclude-filter type=\"regex\" expression=\".*Type$\"/>";
        */
        content +="</ns1:component-scan>";
        ByteArrayXMLApplicationContext applicationContext = 
                new ByteArrayXMLApplicationContext(getXML(content).getBytes());
        
        applicationContext
            .getConfiguration().setProperty("org.brandao.brutos.ioc.provider", TestIocProvider.class.getName());
        ContextDefinitionReader cdr = 
            new ContextDefinitionReader( applicationContext, null, applicationContext );
        cdr.loadDefinitions(new ByteArrayResource(getXML(content).getBytes()));
        applicationContext.configure();
        applicationContext.setParent(cdr.getParent());
        
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test5(){
        String content ="<ns1:component-scan base-package=\"org.brandao.brutos.helper\" use-default-filters=\"false\"/>";
        ByteArrayXMLApplicationContext applicationContext = 
                new ByteArrayXMLApplicationContext(getXML(content).getBytes());
        
        applicationContext
            .getConfiguration().setProperty("org.brandao.brutos.ioc.provider", TestIocProvider.class.getName());
        ContextDefinitionReader cdr = 
            new ContextDefinitionReader( applicationContext, null, applicationContext );
        cdr.loadDefinitions(new ByteArrayResource(getXML(content).getBytes()));
        applicationContext.configure();
        applicationContext.setParent(cdr.getParent());
        
        Assert.assertNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test6(){
        String content = "";
        content +="<ns1:component-scan base-package=\"org.brandao.brutos.helper\" use-default-filters=\"false\">";
        content +="        <ns1:include-filter type=\"regex\" expression=\".*2Controller$\"/>";
        content +="</ns1:component-scan>";
        ByteArrayXMLApplicationContext applicationContext = 
                new ByteArrayXMLApplicationContext(getXML(content).getBytes());
        
        applicationContext
            .getConfiguration().setProperty("org.brandao.brutos.ioc.provider", TestIocProvider.class.getName());
        ContextDefinitionReader cdr = 
            new ContextDefinitionReader( applicationContext, null, applicationContext );
        cdr.loadDefinitions(new ByteArrayResource(getXML(content).getBytes()));
        applicationContext.configure();
        applicationContext.setParent(cdr.getParent());
        
        Assert.assertNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test7(){
        String content = "";
        content +="<ns1:component-scan base-package=\"org.brandao.brutos.helper\">";
        content +="        <ns1:exclude-filter type=\"regex\" expression=\".*2Controller$\"/>";
        content +="</ns1:component-scan>";
        ByteArrayXMLApplicationContext applicationContext = 
                new ByteArrayXMLApplicationContext(getXML(content).getBytes());
        
        applicationContext
            .getConfiguration().setProperty("org.brandao.brutos.ioc.provider", TestIocProvider.class.getName());
        ContextDefinitionReader cdr = 
            new ContextDefinitionReader( applicationContext, null, applicationContext );
        cdr.loadDefinitions(new ByteArrayResource(getXML(content).getBytes()));
        applicationContext.configure();
        applicationContext.setParent(cdr.getParent());
        
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test8(){
        String content = "";
        content +="<ns1:component-scan base-package=\"org.brandao.brutos.helper\" use-default-filters=\"false\">";
        content +="        <ns1:exclude-filter type=\"regex\" expression=\".*Test2.*\"/>";
        content +="        <ns1:include-filter type=\"regex\" expression=\".*Controller$\"/>";
        content +="</ns1:component-scan>";
        ByteArrayXMLApplicationContext applicationContext = 
                new ByteArrayXMLApplicationContext(getXML(content).getBytes());
        
        applicationContext
            .getConfiguration().setProperty("org.brandao.brutos.ioc.provider", TestIocProvider.class.getName());
        ContextDefinitionReader cdr = 
            new ContextDefinitionReader( applicationContext, null, applicationContext );
        cdr.loadDefinitions(new ByteArrayResource(getXML(content).getBytes()));
        applicationContext.configure();
        applicationContext.setParent(cdr.getParent());
        
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }
    
    private String getXML(String content){
        String xml = "";
        xml +="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml +="<ns2:controllers";
        xml +="    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'";
        xml +="    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'";
        xml +="    xmlns:ns1='http://www.brutosframework.com.br/schema/context'";
        xml +="    xsi:schemaLocation='";
        xml +="    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd";
        xml +="    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>";
        xml +="       <ns1:annotation-config/>";
        xml +=content;
        xml +="</ns2:controllers>";
        return xml;
    }
}
