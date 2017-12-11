package pl.grzegorzekkk.carpathsearch.cityboard;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Car {
    private Circle carCircle;
    private int x;
    private int y;
    private int cellSize;

    public Car(int x, int y) {
        cellSize = CityBoard.CELL_SIZE;
        carCircle = new Circle((x - 1) * cellSize, (y - 1) * cellSize, 5, Color.YELLOW);
        carCircle.setStroke(Color.BLACK);
        this.x = x;
        this.y = y;
    }

    public TranslateTransition drive(Direction direction) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), carCircle);

        switch (direction) {
            case UP:
                transition.setByY(-cellSize);
                y -= 1;
                break;
            case LEFT:
                transition.setByX(-cellSize);
                x -= 1;
                break;
            case RIGHT:
                transition.setByX(cellSize);
                x += 1;
                break;
            case DOWN:
                transition.setByY(cellSize);
                y += 1;
                break;
        }

        return transition;
    }

    public Circle getCarCircle() {
        return carCircle;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
