package com.example.javaminiproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegionalMoreInfoController implements Initializable {
    protected int id;
    protected RegionalInfo regionalInfo;

    @FXML
    private Label titleLabel;

    @FXML
    private Label desLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private Pane top_pane;

    @FXML
    private ImageView MainImage;

    @FXML
    private Pane weatherData;

    @FXML
    private ImageView weatherIcon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization logic can be added here if needed
        Button back_button = new Button("Back");
        back_button.setLayoutX(20);
        back_button.setLayoutY(14);
        back_button.getStyleClass().add("back-button");
        top_pane.getChildren().add(back_button);

        if (regionalInfo != null) {
            titleLabel.setText(regionalInfo.getName());
            desLabel.setText(regionalInfo.getDescription());
            String imageUrl = getClass().getResource(regionalInfo.getImage_url()).toString();
            Image image = new Image(imageUrl, true);
            MainImage.setImage(image);
            MainImage.setPreserveRatio(false);
            MainImage.setSmooth(true);
            MainImage.setFitWidth(413);
            MainImage.setFitHeight(257);
            DisplayWeather(regionalInfo.getDistrict());
            ratingLabel.setText("🏊‍♂️"+ regionalInfo.getType() +"   ⭐ " + regionalInfo.getRating());
        } else {
            titleLabel.setText("Regional Information");
        }

        back_button.setOnAction((event) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegionalInfo.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SeaExplorer.scene.setRoot(root);
        });
    }

    @FXML
    private void DisplayWeather(String location) {
        FetchWeatherInfo fetchWeatherInfo = new FetchWeatherInfo();
        WeatherInfo weatherInfo = fetchWeatherInfo.getCurrentWeatherInfo(location);

        DisplayLabel("temp", weatherInfo.getTemp() + "°C", weatherData, 200, 30);
        DisplayLabel("description", weatherInfo.getDescription(), weatherData, 200, 60);
        DisplayLabel("wind", "Wind: " + weatherInfo.getWindSpeed() + " km/h", weatherData, 200, 90);
        DisplayLabel("humidity", "Humidity: " + weatherInfo.getHumidity() + "%", weatherData, 200, 120);
        String imageUrl = "https://openweathermap.org/img/wn/" + weatherInfo.getIconName() + "@4x.png";

        Image image = new Image(imageUrl, true);
        weatherIcon.setImage(image);
    }

    @FXML
    private void DisplayLabel(String id, String text, Pane pane, int layoutX, int layoutY) { // create label and display
        Label label = new Label(text);
        label.setId(id);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);

        pane.getChildren().add(label);
    }

    @FXML
    public void onHomeButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }

    @FXML
    public void onRegionalInfoButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegionalInfo.fxml"));
        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }

    @FXML
    private void onWeatherButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Weather.fxml"));
        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }

    @FXML
    public void onMapButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Map.fxml"));
        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }

    @FXML
    private void onViewMapClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Map.fxml"));

        loader.setControllerFactory(param -> {
            MapController controller = new MapController();
            controller.location = regionalInfo.getLocation();
            return controller;
        });

        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }

    @FXML
    private void onWeekWeatherClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Weather.fxml"));

        loader.setControllerFactory(param -> {
            WeatherController controller = new WeatherController();
            controller.location = regionalInfo.getDistrict();
            return controller;
        });

        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }
}
