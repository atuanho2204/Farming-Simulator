package main.inventory;

import java.util.ArrayList;

public class Inventory {
    private int storageLimit = 0;
    //other varaibles to store


    public void put(InventoryItem item) {

    }

    public void remove(InventoryItem item) {

    }

    public int getStorageLimit() {
        return storageLimit; }

    public String getTextOfInventoryItems() {
        return "This should be a list of the inventory items in string format";
    }

    public ArrayList<InventoryItem> getListOfInventoryItems() {
        return new ArrayList<InventoryItem>();
    }
}
