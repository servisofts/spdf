// import { ColumnType, Model } from "servisofts-model";

type ParamsType = {
    separador: string
}
export var PARAMS: ParamsType = {
    separador: "-"
}

export const init = (props: ParamsType) => {
    // if (!Model.usuario) {
    //     console.error("Contabilidad required Model.usuario;")
    //     return;
    // }
    // if (!Model.roles_permisos) {
    //     console.error("Contabilidad  required Model.roles_permisos;")
    //     return;
    // }
    // if (!Model.empresa) {
    //     console.error("Contabilidad  required Model.roles_permisos;")
    //     return;
    // }
    PARAMS = { ...PARAMS, ...props }
    console.log(PARAMS)
}

