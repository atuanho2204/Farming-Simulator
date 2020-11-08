package main.inventory.inventoryItems;

import main.farm.crops.Crop;
import main.farm.crops.CropTypes;

public class HarvestedCrop extends InventoryItem {
    private final CropTypes type;
    private boolean pesticide = false;

    public HarvestedCrop(int buyCost, int sellCost, String name, CropTypes type) {
        super(buyCost, sellCost, name);
        this.type = type;
    }

    public HarvestedCrop(CropTypes type) {
        super(type);
        this.type = type;
    }

    public CropTypes getType() {
        return type;
    }

    public boolean hasPesticide() {
        return pesticide;
    }
}
