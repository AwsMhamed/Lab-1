package org.example;

public class FailureWorker extends Thread {
    private AttemptTracker tracker;
    public FailureWorker(AttemptTracker tracker) {
        this.tracker = tracker;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {

                FailedAttempt attempt =tracker.getFailureQueue().take();
                UserState userState=tracker.get_UserState(attempt.getEmail());
                userState.set_attempt_counter(userState.get_attempt_counter()+1);

                if(userState.get_attempt_counter()>=tracker.n){
                    userState.set_endTime(System.currentTimeMillis()+tracker.t*1000L);
                }


            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

