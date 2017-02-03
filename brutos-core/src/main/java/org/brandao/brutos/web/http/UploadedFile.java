package org.brandao.brutos.web.http;

import java.io.File;

public interface UploadedFile {

	File getFile();

	void setFile(File file);

	String getContentType();

	void setContentType(String contentType);

	String getFileName();

	void setFileName(String fileName);

}
