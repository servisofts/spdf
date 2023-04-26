import java.io.File;
import java.io.IOException;

import SPDF.SPDF;
import SPDF.element.types.text;
import SPDF.utils.JsonFileChecker;
import Servisofts.Servisofts;

public class App {
    public static void main(String[] args) {
        try {
            Servisofts.DEBUG = false;
            Servisofts.ManejadorCliente = ManejadorCliente::onMessage;
            Servisofts.Manejador = Manejador::onMessage;
            Servisofts.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // public static void main(String[] args) throws Exception {
    // String name = "darmotos_caja";
    // new JsonFileChecker(name).start();
    // }
}