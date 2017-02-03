package org.brandao.brutos.io;

public abstract class AbstractResource implements Resource {

	protected String createRelativePath(String path, String relativePath) {

		path = cleanPath(path);
		// path = path.endsWith("/")? path.substring(0,path.length()-1) : path;

		relativePath = this.cleanPath(relativePath);

		int index = path.lastIndexOf("/");

		if (index != -1) {
			String newPath = path.substring(0, index);
			if (!relativePath.startsWith("/"))
				newPath += "/";
			return newPath + relativePath;
		} else
			return relativePath;

	}

	protected String cleanPath(String path) {
		path = path.replace("\\", "/");
		path = path.replaceAll("/+", "/");
		return path;
	}

	public boolean isOpen() {
		return false;
	}

	public String toString() {
		return getName();
	}
}
