package test.Chris;

<<<<<<< HEAD
import com.sun.javafx.application.PlatformImpl;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.inventory.InventoryUIController;
import main.inventory.inventoryItems.InventoryItem;
import main.inventory.inventoryItems.Seed;
import main.market.marketListing.MarketListing;
import main.util.crops.CropTypes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ChrisTest {
    private GameManager gameManager;
    private Inventory inventory;
    private InventoryUIController controller;

    @Before
    public void setup() throws NullPointerException {
        gameManager = new GameManager(0);
        controller = new InventoryUIController();
        inventory = new Inventory(controller);
        gameManager.setInventory(inventory);
    }

    /**
     *
     * Test Component: test the buySeed method
     * Reason: Check to be able to buy seed from market
     * Method: set Money and then buy a seed from the
     * array list (market) and add to gameManager
     *
     */
    @Test
    public void testBuySeed() throws Exception {
        PlatformImpl.startup(() -> {
        });
        gameManager.setMoney(300);
        ArrayList<InventoryItem> listings = gameManager.getMarket().getMarketListings();
        int price = 0;
        //Go through the listings to find price of corn
        for (InventoryItem item : listings) {
            if (item instanceof Seed) {
                if (((Seed) item).getType() == CropTypes.WHEAT) {
                    price = item.getBuyCost();
                }
            }
        }
        MarketListing.buySeed(CropTypes.WHEAT, gameManager, price);
        assertEquals(300 - price, gameManager.getMoney().intValue());
    }

    /**
     *
     * Test Component: test buySeed method
     * with no money
     * Reason: Check to be able to buy seed from market
     * Method: set no money and try to buy seed
     * for a price with no money
     *
     */
    @Test
    public void testBuySeedWithOutMoney() {
        //purpose is to test given 0 money
        gameManager.setMoney(0);
        int price = 10;
        for (CropTypes type: CropTypes.values()) {
            MarketListing.buySeed(type, gameManager, price);
        }
        assertEquals(0, gameManager.getMoney().intValue());
    }

    /**
     *
     * Test Component: test the sellSeed method
     * Reason: Check to be able to sell seed from inventory
     * Method: given a price, sell seed from market given
     * the seed in inventory
     *
     */
    @Test
    public void testSellSeed() throws Exception {
        int price = 0;
        gameManager.getInventory().putSeed(CropTypes.CORN);
        MarketListing.sellSeed(CropTypes.CORN, gameManager, price);
        assertEquals(0, gameManager.getInventory().getStorageSize());
    }

    /**
     *
     * Test Component: test sellSeed method
     * Reason: Check to be able sell a seed tht is not in
     * your current inventory
     * Method: given a price for the seed, set inventory and
     * sell Cotton given it is null in inventory
     *
     */
    @Test
    public void testSellSeedWithoutSeed() throws Exception {
        int price = 10;
        gameManager.setMoney(0);
        gameManager.getInventory().putSeed(CropTypes.CORN);
        MarketListing.sellSeed(CropTypes.COTTON, gameManager, price);
        assertEquals(0, gameManager.getMoney().intValue());
    }

    /**
     *
     * Test Component: test the sellProduct method
     * Reason: Check to be able to sell product from inventory
     * Method: given a price, sell product from market given
     * the seed in inventory
     *
     */
    @Test
    public void testSellProduct() throws Exception {
        PlatformImpl.startup(() -> {
        });
        int price = 100;
        gameManager.setMoney(50);
        gameManager.getInventory().putProduct(CropTypes.CORN);
        MarketListing.sellProduct(CropTypes.CORN, gameManager, price);
        assertEquals(0, gameManager.getInventory().getStorageSize());
        assertEquals(50 + price, gameManager.getMoney().intValue());
    }
=======
public class ChrisTest {

>>>>>>> origin/skaat
}

