package main.inventory;

import main.gameManager.GameManager;
import main.inventory.inventoryItems.HarvestedCrop;
import main.util.AlertUser;
import main.util.UIManager;
import main.farm.crops.CropTypes;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Inventory {
    private PropertyChangeSupport changeSupport;
    private final int storageLimit = 20;
    private final HashMap<CropTypes, Integer> seedStorage;
    private final int productLimit = 10;
    private final ArrayList<HarvestedCrop> products;
    private int fertilizerTank = 10;
    private int currentFertilizer = 0;


    public Inventory(boolean storeOriginalSeeds) {
        this.changeSupport = new PropertyChangeSupport(this);
        this.products = new ArrayList<>(productLimit);
        this.seedStorage = new HashMap<>();
        if (storeOriginalSeeds) { //if we should store the original amount of seeds
            for (CropTypes type : GameManager.getInstance().getSeeds()) {
                seedStorage.put(type, 2);
            }
        }
    }
    public void putFertilizer(int amount) throws Exception {
        if (currentFertilizer == fertilizerTank && amount > 0) {
            throw new Exception("Your fertilizer tank is full");
        }
        if (currentFertilizer == 0 && amount < 0) {
            throw new Exception("You don't have enough fertilizer");
        }
        if (amount > 0) {
            currentFertilizer = fertilizerTank;
        } else {
            currentFertilizer += amount;
        }
        changeSupport.firePropertyChange("Inventory", "", "Fertilizer tank changed");
    }

    public int getFertilizer() {
        return currentFertilizer;
    }

    public void putSeed(CropTypes type) throws Exception {
        if (type == null || getStorageSize() == getStorageLimit()) {
            throw new Exception();
        } else {
            seedStorage.put(type, seedStorage.getOrDefault(type, 0) + 1);
            changeSupport.firePropertyChange(
                    "Inventory", "", "seed added");
        }
    }

    public void removeSeed(CropTypes type) throws NoSuchElementException {
        if (type == null || getStorageSize() == 0 || !seedStorage.containsKey(type)) {
            throw new NoSuchElementException();
        } else {
            if (seedStorage.get(type) == 1) {
                seedStorage.remove(type);
            } else {
                seedStorage.put(type, seedStorage.get(type) - 1);
            }
            changeSupport.firePropertyChange(
                    "Inventory", "", "seed removed");
        }
    }


    /**
     * Puts the given crop into storage.
     *
     * @param crop the crop to store
     * @throws Exception an exception because there isn't any space
     */
    public void putProduct(HarvestedCrop crop) throws Exception {
        if (crop == null || products.size() >= productLimit) {
            throw new Exception("There is no space in product storage");
        } else {
            products.add(crop);
            changeSupport.firePropertyChange(
                    "Inventory", "", "Crop added");
        }
    }

    public void removeProduct(HarvestedCrop crop) throws NoSuchElementException {
        if (crop == null || products.size() == 0) {
            throw new NoSuchElementException("The crop given is null, or there are no products");
        } else if (!products.contains(crop)) {
            throw new NoSuchElementException("The crop doesn't exist in the products list");
        } else {
            products.remove(crop);
            changeSupport.firePropertyChange(
                    "Inventory", "", "Crop removed");
        }
    }

    public static void sellProduct(HarvestedCrop crop) {
        try {
            GameManager.getInstance().getInventory().removeProduct(crop);
            int newMoney = GameManager.getInstance().getMoney() + crop.getSellCost();
            GameManager.getInstance().setMoney(newMoney);
            UIManager.getInstance().pushUIUpdate();
        } catch (Exception e) {
            AlertUser.alertUser("You do not have that product in your inventory");
        }
    }

    public int getStorageSize() {
        int size = 0;
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

    public ArrayList<HarvestedCrop> getProducts() {
        return products;
    }

    public void subscribeToChanges(PropertyChangeListener l) {
        changeSupport.addPropertyChangeListener(l);
    }
}
