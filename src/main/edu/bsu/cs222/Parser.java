package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class Parser {

    public JsonObject parse(InputStream input) {
        JsonParser parser = new JsonParser();
        Reader reader = new InputStreamReader(input);
        JsonElement rootElement = parser.parse(reader);
        return rootElement.getAsJsonObject();
    }

    public boolean isNull(JsonObject rootObject) {
        JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
        boolean nullity;
        String itemString = pages.toString();
        if (itemString.contains("missing")) {
            nullity = true;
        } else {
            nullity = false;
        }
        return nullity;
    }

    public Boolean isRedirected(String from, String to) {
        if(from.equalsIgnoreCase(to)) {
            return false;
        } else if(to.equals("")) {
            return false;
        } else {
            return true;
        }
    }


    public String redirectResults(JsonObject rootObject) {
        JsonArray fromTo = rootObject.getAsJsonObject("query").getAsJsonArray("redirects");
        String to = "";
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
                        to = to + itemString.charAt(i);
                    }
                }
                to = to.replace("\"","");
                to = to.replace(":","");
            }
        }
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
