package main.market;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayEvent;
import main.gameManager.NewDayListener;
import main.inventory.Inventory;
import main.inventory.InventoryItem;
import main.market.marketListing.MarketListingController;
import main.welcomeScreen.WelcomeSceneController;

import java.util.ArrayList;


/**
 * The Controller for the MarketUI fxml screen
 */
public class MarketUIController implements NewDayListener {
    private Stage primaryStage;
    private GameManager gameManager;


    @FXML
    private StackPane marketScreen;

    /**
     * Constructs the Market Scene.
     *
     * @param primaryStage the stage
     * @param gameManager  the gameManager
     */
    public void construct(Stage primaryStage, GameManager gameManager) {
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;
        this.gameManager.getTimeAdvancer().addListener(this);

        marketScreen.getChildren().add(loadListingUI());
        marketScreen.getChildren().add(loadListingUI());
        try {
            Thread.sleep(10);

        } catch (Exception e) {

        }
        marketScreen.getChildren().add(loadListingUI());
        marketScreen.getChildren().add(loadListingUI());
    }

    private Node loadListingUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "marketListing/marketListing.fxml"
                    )
            );
            Parent parent = loader.load();
            MarketListingController controller = loader.getController();
            controller.construct(primaryStage);
            return new Pane(parent);
        } catch (Exception e) {
            return null;
        }
    }

    public void handleNewDay(NewDayEvent e) {
        //update values
        setUIListings(gameManager.getMarket().getMarketListings());
    }

    public void setUIListings(ArrayList<InventoryItem> listings) {

    }
}