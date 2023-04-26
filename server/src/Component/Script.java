package Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.json.JSONObject;

public class Script {
  public static void main(String[] args) {
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("JavaScript");

    try {
      engine.eval(Files.newBufferedReader(Paths.get("pdfs/text.js"), StandardCharsets.UTF_8));
      Invocable inv = (Invocable) engine;
      String a = inv.invokeFunction("index").toString();
      JSONObject obj = new JSONObject(a);
      System.out.println(obj);

    } catch (ScriptException e) {
      e.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }
}
