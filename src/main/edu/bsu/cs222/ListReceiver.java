package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class ListReceiver {

    public List<String> createUserList(JsonArray array) {
        List<String> userArray = new ArrayList<>();
        for(JsonElement item: array) {
            String uglyUserString = item.toString();
            uglyUserString = tidyUserList(uglyUserString);
            StringBuilder user = createUser(uglyUserString);

            userArray.add(user.toString());
        }
        return userArray;
    }

    public StringBuilder createUser(String uglyUserString) {
        StringBuilder user = new StringBuilder();
        if(uglyUserString.contains("anon")) {
            user = new StringBuilder("Anonymous");
        } else {
            int commaLocation = uglyUserString.indexOf(",");

            for(int i = 0; i < uglyUserString.length(); i++) {
                if (i < commaLocation) {
                    user.append(uglyUserString.charAt(i));
                }
            }
        }
        return user;
    }

    public String tidyUserList(String uglyUserString) {
        uglyUserString = uglyUserString.replace("{","");
        uglyUserString = uglyUserString.replace("}","");
        uglyUserString = uglyUserString.replace("\"","");
        uglyUserString = uglyUserString.replace("user:","");
        return uglyUserString;
    }

    public List<String> createTimezoneList(JsonArray array) {
        List<String> timezoneArray = new ArrayList<>();
        for(JsonElement item: array) {
            StringBuilder timezone = new StringBuilder();
            String uglyUserString = item.toString();
            uglyUserString = tidyTimezoneList(uglyUserString);

            int commaLocation = uglyUserString.indexOf(",");
            for(int i = commaLocation; i < uglyUserString.length(); i++) {
                if (i > commaLocation + 11) {
                    timezone.append(uglyUserString.charAt(i));
                }
            }
            timezoneArray.add(timezone.toString());
        }
        return timezoneArray;
    }

    public String tidyTimezoneList(String uglyUserString) {
        uglyUserString = uglyUserString.replace("{","");
        uglyUserString = uglyUserString.replace("}","");
        uglyUserString = uglyUserString.replace("\"","");
        uglyUserString = uglyUserString.replace("timestamp:","");
        uglyUserString = uglyUserString.replace("Z","");
        uglyUserString = uglyUserString.replace(",anon:", "");
        return uglyUserString;
    }

    public List<String> adjustTimezone(List<String> timezoneList) {
        List<String> adjustedTimezone = new ArrayList<>();
        String finalString;
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
            StringBuilder date = new StringBuilder();
            String uglyUserString = item.toString();

            uglyUserString = tidyDate(uglyUserString);

            int commaLocation = uglyUserString.indexOf(",");
            for(int i = commaLocation + 1; i < commaLocation + 11; i++) {
                date.append(uglyUserString.charAt(i));
            }
            dateArray.add(date.toString());
        }
        return dateArray;
    }

    public String tidyDate(String uglyUserString) {
        uglyUserString = uglyUserString.replace("{","");
        uglyUserString = uglyUserString.replace("}","");
        uglyUserString = uglyUserString.replace("\"","");
        uglyUserString = uglyUserString.replace("timestamp:","");
        uglyUserString = uglyUserString.replace("-","/");
        uglyUserString = uglyUserString.replace(",anon:", "");
        return uglyUserString;
    }
}
