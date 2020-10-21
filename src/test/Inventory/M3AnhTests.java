package test.Inventory;

import com.sun.javafx.application.PlatformImpl;
import main.farm.FarmController;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.inventory.InventoryUIController;
import main.util.crops.CropTypes;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
public class M3AnhTests {
    private FarmController controller;
    private InventoryUIController inventoryUI;
    private Inventory inventory;

    @Before
    public void setup() {
        GameManager.getInstance().clear();  //to be used in tests only
        PlatformImpl.startup(() -> {
            //this code serves to remove the "Toolkit not found" error
        });
        inventoryUI = new InventoryUIController();
        controller = new FarmController();
        GameManager.getInstance();
        inventory = new Inventory(false);
    }

    /**
     *
     * Test Component: test the putSeed method
     * Reason: check that the put seed method is able to put the seed into seed storage.
     * Method: call the putSeed method for multiple crop types and
     *  call method getStorageSize() to check how many items in the seed storage.
     *
     */
    @Test
    public void testPutSeedStorage() throws Exception {
        PlatformImpl.startup(() -> {
        });
        inventory.putSeed(CropTypes.CORN);
        inventory.putSeed(CropTypes.CORN);
        inventory.putSeed(CropTypes.WHEAT);
        inventory.putSeed(CropTypes.LETTUCE);
        HashMap<CropTypes, Integer> test2 = new HashMap<>();
        test2.put(CropTypes.CORN, 2);
        test2.put(CropTypes.WHEAT, 1);
        test2.put(CropTypes.LETTUCE, 1);
        for (CropTypes type: test2.keySet()) {
            assertEquals(test2.get(type), inventory.getListOfSeedItems().get(type));
        }
        assertEquals(4, inventory.getStorageSize());
        assertEquals(20, inventory.getStorageLimit());
    }

    /**
     *
     * Test Component: test the putProduct method
     * Reason: check that the putProduct method is able to put the product into seed storage.
     * Method: call the putProduct method for multiple crop types and
     *  call method getStorageSize() to check how many items in the seed storage.
     *
     */
    @Test
    public void testPutProductStorage() throws Exception {
        PlatformImpl.startup(() -> {
        });
        inventory.putProduct(CropTypes.CORN);
        inventory.putProduct(CropTypes.WHEAT);
        inventory.putProduct(CropTypes.LETTUCE);
        HashMap<CropTypes, Integer> test3 = new HashMap<>();
        test3.put(CropTypes.CORN, 1);
        test3.put(CropTypes.WHEAT, 1);
        test3.put(CropTypes.LETTUCE, 1);
        for (CropTypes type: test3.keySet()) {
            assertEquals(test3.get(type), inventory.getListOfProductItems().get(type));
        }
        assertEquals(3, inventory.getStorageSize());
        assertEquals(20, inventory.getStorageLimit());
    }

    /**
     *
     * Test Component: test the remove seed method
     * Reason: check that the removeSeed() method is able to remove the seed into seed storage.
     * Method: call the removeSeed() method for multiple crop types and
     *  call method getStorageSize() to check how many items in the seed storage.
     *
     */
    @Test (expected = NoSuchElementException.class)
    public void testRemoveSeedStorge() throws Exception {
        PlatformImpl.startup(() -> {
        });
        inventory.putSeed(CropTypes.LETTUCE);
        inventory.putSeed(CropTypes.LETTUCE);
        assertEquals(2, inventory.getStorageSize());
        inventory.removeSeed(CropTypes.COTTON);
        inventory.removeSeed(CropTypes.LETTUCE);
        assertEquals(1, inventory.getStorageSize());
    }

    /**
     *
     * Test Component: test the remove product method
     * Reason: check that the removeProduct() method is able to
     * remove the product into product storage.
     * Method: call the removeProduct() method for multiple crop types and
     *  check the quantity of that product after removing from storage.
     *
     */
    @Test
    public void testRemoveProductStorge() throws Exception {
        PlatformImpl.startup(() -> {
        });
        inventory.putProduct(CropTypes.COTTON);
        inventory.putProduct(CropTypes.COTTON);
        inventory.putProduct(CropTypes.COTTON);
        HashMap<CropTypes, Integer> test4 = new HashMap<>();
        test4.put(CropTypes.COTTON, 3);
        for (CropTypes type: test4.keySet()) {
            assertEquals(test4.get(type), inventory.getListOfProductItems().get(type));
        }

    }

    /**
     *
     * Test Component: test the getStorageSize method
     * Reason: check that the getStorageSize() method is able to count the total items
     *  in product and seed storage
     * Method: call the getStorageSize() method for multiple crop types
     *  in both storage and return the correct number.
     *
     */
    @Test
    public void testStorageSize() throws Exception {
        PlatformImpl.startup(() -> {
        });
        inventory.putProduct(CropTypes.COTTON);
        inventory.putSeed(CropTypes.CORN);
        inventory.putSeed(CropTypes.WHEAT);
        inventory.putProduct(CropTypes.LETTUCE);
        assertEquals(4, inventory.getStorageSize());
    }
}
