package pl.grzegorzekkk.carpathsearch.cityboard;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CityBoard {
    public static final int CELL_SIZE = 50;

    private static CityBoard instance;
    private int size;
    private Point2D startPoint;
    private Point2D endPoint;
    private double lockedRoadChance;
    // 0 - unknown, >0 - driveable, -1 - locked
    private HashMap<Line, Integer> roads;

    private CityBoard() {
        roads = new HashMap<>();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Point2D getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point2D startPoint) {
        this.startPoint = startPoint;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    public double getLockedRoadChance() {
        return lockedRoadChance;
    }

    public void setLockedRoadChance(double lockedRoadChance) {
        this.lockedRoadChance = lockedRoadChance;
    }

    public void addNewRoads(Line... roadArr) {
        Arrays.stream(roadArr).forEach(road -> roads.put(road, 0));
    }

    public Map<Line, Integer> getRoads() {
        return roads;
    }

    public Line getRoad(Point2D pointOnRoad) {
        for (Line road : roads.keySet()) {
            if (road.contains(pointOnRoad)) {
                return road;
            }
        }
        throw new NullPointerException();
    }

    public static CityBoard getInstance() {
        if (instance == null) {
            instance = new CityBoard();
        }
        return instance;
    }
}
