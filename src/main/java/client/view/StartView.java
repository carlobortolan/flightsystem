package client.view;

import com.example.eist22t02zweiundvierziger2022.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import model.FlightCollection;

import java.net.URL;
import java.util.HashMap;


public class StartView extends Canvas {
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 700;
    private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    private HashMap<String, Image> imageCache;



    public static Dimension2D getPreferredSize() {
        return DEFAULT_SIZE;
    }

    private FlightCollection flightCollection;

    public StartView(FlightCollection flightCollection) {
        this.flightCollection = flightCollection;
        setupImageCache();
        setupFlightView();
//      setupImage();
    }

    private void setupFlightView() {
        Dimension2D size = getPreferredSize();
        widthProperty().set(size.getWidth());
        heightProperty().set(size.getHeight());
    }

    private void setupImage() {
//        getGraphicsContext2D().drawImage(getImage("background.png"), 0, 0, getWidth(), getHeight());
        getGraphicsContext2D().drawImage(getImage("start.png"), 0, 0, 50, 50);
    }

    private void setupImageCache() {
        this.imageCache = new HashMap<>();
    }

    private Image getImage(String startImageFilePath) {
        return this.imageCache.computeIfAbsent(startImageFilePath, this::createImage);
    }
    private Image createImage(String startImageFilePath) {
        System.out.println("startImageFilePath = " + startImageFilePath);

        URL startImageUrl = getClass().getClassLoader().getResource(startImageFilePath);

        if (startImageUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
        return new Image(startImageUrl.toExternalForm());
    }

    public static void main(String[] args) {

    }
}