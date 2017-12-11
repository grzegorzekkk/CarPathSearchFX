package pl.grzegorzekkk.carpathsearch;

import javafx.stage.Stage;

public abstract class MyWindow {
    protected Stage stage;

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }
}
