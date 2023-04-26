package SPDF.types;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.json.JSONObject;

import SPDF.SPDFItem;
import SPDF.SPDFItemFactory.SPDFItemType;

public class SPDFItem_header extends SPDFItem {

    public SPDFItem_header(JSONObject props, SPDFItem parent) throws Exception {
        super(props, SPDFItemType.header, parent);
    }

    public void paint(PDPageContentStream g, PDDocument document, PDPage page, int sx, int sy) throws IOException {
        super.paint(g, document, page, sx, sy);
    };
}
