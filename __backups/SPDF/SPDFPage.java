package SPDF;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.json.JSONObject;

public class SPDFPage extends SPDFItemAbstract {

    int totalSize = 0;

    public SPDFPage(JSONObject props, PDDocument document) throws IOException {
        super(props);

        SPDFTypes.PageType type = SPDFTypes.PageType.valueOf(props.getString("type"));

        // header = new SPDFHeader(this);
        // body = new SPDFBody(this);
        // footer = new SPDFFooter(this);
        // iy += header.height;
        // iy += body.height;
        // iy += footer.height;

        switch (type) {
            case PAGINABLE:
                break;
            case RESIZABLE:
                int th = 0;
                for (SPDFItemAbstract object : childs) {
                    th += object.getHeightTotal();
                }
                this.height = th + marginTop + paddingTop
                        + marginBottom + paddingBottom;
                break;
        }
        PDPage pagepdf = new PDPage(new PDRectangle(width, height));
        PDPageContentStream contentStream = new PDPageContentStream(document, pagepdf);
        paint(contentStream, this);
        document.addPage(pagepdf);
    }

    @Override
    public void paint(PDPageContentStream contentStream, SPDFPage page) throws IOException {
        super.paint(contentStream, page);
        contentStream.close();
    }

}
