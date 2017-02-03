package org.brandao.brutos.io;

import java.io.IOException;
import java.net.URL;

public interface Resource extends InputStreamSource {

	URL getURL() throws IOException;

	Resource getRelativeResource(String relativePath) throws IOException;

	boolean exists();

	boolean isOpen();

	String getName();

}
