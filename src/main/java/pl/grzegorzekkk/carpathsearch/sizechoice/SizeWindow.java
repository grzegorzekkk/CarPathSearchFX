package pl.grzegorzekkk.carpathsearch.sizechoice;

import javafx.fxml.FXMLLoader;
import pl.grzegorzekkk.carpathsearch.MyWindow;

import java.io.IOException;

public class SizeWindow extends MyWindow {

    public SizeWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/SizeWindow.fxml"));
        try {
            stage = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SizeWindowController controller = loader.getController();
        controller.setStage(stage);
    }
}
