package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class modifyProductController implements Initializable {
    @FXML
    public AnchorPane modifyProductPane;
    private mainController mainController; // Reference to the main controller
    public void setMainControllerRef(mainController mainController) {
        this.mainController = mainController;
    }
    Stage stage;
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
    }
}