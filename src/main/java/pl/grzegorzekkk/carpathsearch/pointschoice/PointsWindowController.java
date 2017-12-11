package pl.grzegorzekkk.carpathsearch.pointschoice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import pl.grzegorzekkk.carpathsearch.cityboard.CityBoard;
import pl.grzegorzekkk.carpathsearch.cityboard.CityBoardWindow;
import pl.grzegorzekkk.carpathsearch.language.LangProvider;

import java.net.URL;
import java.util.ResourceBundle;

public class PointsWindowController implements Initializable {

    private Stage pointsWindow;

    @FXML
    private ComboBox<Integer> startPosX;

    @FXML
    private ComboBox<Integer> startPosY;

    @FXML
    private ComboBox<Integer> endPosX;

    @FXML
    private ComboBox<Integer> endPosY;

    @FXML
    private TextField lockedChanceField;

    @FXML
    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 1; i <= CityBoard.getInstance().getSize(); i++) {
            startPosX.getItems().add(i);
            startPosY.getItems().add(i);
            endPosX.getItems().add(i);
            endPosY.getItems().add(i);
        }

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onButtonClick();
            }
        });
    }

    @FXML
    private void onButtonClick() {
        int startX, startY, endX, endY;
        double chance;
        LangProvider lp = LangProvider.getInstance();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(lp.getMessage("error"));
        alert.setHeaderText(lp.getMessage("error.main"));

        try {
            startX = startPosX.getSelectionModel().getSelectedItem();
            startY = startPosY.getSelectionModel().getSelectedItem();
            endX = endPosX.getSelectionModel().getSelectedItem();
            endY = endPosY.getSelectionModel().getSelectedItem();
        } catch (NullPointerException ex) {
            alert.setContentText(lp.getMessage("error.points"));
            alert.showAndWait();
            return;
        }
        if (!validateChanceInput()) return;
        if (startX == endX && startY == endY) {
            alert.setContentText(lp.getMessage("error.start_equals_end"));
            alert.showAndWait();
            return;
        }
        chance = Double.valueOf(lockedChanceField.getText());

        CityBoard.getInstance().setStartPoint(new Point2D(startX, startY));
        CityBoard.getInstance().setEndPoint(new Point2D(endX, endY));
        CityBoard.getInstance().setLockedRoadChance(chance);

        pointsWindow.close();
        CityBoardWindow cbw = new CityBoardWindow();
        cbw.show();
    }

    private boolean validateChanceInput() {
        String input = lockedChanceField.getText();
        double chance;
        LangProvider lp = LangProvider.getInstance();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(lp.getMessage("error"));
        alert.setHeaderText(lp.getMessage("error.main"));

        try {
            chance = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            alert.setContentText(lp.getMessage("error.invalid_double"));
            alert.showAndWait();
            return false;
        }

        if (Double.compare(0.0, chance) > 0 || Double.compare(1.0, chance) < 0) {
            alert.setContentText(lp.getMessage("error.out_of_bounds"));
            alert.showAndWait();
            return false;
        }

        return true;
    }

    public void setStage(Stage stage) {
        pointsWindow = stage;
    }

}
