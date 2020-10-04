package main.gameManager;

import main.inventory.Inventory;

public interface NewDayListener {
    void handleNewDay(NewDayEvent e);
}