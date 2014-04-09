package test;

import javax.faces.component.UIComponent;

import custom.comp.base.AttributeConstants;
import custom.comp.base.UIComponentTypes;
import custom.comp.tag.AbstractOutputTag;

public class OutputTextTag extends AbstractOutputTag {
	private String value = null;
	private String inline = null;
	
	public String getComponentType() {
		return UIComponentTypes.OUTPUT_TEXT;
	}

	public void release() {
		super.release();
		value = null;
		inline = null;
	}

	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		setProperty(component, value, AttributeConstants.VALUE);
		setProperty(component, inline, AttributeConstants.INLINE);		
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setInline(String inline) {
		this.inline = inline;
	}
}

