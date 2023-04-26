package SPDF.element;

import java.io.IOException;

import SPDF.utils.PaintProps;

public interface ElementInterface {
    public void validations() throws ElementException;

    public boolean paint(PaintProps props) throws IOException, ElementException;

    public void instanceChildrens() throws ElementException;
}
