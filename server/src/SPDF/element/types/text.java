package SPDF.element.types;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.json.JSONObject;

import SPDF.SPDF;
import SPDF.element.ElementAbstract;
import SPDF.element.ElementException;
import SPDF.element.ElementFactory.ElementType;
import SPDF.utils.PaintProps;

public class text extends ElementAbstract {

    String text;
    String[] lines;
    public PDType0Font font;
    float font_h, font_w, text_w;

    public text(JSONObject props, ElementAbstract parent, SPDF pdf) throws ElementException {
        super(props, parent, ElementType.text, pdf);
    }

    public void instaceFont() {
        try {
            double correccion = 1;
            // this.font = new PDType1Font(FontName.HELVETICA);
            // this.font = new PDType1Font(FontName.TIMES_ROMAN);
            // Font fonta = Font.createFont(Font.TRUETYPE_FONT, );
            this.font = PDType0Font.load(this.pdf.document, new File("font/Helvetica/Helvetica.ttf"));
            // this.font = PDType0Font.load(this.pdf.document, new
            // File("font/NotoEmoji/NotoEmoji.ttf"));
            // this.font = new
            // this.font = new PDType1Font(FontName.COURIER_BOLD);

            // System.out.println(this.font.getAverageFontWidth());
            this.font_h = (font.getFontDescriptor().getCapHeight()) / 1000 * (float) (this.style.fontSize * correccion);
            this.text_w = (((font.getStringWidth(this.text)) / 1000) + ((1000 - this.font.getSpaceWidth()) / 1000))
                    * (float) (this.style.fontSize * correccion);
            this.font_w = this.text_w / this.text.length();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getLines(String txt, float width) {
        return txt.split("(?<=\\G.{" + (int) (width / this.font_w) + "})");
    }

    @Override
    public void validations() throws ElementException {
    }

    @Override
    public void instanceChildrens() throws ElementException {
        this.text = this.props.get("childrens").toString();

        this.instaceFont();
        // if()
        if (this.style.width > 0) {
            this.lines = this.getLines(this.text, this.style.getContentWidth());
        } else {
            this.lines = new String[] { this.text };
            this.style.width = this.text_w;
        }
        if (this.style.height > 0) {
            return;
        }
        this.style.height = ((this.font_h + 4) * (this.lines.length)) + this.style.paddingBottom
                + this.style.paddingTop + this.style.marginBottom + this.style.marginTop;
    }

    @Override
    public boolean paint(PaintProps props) throws IOException, ElementException {
        // if(painted==true) return;

        String text2 = this.text + "";
        String regex = "\\$\\{([a-zA-Z0-9_]+)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text2);
        List<String> valNames = new ArrayList<>();
        boolean hay_cambios = false;

        while (matcher.find()) {
            valNames.add(matcher.group(1));
            if (matcher.group(1).equals("current_page")) {
                text2 = this.text.replaceAll("\\$\\{current_page\\}", props.current_page + "");
                hay_cambios = true;
            }
        }

        if (hay_cambios) {
            this.text_w = (((font.getStringWidth(text2)) / 1000) + ((1000 - this.font.getSpaceWidth()) / 1000))
                    * (float) (this.style.fontSize * 1);
            if (this.lines.length > 1) {
                this.lines = this.getLines(text2, this.style.getContentWidth());
            } else {
                this.lines = new String[] { text2 };
                this.style.width = this.text_w;
            }

            // this.style.width = this.text_w;
        }

        if (!super.paint(props)) {
            return false;
        }

        float y = props.current_y + this.style.height - this.font_h;
        y -= 2;
        for (String line : lines) {

            props.stream.beginText();
            props.stream.setFont(this.font, this.style.fontSize);
            props.stream.setNonStrokingColor(Color.decode(this.style.color));
            props.stream.newLineAtOffset(props.current_x + this.style.marginRight + this.style.paddingRight, y);
            props.stream.showText(line.trim());
            props.stream.endText();
            y -= this.font_h + 4;
        }
        return true;
    }

}