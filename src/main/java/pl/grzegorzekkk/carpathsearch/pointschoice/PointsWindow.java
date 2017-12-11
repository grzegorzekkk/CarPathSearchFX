package pl.grzegorzekkk.carpathsearch.pointschoice;

import javafx.fxml.FXMLLoader;
import pl.grzegorzekkk.carpathsearch.MyWindow;

import java.io.IOException;

public class PointsWindow extends MyWindow {

    public PointsWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/PointsWindow.fxml"));
        try {
            stage = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PointsWindowController controller = loader.<PointsWindowController>getController();
        controller.setStage(stage);
    }
}
