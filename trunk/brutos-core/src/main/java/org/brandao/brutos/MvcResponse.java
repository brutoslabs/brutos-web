package org.brandao.brutos;

import java.io.OutputStream;
import java.util.Locale;

public interface MvcResponse {

	public void process(Object object);

	public OutputStream processStream();

	public void setInfo(String name, String value);

	public String getType();

	public int getLength();

	public String getCharacterEncoding();

	public Locale getLocale();

	public void setLocale(Locale value);

	public void setType(String value);

	public void setLength(int value);

	public void setCharacterEncoding(String value);

}
