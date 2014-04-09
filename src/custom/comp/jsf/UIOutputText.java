package custom.comp.jsf;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;

import custom.comp.base.AttributeConstants;
import custom.comp.base.UIComponentTypes;
import custom.comp.base.UICustomOutput;

public class UIOutputText extends UICustomOutput {

	public String getType() {
		return UIComponentTypes.OUTPUT_TEXT;
	}

	protected void setTemplateParameters(FacesContext facesContext) {
		// get the value and see if there are any params
		boolean valid = true;
		List children = getChildren();
		List params = new ArrayList();
		if (children != null) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Object child = it.next();
				if (child instanceof UIParam) {
					Object value = ((UIParam)child).getParamValue();
					valid &= (value != null);
					params.add(value);
				}
			}
		}

		if (valid) {
			Object o = getObject(AttributeConstants.VALUE, false);
			if (o instanceof String) {
				String value = (String)o;
				String result = MessageFormat.format(value, params.toArray());
				//rc.set(TEXT, result);
			} else if (o != null) {
				//rc.set(TEXT, o.toString());
			}
			
			//rc.set(INLINE, getBoolean(INLINE, true));
		}
	}
}
