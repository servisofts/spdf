package SPDF;

import org.json.JSONObject;

public class SPDFItemStyle {
    public float width = 0, height = 0,
            margin = 0, marginTop = 0, marginBottom = 0, marginLeft = 0, marginRight = 0,
            padding = 0, paddingTop = 0, paddingBottom = 0, paddingLeft = 0, paddingRight = 0,
            x = 0, y = 0,
            cw = 0, ch = 0,
            fontSize = 12,
            borderWidth = 0,
            px, py,
            flex = 0;
    public String alignItems = "start", flexDirection = "column";
    JSONObject props;
    private SPDFItem item;

    public SPDFItemStyle(SPDFItem item) {
        this.item = item;
        this.props = new JSONObject();
        loadProps();
    }

    public SPDFItemStyle(SPDFItem item, JSONObject obj_style) {
        this.item = item;
        this.props = obj_style;
        loadProps();
    }

    public void loadProps() {
        if (validateProp("width")) {
            if (this.props.get("width").toString().indexOf("%") > -1) {
                float porcent = Float.parseFloat(this.props.get("width").toString().replaceAll("%", ""));
                float res = (porcent / 100);
                float porc = (res * this.item.parent.style.cw);
                this.width = porc;
            } else {
                this.width = this.props.getInt("width");
            }
        }
        if (validateProp("height")) {
            if (this.props.get("height").toString().indexOf("%") > -1) {
                double porcent = Float.parseFloat(this.props.get("height").toString().replaceAll("%", ""));
                double res = (porcent / 100);
                this.height = (int) (res * this.item.parent.style.ch);
            } else {
                this.height = this.props.getInt("height");
            }
        }
        if (validateProp("margin")) {
            this.margin = this.props.getInt("margin");
            this.marginTop = margin;
            this.marginBottom = margin;
            this.marginLeft = margin;
            this.marginRight = margin;
        }

        if (validateProp("marginTop"))
            this.marginTop = this.props.getInt("marginTop");
        if (validateProp("marginBottom"))
            this.marginBottom = this.props.getInt("marginBottom");
        if (validateProp("marginLeft"))
            this.marginLeft = this.props.getInt("marginLeft");
        if (validateProp("marginRight"))
            this.marginRight = this.props.getInt("marginRight");

        if (validateProp("padding")) {
            this.padding = this.props.getInt("padding");
            this.paddingTop = padding;
            this.paddingBottom = padding;
            this.paddingLeft = padding;
            this.paddingRight = padding;
        }
        if (validateProp("paddingTop"))
            this.paddingTop = this.props.getInt("paddingTop");
        if (validateProp("paddingBottom"))
            this.paddingBottom = this.props.getInt("paddingBottom");
        if (validateProp("paddingLeft"))
            this.paddingLeft = this.props.getInt("paddingLeft");
        if (validateProp("paddingRight"))
            this.paddingRight = this.props.getInt("paddingRight");

        if (validateProp("flex")) {
            this.flex = this.props.getInt("flex");
        }

        if (validateProp("fontSize"))
            this.fontSize = this.props.getInt("fontSize");
        if (validateProp("borderWidth"))
            this.borderWidth = this.props.getInt("borderWidth");
        if (validateProp("alignItems"))
            this.alignItems = this.props.getString("alignItems");
        if (validateProp("flexDirection"))
            this.flexDirection = this.props.getString("flexDirection");

        this.onLayout();
    }

    public void onLayout() {
        this.cw = width - (paddingLeft + paddingRight + marginLeft + marginRight);
        this.ch = height - (paddingTop + paddingBottom + marginTop + marginBottom);
        if (this.cw < 0) {
            this.cw = 0;
        }
        if (this.ch < 0) {
            this.ch = 0;
        }
    }

    public boolean validateProp(String propName) {
        if (!this.props.has(propName) || this.props.isNull(propName)) {
            return false;
        }
        return true;
    }

    public void afterLoadChilds() {
        System.out.println(this.item.type + " " + this.height);
        if (this.height <= 0) {
            if (this.item.type.toString().equals("div")) {
                System.out.println(this.item.type + " " + this.height);
            }
            float child_h = this.item.getChildrensHeight();
            this.height = child_h + (paddingTop + paddingBottom + marginTop + marginBottom);
            this.onLayout();
        }
        System.out.println(this.item.type + " " + this.height);

    }

    @Override
    public String toString() {
        return "{" +
                " width='" + getWidth() + "'" +
                ", height='" + getHeight() + "'" +
                " cw='" + cw + "'" +
                ", ch='" + ch + "'" +
                ", margin='" + getMargin() + "'" +
                ", marginTop='" + getMarginTop() + "'" +
                ", marginBottom='" + getMarginBottom() + "'" +
                ", marginLeft='" + getMarginLeft() + "'" +
                ", marginRight='" + getMarginRight() + "'" +
                ", padding='" + getPadding() + "'" +
                ", paddingTop='" + getPaddingTop() + "'" +
                ", paddingBottom='" + getPaddingBottom() + "'" +
                ", paddingLeft='" + getPaddingLeft() + "'" +
                ", paddingRight='" + getPaddingRight() + "'" +
                ", x='" + getX() + "'" +
                ", y='" + getY() + "'" +
                ", flex='" + getFlex() + "'" +
                "}";
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMargin() {
        return this.margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public float getMarginTop() {
        return this.marginTop;
    }

    public void setMarginTop(float marginTop) {
        this.marginTop = marginTop;
    }

    public float getMarginBottom() {
        return this.marginBottom;
    }

    public void setMarginBottom(float marginBottom) {
        this.marginBottom = marginBottom;
    }

    public float getMarginLeft() {
        return this.marginLeft;
    }

    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public float getMarginRight() {
        return this.marginRight;
    }

    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }

    public float getPadding() {
        return this.padding;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }

    public float getPaddingTop() {
        return this.paddingTop;
    }

    public void setPaddingTop(float paddingTop) {
        this.paddingTop = paddingTop;
    }

    public float getPaddingBottom() {
        return this.paddingBottom;
    }

    public void setPaddingBottom(float paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public float getPaddingLeft() {
        return this.paddingLeft;
    }

    public void setPaddingLeft(float paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public float getPaddingRight() {
        return this.paddingRight;
    }

    public void setPaddingRight(float paddingRight) {
        this.paddingRight = paddingRight;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getFlex() {
        return this.flex;
    }

    public void setFlex(float flex) {
        this.flex = flex;
    }

    public JSONObject getProps() {
        return this.props;
    }

    public void setProps(JSONObject props) {
        this.props = props;
    }

}
