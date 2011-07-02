/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.web;

import java.net.MalformedURLException;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author Brandao
 */
public class URIMappingTest extends TestCase{

    public void test1() throws MalformedURLException{
        URIMapping urim = new URIMapping("/test/{id}");

        TestCase.assertTrue(urim.matches("/test/myid"));

        Map params = urim.getParameters("/test/myid");
        TestCase.assertEquals("myid", params.get("id"));
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
        Map params = urim.getParameters("10-teste.htm");
        TestCase.assertEquals("10", params.get("id"));
        TestCase.assertEquals("teste", params.get("name"));
    }

    public void test5() throws MalformedURLException{
        URIMapping urim = new URIMapping("/{id:\\d+}-{name}.htm");

        TestCase.assertFalse(urim.matches("/AA-teste.htm"));
    }

}
