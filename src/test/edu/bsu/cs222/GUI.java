package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        VBox parent = new VBox();
        parent.getChildren().add(new Label("Wikipedia Searcher"));
        HBox urlArea = new HBox(new Label("URL"));
        TextField textField = new TextField();
        TextField outPutField = new TextField();
        urlArea.getChildren().add(textField);
        urlArea.getChildren().add(outPutField);
        parent.getChildren().add(urlArea);
        Button mostEditsButton = new Button("Search most edits");
        Button recentEditsButton = new Button("Search recent edits");
        mostEditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parser parser = new Parser();
                WebpageSearcher webpageSearcher = new WebpageSearcher();
                ListReceiver listReceiver = new ListReceiver();
                ListDisplayer listDisplayer = new ListDisplayer();

                String result = webpageSearcher.search(textField.getText());
                URL wikiURL = null;
                try {
                    wikiURL = new URL(result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
                if (wikipediaConnect == null) {
                    System.out.println("Cannot connect to the internet. Try again.");
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
                        System.out.println("Page does not exist!");
                    } else {
                        String redirectOutput = parser.redirectResults(wikiObject);
                        parser.isRedirected(textField.getText(), redirectOutput);


                        List<String> userList = listReceiver.createUserList(revisionsArray);
                        List<String> timezones = listReceiver.createTimezoneList(revisionsArray);
                        List<String> adjustedTimezones = listReceiver.adjustTimezone(timezones);
                        List<String> dates = listReceiver.createDateList(revisionsArray);
                        listDisplayer.displayListByMostEdits(userList);
                    }
                }
            }
        });
        recentEditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parser parser = new Parser();
                WebpageSearcher webpageSearcher = new WebpageSearcher();
                ListReceiver listReceiver = new ListReceiver();
                ListDisplayer listDisplayer = new ListDisplayer();

                String result = webpageSearcher.search(textField.getText());
                URL wikiURL = null;
                try {
                    wikiURL = new URL(result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
                if (wikipediaConnect == null) {
                    System.out.println("Cannot connect to the internet. Try again.");
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
                        System.out.println("Page does not exist!");
                    } else {
                        String redirectOutput = parser.redirectResults(wikiObject);
                        parser.isRedirected(textField.getText(), redirectOutput);


                        List<String> userList = listReceiver.createUserList(revisionsArray);
                        List<String> timezones = listReceiver.createTimezoneList(revisionsArray);
                        List<String> adjustedTimezones = listReceiver.adjustTimezone(timezones);
                        List<String> dates = listReceiver.createDateList(revisionsArray);
                        listDisplayer.displayListByTime(userList, dates, adjustedTimezones);
                    }
                }
            }

        });
        parent.getChildren().add(recentEditsButton);
        parent.getChildren().add(mostEditsButton);


        primaryStage.setScene(new Scene(parent));
        primaryStage.show();

            }
        }