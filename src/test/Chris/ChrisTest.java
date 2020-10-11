package test.Chris;

import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.inventory.inventoryItems.InventoryItem;
import main.inventory.inventoryItems.Seed;
import main.market.Market;
import main.util.crops.CropTypes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ChrisTest {
    private GameManager gameManager;
    private Inventory inventory;

    @Before
    public void setup() throws NullPointerException {
        gameManager = new GameManager(0);
        inventory = new Inventory(gameManager, null, null);
    }

    @Test
    public void testBuySeed() throws Exception{
        gameManager.setMoney(100);
        ArrayList<InventoryItem> listings = gameManager.getMarket().getMarketListings();
        int price = 0;
        //Go through the listings to find price of corn
        for (InventoryItem item : listings) {
            if (item instanceof Seed) {
                if (((Seed) item).getType() == CropTypes.CORN) {
                    price = item.getBuyCost();
                }
            }
        }
        Market.buySeed(CropTypes.CORN, gameManager, price);
        assertEquals(100-price, gameManager.getMoney().intValue());
    }

    @Test
    public void testBuySeedWithOutMoney() {
    //purpose is to test given 0 money
        gameManager.setMoney(0);
        int price = 10;
        for (CropTypes type: CropTypes.values()) {
            Market.buySeed(type, gameManager, price);
        }
        assertEquals(0, gameManager.getMoney().intValue());
    }

    @Test
    public void testSellSeed() throws Exception{
        int price = 0;
        gameManager.setInventory(inventory);
        gameManager.getInventory().putSeed(CropTypes.CORN);
        Market.sellSeed(CropTypes.CORN, gameManager, price);
        assertEquals(0, gameManager.getInventory().getStorageSize());
    }

    @Test
    public void testSellSeedWithoutSeed() throws Exception {
        int price = 10;
        gameManager.setMoney(0);
        gameManager.setInventory(inventory);
        gameManager.getInventory().putSeed(CropTypes.CORN);
        Market.sellSeed(CropTypes.COTTON, gameManager, price);
        assertEquals(0, gameManager.getMoney().intValue());
    }

    @Test
    public void testSellProduct() throws Exception {
        int price = 0;
        inventory.putProduct(CropTypes.CORN);
        //gameManager.setInventory(inventory);
        //gameManager.getInventory().putSeed(CropTypes.CORN);
        //Market.sellProduct(CropTypes.CORN, gameManager, price);
        HashMap<CropTypes, Integer> test = new HashMap<>();
        test.put(CropTypes.CORN, 1);
        assertEquals(0, gameManager.getInventory().getStorageSize());
    }
}

