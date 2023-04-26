package SPDF.element;

import org.json.JSONObject;

import SPDF.SPDF;
import SPDF.element.types.*;

public class ElementFactory {
    public enum ElementType {
        div,
        page,
        text,
        image,
        header,
        footer
    }

    public static ElementAbstract create(JSONObject props, ElementAbstract parent, SPDF pdf) throws ElementException {
        if (!props.has("type") || props.isNull("type")) {
            props.put("type", "div");
        }
        ElementType type = ElementType.valueOf(props.getString("type"));
        switch (type) {
            case div:
                return new div(props, parent, pdf);
            case page:
                return new page(props, parent, pdf);
            case text:
                return new text(props, parent, pdf);
            case image:
                return new image(props, parent, pdf);
        }
        throw new ElementException("ElementTpe ( " + type.name() + " ) not found or not valid");
    }
}
