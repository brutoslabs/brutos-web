package org.brandao.brutos.scanner.vfs;

import java.io.InputStream;
import java.util.zip.ZipEntry;

public class ZipFile implements File {

	private final ZipDir root;
	private final ZipEntry entry;

	public ZipFile(ZipDir root, ZipEntry entry) {
		this.root = root;
		this.entry = entry;
	}

	public String getRelativePath() {
		if (root.getPrefixPath() != null)
			return entry.getName().substring(root.getPrefixPath().length());
		else
			return entry.getName();
	}

	public String getName() {
		String tmp = entry.getName();
		return tmp.substring(tmp.lastIndexOf("/") + 1);
	}

	public InputStream openInputStream() throws VfsException {
		try {
			return root.getZipFile().getInputStream(entry);
		} catch (Exception e) {
			throw new VfsException(e);
		}
	}

}
