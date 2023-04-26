package SPDF;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.json.JSONObject;

import SPDF.element.ElementAbstract;
import SPDF.element.ElementException;
import SPDF.element.ElementFactory;
import SPDF.element.ElementInterface;
import SPDF.element.ElementFactory.ElementType;
import SPDF.element.types.page;
import SPDF.utils.Json;
import SPDF.utils.PaintProps;

public class SPDF {

    private JSONObject data;
    private String path_to_json;
    private ElementAbstract element;
    public PDDocument document;

    public SPDF(String path_json) throws ElementException, IOException {
        this.path_to_json = path_json;
        this.data = Json.path_to_json(path_json);
        document = new PDDocument();
        this.buildElement();
    }
    public SPDF(JSONObject obj) throws ElementException, IOException {
        // this.path_to_json = path_json;
        this.data = obj;
        document = new PDDocument();
        this.buildElement();
    }

    public void buildElement() throws ElementException {
        this.element = ElementFactory.create(this.data, null, this);
        if (this.element.getType() != ElementType.page) {
            throw new ElementException("First element required type ( page ) ");
        }

    }

    public void saveFile(String path) throws IOException, ElementException {

        this.element.paint(new PaintProps(document));
        document.save(path);
        document.close();
    }

    public String getDetail() {
        return "Detalle del pdf: " + "\n"
                + "size: " + "0" + "\n"
                + "page: " + "0" + "\n"
                + "";
    }

}
