import java.io.File;
import java.io.IOException;

import SPDF.SPDF;

public class App {
    // public static void main(String[] args) {
    // try {
    // Servisofts.DEBUG = false;
    // Servisofts.ManejadorCliente = ManejadorCliente::onMessage;
    // Servisofts.Manejador = Manejador::onMessage;
    // Servisofts.initialize();
    // //PDF.prueba("asd", "·FF0000");
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    public static void main(String[] args) throws Exception {
        // new SPDF("pdfs/resizable.json");
        // new SPDF("pdfs/paginable.json");
        // new SPDF("pdfs/text.json", "pdfs/text.pdf");

        new SPDF("pdfs/comprobante.json", "pdfs/comprobante.pdf");
        File jsonFile = new File("pdfs/comprobante.json");
        JsonFileChecker jsonChecker = new JsonFileChecker(jsonFile);
        Thread jsonThread = new Thread(jsonChecker);
        jsonThread.start();
      
    }
}