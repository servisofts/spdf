import { SAction } from "servisofts-model";
import SSocket from 'servisofts-socket'
export default class Action extends SAction {

    getAllHabilitados({ api, key_servicio }) {
        var reducer = this._getReducer();
        var data = reducer.data;
        if(!api) return null;
        if (!data) {
            if (reducer.estado == "cargando") return null;
            SSocket.sendHttp(api, {
                component: "servicio",
                type: "getAllHabilitados",
                estado: "cargando",
                key_servicio: key_servicio,
            })
            return null;
        }
        return data;
    }
}