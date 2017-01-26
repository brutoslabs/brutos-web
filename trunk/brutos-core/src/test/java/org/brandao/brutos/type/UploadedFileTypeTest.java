


package org.brandao.brutos.type;

import junit.framework.TestCase;
import org.brandao.brutos.web.http.UploadedFile;


public class UploadedFileTypeTest extends TestCase{

    private Type type = new UploadedFileType();

    private Object expected1 = null;//new BrutosFile(null);
    private Object test1     = expected1;

    private Object invalidType = new Integer(1);

    public void test1(){
        Object val = type.convert(test1);
        TestCase.assertEquals(expected1, val);
    }

    public void test5(){
        try{
            type.convert(invalidType);
            TestCase.fail("expected UnknownTypeException");
        }
        catch( UnknownTypeException e ){
        }
    }

    public void test6(){
        Object val = type.convert(null);
        TestCase.assertNull(val);
    }

}
