package pl.grzegorzekkk.carpathsearch.cityboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class CbwController implements Initializable {
    private Stage cbwWindow;

    @FXML
    private Label startCords;

    @FXML
    private Label endCords;

    @FXML
    private Label lockedRoadChance;

    @FXML
    private AnchorPane anPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Point2D startPoint = CityBoard.getInstance().getStartPoint();
        startCords.setText((int) startPoint.getX() + "," + (int) startPoint.getY());

        Point2D endPoint = CityBoard.getInstance().getEndPoint();
        endCords.setText((int) endPoint.getX() + "," + (int) endPoint.getY());

        Double chance = CityBoard.getInstance().getLockedRoadChance();
        lockedRoadChance.setText(chance + "");

        drawCrossings();
    }

    public void setStage(Stage stage) {
        cbwWindow = stage;
    }

    private void drawCrossings() {
        int boardSize = CityBoard.getInstance().getSize();

        int y = 0;
        int cellSize = CityBoard.CELL_SIZE;
        int circleSize = 3;

        for (int i = 0; i < boardSize - 1; ++i) {
            int x = 0;

            Circle firstCircle = new Circle(x, y, circleSize);
            Circle secondCircle = new Circle(x, y + cellSize, circleSize);
            anPane.getChildren().addAll(firstCircle, secondCircle);
            for (int j = 0; j < boardSize - 1; ++j) {
                Line upperLine = new Line(x + circleSize, y, x + cellSize - circleSize, y);
                Line vLine = new Line(x, y + circleSize, x, y + cellSize - circleSize);
                Line lowerLine = new Line(x + circleSize, y + cellSize, x + cellSize - circleSize, y + cellSize);

                if (i == 0) CityBoard.getInstance().addNewRoads(upperLine, vLine, lowerLine);
                else CityBoard.getInstance().addNewRoads(vLine, lowerLine);

                x += 50;
                Circle upperCircle = new Circle(x, y, 3);
                Circle lowerCircle = new Circle(x, y + cellSize, 3);
                anPane.getChildren().addAll(upperLine, lowerLine, vLine, upperCircle, lowerCircle);
            }
            Line vLine = new Line(x, y + circleSize, x, y + cellSize - circleSize);
            anPane.getChildren().add(vLine);

            CityBoard.getInstance().addNewRoads(vLine);

            y += 50;
        }
    }

    @FXML
    private void onStartButtonClick() {
        Map<Line, Integer> roads = CityBoard.getInstance().getRoads();
        roads.forEach((k, v) -> {
            if (v > 0) roads.put(k, 1);
        });

        int startX = (int) CityBoard.getInstance().getStartPoint().getX();
        int startY = (int) CityBoard.getInstance().getStartPoint().getY();
        int endPixelX = (int) (CityBoard.getInstance().getEndPoint().getX() - 1) * CityBoard.CELL_SIZE;
        int endPixelY = (int) (CityBoard.getInstance().getEndPoint().getY() - 1) * CityBoard.CELL_SIZE;

        Circle endCircle = new Circle(endPixelX, endPixelY, 5, Color.RED);
        endCircle.setStroke(Color.BLACK);

        Car car = new Car(startX, startY);
        anPane.getChildren().addAll(car.getCarCircle(), endCircle);
        PathGenerator path = new PathGenerator(car);
        path.prepareBoardData(CityBoard.getInstance(), anPane);
        path.createPath();
        path.display();
    }

    @FXML
    private void onResetButtonClick() {
        Map<Line, Integer> roads = CityBoard.getInstance().getRoads();
        roads.forEach((k, v) -> {
            if (v != 0) roads.put(k, 0);
        });

        anPane.getChildren().removeIf(l -> {
            if (l instanceof Line) {
                return ((Line) l).getStroke() == Color.RED;
            } else
                return l instanceof Circle && ((Circle) l).getFill() == Color.YELLOW;
        });
    }
}
