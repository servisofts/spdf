package SPDF.element.style;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class PaintUtils {
    public static void drawRoundedRectangle(PDPageContentStream contentStream, float x, float y, float width,
            float height, float radius) throws IOException {
        contentStream.moveTo(x + radius, y);
        contentStream.lineTo(x + width - radius, y);
        contentStream.curveTo(x + width, y, x + width, y, x + width, y + radius);
        contentStream.lineTo(x + width, y + height - radius);
        contentStream.curveTo(x + width, y + height, x + width, y + height, x + width - radius, y + height);
        contentStream.lineTo(x + radius, y + height);
        contentStream.curveTo(x, y + height, x, y + height, x, y + height - radius);
        contentStream.lineTo(x, y + radius);
        contentStream.curveTo(x, y, x, y, x + radius, y);
        contentStream.closePath();
        // contentStream.fill();
    }
}
