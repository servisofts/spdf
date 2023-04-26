package SPDF;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public interface SPDFItemInterface {
    public void paint(PDPageContentStream g, PDDocument document, PDPage page, float sx, float sy) throws IOException;
}
