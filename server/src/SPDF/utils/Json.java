package SPDF.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

public class Json {

    public static JSONObject path_to_json(String json_path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(json_path));
        String jsonString = "";
        String line;
        while ((line = reader.readLine()) != null) {
            jsonString += line;
        }
        reader.close();
        return new JSONObject(jsonString);
    }
}
