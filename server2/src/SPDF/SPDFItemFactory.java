package SPDF;

import org.json.JSONObject;

import SPDF.types.*;

public class SPDFItemFactory {
    public enum SPDFItemType {
        div,
        page,
        text,
        header,
        footer
    }

    public static SPDFItemInterface create(JSONObject props, SPDFItem parent) throws Exception {
        if (!props.has("type") || props.isNull("type")) {
            props.put("type", "div");
        }
        SPDFItemType type = SPDFItemType.valueOf(props.getString("type"));
        switch (type) {
            case div:
                return new SPDFItem_div(props, parent);
            case page:
                return new SPDFItem_page(props, parent);
            case text:
                return new SPDFItem_text(props, parent);
            case header:
                return new SPDFItem_header(props, parent);
            case footer:
                return new SPDFItem_footer(props, parent);
            default:
                return null;
        }
    }
}
