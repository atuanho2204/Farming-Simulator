package main.farm;

import main.farm.crops.CropCatalog;
import main.farm.crops.CropDetails;
import main.farm.crops.CropStages;
import main.farm.crops.CropTypes;
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
    private static FarmState instance = new FarmState();
    private PropertyChangeSupport changeSupport;
    private int daysEachMonth = 30;
    private int numSunnyDays = 0;
    private int numRainyDays = 0;
    private FarmEquipment farmEquipment = new FarmEquipment();
    private FarmController farmController;

    private FarmState() {
        this.plots = new ArrayList<>(numOfPlots);
        changeSupport = new PropertyChangeSupport(this);
        GameManager.getInstance().getTimeAdvancer().addListener(this);
    }

    public void subscribeToChanges(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public static FarmState getInstance() {
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
        reduceFertilizer(GameManager.getInstance().getDifficulty());
        updateGrowthCycle(false);
        String day = (e.getNewDay() < 10) ? ("0" + e.getNewDay()) : e.getNewDay().toString();
        NotificationManager.getInstance().addNotification("~~~~~~~~~~~~~~~~Day " + day
                + "~~~~~~~~~~~~~~~~~");
        forcePlotUpdate("Show new plot water and growth levels");
        updateSeason();
        resetEquipmentLevels();
    }

    public void updateGrowthCycle(boolean isTesting) {
        boolean areEmptyPlots = true;
        for (Plot plot: plots) {
            if (plot.getCurrentCrop() == null) {
                continue;
            }
            CropTypes type = plot.getCurrentCrop().getType();
            CropStages stage = plot.getCurrentCrop().getStage();
            int plantDay = plot.getCurrentCrop().getPlantDay();
            CropDetails details = CropCatalog.getInstance().getCropDetails(type);
            int growthTime = details.getGrowthTime();
            if (plot.getCurrentFertilizer() > 0) {
                growthTime -= 1;
            }
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
            if (plot.getCurrentCrop().getStage() != CropStages.DEAD) {
                areEmptyPlots = false;
            }
        }
        int lowestCropPrice = CropCatalog.getInstance().getMinBuyPrice();
        if (!isTesting && areEmptyPlots
                && GameManager.getInstance().getMoney() < lowestCropPrice
                && GameManager.getInstance().getInventory().getProducts().size() == 0
                && GameManager.getInstance().getInventory().getSeedStorage().size() == 0) {
            if (farmController != null) {
                farmController.endGame();
            }
        }
    }

    private void resetEquipmentLevels() {
        //set the water level to 0
        farmEquipment.setCurrentWaterPlots(0);

        //set the harvesting level to 0 @Chris
        farmEquipment.setCurrentHarvestPlots(0);
    }

    /**
     * Method reduceWaterLevelsEveryThreeDays decrements each plot's water level by
     * 1 (for easy and medium levels) or 2 (for hard level).
     *
     * @param difficulty level of the game
     */
    private void reduceWaterLevelsEveryThreeDays(Integer difficulty) {
        if (GameManager.getInstance().getDay() % 3 == 2) {
            int waterLost = (difficulty < 3) ? -1 : -2;
            for (Plot plot : plots) {
                plot.waterPlot(waterLost);
            }
        }
    }

    private void reduceFertilizer(Integer difficulty) {
        int fertilizeLost = (difficulty < 3) ? -1 : -2;
        for (Plot plot : plots) {
            plot.fertilizePlot(fertilizeLost);
        }
    }

    public void forcePlotUpdate(String reason) {
        changeSupport.firePropertyChange(
                "plots", "", reason);
    }

    /**
     * Method updateSeason advances to the next season every 'daysEachMonth' days.
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
            double rainRate = 0;
            if (difficulty == 1) {
                rainRate = GameManager.getInstance().getSeason().getRainRate() * 1.5;
            } else {
                rainRate = GameManager.getInstance().getSeason().getRainRate() * difficulty;
            }
            if (Math.random() < rainRate) {
                //System.out.println("rain");
                triggerRain();
                numSunnyDays = 0;
                ++numRainyDays;
            } else {
                ++numSunnyDays;
                //System.out.println("no rain for " + numSunnyDays + " days");
                // locust
                double locustRate = 0.25 * numRainyDays;
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
        NotificationManager.getInstance().addNotification("ALERT!!\nIt's raining!!!\n"
                + "Water level of each plot is incremented by " + increment);
    }

    /**
     * Method triggerDrought decrements each plot's water level by 1, 2, or 3 units.
     */
    public void triggerDrought() {
        int decrement = (int) (Math.random() * (3 - 1 + 1) + 1) * -1;
        for (Plot plot : plots) {
            plot.waterPlot(decrement);
        }
        NotificationManager.getInstance().addNotification("ALERT!!\nThere was a drought--"
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
        int numCropsKilled = 0;
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
            unluckyPlot.getCurrentCrop().setLocust(true);
            unluckyPlot.getCurrentCrop().setCropStage(CropStages.DEAD);
            ++numCropsKilled;
        }

        if (plotsWithLivingCrops.size() > 0) {
            double deadChance = 0.20 * difficulty;
            for (Plot plot : plotsWithLivingCrops) {
                if (!plot.getCurrentCrop().hasPesticide() && Math.random() < deadChance) {
                    plot.getCurrentCrop().setLocust(true);
                    plot.getCurrentCrop().setCropStage(CropStages.DEAD);
                    ++numCropsKilled;
                }
            }
            // decrease the number of rainy days to prevent ridiculously high chances of swarming
            numRainyDays -= 3;
            numRainyDays = Math.max(0, numRainyDays);
        }

        if (numCropsKilled != 0) {
            System.out.println(numCropsKilled);
            NotificationManager.getInstance().addNotification("ALERT!!\nLocusts killed "
                    + numCropsKilled + " of your crops :( :( :(");
        }
    }

    // Getters and Setters

    public int getNumOfPlots() {
        return numOfPlots;
    }

    public List<Plot> getPlots() {
        return plots;
    }


    public FarmEquipment getFarmEquipment() {
        return farmEquipment;
    }

    public void setFarmController(FarmController controller) {
        this.farmController = controller;

    }
}
