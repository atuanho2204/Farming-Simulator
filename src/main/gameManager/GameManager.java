package main.gameManager;

import main.util.TimeAdvancer;

import java.util.ArrayList;
import java.util.List;

/**
 * The Manager of the the Totally-accurate-farm-simulator
 */
public class GameManager implements NewDayListener {
    private Integer day = 0;
    private final TimeAdvancer timeAdvancer;
    private String name = "";
    private List<String> seeds = new ArrayList<>(0);
    private String season = "";
    private Integer money = 0;
    private Integer difficulty = 1;

    public GameManager(Integer day) {
        this.day = day;
        this.timeAdvancer = new TimeAdvancer();
        this.timeAdvancer.addListener(this);
    }

    public TimeAdvancer getTimeAdvancer() {
        return timeAdvancer;
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        System.out.println("advanced");
        this.day = e.getNewDay();
    }

    public Integer getMoney() {
        return money;
    }

    public String getSeason() {
        return season;
    }

    public List<String> getSeeds() {
        return seeds;
    }

    public String getName() {
        return name;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setSeeds(List<String> seeds) {
        this.seeds = seeds;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
}
