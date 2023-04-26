package SPDF.utils;

import java.io.File;

import SPDF.SPDF;

public class JsonFileChecker implements Runnable {

    private File jsonFile;
    private long lastModified;
    private String name;

    public JsonFileChecker(String name) {
        this.name = name;
        this.jsonFile = new File("pdfs/" + name + ".json");
        // this.lastModified = this.jsonFile.lastModified();
        this.lastModified = 0;
    }

    public void start() {
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
                    SPDF pdf1 = new SPDF("pdfs/" + name + ".json");
                    pdf1.saveFile("pdfs/" + name + ".pdf");
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