package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import javax.smartcardio.ATR;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HelloApplication extends Application {
    public static int n;
    public static  int t;
    public static AttemptTracker tracker;

    private Integer readPositiveInteger(List<String> args, int index, String name) {

        String value = index < args.size() ? args.get(index) : "";

        while (true) {

            try {
                int number = Integer.parseInt(value.trim());

                if (number >= 1) {
                    return number;
                }

            } catch (NumberFormatException e) {

            }

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Invalid argument");
            dialog.setHeaderText("Please enter a valid value for " + name);
            dialog.setContentText(name + " (integer >= 1):");

            Optional<String> result = dialog.showAndWait();

            if (result.isEmpty()) {
                return null;
            }

            value = result.get();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        List<String> args = getParameters().getRaw();
        Integer enteredN = readPositiveInteger(args, 0, "n");

        if (enteredN == null) {
            Platform.exit();
            return;
        }

        Integer enteredT = readPositiveInteger(args, 1, "t");

        if (enteredT == null) {
            Platform.exit();
            return;
        }
        n = enteredN;
        t = enteredT;

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
