package main.market;


import main.gameManager.GameManager;
import main.gameManager.NewDayEvent;
import main.gameManager.NewDayListener;
import main.inventory.inventoryItems.InventoryItem;
import java.util.ArrayList;
import main.util.crops.CropCatalog;
import main.util.crops.CropDetails;
import main.util.crops.CropTypes;


public class Market implements NewDayListener {
    private ArrayList<InventoryItem> listings;
    private final GameManager gameManager;
    private final int priceModifier = 1;
    private final double randomness = 0.1; //higher values mean more random

    public Market(GameManager gameManager) {
        this.listings = new ArrayList<>();
        this.gameManager = gameManager;
        this.gameManager.getTimeAdvancer().addListener(this);
        loadListingsIntoMarket();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        loadListingsIntoMarket();
    }

    private void loadListingsIntoMarket() {
        this.listings.clear();
        for (CropTypes type : CropTypes.values()) {
            this.listings.add(new InventoryItem(
                    getPriceForCrop(type), //buy price
                    getPriceForCrop(type), //sell price
                    type.name().toLowerCase()) //name
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
        int difficultySupplement = gameManager.getDifficulty();
        return priceModifier * (int) (details.getBaseSell()
                + Math.round(2 * Math.sin(gameManager.getDay()) + randomSupplment)
                + difficultySupplement);
    }

    /**
     * Gets the market listings.
     * @return an arrayList of inventory items
     */
    public ArrayList<InventoryItem> getMarketListings() {
        return listings;
    }
}
