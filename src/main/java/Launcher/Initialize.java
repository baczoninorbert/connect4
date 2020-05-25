package Launcher;

import Controller.Contoller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Grid;
import view.View;

public class Initialize extends Application {
    private static final int canvasHeight = 600;
    private static final int canvasWidth = 700;
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        View view = new View();
        Grid grid = new Grid();
        Contoller contoller = new Contoller();
        view.createMenu();
    }
}
