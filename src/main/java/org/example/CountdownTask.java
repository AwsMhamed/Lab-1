package org.example;

import javafx.application.Platform;

import javafx.application.Platform;
import javafx.scene.control.Label;




public class CountdownTask implements Runnable {

    private Label countdownLabel;
    private long endTime;


    public CountdownTask(long endTime, Label countdownLabel) {
        this.endTime = endTime;
        this.countdownLabel = countdownLabel;
    }

    @Override
    public void run() {

        while (true) {

            long remaining = endTime - System.currentTimeMillis();

            if (remaining <= 0) {
                Platform.runLater(() ->
                        countdownLabel.setText("")
                );
                break;
            }

            long seconds = (remaining + 999) / 1000;

            Platform.runLater(() ->
                    countdownLabel.setText(
                            "Blocked for " + seconds + " seconds"
                    )
            );

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}