package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class BlockCheckThread extends Thread {
    private AttemptTracker tracker;
    private String email;
    private boolean isBlocked;
    public BlockCheckThread(String email,AttemptTracker tracker){
        this.email=email;
        this.tracker=tracker;
        this.isBlocked=false;
    }
    @Override
    public void run() {
        UserState current_state=tracker.get_UserState(email);
        if(current_state==null){
            isBlocked=false;
            return;
        }
        if(current_state.get_endTime()>System.currentTimeMillis()){
            isBlocked=true;
        }else {isBlocked=false;}

    }

    public boolean get_isBlocked(){
        return isBlocked;
    }
}
