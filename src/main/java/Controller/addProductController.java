package Controller;

import Model.Inventory;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class addProductController implements Initializable {
    @FXML
    public AnchorPane addProductPane;
    private mainController mainController; // Reference to the main controller
    public void setMainControllerRef(mainController mainController) {
        this.mainController = mainController;
    }
    Stage stage;

    //text fields for user inputs
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productInventoryField;
    @FXML
    private TextField productCostField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;

    @FXML
    public void onProductSaveButtonClicked(ActionEvent actionEvent) {
        String name = productNameField.getText();
        double price = Double.parseDouble(productCostField.getText());
        int stock = Integer.parseInt(productInventoryField.getText());
        int min = Integer.parseInt(productMinField.getText());
        int max = Integer.parseInt(productMaxField.getText());
/**
 * TODO: make it so it generates a unique ID
 */
        Product newProduct = new Product(0,name, price, stock,min,max);

        // Call the addProduct method from the Inventory class
        Inventory.addProduct(newProduct);

        //closes window once product is successfully added
        stage = (Stage) addProductPane.getScene().getWindow();
        System.out.println("Product Added");
        stage.close();

        // Show the main view after closing the "Add Part" window
        if (mainController != null) {
            mainController.showMainView();
        }
    }

    public void onAddProductExitClicked (ActionEvent actionEvent){
        stage = (Stage) addProductPane.getScene().getWindow();
        System.out.println("Add product closed");
        stage.close();
        if (mainController != null) {
            mainController.showMainView();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add product Initialized");
    }
}

