package SPDF.element;

import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.json.JSONArray;
import org.json.JSONObject;

import SPDF.SPDF;
import SPDF.element.ElementFactory.ElementType;
import SPDF.element.style.ElementStyle;
import SPDF.element.style.PaintUtils;
import SPDF.utils.PaintProps;

public abstract class ElementAbstract implements ElementInterface {

    public ElementStyle style;
    protected JSONObject props;
    protected ElementAbstract parent;
    public ElementAbstract header;
    public ElementAbstract footer;
    protected ElementType type;
    public ArrayList<ElementAbstract> childrens;
    public boolean painted;
    public SPDF pdf;

    public ElementAbstract(JSONObject props, ElementAbstract parent, ElementType type, SPDF pdf)
            throws ElementException {
        this.pdf = pdf;
        this.props = props;
        this.parent = parent;
        this.type = type;
        this.childrens = new ArrayList<>();
        this.validations();
        this.style = new ElementStyle(this);
        this.style.beforeLoadChildrens();
        this.instanceChildrens();
        instanceHeader();
        instanceFooter();
        this.style.afterLoadChildrens();

    }

    public void instanceHeader() throws ElementException {
        if (!this.props.has("header") || this.props.isNull("header")) {
            return;
        }
        this.header = (ElementAbstract) ElementFactory.create(this.props.getJSONObject("header"), this, this.pdf);
    }

    public void instanceFooter() throws ElementException {
        if (!this.props.has("footer") || this.props.isNull("footer")) {
            return;
        }
        this.footer = (ElementAbstract) ElementFactory.create(this.props.getJSONObject("footer"), this, this.pdf);
    }

    public void instanceChildrens() throws ElementException {
        if (!this.props.has("childrens") || this.props.isNull("childrens")) {
            return;
        }
        this.childrens = new ArrayList<>();
        JSONArray childs = this.props.getJSONArray("childrens");
        for (int i = 0; i < childs.length(); i++) {
            JSONObject obj = childs.getJSONObject(i);
            ElementAbstract item = (ElementAbstract) ElementFactory.create(obj, this, this.pdf);
            childrens.add(item);
        }
    }

    public float px, py, cx, cy, ch;

    @Override
    public boolean paint(PaintProps props) throws IOException, ElementException {

        if (this.painted && !props.alwaysPaint) {
            return true;
        }

        this.px = props.current_x;
        this.py = props.current_y;
        this.cx = props.current_x + (this.style.marginLeft + this.style.paddingLeft);
        this.cy = props.current_y - (this.style.marginTop + this.style.paddingTop);

        if (props.max_y > this.py - this.style.height) {
            if (this.style.height > this.parent.style.getContentHeight()) {
                System.out.println("No se puede pintar demaciado grande");
                // return true;
            } else {
                return false;
            }
        }
        if (this.parent != null) {
            this.alignItems(props);
            this.justifyContent(props);
        }

        this.style.paint(props);
        paintBorder(props);
        paintLogBox(props);

        PaintProps propsChildrens = props.clone();
        propsChildrens.current_x = this.cx;
        propsChildrens.current_y = this.cy;
        propsChildrens.max_y = this.cy - this.style.getContentHeight();

        if (this.header != null) {
            this.header.painted = false;
            PaintProps propsHeader = props.clone();
            propsHeader.current_x = this.cx;
            propsHeader.current_y = this.cy;
            propsHeader.alwaysPaint = true;
            propsChildrens.current_y -= this.header.style.height;

            this.header.paint(propsHeader);
        }
        if (this.footer != null) {
            this.footer.painted = false;
            PaintProps PropsFooter = props.clone();
            PropsFooter.alwaysPaint = true;
            PropsFooter.current_x = this.cx;
            PropsFooter.current_y = (this.cy - this.style.getContentHeight()) + this.footer.style.height;
            propsChildrens.max_y += this.footer.style.height;
            this.footer.paint(PropsFooter);
        }

        for (ElementAbstract elementAbstract : childrens) {
            if (elementAbstract.parent.style.flexDirection.equals("row")) {
                propsChildrens.current_y = this.cy;
            } else {
                propsChildrens.current_x = this.cx;
            }

            if (!elementAbstract.paint(propsChildrens)) {
                return false;
            }
        }

        if (this.parent == null) {
            return true;
        }
        if (this.parent.style.flexDirection.equals("row")) {
            props.current_x += this.style.width;
        } else {
            props.current_y -= this.style.height;
        }
        this.painted = true;
        return true;
    }

    public void alignItems(PaintProps props) {
        // start center end
        if (this.parent.style.alignItems.equals("center")) {
            if (this.parent.style.flexDirection.equals("row")) {
                float space = (this.parent.style.getContentWidth() - this.parent.style.getChildrensWidth()) / 2;
                props.current_x += space;
            } else {
                float space = (this.parent.style.getContentWidth() - this.style.width) / 2;
                props.current_x += space;

            }
        }
        if (this.parent.style.alignItems.equals("end")) {
            if (this.parent.style.flexDirection.equals("row")) {
                float space = (this.parent.style.getContentWidth() - this.parent.style.getChildrensWidth());
                props.current_x += space;
            } else {
                float space = (this.parent.style.getContentWidth() - this.style.width);
                props.current_x += space;

            }
        }
    }

    public void justifyContent(PaintProps props) {
        // start center end space-between space-around space-evenly
        if (this.parent.style.justifyContent.equals("center")) {
            if (this.parent.style.flexDirection.equals("row")) {
                float space = (this.parent.style.getContentHeight() - this.style.height) / 2;
                props.current_y -= space;
            } else {
                float space = (this.parent.style.getContentHeight() - this.parent.style.getChildrensHeight()) / 2;
                props.current_y -= space;
            }
        }
        if (this.parent.style.justifyContent.equals("end")) {
            if (this.parent.style.flexDirection.equals("row")) {
                float space = (this.parent.style.getContentHeight() - this.style.height);
                props.current_y -= space;
            } else {
                float space = (this.parent.style.getContentHeight() - this.parent.style.getChildrensHeight());
                props.current_y -= space;
            }
        }
    }

    public void paintBorder(PaintProps props) throws IOException, ElementException {
        if (this.style.borderWidth > 0) {
            props.stream.setStrokingColor(Color.decode(this.style.borderColor));
            props.stream.setLineWidth(this.style.borderWidth);
            PaintUtils.drawRoundedRectangle(props.stream, props.current_x + this.style.marginLeft,
                    props.current_y - (this.style.height - (this.style.marginBottom)),
                    this.style.width - (this.style.marginRight + style.marginLeft),
                    this.style.height - (style.marginTop + style.marginBottom), this.style.borderRadius);
            props.stream.stroke();
        }
    }

    public void paintLogBox(PaintProps props) throws IOException, ElementException {
        if (!this.props.has("debug") || this.props.isNull("debug")) {
            return;
        }
        props.stream.beginText();
        props.stream.setFont(new PDType1Font(FontName.TIMES_ROMAN), 10);
        props.stream.newLineAtOffset(props.current_x + this.style.marginLeft,
                props.current_y - 8);
        String log = "";
        log += this.type.name();
        log += " w: " + this.style.width;
        log += " h: " + this.style.height;
        log += " cx: " + props.current_x;
        log += " cy: " + props.current_y;
        props.stream.showText(log);
        props.stream.endText();
    }

    public JSONObject getProps() {
        return this.props;
    }

    public void setProps(JSONObject props) {
        this.props = props;
    }

    public ElementAbstract getParent() {
        return this.parent;
    }

    public void setParent(ElementAbstract parent) {
        this.parent = parent;
    }

    public ElementType getType() {
        return this.type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

}
