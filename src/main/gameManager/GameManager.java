package main.gameManager;

import main.employment.EmployeeManager;
import main.inventory.Inventory;
import main.market.Market;
import main.util.Seasons;
import main.util.TimeAdvancer;
import main.farm.crops.CropTypes;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;

import java.util.ArrayList;

import java.util.List;


/**
 * The Manager of the the Totally-accurate-farm-simulator
 */
public class GameManager implements NewDayListener {
    private static GameManager instance;
    private Integer day;
    private final TimeAdvancer timeAdvancer;
    private String name = "";
    private List<CropTypes> seeds = new ArrayList<>(0);
    private Seasons season = Seasons.FALL;
    private Integer money = 0;
    private Integer difficulty;
    private Market market;
    private Inventory inventory;
    private EmployeeManager employees;
    private int[] badgeBookkeeping = new int[3]; // carrotB - organicB - harvestB
    private boolean gotAllBadges = false;

    private GameManager() {
        this.day = 0;
        this.difficulty = 1;
        this.timeAdvancer = new TimeAdvancer(0);
        this.timeAdvancer.addListener(this);
        badgeBookkeeping[0] = 0; // carrot badge
        badgeBookkeeping[1] = 0; // organic badge
        badgeBookkeeping[2] = 0; // harvest badge
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
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

    public Seasons getSeason() {
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
        this.timeAdvancer.setDay(day);
        this.day = day;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeason(Seasons season) {
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

    public EmployeeManager getEmployees() {
        return employees;
    }

    public void setEmployees(EmployeeManager employees) {
        this.employees = employees;
    }

    public void clear() {
        System.out.println("CLEARING GAMEMANAGER...ONLY TO BE DONE IN TESTS!!");
        GameManager.instance = new GameManager();
    }

    public int[] getBadgeBookkeeping() {
        return badgeBookkeeping;
    }

    public boolean isGotAllBadges() {
        return gotAllBadges;
    }

    public void setGotAllBadges(boolean gotAllBadges) {
        this.gotAllBadges = gotAllBadges;
    }
}
