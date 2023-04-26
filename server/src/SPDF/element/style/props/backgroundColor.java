package SPDF.element.style.props;

import java.awt.Color;
import java.io.IOException;

import SPDF.element.style.ElementStyle;
import SPDF.element.style.PaintUtils;
import SPDF.element.style.StylePropAbstract;
import SPDF.utils.PaintProps;

public class backgroundColor extends StylePropAbstract<String> {

    public backgroundColor(ElementStyle style) {
        super("backgroundColor", style);
    }

    @Override
    public boolean paint(PaintProps props) throws IOException {
        if (this.value == null)
            return true;
        props.stream.setNonStrokingColor(Color.decode(this.value));
        props.stream.addRect(props.current_x + this.style.marginLeft,
                props.current_y - (this.style.height - (this.style.marginBottom)),
                this.style.width - (this.style.marginRight + style.marginLeft),
                this.style.height - (style.marginTop + style.marginBottom));
        props.stream.fill();
        props.stream.setNonStrokingColor(Color.decode("#000000"));
        return true;
    }

    @Override
    public void beforeLoadChildrens() {

    }

    @Override
    public void afterLoadChildrens() {
    }

}
