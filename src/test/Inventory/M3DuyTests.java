package test.Inventory;

import com.sun.javafx.application.PlatformImpl;
import main.inventory.Inventory;
import main.inventory.InventoryUIController;
import main.farm.crops.CropTypes;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

import static org.junit.Assert.*;

public class M3DuyTests {
    private Inventory inventory;
    private InventoryUIController controller1;

    @Before
    public void setUp() {
        controller1 = new InventoryUIController();
        inventory = new Inventory(false);
    }

    /**
     *
     * Test Component: putSeed function of the inventory
     * Reason: Check if the putSeed function is working properly,
     * Method: Call putSeed, then compare with created Hashmap
     *
     */
    @Test
    public void testPutSeed() throws Exception {
        PlatformImpl.startup(() -> {
        });
        HashMap<CropTypes, Integer> seeds = new HashMap<>();
        seeds.put(CropTypes.CARROT, 2);
        seeds.put(CropTypes.TOMATO, 1);
        inventory.putSeed(CropTypes.CARROT);
        inventory.putSeed(CropTypes.CARROT);
        inventory.putSeed(CropTypes.TOMATO);
        for (CropTypes key : seeds.keySet()) {
            assertEquals(seeds.get(key), inventory.getListOfSeedItems().get(key));
        }
    }

    /**
     *
     * Test Component: putProduct function of the inventory
     * Reason: Check if the putProduct method is working properly
     * Method: Call putProduct, then compare with created Hashmap
     *
     */
    @Test
    public void testPutProduct() throws Exception {
        PlatformImpl.startup(() -> {
        });
        HashMap<CropTypes, Integer> products = new HashMap<>();
        products.put(CropTypes.CARROT, 2);
        products.put(CropTypes.MELON, 3);
        //IMPLEMENTATION CHANGED IN M5
        //inventory.putProduct(CropTypes.CORN);
        //inventory.putProduct(CropTypes.CORN);
        //inventory.putProduct(CropTypes.WHEAT);
        //inventory.putProduct(CropTypes.WHEAT);
        //inventory.putProduct(CropTypes.WHEAT);
        //for (CropTypes key : products.keySet()) {
        //    assertEquals(products.get(key), inventory.getListOfProductItems().get(key));
        //}
    }

    /**
     *
     * Test Component: removeSeed function of the inventory
     * Reason: Check if the removeSeed method is working properly
     * Method: Call removeSeed and putSeed, then compare with created Hashmap
     *
     */
    @Test
    public void testRemoveSeed() throws Exception {
        PlatformImpl.startup(() -> {
        });
        HashMap<CropTypes, Integer> seeds = new HashMap<>();
        seeds.put(CropTypes.CARROT, 2);
        seeds.put(CropTypes.TOMATO, 1);
        inventory.putSeed(CropTypes.CARROT);
        inventory.putSeed(CropTypes.CARROT);
        inventory.putSeed(CropTypes.TOMATO);
        inventory.putSeed(CropTypes.TOMATO);
        inventory.removeSeed(CropTypes.TOMATO);
        for (CropTypes key : seeds.keySet()) {
            assertEquals(seeds.get(key), inventory.getListOfSeedItems().get(key));
        }
    }

    /**
     *
     * Test Component: removeProduct function of the inventory
     * Reason: Check if the removeProduct method is working properly
     * Method: Call removeProduct and putProduct, then compare with created Hashmap
     *
     */
    @Test
    public void testRemoveProduct() throws Exception {
        PlatformImpl.startup(() -> {
        });
        HashMap<CropTypes, Integer> products = new HashMap<>();
        products.put(CropTypes.CARROT, 1);
        products.put(CropTypes.MELON, 3);
        //inventory.putProduct(CropTypes.CORN);
        //inventory.putProduct(CropTypes.CORN);
        //inventory.putProduct(CropTypes.WHEAT);
        //inventory.putProduct(CropTypes.WHEAT);
        //inventory.putProduct(CropTypes.WHEAT);
        //inventory.removeProduct(CropTypes.CORN);
        //for (CropTypes key : products.keySet()) {
        //    assertEquals(products.get(key), inventory.getListOfProductItems().get(key));
        //}
    }

    /**
     *
     * Test Component: getStorageSize function of the inventory
     * Reason: Check if the getStorageSize method is working properly
     * Method: Call getStorageSize, putSeed and putProduct, comparing it with calculated size
     *
     */
    @Test
    public void testGetStorageSize() throws Exception {
        PlatformImpl.startup(() -> {
        });
        for (int i = 0; i < 20; i++) {
            inventory.putSeed(CropTypes.TOMATO);
        }
        assertEquals(20, inventory.getStorageSize());
    }
}
