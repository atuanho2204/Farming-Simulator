package main.inventory.inventoryItems;

import main.farm.crops.CropTypes;

public class HarvestedCrop extends InventoryItem {
    private final CropTypes type;

    public HarvestedCrop(int buyCost, int sellCost, String name, CropTypes type) {
        super(buyCost, sellCost, name);
        this.type = type;
    }

    /*public HarvestedCrop(Crop crop) {
        this.pesticide = pesticide;
    }*/

    public HarvestedCrop(CropTypes type) {
        super(type);
        this.type = type;
    }

    public CropTypes getType() {
        return type;
    }
}
