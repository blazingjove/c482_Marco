package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partID;
    @FXML
    private TableColumn<Part, Integer>  partName;
    @FXML
    private TableColumn<Part, Integer>  partInventory;
    @FXML
    private TableColumn<Part, Integer>  partCost;
    @FXML
    private TextField partSearchTextField;

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

        //method from mainController.java
        Controller.mainController.partTableMethod(partID, partName, partInventory, partCost, partTable);

        // Create a FilteredList and SortedList for the partTable
        var filteredPartList = new FilteredList<Part>(Inventory.getAllParts(), p -> true);

        // Bind the filtered list to the partSearchTextField text property
        partSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredPartList.setPredicate(part -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            // Compare all part attributes with the search text
            return part.getName().toLowerCase().contains(lowerCaseFilter)
                    || String.valueOf(part.getId()).contains(lowerCaseFilter);
        }));

        // Create a SortedList to display the filtered items in the table
        SortedList<Part> sortedPartList = new SortedList<>(filteredPartList);
        sortedPartList.comparatorProperty().bind(partTable.comparatorProperty());

        // Set the sorted list as the items of the partTable
        partTable.setItems(sortedPartList);


    }
}

