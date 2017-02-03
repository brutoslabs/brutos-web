package org.brandao.brutos.web.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface Download {

	String getContentType();

	Map getHeader();

	long getContentLength();

	void write(OutputStream out) throws IOException;

}
