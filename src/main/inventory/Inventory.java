package main.inventory;

import main.inventory.inventoryItems.InventoryItem;
import main.util.crops.CropTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Inventory {
    private int storageLimit = 0;
    private ArrayList<InventoryItem> productStorage;
    private HashMap<CropTypes, Integer> seedStorage;

    public Inventory() {

    }


    public void putSeed(CropTypes type) throws NoSuchElementException {

    }

    public void removeSeed(CropTypes type) throws NoSuchElementException {

    }

    public void putProduct(CropTypes type) throws NoSuchElementException {

    }



    public int getStorageLimit() {
        return storageLimit;
    }

    public String getTextOfInventoryItems() {
        return "Test 1,\n Test 2";
    }

    public ArrayList<InventoryItem> getListOfInventoryItems() {
        return new ArrayList<InventoryItem>();
    }
}
