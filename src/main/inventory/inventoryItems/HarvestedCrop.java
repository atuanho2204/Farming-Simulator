package main.inventory.inventoryItems;

import main.util.crops.CropTypes;

public class HarvestedCrop extends InventoryItem {
    private CropTypes type;

    public HarvestedCrop(int buyCost, int sellCost, String name, CropTypes type) {
        super(buyCost, sellCost, name);
        this.type = type;
    }

    public HarvestedCrop(CropTypes type) {
        super(type);
    }

    public CropTypes getType() {
        return type;
    }
}
