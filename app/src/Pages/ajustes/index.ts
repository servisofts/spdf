import { SPage } from "servisofts-component";

import root from "./root";
import dato from "./dato";
export default SPage.combinePages("ajustes",
    {
        "": root,
        ...dato

    }
)