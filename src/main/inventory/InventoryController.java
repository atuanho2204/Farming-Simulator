package main.inventory;


import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayEvent;
import main.gameManager.NewDayListener;


/**
 * The Controller for the MarketUI fxml screen
 */
public class InventoryController {
    private Stage primaryStage;
    private GameManager gameManager;


    /**
     * Constructs the Inventory Scene.
     *
     * @param primaryStage the stage
     * @param gameManager the gameManager
     */
    public void construct(Stage primaryStage, GameManager gameManager) {
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;
    }


}