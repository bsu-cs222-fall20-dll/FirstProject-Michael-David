package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class ListReceiver {

    public List<String> createUserList(JsonArray array) {
        List<String> userArray = new ArrayList<>();
        for(JsonElement item: array) {
            String user = "";
            String uglyUserString = item.toString();
            uglyUserString = uglyUserString.replace("{","");
            uglyUserString = uglyUserString.replace("}","");
            uglyUserString = uglyUserString.replace("\"","");
            uglyUserString = uglyUserString.replace("user:","");
            if(uglyUserString.contains("anon")) {
                user = "Anonymous";
            } else {
                int commaLocation = uglyUserString.indexOf(",");

                for(int i = 0; i < uglyUserString.length(); i++) {
                    if (i < commaLocation) {
                         user = user + uglyUserString.charAt(i);
                    }
                }
            }
            userArray.add(user);
        }
        return userArray;
    }

    public List<String> createTimezoneList(JsonArray array) {
        List<String> timezoneArray = new ArrayList<>();
        for(JsonElement item: array) {
            String timezone = "";
            String uglyUserString = item.toString();
            uglyUserString = uglyUserString.replace("{","");
            uglyUserString = uglyUserString.replace("}","");
            uglyUserString = uglyUserString.replace("\"","");
            uglyUserString = uglyUserString.replace("timestamp:","");
            uglyUserString = uglyUserString.replace("Z","");
            uglyUserString = uglyUserString.replace(",anon:", "");
                int commaLocation = uglyUserString.indexOf(",");
                for(int i = commaLocation; i < uglyUserString.length(); i++) {
                    if (i > commaLocation + 11) {
                        timezone = timezone + uglyUserString.charAt(i);
                    }
                }
            timezoneArray.add(timezone);
        }
        return timezoneArray;
    }

    public List<String> adjustTimezone(List<String> timezoneList) {
        List<String> adjustedTimezone = new ArrayList<>();
        String finalString = "";
        for(String time: timezoneList) {
            String temporaryString = time.substring(0,2);
            int timeInt = Integer.parseInt(temporaryString);
            timeInt -=4;
            if(timeInt < 0) {
                timeInt += 24;
            }
            if(timeInt < 10) {
                finalString = "0" + timeInt + time.substring(2);
            } else {
                finalString = timeInt + time.substring(2);
            }
            adjustedTimezone.add(finalString);
        }
        return adjustedTimezone;
    }

    public List<String> createDateList(JsonArray array) {
        List<String> dateArray = new ArrayList<>();
        for(JsonElement item: array) {
            String date = "";
            String uglyUserString = item.toString();
            uglyUserString = uglyUserString.replace("{","");
            uglyUserString = uglyUserString.replace("}","");
            uglyUserString = uglyUserString.replace("\"","");
            uglyUserString = uglyUserString.replace("timestamp:","");
            uglyUserString = uglyUserString.replace("-","/");
            uglyUserString = uglyUserString.replace(",anon:", "");
            int commaLocation = uglyUserString.indexOf(",");
            for(int i = commaLocation + 1; i < commaLocation + 11; i++) {
                date = date + uglyUserString.charAt(i);
            }
            dateArray.add(date);
        }
        return dateArray;
    }
}
