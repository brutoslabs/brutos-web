

package org.brandao.brutos.web;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;


public class URIMappingTest extends TestCase{

    public void test1() throws MalformedURLException{
        URIMapping urim = new URIMapping("/test/{id}");

        TestCase.assertTrue(urim.matches("/test/myid"));

        Map<String,List<String>> params = urim.getParameters("/test/myid");
        TestCase.assertEquals("myid", params.get("id").get(0));
    }

    public void test2() throws MalformedURLException{
        URIMapping urim = new URIMapping("/test/{id:\\d+}");
        TestCase.assertFalse(urim.matches("/test/myid"));
    }

    public void test3() throws MalformedURLException{
        URIMapping urim = new URIMapping("/test/{id:\\d+}");
        TestCase.assertTrue(urim.matches("/test/1002"));
    }

    public void test4() throws MalformedURLException{
        URIMapping urim = new URIMapping("/{id:\\d+}-{name}.htm");

        TestCase.assertTrue(urim.matches("/10-teste.htm"));
        Map<String,List<String>> params = urim.getParameters("10-teste.htm");
        TestCase.assertEquals("10", params.get("id").get(0));
        TestCase.assertEquals("teste", params.get("name").get(0));
    }

    public void test5() throws MalformedURLException{
        URIMapping urim = new URIMapping("/{id:\\d+}-{name}.htm");

        TestCase.assertFalse(urim.matches("/AA-teste.htm"));
    }

}
