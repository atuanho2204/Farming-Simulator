package main.farm;

import javafx.application.Platform;
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
    private static FarmState instance;
    private PropertyChangeSupport changeSupport;

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

    public List<Plot> getPlots() {
        return plots;
    }

    public static void clearFarmStateDangerous() {
        System.out.println("Clearing the farm state...only to be done while testing");
        instance = new FarmState();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        reduceWaterLevels(GameManager.getInstance().getDifficulty(),
                GameManager.getInstance().getSeason());
        updateGrowthCycle();
        NotificationManager.getInstance().addNotification("Day " + e.getNewDay() + " Began");
        forcePlotUpdate("Show new plot water and growth levels");
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

    private void reduceWaterLevels(Integer difficulty, Seasons season) {
        if (GameManager.getInstance().getDay() % 2 != 0) {
            return;
        }
        int waterLost = (difficulty < 3) ? 1 : 2;
        if (season == Seasons.SUMMER) {
            ++waterLost;
        }
        try {
            int finalWaterLost = waterLost;
            Platform.runLater(() -> {
                for (Plot plot : plots) {
                    int maxLevel = plot.getMaxWater();
                    if (plot.getCurrentWater() % maxLevel == 0) {
                        continue;
                    }
                    if (plot.getCurrentWater() > finalWaterLost) {
                        plot.setCurrentWater(plot.getCurrentWater() - finalWaterLost);

                    } else {
                        plot.setCurrentWater(0);
                        if (plot.getCurrentCrop() != null) {
                            plot.getCurrentCrop().setCropStage(CropStages.DEAD);
                        }
                    }
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void forcePlotUpdate(String reason) {
        changeSupport.firePropertyChange(
                "plots", "", reason);
    }

    public int getNumOfPlots() {
        return numOfPlots;
    }
}
