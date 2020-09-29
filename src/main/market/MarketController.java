package main.market;


import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayEvent;
import main.gameManager.NewDayListener;


/**
 * The Controller for the MarketUI fxml screen
 */
public class MarketController implements NewDayListener {
    private Stage primaryStage;
    private GameManager gameManager;


    /**
     * Constructs the Market Scene.
     *
     * @param primaryStage the stage
     * @param gameManager the gameManager
     */
    public void construct(Stage primaryStage, GameManager gameManager) {
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;
    }

    public void handleNewDay(NewDayEvent e) {
        //update values
        setMarketListings();
    }

    public void getMarketListings() {

    }

    private void setMarketListings() {

    }
}