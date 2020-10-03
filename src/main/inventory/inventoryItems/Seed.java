package main.inventory.inventoryItems;

import main.util.crops.CropTypes;

public class Seed extends InventoryItem {


    public Seed(int buyCost, int sellCost, String name) {
        super(buyCost, sellCost, name);
    }

    public Seed(CropTypes type) {
        super(type);
    }
}
