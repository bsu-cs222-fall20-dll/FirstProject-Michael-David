package edu.bsu.cs222;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListDisplayer {
    public void displayListByTime(List<String> userList, List<String> dates, List<String> adjustedTimezones) {
        System.out.println("Time     Day        Modifier");
        System.out.println("============================");
        for(int i = 0; i < userList.size(); i++) {
            System.out.println(adjustedTimezones.get(i) + " " + dates.get(i) + " " + userList.get(i));
        }
    }

    public void displayListByMostEdits(List<String> userList) {
        System.out.println("# of Edits   Modifier");
        System.out.println("=====================");
        HashMap<String,Integer> editorHashMap = new HashMap<>(editorAndEdits(userList));
        for(int i = 0; i <= editorHashMap.size(); i++) {
            HashMap<String,Integer> mostEdits = maxValueLeft(editorHashMap);
            for(String key: mostEdits.keySet()) {
                System.out.println(editorHashMap.get(key) + "            " + key);
                editorHashMap.remove(key);
            }
        }


    }
    public HashMap<String, Integer> maxValueLeft(HashMap<String,Integer> hashMap) {
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        int value = maxEntry.getValue();
        String key = maxEntry.getKey();
        HashMap<String, Integer> trueMap = new HashMap<>();
        trueMap.put(key,value);
        return trueMap;
    }

    public HashMap<String,Integer> editorAndEdits(List<String> userList) {
        HashMap<String, Integer> editorAndEdits = new HashMap<>();
        for(String user: userList) {
            if (editorAndEdits.get(user) != null) {
                int value = editorAndEdits.get(user);
                value += 1;
                editorAndEdits.remove(user);
                editorAndEdits.put(user, value);
            }
            else {
                editorAndEdits.put(user,1);
            }
        }
        return editorAndEdits;
    }
}
