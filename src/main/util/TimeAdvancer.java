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
    private static int newDayWait = 4000; //in MILLISECONDS
    private ArrayList<NewDayListener> listeners;

    public TimeAdvancer(int startDay) {
        this.listeners = new ArrayList<>();
        this.day = startDay;
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void addListener(NewDayListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(NewDayListener listener) {
        this.listeners.remove(listener);
    }

    public void startTime() {
        System.out.println("Time started on day: " + this.day);
        executorService.scheduleWithFixedDelay(
                this::timeStep, newDayWait, newDayWait, TimeUnit.MILLISECONDS);
    }

    public void pauseTime() {
        System.out.println("Time Paused at day: " + this.day);
        executorService.shutdown();
    }

    public static int getNewDayWait() {
        return newDayWait;
    }

    public static void setNewDayWait(int newDayWait) {
        TimeAdvancer.newDayWait = newDayWait;
    }

    private void timeStep() {
        try {
            this.day = day + 1;
            System.out.println("Time stepped to day: " + this.day);
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
