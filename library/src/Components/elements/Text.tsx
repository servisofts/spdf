import { PDFElementType } from "../type";

export default (props: PDFElementType) => {
    if (!props) return;

    let txt = "";
    if (props.children) {
        if (Array.isArray(props.children)) {
            props.children.map(a => {
                txt += "" + a;
            })
        } else {
            txt = props.children
        }

    }
    let data: any = {
        "type": "text",
        "childrens": txt
    }
    if (props.style) {
        data.style = props.style;
    }
    return data;
}