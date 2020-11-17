package test.Market;

import com.sun.javafx.application.PlatformImpl;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.inventory.inventoryItems.InventoryItem;
import main.inventory.inventoryItems.Seed;
import main.market.Market;
import main.farm.crops.CropTypes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Chris
 */
public class MarketTest {

    @Before
    public void setup() throws NullPointerException {
        GameManager.getInstance().clear(); //test only
        PlatformImpl.startup(() -> {
            //fix TOOLKIT NOT INITIALIZED
        });
        GameManager.getInstance().setInventory(new Inventory(true));
        GameManager.getInstance().setMarket(new Market());
    }

    /**
     * Test Component: test the buySeed method
     * Reason: Check to be able to buy seed from market
     * Method: set Money and then buy a seed from the
     * array list (market) and add to gameManager
     */
    @Test
    public void testBuySeed() throws Exception {
        GameManager.getInstance().setMoney(100);

        ArrayList<InventoryItem> listings = GameManager.getInstance()
                .getMarket().getMarketListings();

        int price = 0;
        //Go through the listings to find price of corn
        for (InventoryItem item : listings) {
            if (item instanceof Seed) {
                if (((Seed) item).getType() == CropTypes.MELON) {
                    price = item.getBuyCost();
                }
            }
        }
        Market.buySeed(CropTypes.MELON, price);
        assertEquals(100 - price, GameManager.getInstance().getMoney().intValue());
    }

    /**
     * Test Component: test buySeed method
     * with no money
     * Reason: Check to be able to buy seed from market
     * Method: set no money and try to buy seed
     * for a price with no money.
     */
    @Test
    public void testBuySeedWithOutMoney() {
        //purpose is to test given 0 money
        GameManager.getInstance().setMoney(0);
        int price = 10;
        for (CropTypes type : CropTypes.values()) {
            Market.buySeed(type, price);
        }
        assertEquals(0, GameManager.getInstance().getMoney().intValue());
    }

    /**
     * Test Component: test the sellSeed method
     * Reason: Check to be able to sell seed from inventory
     * Method: given a price, sell seed from market given
     * the seed in inventory
     */
    @Test
    public void testSellSeed() throws Exception {
        int price = 0;
        GameManager.getInstance().getInventory().putSeed(CropTypes.CARROT);
        Market.sellSeed(CropTypes.CARROT, price);
        assertEquals(0, GameManager.getInstance().getInventory().getStorageSize());
    }

    /**
     * Test Component: test sellSeed method
     * Reason: Check to be able sell a seed tht is not in
     * your current inventory
     * Method: given a price for the seed, set inventory and
     * sell Cotton given it is null in inventory
     */
    @Test
    public void testSellSeedWithoutSeed() throws Exception {
        int price = 10;
        GameManager.getInstance().setMoney(0);
        //GameManager.getInstance().setInventory(inventory);
        GameManager.getInstance().getInventory().putSeed(CropTypes.CARROT);
        Market.sellSeed(CropTypes.TOMATO, price);
        GameManager.getInstance().getInventory().putSeed(CropTypes.CARROT);
        Market.sellSeed(CropTypes.TOMATO, price);
        assertEquals(0, GameManager.getInstance().getMoney().intValue());
    }

    /**
     * Test Component: test the sellProduct method
     * Reason: Check to be able to sell product from inventory
     * Method: given a price, sell product from market given
     * the seed in inventory
     */
    @Test
    public void testSellProduct() throws Exception {
        PlatformImpl.startup(() -> {
        });
        //IMPLEMENTATION CHAGNED IN M5

        //int price = 100;
        //GameManager.getInstance().setMoney(50);
        //GameManager.getInstance().getInventory().putProduct(CropTypes.CORN);
        //Market.sellProduct(CropTypes.CORN, price);
        //assertEquals(0, GameManager.getInstance().getInventory().getStorageSize());
        //assertEquals(50 + price, GameManager.getInstance().getMoney().intValue());
    }
}
