package org.brandao.brutos.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSource {

	InputStream getInputStream() throws IOException;

}
