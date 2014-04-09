package custom.comp.base;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

public class CustomResponseWriter extends ResponseWriter {

	/** the real writer */
	private final StringWriter stringWriter;
	
	/** the original writer */
	
	private final ResponseWriter originalResponseWriter;
	
	/** a clone backed by the StringWriter */
	private final ResponseWriter clonedResponseWriter;
	
	/**
	 * Creates a CustomResponseWriter
	 * @param orw
	 */
	public CustomResponseWriter(final ResponseWriter orw) {
		super();
		stringWriter = new StringWriter();
		originalResponseWriter = orw;
		clonedResponseWriter = originalResponseWriter.cloneWithWriter(stringWriter);
	}

	/**
	 * Get all content written to this writer
	 */
	public String toString() {
		return stringWriter.toString();
	}

	/**
	 * Return the original writer
	 */
	public ResponseWriter getOriginalResponseWriter() {
		return originalResponseWriter;
	}

	/**
	 * @see ResponseWriter#cloneWithWriter(Writer)
	 */
	public ResponseWriter cloneWithWriter(final Writer writer) {
		return getOriginalResponseWriter().cloneWithWriter(writer);
	}

	/**
	 * @see ResponseWriter#endDocument()
	 */
	public void endDocument() throws IOException {
		originalResponseWriter.endDocument();
	}

	/**
	 * @see ResponseWriter#endElement(String)
	 */
	public void endElement(final String arg0) throws IOException {
		originalResponseWriter.endElement(arg0);
	}

	/**
	 * @see ResponseWriter#flush()
	 */
	public void flush() throws IOException {
		originalResponseWriter.flush();
	}

	/**
	 * @see ResponseWriter#getCharacterEncoding()
	 */
	public String getCharacterEncoding() {
		return originalResponseWriter.getCharacterEncoding();
	}

	/**
	 * @see ResponseWriter#getContentType()
	 */
	public String getContentType() {
		return originalResponseWriter.getContentType();
	}

	/**
	 * @see ResponseWriter#startDocument()
	 */
	public void startDocument() throws IOException {
		originalResponseWriter.startDocument();
	}

	/**
	 * @see ResponseWriter#startElement(String, UIComponent)
	 */
	public void startElement(String arg0, UIComponent arg1) throws IOException {
		originalResponseWriter.startElement(arg0, arg1);
	}

	/**
	 * @see ResponseWriter#writeAttribute(String, Object, String)
	 */
	public void writeAttribute(String arg0, Object arg1, String arg2) throws IOException {
		originalResponseWriter.writeAttribute(arg0, arg1, arg2);
	}

	/**
	 * @see ResponseWriter#writeComment(Object)
	 */
	public void writeComment(Object arg0) throws IOException {
		originalResponseWriter.writeComment(arg0);
	}

	/**
	 * @see ResponseWriter#writeText(Object, String)
	 */
	public void writeText(Object arg0, String arg1) throws IOException {
		originalResponseWriter.writeText(arg0, arg1);
	}

	/**
	 * @see ResponseWriter#writeText(char[], int, int)
	 */
	public void writeText(char[] arg0, int arg1, int arg2) throws IOException {
		originalResponseWriter.writeText(arg0, arg1, arg2);
	}

	/**
	 * @see ResponseWriter#writeURIAttribute(String, Object, String)
	 */
	public void writeURIAttribute(String arg0, Object arg1, String arg2) throws IOException {
		originalResponseWriter.writeURIAttribute(arg0, arg1, arg2);
	}

	/**
	 * @see ResponseWriter#close()
	 */
	public void close() throws IOException {
		originalResponseWriter.close();
	}

	/**
	 * @see ResponseWriter#write(char[], int, int)
	 */
	public void write(char[] cbuf, int off, int len) throws IOException {
		originalResponseWriter.write(cbuf, off, len);
	}

}
