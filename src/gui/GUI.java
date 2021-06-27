package gui;

import events.logEvent.LogEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import log.Logger;
import log.log_ressources.LogStrings;
import log.log_ressources.LogStringsEnglish;

public class GUI extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("appLook.fxml"));
        primaryStage.setTitle("Storage");
        primaryStage.setScene(new Scene(root, 1300, 1000));
        primaryStage.show();
    }



}
