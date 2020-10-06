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
                to = buildRedirectName(item);
            }
        }
        return to.toString();
    }

    public StringBuilder buildRedirectName(JsonElement item) {
        StringBuilder to = new StringBuilder();
        String itemString = item.toString();
        itemString = tidyRedirectResults(itemString);
        int commaLocation = itemString.indexOf(",");
        for(int i = 0; i < itemString.length(); i++) {
            if (i > commaLocation){
                to.append(itemString.charAt(i));
            }
        }
        return tidyToString(to);
    }

    public String tidyRedirectResults(String itemString) {
        itemString = itemString.replace("{", "");
        itemString = itemString.replace("}", "");
        itemString = itemString.replace("from", "");
        itemString = itemString.replace("to", "");
        return itemString;
    }

    public StringBuilder tidyToString(StringBuilder to) {
        to = new StringBuilder(to.toString().replace("\"", ""));
        to = new StringBuilder(to.toString().replace(":", ""));
        return to;
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
