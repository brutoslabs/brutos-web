package org.brandao.brutos.scanner.vfs;

import java.net.URL;
import java.util.List;

import org.jboss.vfs.VirtualFile;

public class JbossVFSURLType implements URLType {

	public Dir toDir(URL url) throws Exception {
		VirtualFile vf = null;
		try {
			vf = (VirtualFile) url.openConnection().getContent();
			List<VirtualFile> lvf = vf.getChildrenRecursively();

			for (VirtualFile v : lvf)
				v.getPhysicalFile();

			java.io.File file = new java.io.File(vf.getPhysicalFile()
					.getParentFile(), vf.getName());

			return new JbossVFSDir(file);
		} catch (VfsException e) {
			return new JbossVFSDir(vf.getPhysicalFile());
		} catch (Throwable e) {
			throw new VfsException("could not open VirtualFile: " + url, e);
		}

	}

	public boolean matches(URL url) throws Exception {
		return url.getProtocol().equals("vfs");
	}

}
