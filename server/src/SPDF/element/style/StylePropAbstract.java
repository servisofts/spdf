package SPDF.element.style;

import java.lang.reflect.Type;

import SPDF.utils.PaintProps;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public abstract class StylePropAbstract<T> {

    public T value;

    public String key;
    public ElementStyle style;

    public StylePropAbstract(String key, ElementStyle style) {
        this.key = key;
        this.style = style;
        this.loadProp();
    }

    public void loadProp() {
        if (!validateProp())
            return;
        this.value = (T) this.style.jsonStyle.get(this.key);
    }

    public boolean validateProp() {
        if (!this.style.jsonStyle.has(this.key) || this.style.jsonStyle.isNull(this.key)) {
            return false;
        }
        return true;
    }

    public abstract boolean paint(PaintProps props) throws IOException;

    public abstract void beforeLoadChildrens();

    public abstract void afterLoadChildrens();
}
