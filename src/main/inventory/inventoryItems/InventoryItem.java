package main.inventory.inventoryItems;

import main.farm.crops.CropCatalog;
import main.farm.crops.CropTypes;

public class InventoryItem {
    private int buyCost;
    private int sellCost;
    private float priceModifier = 1;
    private String name;

    public InventoryItem(CropTypes type) {
        this.buyCost = CropCatalog.getInstance().getCropDetails(type).getBaseBuy();
        this.sellCost = CropCatalog.getInstance().getCropDetails(type).getBaseSell() * 2;
        this.name = type.name().toLowerCase();
    }

    public InventoryItem(int buyCost, int sellCost, String name) {
        this.buyCost = buyCost;
        this.sellCost = sellCost;
        this.name = name;
    }

    public InventoryItem(InventoryItem item) {
        this.buyCost = item.buyCost;
        this.sellCost = item.sellCost;
        this.name = item.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellCost() {
        return (int) (sellCost * priceModifier);
    }

    public void setSellCost(int sellCost) {
        this.sellCost = sellCost;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }
}
