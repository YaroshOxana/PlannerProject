package com.planner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class PlannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PlannerApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource(Data.readTheme()).toExternalForm());
        stage.setTitle("Planner");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Data data = new Data(PlannerController.tasks);
                try {
                    Data.writeData(data, PlannerController.path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Data.saveDate(PlannerController.path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}