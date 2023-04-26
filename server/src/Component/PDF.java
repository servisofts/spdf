package Component;

import java.io.File;

import org.json.JSONObject;

import SPDF.SPDF;
import Server.SSSAbstract.SSSessionAbstract;
import Servisofts.SConfig;

public class pdf {
    public static final String COMPONENT = "pdf";

    public static void onMessage(JSONObject obj, SSSessionAbstract session) {
        switch (obj.getString("type")) {
            case "registro":
                registro(obj, session);
                break;
        }
    }

    public static void registro(JSONObject obj, SSSessionAbstract session) {
        try {
            JSONObject data = obj.getJSONObject("data");
            SPDF pdf1 = new SPDF(data);

            File file = new File(SConfig.getJSON("files").getString("url") + "pdf");
            if (!file.exists()) {
                file.mkdirs();
            }
            pdf1.saveFile(SConfig.getJSON("files").getString("url") + "pdf/nombre.pdf");
            obj.put("data", "pdf/nombre.pdf");
            obj.put("estado", "exito");
        } catch (Exception e) {
            obj.put("estado", "error");
            obj.put("error", e.getLocalizedMessage());

            e.printStackTrace();
        }
    }

}
