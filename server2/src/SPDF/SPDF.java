package SPDF;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.json.JSONObject;

public class SPDF {
    JSONObject props;
    SPDFItem item;
    int cantidad_paginas;
    String save_path;

    public SPDF(String json_path, String save_path) throws Exception {
        this.save_path = save_path;
        this.props = load_json_file(json_path);
        this.item = (SPDFItem) SPDFItemFactory.create(props, null);
        double childh = this.item.getChildrensHeight();
        cantidad_paginas = 1;
        if (item.style.ch < childh) {
            cantidad_paginas = (int) Math.ceil(childh / item.style.ch);
        }
        this.buildPDF();
    }

    public void buildPDF() throws IOException {
        PDDocument document = new PDDocument();
        for (int i = 0; i < cantidad_paginas; i++) {
            PDPage pagepdf = new PDPage(new PDRectangle(this.item.style.width, this.item.style.height));
            PDPageContentStream contentStream = new PDPageContentStream(document, pagepdf);
            item.paint(contentStream, document, pagepdf, 0, this.item.style.height);
            contentStream.close();
            document.addPage(pagepdf);
        }
        document.save(this.save_path);
        document.close();
    }

    public JSONObject load_json_file(String json_path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(json_path));
        String jsonString = "";
        String line;
        while ((line = reader.readLine()) != null) {
            jsonString += line;
        }
        reader.close();
        return new JSONObject(jsonString);
    }

}
