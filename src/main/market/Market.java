package main.market;

import main.farm.FarmState;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.Seed;
import main.util.AlertUser;
import main.util.UIManager;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;
import main.inventory.inventoryItems.InventoryItem;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import main.farm.crops.CropCatalog;
import main.farm.crops.CropDetails;
import main.farm.crops.CropTypes;


public class Market implements NewDayListener {
    private static PropertyChangeSupport changeSupport;
    private ArrayList<InventoryItem> listings;
    private final int priceModifier = 1;
    private static int plotPrice = 100;

    public Market() {
        this.listings = new ArrayList<>();
        this.changeSupport = new PropertyChangeSupport(this);
        loadListingsIntoMarket();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        loadListingsIntoMarket();
    }

    public int getPlotPrice() {
        return plotPrice;
    }

    private void loadListingsIntoMarket() {
        this.listings.clear();
        for (CropTypes type : CropTypes.values()) {
            this.listings.add(new Seed(
                    getPriceForCrop(type), //buy price
                    getPriceForCrop(type), //sell price
                    "Seed-" + type.name().toLowerCase(), //name
                    type) //seed type
            );
        }
    }

    /**
     * This function will return the price for a given crop based on:
     * the day, difficulty and other factors.
     *
     * @param type the cropType to return a price for
     * @return an int represeting the buy price for the crop
     */
    private int getPriceForCrop(CropTypes type) {
        CropDetails details = CropCatalog.getInstance().getCropDetails(type);
        //int randomSupplment = (int) Math.round((Math.random() * 2 - 1));
        int difficultySupplement = GameManager.getInstance().getDifficulty();
        return priceModifier * (int) (details.getBaseSell()
                + Math.round(2 * Math.sin(GameManager.getInstance().getDay()))
                + difficultySupplement);
    }

    /**
     * Gets the market listings.
     *
     * @return an arrayList of inventory items
     */
    public ArrayList<InventoryItem> getMarketListings() {
        return listings;
    }

    public static void buySeed(CropTypes type, int price) {
        try {
            if (GameManager.getInstance().getMoney() >= price) {
                GameManager.getInstance().getInventory().putSeed(type);
                int money = GameManager.getInstance().getMoney() - price;
                GameManager.getInstance().setMoney(money);
                UIManager.getInstance().pushUIUpdate();
            } else {
                AlertUser.alertUser("Do not have enough money");
            }
        } catch (Exception e) {
            AlertUser.alertUser("Do not have enough space");
        }
    }

    public static void sellSeed(CropTypes type, int price) {
        try {
            GameManager.getInstance().getInventory().removeSeed(type);
            int money = GameManager.getInstance().getMoney() + price;
            GameManager.getInstance().setMoney(money);
            UIManager.getInstance().pushUIUpdate();
        } catch (Exception e) {
            AlertUser.alertUser("Do not have seed");
        }
    }

    public static void buyPesticide(int price) {
        try {
            if (GameManager.getInstance().getMoney() >= price) {
                GameManager.getInstance().getInventory().putPesticide(10);
                int money = GameManager.getInstance().getMoney() - price;
                GameManager.getInstance().setMoney(money);
                UIManager.getInstance().pushUIUpdate();
            } else {
                AlertUser.alertUser("You need " + (price - GameManager.getInstance().getMoney())
                        + " more dollars");
            }
        } catch (Exception e) {
            AlertUser.alertUser("Your pesticide tank is full");
        }
    }

    public static void buyFertilizer(int price) {
        try {
            if (GameManager.getInstance().getMoney() >= price) {
                GameManager.getInstance().getInventory().putFertilizer(10);
                int money = GameManager.getInstance().getMoney() - price;
                GameManager.getInstance().setMoney(money);
                UIManager.getInstance().pushUIUpdate();
            } else {
                AlertUser.alertUser("You need " + (price - GameManager.getInstance().getMoney())
                        + " more dollars");
            }
        } catch (Exception e) {
            AlertUser.alertUser(e.getMessage());
        }
    }

    public static void buyIrrigation(int price) {
        try {
            if (GameManager.getInstance().getMoney() >= price) {
                //adds the irrigation on the farmState
                FarmState.getInstance().getFarmEquipment().addIrrigation();

                int money = GameManager.getInstance().getMoney() - price;
                GameManager.getInstance().setMoney(money);
                UIManager.getInstance().pushUIUpdate();
            } else {
                AlertUser.alertUser("You need " + (price - GameManager.getInstance().getMoney())
                        + " more dollars");
            }
        } catch (Exception e) {
            AlertUser.alertUser(e.getMessage());
        }
    }

    public static void buyTractor(int price) {
        try {
            if (GameManager.getInstance().getMoney() >= price) {
                //adds the irrigation on the farmState
                FarmState.getInstance().getFarmEquipment().addTractor();

                int money = GameManager.getInstance().getMoney() - price;
                GameManager.getInstance().setMoney(money);
                UIManager.getInstance().pushUIUpdate();
            } else {
                AlertUser.alertUser("You need " + (price - GameManager.getInstance().getMoney())
                        + " more dollars");
            }
        } catch (Exception e) {

        }
    }


}
