package main.inventory;

public class InventoryItem {
    private int buyCost;
    private int sellCost;
    private int fillSpace = 1;
    private String name;

    public InventoryItem(int buyCost, int sellCost, String name) {
        this.buyCost = buyCost;
        this.sellCost = sellCost;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFillSpace() {
        return fillSpace;
    }

    public void setFillSpace(int fillSpace) {
        this.fillSpace = fillSpace;
    }

    public int getSellCost() {
        return sellCost;
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
