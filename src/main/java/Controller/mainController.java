package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class "mainController" provides logic for the main screen of the app
 * @author Marco Alvarez
 */

public class mainController implements Initializable{


    // blow is the logic to exit main view when exit button is hit
    @FXML
    private AnchorPane mainPane;
    Stage stage;
    public void onExitClicked(ActionEvent actionEvent) {
        stage = (Stage) mainPane.getScene().getWindow();
        System.out.println("Program closed");
        stage.close();
    }

    //variables for parts
    //public Button onPartModify;
    //public Button partDeleteButton;
    //public Button onPartAdd;

    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer>  partID;
    @FXML
    private TableColumn<Part, Integer>  partName;
    @FXML
    private TableColumn<Part, Integer>  partInventory;
    @FXML
    private TableColumn<Part, Integer>  partCost;

    //variable for products
    //public Button onProductModify;
    //public Button productDeleteButton;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer>  productID;
    @FXML
    private TableColumn<Product, Integer>  productName;
    @FXML
    private TableColumn<Product, Integer>  productInventory;
    @FXML
    private TableColumn<Product, Integer>  productCost;
    public Button onProductAdd;

    //add Part button opens add part window
    @FXML
    public void onPartAdd(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/c482/views/addPartView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);

            // Pass the mainController reference to addPartController
            addPartController addPartController = fxmlLoader.getController();
            addPartController.setMainControllerRef(this);

            // Hide the main view
            mainPane.getScene().getWindow().hide();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to show the main view
    public void showMainView() {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.show();
    }

    // modify part button opens modify part window and closes main temp
    @FXML
    public void onPartModify(ActionEvent actionEvent) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/c482/views/modifyPartView.fxml"));
                Parent root = loader.load();

                //pulls the data of the item that is selected
                modifyPartController modifyPartController = loader.getController();
                modifyPartController.setMainControllerRef(this);
                modifyPartController.setSelectedPart(selectedPart);

                //open modify part scene
                Stage stage = new Stage();
                stage.setTitle("Modify Part");
                stage.setScene(new Scene(root));

                //show main when part mod closed
                mainPane.getScene().getWindow().hide();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // onPartDelete button set to delete data selected
    @FXML
    public void onPartDelete(ActionEvent actionEvent){
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
    }

    // onProductDelete button will delete selected product
    @FXML
    public void onProductDelete(ActionEvent actionEvent){
        System.out.println("test1");
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selectedProduct);
    }


    //opens product add view and closes main temporarily
    @FXML
    public void onProductAdd(ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/c482/views/addProductView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);

            // Pass the mainController reference to addPartController
            addProductController addProductController = fxmlLoader.getController();
            addProductController.setMainControllerRef(this);

            //hide the main view
            mainPane.getScene().getWindow().hide();

            // Show the add product view
            stage.show();
        }         catch (IOException e) {
            e.printStackTrace();
        }
    }

        @FXML
        public void onProductModify(ActionEvent actionEvent){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/c482/views/modifyProductView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setScene(scene);

                // Pass the mainController reference to addPartController
                modifyProductController modifyProductController = fxmlLoader.getController();
                modifyProductController.setMainControllerRef(this);

                //hide the main view
                mainPane.getScene().getWindow().hide();

                //show on product mod view
                stage.show();
            }         catch (IOException e) {
                e.printStackTrace();
            }
        }

    //Listener for part text field search
    //calling FXML elements
    @FXML
    private TextField partSearchTextField;
    @FXML
    private TextField productSearchTextField;




    //initialized command when code is ran
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTable.setItems(Inventory.getAllParts());

        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(Inventory.getAllProducts());


        // Create a FilteredList and SortedList for the partTable
        FilteredList<Part> filteredPartList = new FilteredList<>(Inventory.getAllParts(), p -> true);

        // Bind the filtered list to the partSearchTextField text property
        partSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPartList.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Compare all part attributes with the search text
                return part.getName().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(part.getId()).contains(lowerCaseFilter);
            });
        });

        // Create a SortedList to display the filtered items in the table
        SortedList<Part> sortedPartList = new SortedList<>(filteredPartList);
        sortedPartList.comparatorProperty().bind(partTable.comparatorProperty());

        // Set the sorted list as the items of the partTable
        partTable.setItems(sortedPartList);

        /**
         * same as  above to filter product text area input in product table
         */

        // Create a FilteredList and SortedList for the ProductTable
        FilteredList<Product> filteredProductList = new FilteredList<>(Inventory.getAllProducts(), p -> true);

// Bind the filtered list to the productSearchTextField text property
        productSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProductList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Compare all Product attributes with the search text
                return product.getName().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(product.getId()).contains(lowerCaseFilter);
            });
        });

// Create a SortedList to display the filtered items in the table
        SortedList<Product> sortedProductList = new SortedList<>(filteredProductList);
        sortedProductList.comparatorProperty().bind(productTable.comparatorProperty());

// Set the sorted list as the items of the product table
        productTable.setItems(sortedProductList);



        System.out.println("I am Initialized");
    }
}