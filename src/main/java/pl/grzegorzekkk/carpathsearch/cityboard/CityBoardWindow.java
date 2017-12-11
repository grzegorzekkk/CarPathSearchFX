package pl.grzegorzekkk.carpathsearch.cityboard;

import javafx.fxml.FXMLLoader;
import pl.grzegorzekkk.carpathsearch.MyWindow;

import java.io.IOException;

public class CityBoardWindow extends MyWindow {
    public CityBoardWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/BoardWindow.fxml"));

        try {
            stage = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
