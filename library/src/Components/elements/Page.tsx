import { PDFElementType } from "../type";
import { toJson } from "..";

export type PagePropsType = {
    header: any,
    footer: any
} & PDFElementType

export default (props: PagePropsType) => {
    if (!props) return;

    let childrens = [];
    let data: any = {
        "type": "page",
    }
    if (props.style) {
        data.style = props.style;
    }
    return data;
}