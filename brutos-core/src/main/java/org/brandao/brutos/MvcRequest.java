package org.brandao.brutos;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public interface MvcRequest {

	public Object getValue(String name);

	public Object getProperty(String name);

	public InputStream getStream() throws IOException;

	public String getType();

	public int getLength();

	public String getCharacterEncoding();

	public Locale getLocale();

}
