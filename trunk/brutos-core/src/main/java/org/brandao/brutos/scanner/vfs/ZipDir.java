package org.brandao.brutos.scanner.vfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class ZipDir implements CloseableDir {

	private java.util.zip.ZipFile file;
	private String prefix;

	public ZipDir(String prefix, JarFile file) {
		this.file = file;
		this.prefix = prefix;
	}

	public String getPrefixPath() {
		return this.prefix;
	}

	public void close() throws IOException {
		try {
			file.close();
		} catch (IOException e) {
		}
	}

	public File[] getFiles() {
		List result = new ArrayList();
		Enumeration entries = file.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			if (!entry.isDirectory()) {
				if ((prefix == null || entry.getName().startsWith(prefix))) {
					result.add(new ZipFile(this, entry));
				}
			}
		}

		File[] files = new File[result.size()];

		for (int i = 0; i < files.length; i++)
			files[i] = (File) result.get(i);

		return files;
	}

	public String getPath() {
		return file.getName();
	}

	public java.util.zip.ZipFile getZipFile() {
		return file;
	}
}
