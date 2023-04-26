package SPDF.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class PaintProps {
    public PDDocument document;
    public PDPage page;
    public PDPageContentStream stream;
    public int cant_page;
    public int current_page;
    public float x,y;
    public boolean alwaysPaint;
    
    public float current_x, current_y, max_y;

    

    public PaintProps(PDDocument document) {
        this.document = document;
        this.cant_page = 1;
    }

    public PaintProps clone() {
        PaintProps p = new PaintProps(document);
        p.stream = this.stream;
        p.page = this.page;
        p.cant_page = this.cant_page;
        p.current_page = this.current_page;
        // p.px = this.px;
        // p.py = this.py;
        // p.pw = this.pw;
        p.x = this.x;
        p.y = this.y;
        p.alwaysPaint= this.alwaysPaint;
        p.max_y = this.max_y;
        p.current_x = this.current_x;
        p.current_y = this.current_y;
        return p;
    }
}
