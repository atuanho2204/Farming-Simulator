package main.util;

import main.gameManager.NewDayEvent;
import main.gameManager.NewDayListener;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeAdvancer {
    private Integer day;
    private ScheduledExecutorService executorService;
    private final int deltaT = 1000; //in MILLISECONDS
    private ArrayList<NewDayListener> listeners;

    public TimeAdvancer() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(NewDayListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(NewDayListener listener) {
        this.listeners.remove(listener);
    }

    public void startTime() {
        System.out.println("Time started");
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(this::timeStep,
                deltaT, deltaT, TimeUnit.MILLISECONDS);
    }

    public void pauseTime() {
        System.out.println("Time Paused");
        executorService.shutdown();
    }


    private void timeStep() {
        System.out.println("Time stepped");
        this.day += 1;
        NewDayEvent newDay = new NewDayEvent(this.day);
        for (int i = 0; i < this.listeners.size(); i++) {
            System.out.println(listeners.get(i));
            listeners.get(i).handleNewDay(newDay);
        }
    }
}
