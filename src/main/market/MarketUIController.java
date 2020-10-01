package main.market;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayEvent;
import main.gameManager.NewDayListener;
import main.inventory.InventoryItem;
import main.market.marketListing.MarketListingController;


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

        setMarketListings();
    }

    private void setMarketListings() {
        try {
            for (InventoryItem listing : gameManager.getMarket().getMarketListings()) {
                marketScreen.getChildren().add(loadListingUI(listing));
                System.out.println("Loading market item: "
                        + listing.getName()
                        + " with price: "
                        + listing.getBuyCost());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            for (StackTraceElement l : e.getStackTrace()) {
                System.out.println(l);
            }
        }
    }

    private Node loadListingUI(InventoryItem listing) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "marketListing/marketListing.fxml"
                    )
            );
            Parent parent = loader.load();
            MarketListingController controller = loader.getController();
            controller.construct(primaryStage, listing);
            return new Pane(parent);
        } catch (Exception e) {
            return null;
        }
    }

    public void handleNewDay(NewDayEvent e) {
        //update values
        setMarketListings();
    }
}