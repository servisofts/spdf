package SPDF.element.types;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.json.JSONObject;

import SPDF.SPDF;
import SPDF.element.ElementAbstract;
import SPDF.element.ElementException;
import SPDF.element.ElementFactory.ElementType;
import SPDF.utils.PaintProps;

public class image extends ElementAbstract {

    public String src;

    public image(JSONObject props, ElementAbstract parent, SPDF pdf) throws ElementException {
        super(props, parent, ElementType.image, pdf);
    }

    @Override
    public void validations() throws ElementException {
    }

    @Override
    public void instanceChildrens() throws ElementException {
        this.src = this.props.get("src").toString();
    }

    @Override
    public boolean paint(PaintProps props) throws IOException, ElementException {

        PDRectangle mediaBox = props.page.getMediaBox();
        URL url = new URL(this.src);
        BufferedImage image = ImageIO.read(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(props.document, baos.toByteArray(), "image");

        // float imageWidth = pdImage.getWidth();
        // float imageHeight = pdImage.getHeight();
        // float scale = 1.0f; // Change scale as needed
        // PaintUtils.drawRoundedRectangle(props.stream, props.current_x +
        // this.style.marginLeft,
        props.stream.drawImage(pdImage, props.current_x + this.style.marginLeft,
                props.current_y - (this.style.height - (this.style.marginBottom)),
                this.style.width - (this.style.marginRight + style.marginLeft),
                this.style.height - (style.marginTop + style.marginBottom));
        // props.stream.drawImage(pdImage, (mediaBox.getWidth() - imageWidth * scale) /
        // 2,
        // (mediaBox.getHeight() - imageHeight * scale) / 2, imageWidth * scale,
        // imageHeight * scale);
        if (!super.paint(props)) {
            return false;
        }
        return true;
    }

}
