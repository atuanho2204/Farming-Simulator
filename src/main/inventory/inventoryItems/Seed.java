package main.inventory.inventoryItems;

import main.farm.crops.CropTypes;

public class Seed extends InventoryItem {
    private CropTypes type;

    public Seed(int buyCost, int sellCost, String name, CropTypes type) {
        super(buyCost, sellCost, name);
        this.type = type;
    }

    public Seed(CropTypes type) {
        super(type);
    }

    public CropTypes getType() {
        return type;
    }
}
