package Controller;

import Model.Inventory;
import Model.Part;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class modifyProductController implements Initializable {
    @FXML
    public AnchorPane modifyProductPane;
    private mainController mainController; // Reference to the main controller
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
    public void setMainControllerRef(mainController mainController) {
        this.mainController = mainController;
    }
    Stage stage;



    //code closes the modify part view and opens main when clicked
    public void modifyProductExitClicked (ActionEvent actionEvent){
        stage = (Stage) modifyProductPane.getScene().getWindow();
        System.out.println("modify product closed");
        stage.close();
        // Show the main view after closing the "Add Part" window
        if (mainController != null) {
            mainController.showMainView();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("modify product Initialized");
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