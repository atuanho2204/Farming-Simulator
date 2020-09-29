package main.market;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayEvent;
import main.gameManager.NewDayListener;
import main.market.marketListing.MarketListingController;
import main.welcomeScreen.WelcomeSceneController;


/**
 * The Controller for the MarketUI fxml screen
 */
public class MarketController implements NewDayListener {
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

        marketScreen.getChildren().addAll(
                loadListingUI(),
                loadListingUI(),
                loadListingUI(),
                loadListingUI()
        );
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
            return new HBox(parent);
        } catch (Exception e) {
            return null;
        }
    }

    public void handleNewDay(NewDayEvent e) {
        //update values
        setMarketListings();
    }
}