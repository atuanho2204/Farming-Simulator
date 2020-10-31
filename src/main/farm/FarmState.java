package main.farm;

import main.farm.crops.*;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.notifications.NotificationManager;
import main.util.Seasons;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class FarmState implements NewDayListener {
    private final int numOfPlots = 12;
    private List<Plot> plots;
    private static FarmState instance;
    private PropertyChangeSupport changeSupport;
    private int daysEachMonth = 30;
    private int numSunnyDays = 0;
    private int numRainyDays = 0;

    private FarmState() {
        this.plots = new ArrayList<>(numOfPlots);
        changeSupport = new PropertyChangeSupport(this);
        GameManager.getInstance().getTimeAdvancer().addListener(this);
    }

    public void subscribeToChanges(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public static FarmState getInstance() {
        if (instance == null) {
            instance = new FarmState();
        }
        return instance;
    }

    public static void clearFarmStateDangerous() {
        System.out.println("Clearing the farm state...only to be done while testing");
        instance = new FarmState();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        randomizeEvents();
        reduceWaterLevelsEveryThreeDays(GameManager.getInstance().getDifficulty());
        updateGrowthCycle();
        NotificationManager.getInstance().addNotification("~~~~~~~~~~~~~~~~~~~~Day " + e.getNewDay()
                + "~~~~~~~~~~~~~~~~~~~~");
        forcePlotUpdate("Show new plot water and growth levels");
        updateSeason();
    }

    public void updateGrowthCycle() {
        for (Plot plot: plots) {
            if (plot.getCurrentCrop() == null) {
                continue;
            }
            CropTypes type = plot.getCurrentCrop().getType();
            CropStages stage = plot.getCurrentCrop().getStage();
            int plantDay = plot.getCurrentCrop().getPlantDay();
            CropDetails details = CropCatalog.getInstance().getCropDetails(type);
            int growthTime = details.getGrowthTime();
            int currentDay = GameManager.getInstance().getDay();

            if (currentDay - plantDay > 0 && (currentDay - plantDay) % growthTime == 0) {
                if (stage == CropStages.DEAD) {
                    continue;
                } else if (stage == CropStages.SPROUTING) {
                    plot.getCurrentCrop().setCropStage(CropStages.IMMATURE);
                } else if (stage == CropStages.IMMATURE) {
                    plot.getCurrentCrop().setCropStage(CropStages.MATURE);
                } else if (stage == CropStages.MATURE) {
                    plot.getCurrentCrop().setCropStage(CropStages.DEAD);
                }
            }
        }
    }

    /**
     * Method reduceWaterLevelsEveryThreeDays decrements each plot's water level by
     * 1 (for easy and medium levels) or 2 (for hard level).
     *
     * @param difficulty
     */
    private void reduceWaterLevelsEveryThreeDays(Integer difficulty) {
        if (GameManager.getInstance().getDay() % 3 == 2) {
            int waterLost = (difficulty < 3) ? -1 : -2;
            for (Plot plot : plots) {
                plot.waterPlot(waterLost);
            }
        }
    }

    private void forcePlotUpdate(String reason) {
        changeSupport.firePropertyChange(
                "plots", "", reason);
    }

    /**
     * Method updateSeason advances to the next season every <daysEachMonth> days.
     */
    private void updateSeason() {
        if (GameManager.getInstance().getDay() % daysEachMonth == 0) {
            int currentSeason = GameManager.getInstance().getSeason().ordinal();
            Seasons nextSeason = Seasons.values()[++currentSeason % Seasons.values().length];
            GameManager.getInstance().setSeason(nextSeason);
            NotificationManager.getInstance().addNotification(
                    "Hello " + nextSeason.toString().toLowerCase() + "!!");
        }
    }

    /**
     * Method randomizeEvents initiates rain, drought, and locust swarming based on
     * rain rate of each season. Only one event happens each day.
     *
     * Rain occurs depends on rain rate formed by season's rainRate * difficulty
     * A drought happens after 5 sunny days of no rain.
     * The more rainy days, the higher chance locust swarming will occur.
     */
    private void randomizeEvents() {
        int difficulty = GameManager.getInstance().getDifficulty();
        // drought
        if (numSunnyDays == 5) {
            //System.out.println("drought");
            triggerDrought();
            numSunnyDays = 0;
            numRainyDays = 0;
        } else {
            // rain
            double rainRate = GameManager.getInstance().getSeason().getRainRate() * difficulty;
            if (Math.random() < rainRate) {
                //System.out.println("rain");
                triggerRain();
                numSunnyDays = 0;
                ++numRainyDays;
            } else {
                ++numSunnyDays;
                //System.out.println("no rain for " + numSunnyDays + " days");
                // locust
                double locustRate = 0.2 * numRainyDays;
                if (Math.random() < locustRate) {
                    triggerLocustSwarms(difficulty);
                }
            }
        }
    }

    /**
     * Method triggerRain increments each plot's water level by 1 or 2 units.
     */
    public void triggerRain() {
        // random increment ranges from 1 to 2
        int increment = (int) (Math.random() * (2 - 1 + 1) + 1);
        for (Plot plot : plots) {
            plot.waterPlot(increment);
        }
        NotificationManager.getInstance().addNotification("ALERT!! It's raining!!!\n" +
                "Water level of each plot is incremented by " + increment);
    }

    /**
     * Method triggerDrought decrements each plot's water level by 1, 2, or 3 units.
     */
    public void triggerDrought() {
        int decrement = (int) (Math.random() * (3 - 1 + 1) + 1) * -1;
        for (Plot plot : plots) {
            plot.waterPlot(decrement);
        }
        NotificationManager.getInstance().addNotification("ALERT!! There was a drought--"
                + "no rain for 5 days straight!!!\nWater level of each plot "
                + "is decremented by " + (decrement * -1));
    }

    /**
     * When a swarm of locusts occurs, at least one crop is killed if not all plots
     * have pesticide.
     * The likelihood to be killed of each remaining living crop is determined
     * by the deadRate of difficulty * 0.15
     * @param difficulty current level of the game
     */
    public void triggerLocustSwarms(int difficulty) {
        List<Plot> plotsWithLivingCrops = new ArrayList<>();
        for (Plot plot : plots) {
            if (plot.getCurrentCrop() != null
                    && plot.getCurrentCrop().getStage() != CropStages.DEAD
                    && !plot.getCurrentCrop().hasPesticide()) {
                plotsWithLivingCrops.add(plot);
            }
        }

        // At least one crop is killed
        if (plotsWithLivingCrops.size() > 0) {

            Plot unluckyPlot = plotsWithLivingCrops.remove(
                    (int) (Math.random() * plotsWithLivingCrops.size()));
            unluckyPlot.getCurrentCrop().setCropStage(CropStages.DEAD);
            NotificationManager.getInstance().addNotification("ALERT!! Locusts killed your "
                    + unluckyPlot.getCurrentCrop().getType().toString() + "... :(");
        }

        if (plotsWithLivingCrops.size() > 0) {
            double deadChance = 0.15 * difficulty;
            for (Plot plot : plotsWithLivingCrops) {
                if (!plot.getCurrentCrop().hasPesticide() && Math.random() < deadChance) {
                    plot.getCurrentCrop().setCropStage(CropStages.DEAD);
                    NotificationManager.getInstance().addNotification("ALERT!! Locusts killed your "
                            + plot.getCurrentCrop().getType().toString() + "... :(");
                }
            }
            // decrease the number of rainy days to prevent ridiculously high chances of swarming
            numRainyDays -= 3;
            numRainyDays = Math.max(0, numRainyDays);
        }
    }


    /** Getters and Setters **/

    public int getNumOfPlots() {
        return numOfPlots;
    }

    public List<Plot> getPlots() {
        return plots;
    }

    public void setNumSunnyDays(int numDays) {
        this.numSunnyDays = numDays;
    }

    public void setNumRainyDays(int numDays) {
        this.numRainyDays = numDays;
    }
}
