import { SModel } from "servisofts-model";
import spdf from "./spdf";
import servicio from "./servicio";
const Model = {
    ...spdf,
    ...servicio
}
export default {
    ...Model,
    ...SModel.declare(Model)
}