import { SReducer } from "servisofts-model";

export default class Reducer extends SReducer {

    getAllHabilitados(state: any, action: any): void {
        if (action.estado == "exito") {
            state.data = {};
            action.data.map((item) => {
                state.data[item.key] = item;
            })
        }
    }

}