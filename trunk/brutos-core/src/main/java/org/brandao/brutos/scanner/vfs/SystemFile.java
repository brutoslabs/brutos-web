package org.brandao.brutos.scanner.vfs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SystemFile implements File {

	private Dir path;
	private java.io.File file;

	public SystemFile(Dir path, java.io.File file) {
		this.path = path;
		this.file = file;
	}

	public String getRelativePath() {
		String relativePath = file.getPath().replace("\\", "/")
				.replaceAll("/+", "/");
		String rootPath = path.getPath();
		if (relativePath.startsWith(rootPath))
			return relativePath.substring(rootPath.length() + 1);
		else
			throw new VfsException();
	}

	public String getName() {
		return file.getName();
	}

	public InputStream openInputStream() throws VfsException {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException ex) {
			throw new VfsException(ex);
		}
	}

}
