

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

    public void test6() throws MalformedURLException{
        URIMapping urim = new URIMapping("/{id:\\d{1,}}-{name}.htm");

        TestCase.assertTrue(urim.matches("/0-teste.htm"));
        Map<String,List<String>> params = urim.getParameters("/0-teste.htm");
        TestCase.assertEquals("0", params.get("id").get(0));
        TestCase.assertEquals("teste", params.get("name").get(0));
    }

    public void test7() throws MalformedURLException{
        URIMapping urim = new URIMapping("/{id:\\d{1,}}");

        TestCase.assertTrue(urim.matches("/00"));
        Map<String,List<String>> params = urim.getParameters("/00");
        TestCase.assertEquals("00", params.get("id").get(0));
    }

    public void test8() throws MalformedURLException{
        URIMapping urim = new URIMapping("/domain/{domain:([一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+(\\-[一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+)*)(\\.([一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+(\\-[一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+)*)){1,}}");

        TestCase.assertTrue(urim.matches("/domain/aaa.ods.net.br"));
        Map<String,List<String>> params = urim.getParameters("/domain/aaa.ods.net.br");
        TestCase.assertEquals("aaa.ods.net.br", params.get("domain").get(0));
    }

    public void test9() throws MalformedURLException{
    	try{
    		new URIMapping("/domain/{domain:([一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+(\\-[一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+)*)(\\.([一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+(\\-[一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+)*)){1,}");
    		TestCase.fail();
    	}
    	catch(Throwable e){
    		
    	}
    }
    
    public void test10() throws MalformedURLException{
        URIMapping urim = new URIMapping("/domain/{domain}");
        TestCase.assertEquals("/domain/aaaaa.ods.net.br", urim.getURI(new Object[]{"aaaaa.ods.net.br"}));
    }
    
}
