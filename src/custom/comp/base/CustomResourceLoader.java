package custom.comp.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.portlet.PortletContext;
import javax.servlet.ServletContext;



import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

public class CustomResourceLoader extends ResourceLoader {
	/** a resource loader that can load templates using their path */
	final private FileResourceLoader frl = new FileResourceLoader();
	
	/** a resource loader that can load templates from the classpath */
	final private ClasspathResourceLoader crl = new ClasspathResourceLoader();
		
	final private Map resources = new HashMap();
	
	/**
	 * @see ResourceLoader#getLastModified(Resource)
	 */
	public long getLastModified(Resource resource) {
		return 0;
	}

	/**
	 * Initializes both resource loaders
	 * @see ResourceLoader#commonInit(RuntimeServices, ExtendedProperties)
	 */
	public void commonInit(RuntimeServices rs, ExtendedProperties properties) {
		super.commonInit(rs, properties);
		crl.commonInit(rs, properties);
		frl.commonInit(rs, properties);
	}
	
	/**
	 * Finds a template and returns it as a stream
	 */
	public InputStream getResourceStream(String name) throws ResourceNotFoundException {
		byte[] b = (byte[])resources.get(name);
		if (b == null) {
			// not the most efficient, but first try file system and then classpath
			InputStream is = null;
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				ServletContext context = (ServletContext) fc.getExternalContext().getContext();
				String path = context.getRealPath("/WEB-INF/templates/" + name);
				is = frl.getResourceStream(path);
			} catch (Exception e) {
				is = crl.getResourceStream(name);
			}
			
			try {
				if (is != null) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buf = new byte[4096];
					int count = 0;
					while ( (count = is.read(buf)) > -1) { // NOPMD by NL65921 on 17-9-10 9:03
						baos.write(buf, 0, count);
					}
					b = baos.toByteArray();
					resources.put(name, b);
				} else {
					throw new ResourceNotFoundException("Could not find " + name);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw new ResourceNotFoundException("Error reading " + name);
			}
		}
		return new ByteArrayInputStream(b);
	}

	/**
	 * Initializes the resource loader
	 * Note: supplied properties are ignore
	 */
	public void init(ExtendedProperties dummy) {
		// ignore received properties
		ExtendedProperties properties = new ExtendedProperties();
		properties.addProperty("path", "");
		frl.init(properties);
	}

	/**
	 * Changes to templates are not supported without a restart
	 * @see ResourceLoader#isSourceModified(Resource)
	 */
	public boolean isSourceModified(Resource resource) {
		return false;
	}

}
