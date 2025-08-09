package com.example.javaminiproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

/**
 * @author Kivilak Chathuranga
 */


public class MapController implements Initializable {
    protected String location = "Sri+Lanka";
    @FXML
    private Pane map_dashboard;

    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        map_dashboard.setClip(null);
        final String[] search_location = new String[1];

        TextField search_field = new TextField();
        search_field.setPromptText("Search");
        search_field.setLayoutX(373);
        search_field.setLayoutY(14);
        search_field.getStyleClass().add("serach-field");
        map_dashboard.getChildren().add(search_field);

        Button back_button = new Button("Back");
        back_button.setLayoutX(20);
        back_button.setLayoutY(14);
        back_button.getStyleClass().add("back-button");
        map_dashboard.getChildren().add(back_button);

        URL search_url = getClass().getResource("images/weather/search.png");
        try {
            DisplayImage(search_url.toString(), map_dashboard, 25, 25, 384, 22);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("map-dashboard");
        WebView webView = getWebView(this.location);
        stackPane.getChildren().add(webView);

        stackPane.setLayoutX(0);
        stackPane.setLayoutY(67);
        map_dashboard.getChildren().add(stackPane);

        search_field.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search_location[0] = search_field.getText();
                stackPane.getChildren().clear();
                stackPane.getChildren().add(getWebView(search_location[0]));
            }
        });

        back_button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SeaExplorer.scene.setRoot(root);
        });
    }

    @NotNull
    private WebView getWebView(String location) {
        WebView webView = new WebView();
        webView.setPrefSize(1040, 701);
        WebEngine webEngine = webView.getEngine();
        String url = "https://www.google.com/maps/place/" + location + "/@7.8731,80.7718,7z/data=!3m1!4b1!4m6!3m5!1s0x3ae25c2f2f2f2f2f:0x3ae25c2f2f2f2f2f!8m2!3d7.8731!4d80.7718!16zL20vMDJtZzQ?entry=ttu";
        webEngine.load(url);
        webView.setLayoutX(0);
        webView.setLayoutY(0);
        return webView;
    }

    @FXML
    private void DisplayImage(String url, Pane pane, int width, int height, int layoutX, int layoutY) throws IOException {
        Image image = new Image(url, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        imageView.setLayoutX(layoutX);
        imageView.setLayoutY(layoutY);
        pane.getChildren().add(imageView);
    }

    @FXML
    private void onHomeButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }

    @FXML
    private void onRegionalInfoButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegionalInfo.fxml"));
        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }

    @FXML
    private void onWeatherButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Weather.fxml"));
        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }
}
