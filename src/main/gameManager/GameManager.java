package main.gameManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameManager {
    private Integer day;
    private ScheduledExecutorService executorService;
    private final int deltaT = 4000; //in milliseconds
    private NewDayListener listener;

    public GameManager(Integer day) {
        this.day = day;
    }

    public NewDayListener getListener() {
        return listener;
    }

    public void setListener(NewDayListener listener) {
        this.listener = listener;
    }

    public void startTime() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::timeStep, deltaT, deltaT, TimeUnit.MILLISECONDS);
    }

    public void pauseTime() {
        executorService.shutdown();
    }


    private void timeStep() {
        this.day++;
        try {
            NewDayEvent newDay = new NewDayEvent(this.day);
            listener.handleNewDay(newDay);
        } catch (Exception e) {
            System.out.println(e);
            pauseTime();
        }
    }
}
