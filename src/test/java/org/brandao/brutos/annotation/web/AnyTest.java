package org.brandao.brutos.annotation.web;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.annotation.web.helper.BrutosTestCase;
import org.brandao.brutos.annotation.web.helper.WebApplicationTester;
import org.brandao.brutos.annotation.web.helper.AnyTesttHelper.BeanAnyConstructor;
import org.brandao.brutos.annotation.web.helper.AnyTesttHelper.BeanAnyConstructorControllerTest;
import org.brandao.brutos.annotation.web.helper.AnyTesttHelper.BeanAnyField;
import org.brandao.brutos.annotation.web.helper.AnyTesttHelper.BeanAnyFieldControllerTest;
import org.brandao.brutos.annotation.web.helper.AnyTesttHelper.BeanAnyProperty;
import org.brandao.brutos.annotation.web.helper.AnyTesttHelper.BeanAnyPropertyControllerTest;
import org.brandao.brutos.annotation.web.helper.AnyTesttHelper.PropertyTypeA;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.test.WebApplicationContextTester;

import com.mockrunner.mock.web.MockHttpServletRequest;

public class AnyTest extends BrutosTestCase{

	public void testAnyField(){
		WebApplicationContextTester.run(
			"/action", 
			new WebApplicationTester(){
				
            	public void prepareRequest(MockHttpServletRequest request) {
            		
            		request.setupAddParameter("prop.property.type", "A");
            		request.setupAddParameter("prop.property.commonField", "Value1");
            		request.setupAddParameter("prop.property.fieldA", "Value");
            		
            	}
            	
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					BeanAnyField bean =
							(BeanAnyField) request.getAttribute("prop");
					
					assertNotNull(bean);
					assertNotNull(bean.property);
					assertEquals("Value1", bean.property.commonField);
					
					assertTrue(bean.property instanceof PropertyTypeA);
					assertEquals("Value", ((PropertyTypeA)bean.property).fieldA);
					
				}
				
			}, 
			new Class[]{BeanAnyFieldControllerTest.class}
		);
	}

	public void testAnyProperty(){
		WebApplicationContextTester.run(
			"/action", 
			new WebApplicationTester(){
				
            	public void prepareRequest(MockHttpServletRequest request) {
            		
            		request.setupAddParameter("prop.property.type", "A");
            		request.setupAddParameter("prop.property.commonField", "Value1");
            		request.setupAddParameter("prop.property.fieldA", "Value");
            		
            	}
            	
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					BeanAnyProperty bean =
							(BeanAnyProperty) request.getAttribute("prop");
					
					assertNotNull(bean);
					assertNotNull(bean.getProperty());
					assertEquals("Value1", bean.getProperty().commonField);
					
					assertTrue(bean.getProperty() instanceof PropertyTypeA);
					assertEquals("Value", ((PropertyTypeA)bean.getProperty()).fieldA);
					
				}
				
			}, 
			new Class[]{BeanAnyPropertyControllerTest.class}
		);
	}

	public void testAnyConstructor(){
		WebApplicationContextTester.run(
			"/action", 
			new WebApplicationTester(){
				
            	public void prepareRequest(MockHttpServletRequest request) {
            		
            		request.setupAddParameter("prop.property.type", "A");
            		request.setupAddParameter("prop.property.commonField", "Value1");
            		request.setupAddParameter("prop.property.fieldA", "Value");
            		
            	}
            	
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					BeanAnyConstructor bean =
							(BeanAnyConstructor) request.getAttribute("prop");
					
					assertNotNull(bean);
					assertNotNull(bean.property);
					assertEquals("Value1", bean.property.commonField);
					
					assertTrue(bean.property instanceof PropertyTypeA);
					assertEquals("Value", ((PropertyTypeA)bean.property).fieldA);
					
				}
				
			}, 
			new Class[]{BeanAnyConstructorControllerTest.class}
		);
	}
	
}
