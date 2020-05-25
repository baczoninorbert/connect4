package Launcher;

import javafx.application.Application;
import javafx.stage.Stage;
import view.View;

public class Initialize extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        View view = new View();
        view.createMenu();
    }
}
