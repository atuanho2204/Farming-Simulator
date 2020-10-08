package main.util;


import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TimeAdvancer {
    private int day;
    private final ScheduledExecutorService executorService;
    private final int deltaT = 4000; //in MILLISECONDS
    private ArrayList<NewDayListener> listeners;

    public TimeAdvancer() {
        this.listeners = new ArrayList<>();
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void addListener(NewDayListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(NewDayListener listener) {
        this.listeners.remove(listener);
    }

    public void startTime() {
        System.out.println("Time started");
        executorService.scheduleAtFixedRate(this::timeStep, deltaT, deltaT, TimeUnit.MILLISECONDS);
    }

    public void pauseTime() {
        System.out.println("Time Paused");
        executorService.shutdown();
    }


    private void timeStep() {
        try {
            System.out.println("Time stepped to day: " + this.day);
            this.day = day + 1;
            NewDayEvent newDay = new NewDayEvent(this.day);
            for (NewDayListener listener : this.listeners) {
                //System.out.println(listener);
                listener.handleNewDay(newDay);
            }
        } catch (Exception e) {
            System.out.println("Error in timeStepping: " + e.getMessage());
            for (StackTraceElement l : e.getStackTrace()) {
                System.out.println(l);
            }
        }
    }
}
