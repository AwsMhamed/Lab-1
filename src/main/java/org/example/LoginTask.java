package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginTask implements  Runnable{
    private String email;
    private String password;
    private AttemptTracker tracker;
    private Label errorLabel;
    private HelloController Controller;

    public LoginTask(String email, String password, AttemptTracker tracker, Label errorLabel,HelloController Controller){
        this.password=password;
        this.email=email;
        this.tracker = tracker;
        this.errorLabel=errorLabel;
        this.Controller=Controller;
    }


    @Override
    public void run() {
        User temp =null;
        ArrayList<User> Users=UserApp.LoadUsers();
        for (User s : Users) {
            if (s.getEmail().equals(email)) {
                temp = s;
                break;
            }
        }
        if (temp == null) {
            Platform.runLater(() -> {
                errorLabel.setText("Email does not exist!");
                Controller.enableLogin();
            });
            return;
        }

        tracker.getEmail_states().putIfAbsent(email, new UserState());

        BlockCheckThread check =new BlockCheckThread(email,tracker);
        check.start();
        try {
            check.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endtime=tracker.get_UserState(email).get_endTime();

        if(check.get_isBlocked()){
            CountdownTask task = new CountdownTask(endtime, errorLabel);
            Thread thread = new Thread(task);
            thread.start();
            Controller.enableLogin();
            return;
        }

        if (temp.getPass().equals((password))){
            tracker.get_UserState(email).set_attempt_counter(0);
            tracker.get_UserState(email).set_endTime(0);
            Platform.runLater(() -> openWelcomeScreen());
        return;

        }try {
            tracker.getFailureQueue().put(new FailedAttempt(email));

            Platform.runLater(() -> {
                errorLabel.setText("Incorrect password! Remaining attempts: "+
                        (tracker.n- (tracker.get_UserState(email).get_attempt_counter())));

                Controller.enableLogin();
            });


        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }



    }
    @FXML
    private void openWelcomeScreen() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org.example/welcome.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) errorLabel.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

            stage.setOnCloseRequest(event -> Platform.exit());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
