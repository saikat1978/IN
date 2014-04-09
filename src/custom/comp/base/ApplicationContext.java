package custom.comp.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ResourceBundle;

public class ApplicationContext {
	private String globalSettingsPath = null;
	private String webContentRoot = null;
	private ResourceBundle resourceBundle = null;
	
	private static ThreadLocal tl = new ThreadLocal();
	
	private ApplicationContext() {
		super();
	}
	
	public static ApplicationContext getInstance() {
		return (ApplicationContext)tl.get();
	}
	
	public static ApplicationContext create() {
		ApplicationContext ac = new ApplicationContext();
		tl.set(ac);
		return ac;
	}

	public InputStream locate(String uri) {
		// assume full path name first, then relative to web content root, otherwise class path
		InputStream is = null;
		
		File f = locateFile(uri);
		if (f != null) {
			try {
				is = new FileInputStream(f);
				return is;
			} catch (FileNotFoundException fnfe) { // NOPMD by NL65921 on 17-9-10 11:03
				// should never happen since we already checked if file exists
			}
		}
		
		// not found, try classpath
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(uri);
	}
	
	public File locateFile(String uri) {
		// full path
		File f = new File(uri);
		if (f.exists()) {
			return f;
		}
		
		// relative to web content
		f = new File(getWebContentRoot(), uri);
		if (f.exists()) {
			return f;
		}
		
		return null;
	}
	public String getGlobalSettingsPath() {
		return globalSettingsPath;
	}

	public void setGlobalSettingsPath(String globalSettingsPath) {
		this.globalSettingsPath = globalSettingsPath;
	}

	public String getWebContentRoot() {
		return webContentRoot;
	}

	public void setWebContentRoot(String webContentRoot) {
		this.webContentRoot = webContentRoot;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}
}
