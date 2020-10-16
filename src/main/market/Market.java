package main.market;


import main.gameManager.GameManager;
import main.inventory.inventoryItems.HarvestedCrop;
import main.inventory.inventoryItems.Seed;
import main.util.AlertUser;
import main.util.UIManager;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;
import main.inventory.inventoryItems.InventoryItem;
import java.util.ArrayList;
import main.util.crops.CropCatalog;
import main.util.crops.CropDetails;
import main.util.crops.CropTypes;


public class Market implements NewDayListener {
    private ArrayList<InventoryItem> listings;
    private final int priceModifier = 1;
    private final double randomness = 0.1; //higher values mean more random

    public Market() {
        this.listings = new ArrayList<>();

        loadListingsIntoMarket();

    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        loadListingsIntoMarket();
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
        for (CropTypes type : CropTypes.values()) {
            this.listings.add(new HarvestedCrop(
                    getPriceForCrop(type) * 2, //buy price
                    getPriceForCrop(type) * 2, //sell price
                    "Product-" + type.name().toLowerCase(), //name
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
        int randomSupplment = (int) Math.round((Math.random() * 10 - 5)  * randomness);
        int difficultySupplement = GameManager.getInstance().getDifficulty();
        return priceModifier * (int) (details.getBaseSell()
                + Math.round(2 * Math.sin(GameManager.getInstance().getDay()) + randomSupplment)
                + difficultySupplement);
    }

    /**
     * Gets the market listings.
     * @return an arrayList of inventory items
     */
    public ArrayList<InventoryItem> getMarketListings() {
        return listings;
    }

    public static void buySeed(CropTypes type, GameManager gameManager, int price) {
        try {
            if (gameManager.getMoney() >= price) {
                gameManager.getInventory().putSeed(type);
                int money = gameManager.getMoney() - price;
                gameManager.setMoney(money);
                UIManager.getInstance().pushUIUpdate();
            } else {
                AlertUser.alertUser("Do not have enough money");
            }
        } catch (Exception e) {
            AlertUser.alertUser("Do not have enough space");
        }
    }

    public static void sellSeed(CropTypes type, GameManager gameManager, int price) {
        try {
            gameManager.getInventory().removeSeed(type);
            int money = gameManager.getMoney() + price;
            gameManager.setMoney(money);
            UIManager.getInstance().pushUIUpdate();
        } catch (Exception e) {
            AlertUser.alertUser("Do not have seed");
        }
    }

    public static void sellProduct(CropTypes type, GameManager gameManager, int price) {
        try {
            gameManager.getInventory().removeProduct(type);
            int newMoney = gameManager.getMoney() + price;
            gameManager.setMoney(newMoney);
            UIManager.getInstance().pushUIUpdate();
        } catch (Exception e) {
            AlertUser.alertUser("You do not have that product in your inventory");
        }
    }
}
