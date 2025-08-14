package SPDF.element.style;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import SPDF.element.ElementAbstract;
import SPDF.element.ElementException;
import SPDF.element.style.props.backgroundColor;
import SPDF.utils.PaintProps;

public class ElementStyle {
    private ElementAbstract element;
    public JSONObject jsonStyle;
    // Size
    public float width = 0, height = 0;
    // Margin
    public float margin = 0, marginTop = 0, marginBottom = 0, marginLeft = 0, marginRight = 0;
    // Padding
    public float padding = 0, paddingTop = 0, paddingBottom = 0, paddingLeft = 0, paddingRight = 0;
    // Border
    public float borderWidth = 0, borderRadius = 0, borderTopWidth = 0, borderBottomWidth = 0,
            borderLeftWidth = 0, borderRightWidth = 0;

    public String borderColor = "#000000";
    public String fontWeight = "regular";

    // Other
    public float flex = 0;

    // Text
    public float fontSize = 12;
    public String color = "#000000", font = "Times New Roman";
    public String textAlign = "left";
    // direction
    public String alignItems = "start", flexDirection = "column", justifyContent = "start";

    public String position = "relative";
    public int left = 0, top = 0, right = 0, bottom = 0;
    private ArrayList<StylePropAbstract> props;

    public ElementStyle(ElementAbstract element) {
        this.element = element;
        this.props = new ArrayList<>();

        if (!this.element.getProps().has("style") || this.element.getProps().isNull("style")) {
            this.jsonStyle = new JSONObject();
            return;
        } else {
            this.jsonStyle = this.element.getProps().getJSONObject("style");
        }
        this.props.add(new backgroundColor(this));

    }

    // ********* Antes de crear los hijos *********
    public void beforeLoadChildrens() {

        for (int i = 0; i < props.size(); i++) {
            props.get(i).beforeLoadChildrens();
        }
        if (validateProp("width")) {
            if (this.jsonStyle.get("width") instanceof Integer) {
                this.width = this.jsonStyle.getInt("width");
            }
            if (this.jsonStyle.get("width") instanceof String) {
                String w = this.jsonStyle.getString("width");
                if (w.indexOf("%") > -1) {
                    float porcent = Float.parseFloat(w.replaceAll("%", ""));
                    float res = (porcent / 100);
                    float porc = (res * this.element.getParent().style.getContentWidth());
                    this.width = porc;
                }
            }
        }
        if (validateProp("height")) {
            if (this.jsonStyle.get("height") instanceof Integer) {
                this.height = this.jsonStyle.getInt("height");
            }
            if (this.jsonStyle.get("height") instanceof String) {
                String h = this.jsonStyle.getString("height");
                if (h.indexOf("%") > -1) {
                    float porcent = Float.parseFloat(h.replaceAll("%", ""));
                    float res = (porcent / 100);
                    float porc = (res * this.element.getParent().style.getContentHeight());
                    this.height = porc;
                }
            }
        }

        if (validateProp("margin")) {
            this.margin = this.jsonStyle.getInt("margin");
            this.marginTop = margin;
            this.marginBottom = margin;
            this.marginLeft = margin;
            this.marginRight = margin;
        }
        if (validateProp("marginTop"))
            this.marginTop = this.jsonStyle.getInt("marginTop");
        if (validateProp("marginBottom"))
            this.marginBottom = this.jsonStyle.getInt("marginBottom");
        if (validateProp("marginLeft"))
            this.marginLeft = this.jsonStyle.getInt("marginLeft");
        if (validateProp("marginRight"))
            this.marginRight = this.jsonStyle.getInt("marginRight");

        if (validateProp("padding")) {
            this.padding = this.jsonStyle.getInt("padding");
            this.paddingTop = padding;
            this.paddingBottom = padding;
            this.paddingLeft = padding;
            this.paddingRight = padding;
        }
        if (validateProp("paddingTop"))
            this.paddingTop = this.jsonStyle.getInt("paddingTop");
        if (validateProp("paddingBottom"))
            this.paddingBottom = this.jsonStyle.getInt("paddingBottom");
        if (validateProp("paddingLeft"))
            this.paddingLeft = this.jsonStyle.getInt("paddingLeft");
        if (validateProp("paddingRight"))
            this.paddingRight = this.jsonStyle.getInt("paddingRight");

        if (validateProp("borderWidth"))
            this.borderWidth = this.jsonStyle.getInt("borderWidth");

        if (validateProp("borderTopWidth"))
            this.borderTopWidth = this.jsonStyle.getInt("borderTopWidth");
        if (validateProp("borderBottomWidth"))
            this.borderBottomWidth = this.jsonStyle.getInt("borderBottomWidth");
        if (validateProp("borderLeftWidth"))
            this.borderLeftWidth = this.jsonStyle.getInt("borderLeftWidth");
        if (validateProp("borderRightWidth"))
            this.borderRightWidth = this.jsonStyle.getInt("borderRightWidth");

        if (validateProp("borderRadius"))
            this.borderRadius = this.jsonStyle.getInt("borderRadius");
        if (validateProp("borderColor"))
            this.borderColor = this.jsonStyle.getString("borderColor");

        if (validateProp("flex")) {
            this.flex = this.jsonStyle.getInt("flex");
        }
        // if (validateProp("backgroundColor"))
        // this.backgroundColor = this.jsonStyle.getString("backgroundColor");

        if (validateProp("fontSize"))
            this.fontSize = this.jsonStyle.getInt("fontSize");
        if (validateProp("textAlign"))
            this.textAlign = this.jsonStyle.getString("textAlign");
        if (validateProp("color"))
            this.color = this.jsonStyle.getString("color");
        if (validateProp("font"))
            this.font = this.jsonStyle.getString("font");
        if (validateProp("fontWeight"))
            this.fontWeight = this.jsonStyle.getString("fontWeight");

        if (validateProp("alignItems"))
            this.alignItems = this.jsonStyle.getString("alignItems");
        if (validateProp("justifyContent"))
            this.justifyContent = this.jsonStyle.getString("justifyContent");
        if (validateProp("flexDirection"))
            this.flexDirection = this.jsonStyle.getString("flexDirection");
        if (validateProp("position"))
            this.position = this.jsonStyle.getString("position");

        if (validateProp("left"))
            this.left = this.jsonStyle.getInt("left");
        if (validateProp("top"))
            this.top = this.jsonStyle.getInt("top");
        if (validateProp("right"))
            this.right = this.jsonStyle.getInt("right");
        if (validateProp("bottom"))
            this.bottom = this.jsonStyle.getInt("bottom");
        if (validateProp("backgroundColor"))
            this.jsonStyle.put("backgroundColor", this.jsonStyle.getString("backgroundColor").replaceAll("#", ""));
    }

    // ********* Despues de crear los hijos *********

    public void afterLoadChildrens() throws ElementException {

        for (int i = 0; i < props.size(); i++) {
            props.get(i).afterLoadChildrens();
        }
        if (!validateProp("height") && height <= 0) {
            if (this.flexDirection.equals("row")) {
                this.height = this.getMaxChildrensHeight() + (paddingTop + paddingBottom + marginTop + marginBottom)
                        + this.getHeaderHeight() + this.getFooterHeight();
            } else {
                this.height = this.getChildrensHeight() + (paddingTop + paddingBottom + marginTop + marginBottom)
                        + this.getHeaderHeight() + this.getFooterHeight();
            }

        }
        int flext = 0;
        float widtht = 0;
        float heightt = 0;
        for (ElementAbstract elementAbstract : this.element.childrens) {
            if (elementAbstract.style.flex > 0) {
                flext += elementAbstract.style.flex;
            } else {
                widtht += elementAbstract.style.width;
                heightt += elementAbstract.style.height;
            }

        }

        if (flext > 0) {
            float sizeF = 0;
            if (flexDirection.equals("row")) {
                sizeF = (this.getContentWidth() - widtht) / flext;
            } else {
                sizeF = (this.getContentHeight() - heightt) / flext;
            }
            for (ElementAbstract elementAbstract : this.element.childrens) {
                if (elementAbstract.style.flex > 0) {

                    if (flexDirection.equals("row")) {
                        elementAbstract.style.width = sizeF * elementAbstract.style.flex;
                    } else {
                        elementAbstract.style.height = sizeF * elementAbstract.style.flex;
                    }
                    elementAbstract.instanceChildrens();
                }
            }
        }

    }

    public boolean paint(PaintProps p) throws IOException {
        for (int i = 0; i < props.size(); i++) {
            props.get(i).paint(p);
        }
        return true;
    }

    public float getContentWidth() {
        return this.width - (paddingLeft + paddingRight + marginLeft + marginRight);
    }

    public float getContentHeight() {
        return height - (paddingTop + paddingBottom + marginTop + marginBottom);
    }

    public float getChildrensHeight() {
        float h = 0;
        for (ElementAbstract elementAbstract : this.element.childrens) {
            if(elementAbstract.style.position.equals("absolute")){
                continue;
            }
            h += elementAbstract.style.height;
        }
        return h;
    }

    public float getMaxChildrensHeight() {
        float h = 0;
        for (ElementAbstract elementAbstract : this.element.childrens) {
            if (h <= elementAbstract.style.height) {
                h = elementAbstract.style.height;

            }
        }
        return h;
    }

    public float getHeaderHeight() {
        float h = 0;
        if (this.element.header != null) {
            h += this.element.header.style.height;
        }
        return h;
    }

    public float getFooterHeight() {
        float h = 0;
        if (this.element.footer != null) {
            h += this.element.footer.style.height;
        }
        return h;
    }

    public float getChildrensWidth() {
        float h = 0;
        for (ElementAbstract elementAbstract : this.element.childrens) {
            h += elementAbstract.style.width;
        }
        return h;
    }

    public float getMaxChildrensWidth() {
        float h = 0;
        for (ElementAbstract elementAbstract : this.element.childrens) {
            if (h <= elementAbstract.style.width) {
                h = elementAbstract.style.width;
            }
        }
        return h;
    }

    public boolean validateProp(String propName) {
        if (!this.jsonStyle.has(propName) || this.jsonStyle.isNull(propName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" +
                ", width='" + width + "'" +
                ", height='" + height + "'" +
                "}";
    }

}
