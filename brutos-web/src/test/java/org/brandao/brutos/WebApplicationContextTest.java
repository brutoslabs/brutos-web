


package org.brandao.brutos;

import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.helper.controller.AbstractTester;
import org.brandao.brutos.helper.controller.SimpleBean;
import org.brandao.brutos.helper.controller.TypeTest;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.web.XMLWebApplicationContext;


public class WebApplicationContextTest extends AbstractTester implements Test{

    public Class getApplicationContext(String resourceName) {
        return XMLWebApplicationContext.class;
    }

    public void testContext1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/context/context-test1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Properties prop = app.getConfiguration();
                    TestCase.assertEquals("value", prop.getProperty("teste"));
                    TestCase.assertEquals("value2", prop.getProperty("teste2"));
                }
            
        });
    }

    public void testContext2(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/context/context-test2.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Type type = app.getTypeManager().getType(SimpleBean.class);
                    TestCase.assertTrue(type instanceof TypeTest);
                }

        });
    }

    public void testContext3(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/context/context-test3.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Type type = app.getTypeManager().getType(SimpleBean.class);
                    TestCase.assertTrue(type instanceof TypeTest);
                }

        });
    }

    public void testContext4(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/context/context-test4.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                }

        });
    }

}
