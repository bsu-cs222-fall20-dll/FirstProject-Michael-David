package edu.bsu.cs222;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

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

        SearchResults searchResults = new SearchResults();

        mostEditsButton.setOnAction(actionEvent -> {
            int action = 1;
            searchResults.buildResults(listOutPut, output, textField, action);
        });
        recentEditsButton.setOnAction(actionEvent -> {
            int action = 2;
            searchResults.buildResults(listOutPut, output, textField, action);
        });

        parent.getChildren().add(recentEditsButton);
        parent.getChildren().add(mostEditsButton);
        parent.getChildren().add(output);
        parent.getChildren().add(listOutPut);


        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}