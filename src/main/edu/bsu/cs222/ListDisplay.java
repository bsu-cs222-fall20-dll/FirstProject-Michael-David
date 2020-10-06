package edu.bsu.cs222;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListDisplay {

    public TextFlow displayListByTime(List<String> userList, List<String> dates, List<String> adjustedTimezones) {
        Text listByTimeHeader = new Text();
        listByTimeHeader.setText("Time       Day             Modifier\n" +
                "============================\n");
        Text listByTime = new Text();
        StringBuilder outputString = new StringBuilder();
        for (int i = 0; i < userList.size(); i++) {
            outputString.append(adjustedTimezones.get(i)).append(" ").append(dates.get(i)).append(" ").append(userList.get(i)).append("\n");
        }
        listByTime.setText(outputString.toString());
        return new TextFlow(listByTimeHeader, listByTime);
    }

    public TextFlow displayListByMostEdits(List<String> userList) {
        Text listByMostEditsHeader = new Text();
        listByMostEditsHeader.setText("# of Edits   Modifier\n" +
                "=====================\n");
        HashMap<String,Integer> editorHashMap = new HashMap<>(editorAndEdits(userList));
        Text listByMostEdits = new Text();
        StringBuilder outputString = new StringBuilder();
        for(int i = 0; i <= editorHashMap.size(); i++) {
            HashMap<String,Integer> mostEdits = maxValueLeft(editorHashMap);
            for(String key: mostEdits.keySet()) {
                outputString.append(editorHashMap.get(key)).append("                ").append(key).append("\n");
                editorHashMap.remove(key);
            }

        }
        listByMostEdits.setText(outputString.toString());
        return new TextFlow(listByMostEditsHeader, listByMostEdits);

    }
    public HashMap<String, Integer> maxValueLeft(HashMap<String,Integer> hashMap) {
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        assert maxEntry != null;
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
