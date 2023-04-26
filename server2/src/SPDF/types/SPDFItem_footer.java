package SPDF.types;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.json.JSONObject;

import SPDF.SPDFItem;
import SPDF.SPDFItemFactory.SPDFItemType;

public class SPDFItem_footer extends SPDFItem {

    public SPDFItem_footer(JSONObject props, SPDFItem parent) throws Exception {
        super(props, SPDFItemType.footer, parent);
    }

    public void paint(PDPageContentStream g, PDDocument document, PDPage page, int sx, int sy) throws IOException {
        super.paint(g, document, page, sx, sy);
    };
}
