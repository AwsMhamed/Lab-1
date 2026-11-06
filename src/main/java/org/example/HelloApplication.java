package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.smartcardio.ATR;
import java.io.IOException;

public class HelloApplication extends Application {
    public static int n;
    public static  int t;
    public static AttemptTracker tracker;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org.example/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Users login");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> Platform.exit());

        tracker = new AttemptTracker(n, t);

        FailureWorker worker = new FailureWorker(tracker);
        worker.setDaemon(true);
        worker.start();
    }
}
