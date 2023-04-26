package SPDF;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.json.JSONObject;

import SPDF.SPDFTypes.PageType;

public class SPDF {

    PDDocument document;

    public SPDF(String json_path) throws IOException {
        JSONObject props = load_json_file(json_path);

        document = new PDDocument();
        new SPDFPage(props, document);
        document.save(props.getString("save_path"));
        document.close();

        // SPDFHeader header = new SPDFHeader(props);
        // SPDFBody body = new SPDFBody(props);
        // SPDFFooter footer = new SPDFFooter(props);
        // JSONObject page = data.getJSONObject("page");
        // JSONObject header = data.getJSONObject("header");
        // JSONObject body = data.getJSONObject("body");
        // JSONObject footer = data.getJSONObject("footer");
        // SPDFTypes.PageType type =
        // SPDFTypes.PageType.valueOf(objpage.getString("type"));
        // int width = objpage.getInt("width");
        // int height = 0;
        // switch (type) {
        // case PAGINABLE:
        // height = objpage.getInt("height");
        // break;
        // case RESIZABLE:
        // // No requiere height
        // break;
        // }

        // PDPage page = new PDPage(new PDRectangle(width, height));
        // document.addPage(page);

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
