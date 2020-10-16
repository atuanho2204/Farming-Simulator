package main.gameManager;

import main.inventory.Inventory;
import main.market.Market;
import main.util.TimeAdvancer;
import main.util.crops.CropTypes;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The Manager of the the Totally-accurate-farm-simulator
 */
public class GameManager implements NewDayListener {
    private static GameManager instance = new GameManager();
    private Integer day = 0;
    private final TimeAdvancer timeAdvancer;
    private String name = "";
    private List<CropTypes> seeds = new ArrayList<>(0);
    private String season = "";
    private Integer money = 0;
    private Integer difficulty = 1;
    private Market market;
    private Inventory inventory;

    private GameManager() {
        this.timeAdvancer = new TimeAdvancer(0);
        this.timeAdvancer.addListener(this);
    }

    public static GameManager getInstance() {
        return instance;
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        //System.out.println("day advanced");
        this.day = e.getNewDay();
    }

    public Integer getMoney() {
        return money;
    }

    public String getSeason() {
        return season;
    }

    public List<CropTypes> getSeeds() {
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

    public TimeAdvancer getTimeAdvancer() {
        return timeAdvancer;
    }

    public Market getMarket() {
        return this.market;
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

    public void setSeeds(List<CropTypes> seeds) {
        this.seeds = seeds;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setMarket(Market market) {
        this.market = market;
    }
}
