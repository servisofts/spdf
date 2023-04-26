package SPDF.element.types;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.json.JSONObject;

import SPDF.SPDF;
import SPDF.element.ElementAbstract;
import SPDF.element.ElementException;
import SPDF.element.ElementFactory.ElementType;
import SPDF.utils.PaintProps;

public class div extends ElementAbstract {

    public div(JSONObject props, ElementAbstract parent, SPDF pdf) throws ElementException {
        super(props, parent, ElementType.div, pdf);
    }

    @Override
    public void validations() throws ElementException {
    }

    @Override
    public boolean paint(PaintProps props) throws IOException, ElementException {
        return super.paint(props);
    }

}
