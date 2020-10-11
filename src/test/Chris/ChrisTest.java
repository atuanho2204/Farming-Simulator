package test.Chris;

import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.inventory.InventoryUIController;
import main.inventory.inventoryItems.InventoryItem;
import main.inventory.inventoryItems.Seed;
import main.market.Market;
import java.util.HashMap;
import main.util.crops.CropTypes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ChrisTest {
    private GameManager gameManager;
    private Stage stage;
    private InventoryUIController controller;

    @Before
    public void setup() {
        gameManager = new GameManager(0);
        stage = new Stage();
        controller = new InventoryUIController();
    }

    @Test
    public void testBuySeed() {
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
        Inventory inventory = new Inventory(gameManager, stage, controller);
        gameManager.setInventory(inventory);
        gameManager.getInventory().putSeed(CropTypes.CORN);
        Market.sellSeed(CropTypes.CORN, gameManager, price);
        assertEquals(0, gameManager.getInventory().getStorageSize());
    }

    @Test
    public void testSellSeedWithoutSeed() throws Exception {
        int price = 10;
        gameManager.setMoney(0);
        Inventory inventory = new Inventory(gameManager, stage, controller);
        gameManager.setInventory(inventory);
        gameManager.getInventory().putSeed(CropTypes.CORN);
        Market.sellSeed(CropTypes.COTTON, gameManager, price);
        assertEquals(0, gameManager.getMoney().intValue());
    }

    @Test
    public void testSellProduct() throws Exception {
        int price = 0;
        Inventory inventory = new Inventory(gameManager, stage, controller);
        gameManager.setInventory(inventory);
        gameManager.getInventory().putSeed(CropTypes.CORN);
        Market.sellProduct(CropTypes.CORN, gameManager, price);
        assertEquals(0, gameManager.getInventory().getStorageSize());
    }
}

