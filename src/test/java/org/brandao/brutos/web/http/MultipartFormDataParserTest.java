package org.brandao.brutos.web.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.brandao.brutos.MutableRequestParserEventImp;
import org.brandao.brutos.web.http.MultipartFormDataParser.Field;
import org.junit.Test;

public class MultipartFormDataParserTest {
	
	@Test
	public void testEndFile() throws IOException {
		try(InputStream in = getClass().getClassLoader().getResourceAsStream("org/brandao/brutos/web/http/multipart_formdata_parser/multipart-end-file.txt")) {
			MultipartFormDataParser mp = new MultipartFormDataParser(in, "UTF-8", "--abcde12345", 8*1024*1024, 4096, new MutableRequestParserEventImp());
			
			assertTrue(mp.hasMoreElements());
			Field f = mp.nextElement();
			assertEquals("id", f.getHeader().get("content-disposition").getParams().get("name"));
			assertEquals("text/plain", f.getHeader().get("content-type").getValue());
			assertEquals("123e4567-e89b-12d3-a456-426655440000", f.getValue());

			assertTrue(mp.hasMoreElements());
			f = mp.nextElement();
			assertEquals("profileImage", f.getHeader().get("content-disposition").getParams().get("name"));
			assertEquals("image1.png", f.getHeader().get("content-disposition").getParams().get("filename"));
			assertEquals("image/png", f.getHeader().get("content-type").getValue());
			assertEquals("x-header", f.getHeader().get("x-custom-header").getValue());
			assertTrue(f.getValue() instanceof UploadedFile);
			assertFalse(mp.hasMoreElements());
			
		}
	}
}
