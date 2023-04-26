import java.io.File;

import SPDF.SPDF;

public class JsonFileChecker implements Runnable {

    private File jsonFile;
    private long lastModified;

    public JsonFileChecker(File file) {
        this.jsonFile = file;
        this.lastModified = file.lastModified();
    }

    public void start(){
        Thread jsonThread = new Thread(this);
        jsonThread.start();
    }
    @Override
    public void run() {
        while (true) {
            long currentModified = jsonFile.lastModified();
            if (currentModified != lastModified) {
                System.out.println("El archivo JSON ha cambiado!");
                lastModified = currentModified;
                try {
                    new SPDF("pdfs/comprobante.json", "pdfs/comprobante.pdf");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000); // Espera 1 segundo antes de volver a verificar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}