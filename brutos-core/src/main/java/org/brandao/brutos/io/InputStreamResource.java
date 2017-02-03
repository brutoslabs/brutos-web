package org.brandao.brutos.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class InputStreamResource extends AbstractResource {

	private InputStream input;

	public InputStreamResource(InputStream input) {
		this.input = input;
	}

	public URL getURL() throws IOException {
		throw new FileNotFoundException(" URL does not exist");
	}

	public Resource getRelativeResource(String relativePath) throws IOException {
		throw new FileNotFoundException("Cannot create a relative resource: "
				+ relativePath);
	}

	public boolean exists() {
		return true;
	}

	public InputStream getInputStream() throws IOException {
		return input;
	}

	public String getName() {
		return "InputStream";
	}

}
