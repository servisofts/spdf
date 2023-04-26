package SPDF.types;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONObject;

import SPDF.SPDFItem;
import SPDF.SPDFItemFactory.SPDFItemType;

public class SPDFItem_text extends SPDFItem {

    public String txt;
    public PDType1Font pdfFont;
    public String[] lines;
    public float separation;

    public SPDFItem_text(JSONObject props, SPDFItem parent) throws Exception {
        super(props, SPDFItemType.text, parent);

    }

    @Override
    public boolean loadChildrens() throws Exception {
        this.pdfFont = new PDType1Font(FontName.HELVETICA);
        this.separation = pdfFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * this.style.fontSize;

        this.txt = this.props.getString("childrens");
        // float w = (pdfFont.getFontDescriptor().getFontBoundingBox().getWidth() /
        // 1000) * this.style.fontSize;
        // float w = (this.pdfFont.getSpaceWidth() / 1000);

        float w = (pdfFont.getFontDescriptor().getAverageWidth()
                / (pdfFont.getFontDescriptor().getFontBoundingBox().getWidth() - this.pdfFont.getSpaceWidth()))
                * this.style.fontSize;
        // pdfFont.getWidthFromFont(0)
        System.out.println(this.style.cw / w);
        lines = this.txt.split("(?<=\\G.{"
                + (int) (this.style.cw / w) + "})");
        return true;
    }

    public void paint(PDPageContentStream g, PDDocument document, PDPage page, float sx, float sy) throws IOException {
        super.paint(g, document, page, sx, sy);
        
        float x = sx + this.style.marginLeft + style.paddingLeft;
        float y = sy - (((float) (this.separation * 0.85)) + (this.style.marginBottom +
                style.paddingBottom));
        for (String line : lines) {
            g.beginText();
            g.setFont(pdfFont, style.fontSize);
            if (this.style.alignItems.equals("center")) {
                float titleWidth = (pdfFont.getStringWidth(line) / 1000) * this.style.fontSize;
                float media = (x) + (this.style.cw / 2);
                g.newLineAtOffset(media - (titleWidth / 2), y);
            } else if (this.style.alignItems.equals("end")) {
                float titleWidth = (pdfFont.getStringWidth(line) / 1000) * this.style.fontSize;
                g.newLineAtOffset((x + this.style.cw) - titleWidth, y);
            } else {
                g.newLineAtOffset(x, y);
            }

            g.showText(line.trim());
            g.endText();
            y -= this.separation; // Definir la separación entre cada línea
        }

    };

    @Override
    public float getChildrensHeight() {
        return this.lines.length * this.separation;
    }
}
