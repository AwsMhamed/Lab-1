package org.example;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class AttemptTracker {
    private BlockingQueue<FailedAttempt> failureQueue =
            new ArrayBlockingQueue<>(1);
    private ConcurrentHashMap<String, UserState> email_states =
            new ConcurrentHashMap<>();
    int n;  int t;

    public AttemptTracker(int n,int t){
        this.t=t;
        this.n=n;
    }

    public ConcurrentHashMap<String, UserState> getEmail_states() {
        return email_states;
    }
    public UserState get_UserState(String email){
        return email_states.get(email);
    }

    public BlockingQueue<FailedAttempt> getFailureQueue() {
        return failureQueue;
    }


}
