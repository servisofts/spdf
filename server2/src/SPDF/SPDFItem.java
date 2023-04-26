package SPDF;

import java.awt.Color;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.json.JSONArray;
import org.json.JSONObject;

import SPDF.SPDFItemFactory.SPDFItemType;

public abstract class SPDFItem implements SPDFItemInterface {

    SPDFItemFactory.SPDFItemType type;
    protected SPDFItem parent;
    protected JSONObject props;
    protected ArrayList<SPDFItem> items;
    protected SPDFItemStyle style;
    public boolean isPainted = false;

    public SPDFItem(JSONObject props, SPDFItemFactory.SPDFItemType type, SPDFItem parent) throws Exception {
        this.type = type;
        this.props = props;
        this.parent = parent;
        if (parent == null && type != SPDFItemType.page) {
            throw new Exception("El primer item debe ser de 'type:page'");
        }
        items = new ArrayList<>();
        if (this.props.has("style") && !this.props.isNull("style")) {
            this.style = new SPDFItemStyle(this, props.getJSONObject("style"));
        } else {
            this.style = new SPDFItemStyle(this);
        }

        this.loadChildrens();
        this.style.afterLoadChilds();
        // System.out.println(style.toString());
    }

    public boolean loadChildrens() throws Exception {
        if (!this.props.has("childrens") || this.props.isNull("childrens")) {
            return false;
        }
        JSONArray childs = this.props.getJSONArray("childrens");
        for (int i = 0; i < childs.length(); i++) {
            JSONObject obj = childs.getJSONObject(i);
            SPDFItem item = (SPDFItem) SPDFItemFactory.create(obj, this);
            items.add(item);
        }
        return true;
    }

    public float getChildrensHeight() {
        int he = 0;
        int wt = 0;
        float lastHeight = 0;
        if (this.style.flexDirection.equals("row")) {
            for (SPDFItem spdfItem : items) {
                if (lastHeight < spdfItem.style.height) {
                    lastHeight = spdfItem.style.height;
                }
                if ((spdfItem.style.width + wt) < this.style.cw) {
                    wt += spdfItem.style.width;
                } else {
                    wt = 0;
                    he += lastHeight;
                    lastHeight = 0;
                }
            }
            he += lastHeight;
            return he;
        }
        for (SPDFItem spdfItem : items) {
            he += spdfItem.style.height;
        }
        return he;
    }

    public void paint(PDPageContentStream g, PDDocument document, PDPage page, float sx, float sy) throws IOException {
        this.style.px = sx;
        this.style.py = sy;
        float x = sx + style.marginLeft;
        float sy2 = sy - style.marginTop;
        float currentSize = 0, currentHeight = 0;

        for (SPDFItem spdfItem : items) {

            // sx += spdfItem.style.width;

            if (this.style.flexDirection.equals("row")) {
                if ((currentSize + spdfItem.style.width) > this.style.cw) {
                    x = sx;
                    sy2 -= currentHeight;
                    currentHeight = spdfItem.style.height;
                    currentSize = spdfItem.style.width;
                } else {
                    if (currentHeight < spdfItem.style.height) {
                        currentHeight = spdfItem.style.height;
                    }
                    currentSize += spdfItem.style.width;
                }
            }
            spdfItem.paint(g, document, page, x + style.paddingLeft, sy2 - style.paddingTop);
            if (this.style.flexDirection.equals("column")) {
                sy2 -= spdfItem.style.height;
            } else {
                x += spdfItem.style.width;
            }
        }
        if (style.borderWidth > 0)
            this.drawBorder(g, sx, sy);

        this.isPainted = true;
    };

    public void paint_(PDPageContentStream g, PDDocument document, PDPage page,
            float sx, float sy) throws IOException {

        float sy2 = sy - style.marginTop;
        if (parent != null) {
            System.out.println(sy2);
        }
        if (style.borderWidth > 0) {
            this.drawBorder(g, sx, sy);
        }
        sx += style.marginLeft;

        float currentSize = 0;
        float x = sx;
        float lastHeight = 0;
        boolean exito = true;
        for (SPDFItem spdfItem : items) {
            if (spdfItem.isPainted)
                continue;
            if (spdfItem.items.size() <= 0) {
                if (sy2 - spdfItem.style.height < this.style.paddingBottom +
                        this.style.marginBottom) {
                    exito = false;
                    continue;
                }
            }

            if (this.style.flexDirection.equals("row")) {

                if ((currentSize + spdfItem.style.width) > this.style.cw) {
                    x = sx;
                    sy2 -= lastHeight;
                    lastHeight = spdfItem.style.height;
                    currentSize = spdfItem.style.width;
                } else {
                    if (lastHeight < spdfItem.style.height) {
                        lastHeight = spdfItem.style.height;
                    }
                    currentSize += spdfItem.style.width;
                }
                // sx += spdfItem.style.width;
            }
            spdfItem.paint(g, document, page, x + style.paddingLeft, sy2 -
                    style.paddingTop);
            if (this.style.flexDirection.equals("column")) {
                sy2 -= spdfItem.style.height;
            } else {
                x += spdfItem.style.width;
            }
        }
        this.isPainted = exito;
        // contentStream.addRect(0, 0, this, fh);
        // contentStream.stroke();
    };

    public void drawBorder(PDPageContentStream g, float sx, float sy) throws IOException {
        g.setStrokingColor(Color.black);
        g.setLineWidth(this.style.borderWidth);
        g.addRect(sx + this.style.marginLeft, sy - (this.style.height - this.style.marginBottom),
                this.style.width - (this.style.marginRight + style.marginLeft),
                this.style.height - (style.marginTop + style.marginBottom));
        g.stroke();
    }

    public void drawGrid(PDPageContentStream g, int space) throws IOException {
        g.setLineWidth(1);
        g.setStrokingColor(Color.decode("#DDDDDD"));
        for (int i = 0; i < this.style.width; i += space) {
            g.moveTo(i, 0);
            g.lineTo(i, style.height);

            g.stroke();
        }
        for (int i = (int) this.style.height; i > 0; i -= space) {
            g.moveTo(0, i);
            g.lineTo(style.width, i);
            g.stroke();
        }

    }
}
