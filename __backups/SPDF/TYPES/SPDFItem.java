package SPDF.TYPES;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.json.JSONObject;

import SPDF.SPDFItemAbstract;
import SPDF.SPDFPage;

public class SPDFItem extends SPDFItemAbstract {

    public SPDFItem( SPDFItemAbstract parent,JSONObject props) {
        super(parent, props);

    }

    @Override
    public void paint(PDPageContentStream contentStream, SPDFPage page) throws IOException {
        super.paint(contentStream, page);
    }
}
