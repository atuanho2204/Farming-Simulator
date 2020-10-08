package main.inventory;

import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.InventoryItem;
import main.util.crops.CropTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Inventory {
    private GameManager gameManager;
    private Stage primaryStage;
    private int storageLimit = 20;
    private HashMap<CropTypes, Integer> productStorage;
    private HashMap<CropTypes, Integer> seedStorage;
    private InventoryUIController inventoryController;

    public Inventory(GameManager gameMan, Stage stage, InventoryUIController controller) {
        this.gameManager = gameMan;
        this.primaryStage = stage;
        this.inventoryController = controller;
        this.productStorage = new HashMap<>();
        this.seedStorage = new HashMap<>();
    }

    public void putSeed(CropTypes type) throws NoSuchElementException {
        try {
            if (type == null || getStorageSize() == getStorageLimit()) {
                throw new NoSuchElementException();
            } else {
                seedStorage.put(type, seedStorage.getOrDefault(type, 0) + 1);
                inventoryController.setInventoryListings();
            }
        } catch (Exception e) {
            System.out.println("The crop does not exist");
        }
    }

    public void removeSeed(CropTypes type) throws NoSuchElementException {
        try {
            if (type == null || getStorageSize() == 0 || !seedStorage.containsKey(type)) {
                throw new NoSuchElementException();
            } else {
                if (seedStorage.get(type) == 1) {
                    seedStorage.remove(type);
                } else {
                    seedStorage.put(type, seedStorage.get(type) - 1);
                }
                inventoryController.setInventoryListings();
            }
        } catch (Exception e) {
            System.out.println("That seed does not exits in storage");
        }
    }

    public void putProduct(CropTypes type) throws NoSuchElementException {
        if (type == null || getStorageSize() == getStorageLimit()) {
            throw new NoSuchElementException();
        } else {
            productStorage.put(type, productStorage.getOrDefault(type, 0) + 1);
            inventoryController.setInventoryListings();
        }
    }

    public void removeProduct(CropTypes type) throws NoSuchElementException {
        if (type == null || getStorageSize() == 0 || !productStorage.containsKey(type)) {
            throw new NoSuchElementException();
        } else {
            if (productStorage.get(type) == 1) {
                productStorage.remove(type);
            } else {
                productStorage.put(type, productStorage.get(type) - 1);
            }
            inventoryController.setInventoryListings();
        }
    }

    public int getStorageSize() {
        int size = 0;
        for (int fill : productStorage.values()) {
            size += fill;
        }
        for (int fill : seedStorage.values()) {
            size += fill;
        }
        return size;
    }

    public int getStorageLimit() {
        return storageLimit;
    }

    public HashMap<CropTypes, Integer> getListOfSeedItems() {
        return seedStorage;
    }

    public HashMap<CropTypes, Integer> getListOfProductItems() {
        return productStorage;
    }

    public ArrayList<InventoryItem> getListOfInventoryItems() {
        return new ArrayList<InventoryItem>();
    }

    public void print() {
        for (CropTypes item : productStorage.keySet()) {
            System.out.println(item + " " + productStorage.get(item));
        }
    }
}
