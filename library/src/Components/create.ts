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
            window.open(url + e.data, "NombreVentana", "height=612,width=900");
        }).catch(e => {
            reject(e);
            console.error(e);
        })
    })

}