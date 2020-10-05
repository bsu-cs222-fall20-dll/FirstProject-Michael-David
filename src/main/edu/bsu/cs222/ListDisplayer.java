package edu.bsu.cs222;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListDisplayer {

    public TextFlow displayListByTime(List<String> userList, List<String> dates, List<String> adjustedTimezones) {
        Text listByTimeHeader = new Text();
        listByTimeHeader.setText("Time       Day             Modifier\n" +
                "============================\n");
        Text listByTime = new Text();
        String outputString = "";
        for (int i = 0; i < userList.size(); i++) {
            outputString += (adjustedTimezones.get(i) + " " + dates.get(i) + " " + userList.get(i) + "\n");
        }
        listByTime.setText(outputString);
        TextFlow output = new TextFlow(listByTimeHeader, listByTime);
        return output;
    }

    public TextFlow displayListByMostEdits(List<String> userList) {
        Text listByMostEditsHeader = new Text();
        listByMostEditsHeader.setText("# of Edits   Modifier\n" +
                "=====================\n");
        HashMap<String,Integer> editorHashMap = new HashMap<>(editorAndEdits(userList));
        Text listByMostEdits = new Text();
        String outputString = "";
        for(int i = 0; i <= editorHashMap.size(); i++) {
            HashMap<String,Integer> mostEdits = maxValueLeft(editorHashMap);
            for(String key: mostEdits.keySet()) {
                outputString += (editorHashMap.get(key) + "                " + key + "\n");
                editorHashMap.remove(key);
            }

        }
        listByMostEdits.setText(outputString);
        TextFlow output = new TextFlow(listByMostEditsHeader, listByMostEdits);
        return output;

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
