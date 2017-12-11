package pl.grzegorzekkk.carpathsearch.cityboard;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import pl.grzegorzekkk.carpathsearch.FileStatsLogger;

import java.util.*;

import static pl.grzegorzekkk.carpathsearch.cityboard.CityBoard.CELL_SIZE;

public class PathGenerator {

    private AnchorPane anchorPane;
    private SequentialTransition st;
    private CityBoard cityBoard;
    private Car car;
    private FileStatsLogger logger;

    public PathGenerator(Car c) {
        st = new SequentialTransition();

        st.setOnFinished(e -> cityBoard.getRoads().forEach((k, v) -> {
            if (v > 0) cityBoard.getRoads().put(k, 1);
        }));

        car = c;
        logger = new FileStatsLogger("stats");
    }

    public void createPath() {
        int boardEndX = (int) cityBoard.getEndPoint().getX();
        int boardEndY = (int) cityBoard.getEndPoint().getY();
        int loopCounter = 0;

        logger.log((car.getY() - 1) * cityBoard.getSize() + car.getX(),
                getDistanceLeft(car.getX(), car.getY()));
        while (car.getX() != boardEndX || car.getY() != boardEndY) {
            findWay();

            loopCounter++;
            if (loopCounter > 100) break;
        }
        logger.close();
    }

    public void prepareBoardData(CityBoard cb, AnchorPane pane) {
        cityBoard = cb;
        anchorPane = pane;
    }

    private int getDistanceLeft(int x, int y) {
        int boardEndX = (int) cityBoard.getEndPoint().getX();
        int boardEndY = (int) cityBoard.getEndPoint().getY();
        int distance = 0;

        distance += Math.abs(boardEndX - x) + Math.abs(boardEndY - y);
        return distance;
    }

    public void display() {
        st.play();
    }

    private boolean canDrive(Direction direction, boolean areOtherLocked) {
        int carPixelX = (car.getX() - 1) * CELL_SIZE;
        int carPixelY = (car.getY() - 1) * CELL_SIZE;
        int carRadius = (int) car.getCarCircle().getRadius();
        Map<Line, Integer> roads = cityBoard.getRoads();
        Random random = new Random();

        Point2D pointOnRoad = null;

        switch (direction) {
            case UP:
                if (car.getY() == 1) return false;
                pointOnRoad = new Point2D(carPixelX, carPixelY - carRadius - 2);
                break;
            case DOWN:
                if (car.getY() == cityBoard.getSize()) return false;
                pointOnRoad = new Point2D(carPixelX, carPixelY + carRadius + 2);
                break;
            case LEFT:
                if (car.getX() == 1) return false;
                pointOnRoad = new Point2D(carPixelX - carRadius - 2, carPixelY);
                break;
            case RIGHT:
                if (car.getX() == cityBoard.getSize()) return false;
                pointOnRoad = new Point2D(carPixelX + carRadius + 2, carPixelY);
                break;
        }
        Line road = CityBoard.getInstance().getRoad(pointOnRoad);

        int driveCounter = roads.get(road);
        boolean isAlreadyDriven = (driveCounter > 0);
        boolean isLocked = (driveCounter == -1);

        if (isLocked || (driveCounter > 1 && !areOtherLocked)) {
            return false;
        }
        if (Double.compare(cityBoard.getLockedRoadChance(), random.nextDouble()) > 0 && !isAlreadyDriven) {
            addLockedRoad(road);

            roads.put(road, -1);
            return false;
        }

        roads.put(road, ++driveCounter);

        st.getChildren().add(car.drive(direction));

        int crossing = (car.getY() - 1) * cityBoard.getSize() + car.getX();
        logger.log(crossing, getDistanceLeft(car.getX(), car.getY()));
        return true;
    }

    private void addLockedRoad(Line road) {
        Line lockedRoad = new Line(road.getStartX(), road.getStartY(),
                road.getEndX(), road.getEndY());
        lockedRoad.setStroke(Color.RED);
        lockedRoad.setStrokeWidth(3.0);
        lockedRoad.setOpacity(0.0);

        FadeTransition ft = new FadeTransition(Duration.seconds(0.3), lockedRoad);
        ft.setToValue(1.0);
        st.getChildren().add(ft);

        anchorPane.getChildren().add(lockedRoad);
    }

    private boolean findWay() {
        ListMultimap<Integer, Direction> shortestPath = MultimapBuilder.treeKeys().linkedListValues().build();

        shortestPath.put(getDistanceLeft(car.getX() + 1, car.getY()), Direction.RIGHT);
        shortestPath.put(getDistanceLeft(car.getX() - 1, car.getY()), Direction.LEFT);
        shortestPath.put(getDistanceLeft(car.getX(), car.getY() + 1), Direction.DOWN);
        shortestPath.put(getDistanceLeft(car.getX(), car.getY() - 1), Direction.UP);

        List<Direction> directions = new LinkedList<>();
        for (Integer distance : shortestPath.keySet()) {
            List<Direction> tmp = new LinkedList<>();
            tmp.addAll(shortestPath.get(distance));
            Collections.shuffle(tmp);
            directions.addAll(tmp);
        }

        return canDrive(directions.get(0), false) || canDrive(directions.get(1), false)
                || canDrive(directions.get(2), false) || canDrive(directions.get(3), true);
    }
}
