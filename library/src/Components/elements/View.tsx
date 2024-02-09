import { PDFElementType } from "../type";
import { toJson } from "..";

export default (props: PDFElementType) => {
    if (!props) return;

    // let childrens = [];
    // if (props.children) {
    //     if (Array.isArray(props.children)) {
    //         props.children.map(a => {
    //             let sf = toJson(a);
    //             if (sf) {
    //                 childrens.push(sf)
    //             }
    //         })
    //     } else {
    //         let sf = toJson(props.children);
    //         if (sf) {
    //             childrens.push(sf)
    //         }
    //     }

    // }
    let data: any = {
        "type": "div"
    }
    if (props.style) {
        data.style = props.style;
    }
    return data;
}