package pl.grzegorzekkk.carpathsearch.sizechoice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import pl.grzegorzekkk.carpathsearch.cityboard.CityBoard;
import pl.grzegorzekkk.carpathsearch.pointschoice.PointsWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class SizeWindowController implements Initializable {

    private Stage sizeWindow;

    @FXML
    private ComboBox<String> sizesBox;

    @FXML
    private Scene scene;

    @FXML
    private void buttonAction() {
        String choosedSize = sizesBox.getSelectionModel().getSelectedItem();
        int parsedSize;
        try {
            parsedSize = Integer.parseInt(choosedSize.substring(0, 2));
        } catch (NumberFormatException ex) {
            parsedSize = Character.getNumericValue(choosedSize.charAt(0));
        }
        CityBoard.getInstance().setSize(parsedSize);

        sizeWindow.close();

        PointsWindow pw = new PointsWindow();
        pw.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 2; i <= 10; i++) {
            sizesBox.getItems().add(i + " x " + i);
        }

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                buttonAction();
            }
        });
    }

    public void setStage(Stage stage) {
        sizeWindow = stage;
    }
}
