package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SearchResults {

    public void buildResults(TextFlow listOutPut, TextFlow output, TextField textField, int action) {
        listOutPut.getChildren().clear();
        output.getChildren().clear();

        URL wikiURL = buildURL(textField);
        createOutput(listOutPut, output, textField, action, wikiURL);
    }

    public void createOutput(TextFlow listOutPut, TextFlow output, TextField textField, int action, URL wikiURL) {
        WebpageSearcher webpageSearcher = new WebpageSearcher();

        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        if (wikipediaConnect == null) {
            Text cannotConnect = new Text("Cannot connect to the internet. Try again.");
            output.getChildren().add(cannotConnect);
        } else {
            InputStream inputStream = generateWikipediaStream(wikipediaConnect);
            parseAndDisplay(listOutPut, output, textField, action, inputStream);
        }
    }

    public void parseAndDisplay(TextFlow listOutPut, TextFlow output, TextField textField, int action, InputStream inputStream) {
        Parser parser = new Parser();

        JsonObject wikiObject = parser.parse(inputStream);
        boolean nullity = parser.isNull(wikiObject);
        if (nullity) {
            Text pageDoesNotExist = new Text("Page does not exist!");
            output.getChildren().add(pageDoesNotExist);
        } else {
            JsonArray revisionsArray = parser.getRevisionsList(wikiObject);
            generateRedirectStream(output, wikiObject, textField);
            generateOutputStream(listOutPut, revisionsArray,action);
        }
    }

    public void generateOutputStream(TextFlow listOutPut, JsonArray revisionsArray, int action) {
        ListDisplay listDisplay = new ListDisplay();
        ListReceiver listReceiver = new ListReceiver();

        List<String> userList = listReceiver.createUserList(revisionsArray);
        List<String> timezones = listReceiver.createTimezoneList(revisionsArray);
        List<String> adjustedTimezones = listReceiver.adjustTimezone(timezones);
        List<String> dates = listReceiver.createDateList(revisionsArray);

        if (action == 2) {
            listOutPut.getChildren().add(listDisplay.displayListByTime(userList, dates, adjustedTimezones));
        }
        if (action == 1) {
            listOutPut.getChildren().add(listDisplay.displayListByMostEdits(userList));
        }
    }

    public void generateRedirectStream(TextFlow output, JsonObject wikiObject, TextField textField) {
        Parser parser = new Parser();
        String redirectOutput = parser.redirectResults(wikiObject);
        if (parser.isRedirected(textField.getText(), redirectOutput)) {
            output.getChildren().add(new Text("Your search has been redirected from " + textField.getText() + " to " + redirectOutput + "."));
        }
    }

    public InputStream generateWikipediaStream(HttpURLConnection wikipediaConnect) {
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        InputStream inputStream = null;
        try {
            inputStream = webpageSearcher.getPageStream(wikipediaConnect);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public URL buildURL(TextField textField) {
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String result = webpageSearcher.search(textField.getText());
        URL wikiURL = null;
        try {
            wikiURL = new URL(result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return wikiURL;
    }
}
