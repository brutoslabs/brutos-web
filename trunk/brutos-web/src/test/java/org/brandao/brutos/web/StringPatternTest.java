package org.brandao.brutos.web;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

public class StringPatternTest extends TestCase{

    public void test1() throws MalformedURLException{
        StringPattern urim = new StringPattern("/test/{id}");

        TestCase.assertTrue(urim.matches("/test/myid"));

        Map<String,List<String>> params = urim.getParameters("/test/myid");
        TestCase.assertEquals("myid", params.get("id").get(0));
    }

    public void test2() throws MalformedURLException{
        StringPattern urim = new StringPattern("/test/{id:\\d+}");
        TestCase.assertFalse(urim.matches("/test/myid"));
    }

    public void test3() throws MalformedURLException{
        StringPattern urim = new StringPattern("/test/{id:\\d+}");
        TestCase.assertTrue(urim.matches("/test/1002"));
    }

    public void test4() throws MalformedURLException{
        StringPattern urim = new StringPattern("/{id:\\d+}-{name}.htm");

        TestCase.assertTrue(urim.matches("/10-teste.htm"));
        Map<String,List<String>> params = urim.getParameters("/10-teste.htm");
        TestCase.assertEquals("10", params.get("id").get(0));
        TestCase.assertEquals("teste", params.get("name").get(0));
    }

    public void test5() throws MalformedURLException{
        StringPattern urim = new StringPattern("/{id:\\d+}-{name}.htm");

        TestCase.assertFalse(urim.matches("/AA-teste.htm"));
    }

    public void test6() throws MalformedURLException{
        StringPattern urim = new StringPattern("/{id:\\d{1,}}-{name}.htm");

        TestCase.assertTrue(urim.matches("/0-teste.htm"));
        Map<String,List<String>> params = urim.getParameters("/0-teste.htm");
        TestCase.assertEquals("0", params.get("id").get(0));
        TestCase.assertEquals("teste", params.get("name").get(0));
    }

    public void test7() throws MalformedURLException{
        StringPattern urim = new StringPattern("/{id:\\d{1,}}");

        TestCase.assertTrue(urim.matches("/00"));
        Map<String,List<String>> params = urim.getParameters("/00");
        TestCase.assertEquals("00", params.get("id").get(0));
    }

    public void test8() throws MalformedURLException{
        StringPattern urim = new StringPattern("/domain/{domain:([一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+(\\-[一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+)*)(\\.([一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+(\\-[一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+)*)){1,}}");

        TestCase.assertTrue(urim.matches("/domain/aaa.ods.net.br"));
        Map<String,List<String>> params = urim.getParameters("/domain/aaa.ods.net.br");
        TestCase.assertEquals("aaa.ods.net.br", params.get("domain").get(0));
    }

    public void test9() throws MalformedURLException{
    	try{
    		new StringPattern("/domain/{domain:([一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+(\\-[一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+)*)(\\.([一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+(\\-[一-龠ぁ-ゔァ-ヴーa-zA-Z0-9ａ-ｚＡ-Ｚ０-９々〆〤]+)*)){1,}");
    		TestCase.fail();
    	}
    	catch(Throwable e){
    		
    	}
    }
        
    public void test10() throws MalformedURLException{
        StringPattern urim = new StringPattern("/domain/{domain}");
        TestCase.assertEquals("/domain/aaaaa.ods.net.br", urim.toString(new Object[]{"aaaaa.ods.net.br"}));
    }

    public void test11() throws MalformedURLException{
        StringPattern urim = new StringPattern("/edit-record/{domainId:[a-z0-9\\-]+}/{recordId:[a-z0-9\\-]+}");

        TestCase.assertTrue(urim.matches("/edit-record/bbb-aa1/aaa-aa1"));
        Map<String,List<String>> params = urim.getParameters("/edit-record/bbb-aa1/aaa-aa1");
        TestCase.assertEquals("bbb-aa1", params.get("domainId").get(0));
        TestCase.assertEquals("aaa-aa1", params.get("recordId").get(0));
    }

    public void test12() throws MalformedURLException{
        StringPattern urim = new StringPattern("/domain/");
        TestCase.assertTrue(urim.matches("/domain/"));
    }
    
}
