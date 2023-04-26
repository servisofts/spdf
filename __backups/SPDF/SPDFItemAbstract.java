package SPDF;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.json.JSONArray;
import org.json.JSONObject;

import SPDF.TYPES.SPDFItem;

public abstract class SPDFItemAbstract {
    SPDFPage page;

    JSONObject props;
    int width = 0, height = 0,
            margin = 0, marginTop = 0, marginBottom = 0, marginLeft = 0, marginRight = 0,
            padding = 0, paddingTop = 0, paddingBottom = 0, paddingLeft = 0, paddingRight = 0,
            x = 0, y = 0,
            cx = 0, cy = 0, cw = 0, ch = 0,
            ix = 0, iy = 0,
            flex = 0,
            size = 0;
    String name = "";
    JSONArray childrens;
    ArrayList<SPDFItemAbstract> childs;

    public SPDFItemAbstract(JSONObject props) {
        this.props = props;
        this.childs = new ArrayList<>();
        this.loadProps();
    }

    public SPDFItemAbstract(SPDFItemAbstract parent, JSONObject props) {
        this.props = props;
        this.parent = parent;
        this.childs = new ArrayList<>();
        this.loadProps();
    }

    SPDFItemAbstract parent;

    // public SPDFItemAbstract(SPDFItemAbstract parent, String name) {

    // this.props = parent.props.getJSONObject(name);
    // this.name = name;
    // this.loadProps();
    // }

    public void loadProps() {
        if (validateProp("width")) {
            this.width = this.props.getInt("width");
        }
        if (validateProp("height")) {
            this.height = this.props.getInt("height");
        }
        if (validateProp("label")) {
            this.name = this.props.getString("label");
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

        if (validateProp("flex"))
            this.flex = this.props.getInt("flex");

        calcContent();
        if (validateProp("childrens")) {
            this.childrens = this.props.getJSONArray("childrens");
            this.initChildrens();
        }
    }

    public void initChildrens() {
        for (int i = 0; i < this.childrens.length(); i++) {
            JSONObject child = this.childrens.getJSONObject(i);
            SPDFItem item = new SPDFItem(this, child);
            childs.add(item);
            this.iy += item.getHeight();
            System.out.println(child.toString());
        }
    }

    public void calcContent() {
        this.cx = this.marginLeft + this.paddingLeft;
        this.cy = this.marginTop + this.paddingTop;
        if (this.parent != null) {
            this.x = this.parent.cx + this.parent.ix;
            this.y = this.parent.cy + this.parent.iy;
            this.cx += this.parent.cx;
            this.cy += this.parent.cy;

            if (this.width == 0) {
                this.width = this.parent.cw;
            }
        }
        this.cw = this.width - (this.marginLeft + this.paddingLeft + this.marginRight + this.paddingRight);
        this.ch = this.height - (this.marginTop + this.paddingTop + this.marginBottom + this.paddingBottom);
    }

    public void paintBorder(PDPageContentStream contentStream) throws IOException {

        int fw = (this.width - (marginLeft + marginRight));
        int fh = (this.height - (marginTop + marginBottom));
        int fx = (this.x + marginLeft);
        int fy = this.page.height - (this.y + marginTop) - fh;
        contentStream.addRect(fx, fy, fw, fh);
        contentStream.stroke();
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(FontName.HELVETICA), 8);
        contentStream.newLineAtOffset(fx, fy);
        contentStream.showText("x:" + fx + " y:" + fy + " w:" + fw + " h:" + fh + " cx:" + cx + " cy:" + cy);
        contentStream.endText();
    }

    public boolean validateProp(String propName) {
        if (!this.props.has(propName) || this.props.isNull(propName)) {
            return false;
        }
        return true;
    }

    public void paint(PDPageContentStream contentStream, SPDFPage page) throws IOException {
        this.page = page;
        paintBorder(contentStream);
        for (SPDFItemAbstract object : childs) {
            object.paint(contentStream, page);
        }

    };

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHeightTotal() {
        return height + marginBottom + marginTop;
    }

    public int getWidthTotal() {
        return width + marginLeft + marginRight;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTotal_cy() {
        if (this.parent == null)
            return this.cy;
        return this.parent.getTotal_cy() + this.cy;
    }
}
