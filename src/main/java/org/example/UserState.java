package org.example;

import java.time.LocalTime;

public class UserState {
    private int attempt_counter;
    private Long endTime;

    public UserState(){
        this.attempt_counter=0;
        this.endTime= 0L;
    }
    public synchronized int get_attempt_counter(){
        return this.attempt_counter;
    }
    public synchronized long get_endTime(){
        return this.endTime;
    }
    public synchronized void set_attempt_counter(int new_attempt_counter){
         this.attempt_counter=new_attempt_counter;
    }
    public synchronized void set_endTime(long new_endTime){
         this.endTime=new_endTime;
    }
    public synchronized void checkBlockExpired() {
        if (endTime > 0 && System.currentTimeMillis() >= endTime) {
            attempt_counter = 0;
            endTime = 0L;
        }
    }
}
