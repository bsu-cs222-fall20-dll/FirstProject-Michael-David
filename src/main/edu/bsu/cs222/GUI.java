package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setHeight(800);
        primaryStage.setWidth(400);
        VBox parent = new VBox();
        parent.getChildren().add(new Label("Wikipedia Searcher"));
        HBox urlArea = new HBox(new Label("URL"));
        TextField textField = new TextField();
        urlArea.getChildren().add(textField);
        parent.getChildren().add(urlArea);
        TextFlow output = new TextFlow();
        TextFlow listOutPut = new TextFlow();
        Button mostEditsButton = new Button("Search most edits");
        Button recentEditsButton = new Button("Search recent edits");

        mostEditsButton.setOnAction(actionEvent -> {
            listOutPut.getChildren().clear();
            output.getChildren().clear();
            Parser parser = new Parser();
            WebpageSearcher webpageSearcher = new WebpageSearcher();
            ListReceiver listReceiver = new ListReceiver();
            ListDisplay listDisplay = new ListDisplay();

            String result = webpageSearcher.search(textField.getText());
            URL wikiURL = null;
            try {
                wikiURL = new URL(result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
            if (wikipediaConnect == null) {
                Text cannotConnect = new Text("Cannot connect to the internet. Try again.");
                output.getChildren().add(cannotConnect);
            } else {
                InputStream is = null;
                try {
                    is = webpageSearcher.getPageStream(wikipediaConnect);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonObject wikiObject = parser.parse(is);
                JsonArray revisionsArray = parser.getRevisionsList(wikiObject);

                boolean nullity = parser.isNull(wikiObject);
                if (nullity) {
                    Text pageDoesNotExist = new Text("Page does not exist!");
                    output.getChildren().add(pageDoesNotExist);
                } else {
                    String redirectOutput = parser.redirectResults(wikiObject);
                    if (parser.isRedirected(textField.getText(), redirectOutput)){
                        output.getChildren().add(new Text("Your search has been redirected from " + textField.getText()+ " to " + redirectOutput + "."));

                    }


                    List<String> userList = listReceiver.createUserList(revisionsArray);
                    listOutPut.getChildren().add(listDisplay.displayListByMostEdits(userList));
                }
            }
        });
        recentEditsButton.setOnAction(actionEvent -> {
            listOutPut.getChildren().clear();
            output.getChildren().clear();
            Parser parser = new Parser();
            WebpageSearcher webpageSearcher = new WebpageSearcher();
            ListReceiver listReceiver = new ListReceiver();
            ListDisplay listDisplay = new ListDisplay();

            String result = webpageSearcher.search(textField.getText());
            URL wikiURL = null;
            try {
                wikiURL = new URL(result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
            if (wikipediaConnect == null) {
                Text cannotConnect = new Text("Cannot connect to the internet. Try again.");
                output.getChildren().add(cannotConnect);
            } else {
                InputStream is = null;
                try {
                    is = webpageSearcher.getPageStream(wikipediaConnect);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonObject wikiObject = parser.parse(is);
                JsonArray revisionsArray = parser.getRevisionsList(wikiObject);

                boolean nullity = parser.isNull(wikiObject);
                if (nullity) {
                    Text pageDoestNotExist = new Text("Page does not exist!");
                    output.getChildren().add(pageDoestNotExist);
                } else {
                    String redirectOutput = parser.redirectResults(wikiObject);
                    if (parser.isRedirected(textField.getText(), redirectOutput)){
                        output.getChildren().add(new Text("Your search has been redirected from " + textField.getText()+ " to " + redirectOutput + "."));

                    }


                    List<String> userList = listReceiver.createUserList(revisionsArray);
                    List<String> timezones = listReceiver.createTimezoneList(revisionsArray);
                    List<String> adjustedTimezones = listReceiver.adjustTimezone(timezones);
                    List<String> dates = listReceiver.createDateList(revisionsArray);
                    listOutPut.getChildren().add(listDisplay.displayListByTime(userList, dates, adjustedTimezones));
                }
            }
        });
        parent.getChildren().add(recentEditsButton);
        parent.getChildren().add(mostEditsButton);
        parent.getChildren().add(output);
        parent.getChildren().add(listOutPut);


        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}