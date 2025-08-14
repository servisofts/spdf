import { PagePropsType } from "./elements/Page";
import { Linking } from 'react-native'
import toJson from "./toJson";
import SSocket from 'servisofts-socket'


export default (page: PagePropsType) => {
    return new Promise((resolve, reject) => {
        const json = toJson(page)
        let url = SSocket.api.spdf
        console.log(json)
        SSocket.sendHttpAsync(url + "api", {
            "component": "pdf",
            "type": "registro",
            "data": json
        }).then(e => {
            resolve(e);
            // window.open(url + e.data, "NombreVentana", "height=612,width=900");
            const finalUrl = url + e.data;
            const newWindow = window.open(finalUrl, "NombreVentana", "height=612,width=900");

            // Si window.open fue bloqueado o no se abrió (Safari)
            if (!newWindow || newWindow.closed || typeof newWindow.closed === "undefined") {
                // Crear un link de descarga y simular clic
                const a = document.createElement("a");
                a.href = finalUrl;
                a.download = ""; // Nombre sugerido opcional
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            }
        }).catch(e => {
            reject(e);
            console.error(e);
        })
    })

}