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
    if(props.footer){
        data.footer = props.footer
    }
    if(props.header){
        data.header = props.header
    }
    if (props.style) {
        data.style = props.style;
    }
    return data;
}