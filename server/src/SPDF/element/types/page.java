package SPDF.element.types;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.json.JSONObject;

import SPDF.SPDF;
import SPDF.element.ElementAbstract;
import SPDF.element.ElementException;
import SPDF.element.ElementFactory;
import SPDF.element.ElementFactory.ElementType;
import SPDF.utils.PaintProps;

public class page extends ElementAbstract {

    public page(JSONObject props, ElementAbstract parent, SPDF pdf) throws ElementException {
        super(props, parent, ElementType.page, pdf);

    }

    @Override
    public void validations() throws ElementException {
        if (this.parent != null) {
            throw new ElementException("Element type ( page ) allow only one instance in root props.");
        }
    }

    @Override
    public boolean paint(PaintProps props) throws IOException, ElementException {

        // float height = this.style.getContentHeight();
        // if (header != null) {
        // height -= header.style.height;
        // }
        // if (footer != null) {
        // height -= footer.style.height;
        // }
        // if (this.style.getChildrensHeight() > height) {
        // props.cant_page = (int) Math.ceil(this.style.getChildrensHeight() / height);
        // }

        boolean exito = false;
        props.current_page = 0;
        while (!exito) {
            PDPage pagepdf = new PDPage(new PDRectangle(this.style.width, this.style.height));
            props.page = pagepdf;
            props.stream = new PDPageContentStream(props.document, pagepdf);
            props.current_page += 1;
            props.current_x = 0;
            props.current_y = this.style.height;
            props.max_y = 0;
            exito = super.paint(props);
            props.stream.close();
            props.document.addPage(pagepdf);
            System.out.println("Nueva pagina agregada");
        }
        props.cant_page = props.current_page;
        System.out.println("Terminado");
        // for (int i = 0; i < props.cant_page; i++) {

        // }
        return true;

    }

}
