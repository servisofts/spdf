package Component;

import org.json.JSONArray;
import org.json.JSONObject;
import Servisofts.SPGConect;
import Servisofts.SUtil;
import Server.SSSAbstract.SSSessionAbstract;

public class CompraVentaComentario {
    public static final String COMPONENT = "compra_venta_comentario";

    public static void onMessage(JSONObject obj, SSSessionAbstract session) {
        switch (obj.getString("type")) {
            case "getAll":
                getAll(obj, session);
                break;
            case "getByKey":
                getByKey(obj, session);
                break;
            case "registro":
                registro(obj, session);
                break;
            case "editar":
                editar(obj, session);
                break;
        }
    }

    public static void getAll(JSONObject obj, SSSessionAbstract session) {
        try {
            String consulta = "select get_all('" + COMPONENT + "', 'key_compra_venta', '"+obj.getString("key_compra_venta")+"') as json";
            JSONObject data = SPGConect.ejecutarConsultaObject(consulta);
            obj.put("data", data);
            obj.put("estado", "exito");
        } catch (Exception e) {
            obj.put("estado", "error");
            obj.put("error", e.getMessage());
            e.printStackTrace();
        }
    }

    
    
    public static void getByKey(JSONObject obj, SSSessionAbstract session) {
        try {
            String consulta = "select get_by_key('" + COMPONENT + "', '" + obj.getString("key") + "') as json";
            JSONObject data = SPGConect.ejecutarConsultaObject(consulta);
            obj.put("data", data);
            obj.put("estado", "exito");
        } catch (Exception e) {
            obj.put("estado", "error");
            obj.put("error", e.getMessage());
            e.printStackTrace();
        }
    }

    public static JSONObject getByKey(String key) {
        try {
            String consulta = "select get_by_key('" + COMPONENT + "', '" + key + "') as json";
            return SPGConect.ejecutarConsultaObject(consulta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void registro(JSONObject obj, SSSessionAbstract session) {
        try {
            JSONObject data = obj.getJSONObject("data");
            data.put("key", SUtil.uuid());
            data.put("estado", 1);
            data.put("fecha_on", SUtil.now());
            data.put("key_usuario", obj.getString("key_usuario"));

            SPGConect.insertArray(COMPONENT, new JSONArray().put(data));

            JSONObject compraVentaParticipantes = CompraVentaParticipante.getAll(data.getString("key_compra_venta"));
            JSONArray key_usuarios = new JSONArray();
            for (int i = 0; i < JSONObject.getNames(compraVentaParticipantes).length; i++) {
                key_usuarios.put(compraVentaParticipantes.getJSONObject(JSONObject.getNames(compraVentaParticipantes)[i]).getString("key_usuario_participante"));
            } 

            obj.put("sendUsers", key_usuarios);

            obj.put("data", data);
            obj.put("estado", "exito");
        } catch (Exception e) {
            obj.put("estado", "error");
            obj.put("error", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void editar(JSONObject obj, SSSessionAbstract session) {
        try {

            JSONObject aux = getByKey(obj.getJSONObject("data").getString("key"));
            aux.put("key_compra_venta", JSONObject.getNames(aux)[0]);
            aux.put("key", SUtil.uuid());
            aux.put("fecha_on", SUtil.now());
            SPGConect.insertArray(COMPONENT+"_historico", new JSONArray().put(aux));

            JSONObject data = obj.getJSONObject("data");
            SPGConect.editObject(COMPONENT, data);

            obj.put("data", data);
            obj.put("estado", "exito");
            
            JSONObject compraVentaParticipantes = CompraVentaParticipante.getAll(JSONObject.getNames(aux)[0]);
            JSONArray key_usuarios = new JSONArray();
            for (int i = 0; i < JSONObject.getNames(compraVentaParticipantes).length; i++) {
                key_usuarios.put(compraVentaParticipantes.getJSONObject(JSONObject.getNames(compraVentaParticipantes)[i]).getString("key_usuario_participante"));
            } 

            obj.put("sendUsers", key_usuarios);


        } catch (Exception e) {
            obj.put("estado", "error");
            obj.put("error", e.getMessage());
            e.printStackTrace();
        }
    }


}
