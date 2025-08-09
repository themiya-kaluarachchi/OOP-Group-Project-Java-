package com.example.javaminiproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class WeatherController implements Initializable {
    protected String location = "Sri Lanka"; // default location

    @FXML
    private Label city;

    @FXML
    private Label todayTemp;

    @FXML
    private Label todayWeather;

    @FXML
    private Label todayDateTime;

    @FXML
    private Label todayWind;

    @FXML
    private Label todayHumidity;

    @FXML
    private ImageView todayWeatherImg;

    @FXML
    private HBox weather7DaysHBox;

    @FXML
    private Pane weather_pane;

    @FXML
    private Pane top_pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //get current date and time
        LocalDateTime now = LocalDateTime.now();


        //fetch defualt weather info for Sri Lanka
        FetchWeatherInfo fetchWeatherInfo = new FetchWeatherInfo();

        DisplayWeather(fetchWeatherInfo, now, this.location);

        TextField search_field = new TextField();
        search_field.setPromptText("Search");
        search_field.setLayoutX(373);
        search_field.setLayoutY(14);
        search_field.getStyleClass().add("serach-field");
        top_pane.getChildren().add(search_field);

        Button back_button = new Button("Back");
        back_button.setLayoutX(20);
        back_button.setLayoutY(14);
        back_button.getStyleClass().add("back-button");
        top_pane.getChildren().add(back_button);

        URL search_url = getClass().getResource("images/weather/search.png");

        try {
            DisplayImage(search_url.toString(), weather_pane, 25, 25, 378, 22);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        search_field.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String text = search_field.getText();
                DisplayWeather(fetchWeatherInfo, now, text);
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

    private void DisplayWeather(FetchWeatherInfo fetchWeatherInfo, LocalDateTime now, String location) {
        String[] words = location.split(" ");
        String word = String.join("%20", words); // replace spaces with %20 for URL encoding
        WeatherInfo[] weatherInfo = fetchWeatherInfo.getWeekWeather(word);
        DateTimeFormatter fromatDate = DateTimeFormatter.ofPattern("E, MMM dd, HH:mm a");

        city.setText(location.toUpperCase());
        todayTemp.setText(weatherInfo[0].getTemp() + " °c");
        todayWeather.setText(Character.toUpperCase(weatherInfo[0].getDescription().charAt(0)) + weatherInfo[0].getDescription().substring(1));
        todayDateTime.setText(now.format(fromatDate));
        todayWind.setText(weatherInfo[0].getWindSpeed() + " km/h");
        todayHumidity.setText("Humiditiy: " + weatherInfo[0].getHumidity() + " %");

        String imageUrl = "https://openweathermap.org/img/wn/" + weatherInfo[0].getIconName() + "@4x.png";
        Image image = new Image(imageUrl, true);
        todayWeatherImg.setImage(image);

        // Display weather for the next 7 days
        weather7DaysHBox.getChildren().clear();
        DisplayWeekPane(weather7DaysHBox, now.toLocalDate(), 1,weatherInfo[1]);
        DisplayWeekPane(weather7DaysHBox, now.toLocalDate(), 2,weatherInfo[2]);
        DisplayWeekPane(weather7DaysHBox, now.toLocalDate(), 3,weatherInfo[3]);
        DisplayWeekPane(weather7DaysHBox, now.toLocalDate(), 4,weatherInfo[4]);
        DisplayWeekPane(weather7DaysHBox, now.toLocalDate(), 5,weatherInfo[5]);
        DisplayWeekPane(weather7DaysHBox, now.toLocalDate(), 6,weatherInfo[6]);
    }

    @FXML
    private void DisplayWeekPane(HBox hbox, LocalDate date, int incDays, WeatherInfo weatherInfo) {
        Pane pane = new Pane();
        pane.getStyleClass().add("weather-card");
        //pane.setId(id);
        DisplayLabel("day-label", date.plusDays(incDays).format(DateTimeFormatter.ofPattern("E, MMM dd")), pane, 10, 20);

        String imageUrl = "https://openweathermap.org/img/wn/" + weatherInfo.getIconName() + "@4x.png";
        try {
            DisplayImage(imageUrl, pane, 150, 150, 0, 50);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DisplayLabel("temp-label", weatherInfo.getTemp() + " °c", pane, 10, 170);
        DisplayLabel("description-label", weatherInfo.getMain(), pane, 10, 190);
        hbox.getChildren().add(pane);
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
    private void DisplayLabel(String id, String text, Pane pane, int layoutX, int layoutY) { // create label and display
        Label label = new Label(text);
        label.getStyleClass().add(id);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);

        pane.getChildren().add(label);
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
    private void onMapButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Map.fxml"));
        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }

    @FXML
    private void onBackButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();
        SeaExplorer.scene.setRoot(root);
    }
}
