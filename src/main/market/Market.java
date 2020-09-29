package main.market;

import main.gameManager.GameManager;
import main.gameManager.NewDayEvent;
import main.gameManager.NewDayListener;
import main.inventory.InventoryItem;
import main.util.Crop;

import java.util.ArrayList;

public class Market implements NewDayListener {
    private ArrayList<Crop> crops;
    private GameManager gameManager;
    private int priceModifier = 1;

    public Market(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameManager.getTimeAdvancer().addListener(this);
    }

    @Override
    public void handleNewDay(NewDayEvent e) {

    }

    public ArrayList<InventoryItem> getMarketListings() {
        return new ArrayList<>();
    }
}
