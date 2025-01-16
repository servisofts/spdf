
import SPDF.utils.JsonFileChecker;

public class debug {
    public static void main(String[] args) throws Exception {
        // String name = "darmotos_informe_economico";
        String name = "factura";
        new JsonFileChecker(name).start();
    }
}
