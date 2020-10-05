package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
@SuppressWarnings("deprecation")
public class Parser {

    public JsonObject parse(InputStream input) {
        JsonParser parser = new JsonParser();
        Reader reader = new InputStreamReader(input);
        JsonElement rootElement = parser.parse(reader);
        return rootElement.getAsJsonObject();
    }

    public boolean isNull(JsonObject rootObject) {
        JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
        String itemString = pages.toString();
        return itemString.contains("missing");
    }

    public Boolean isRedirected(String from, String to) {
        if(from.equalsIgnoreCase(to)) {
            return false;
        } else return !to.equals("");
    }


    public String redirectResults(JsonObject rootObject) {
        JsonArray fromTo = rootObject.getAsJsonObject("query").getAsJsonArray("redirects");
        StringBuilder to = new StringBuilder();
        if(fromTo != null) {
            for(JsonElement item: fromTo) {
                String itemString = item.toString();
                itemString = itemString.replace("{", "");
                itemString = itemString.replace("}", "");
                itemString = itemString.replace("from", "");
                itemString = itemString.replace("to", "");
                int commaLocation = itemString.indexOf(",");
                for(int i = 0; i < itemString.length(); i++) {
                    if (i > commaLocation){
                        to.append(itemString.charAt(i));
                    }
                }
                to = new StringBuilder(to.toString().replace("\"", ""));
                to = new StringBuilder(to.toString().replace(":", ""));
            }
        }
        return to.toString();

    }


    public JsonArray getRevisionsList(JsonObject rootObject) {
        JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
        JsonArray array = null;
        for (Map.Entry<String,JsonElement> entry : pages.entrySet()) {
            JsonObject entryObject = entry.getValue().getAsJsonObject();
            array = entryObject.getAsJsonArray("revisions");
        }
        return array;
    }

}
