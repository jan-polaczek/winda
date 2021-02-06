package sample;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.*;

public class Elevator {
    enum Mode {
        SIMPLE,
        SMART
    };
    private Mode mode = Mode.SIMPLE;
    private final int DEFAULT_SPEED = 5;
    private final int REFRESH_TIME = 50;
    private final int WAIT_TIME = 1000;
    private final int BASE_ANGLE = 30;
    private final int MAX_ANGLE = 120;
    private boolean shouldStopOverride = false;
    private int height = 0;
    private int speed = 0;
    private int targetFloor = 0;
    private long dontMoveUntil = 0;
    LinkedList<Integer> insideList = new LinkedList<>();
    LinkedList<Integer> outsideList = new LinkedList<>();
    private final Rectangle shape;
    private final Button[] insideButtons;
    private final Button[] outsideButtons;
    private final Rotate floorPointerRotation = new Rotate(BASE_ANGLE, 100, 0);
    private final Circle waitingIndicator;
    Timer timer = new Timer();

    public Elevator(Rectangle shape, Button[] insideButtons, Button[] outsideButtons, Line floorPointer, Circle waitingIndicator) {
        this.shape = shape;
        this.insideButtons = insideButtons;
        this.outsideButtons = outsideButtons;
        this.waitingIndicator = waitingIndicator;
        floorPointer.getTransforms().add(this.floorPointerRotation);
        System.out.println(waitingIndicator.toString());
    }

    public void go()  {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!shouldStop()) {
                        if (dontMoveUntil < System.currentTimeMillis()) {
                            decideMove();
                            setWaitingIndicatorToInactive();
                        }
                    } else {
                        int currentFloor = (int)translateHeightToFloor(height);
                        setButtonToInactive(insideButtons, currentFloor);
                        setButtonToInactive(outsideButtons, currentFloor);
                        setWaitingIndicatorToActive();
                        if (!insideList.isEmpty()) {
                            dontMoveUntil = System.currentTimeMillis() + WAIT_TIME;
                            targetFloor = insideList.removeLast();
                        } else if (!outsideList.isEmpty()) {
                            dontMoveUntil = System.currentTimeMillis() + WAIT_TIME;
                            targetFloor = outsideList.removeLast();
                        }
                    }
                });
            }
        }, 0, REFRESH_TIME);
    }

    private void decideMove() {
        if (this.mode == Mode.SIMPLE) {
            this.move();
        } else if (this.mode == Mode.SMART) {
            float currentFloorHeight = translateHeightToFloor(this.height);
            if (currentFloorHeight % 1 == 0) {
                int currentFloor = (int) currentFloorHeight;
                if (this.insideList.contains(currentFloor) || this.outsideList.contains(currentFloor)) {
                    removeAll(this.insideList, currentFloor);
                    removeAll(this.outsideList, currentFloor);
                    this.shouldStopOverride = true;
                    this.insideList.add(this.targetFloor);
                } else {
                    this.move();
                }
            } else {
                this.move();
            }
        }
    }

    private static void removeAll(LinkedList<Integer> list, Integer element) {
        while (list.contains(element)) {
            list.remove(element);
        }
    }

    public void addFloorFromInside(int floor) {
        if (this.insideList.isEmpty() || this.insideList.peek() != floor) {
            this.insideList.push(floor);
            this.setButtonToActive(this.insideButtons, floor);
        }
    }

    private void setButtonToActive(Button[] list, int floor) {
        list[floor].setStyle("-fx-background-color: #ffe500;  -fx-border-color: #aaaaaa;");
    }

    private void setButtonToInactive(Button[] list, int floor) {
        list[floor].setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #aaaaaa;");
    }

    private void setWaitingIndicatorToActive() {
        this.waitingIndicator.setStyle("-fx-fill: #ffe500");
    }

    private void setWaitingIndicatorToInactive() {
        this.waitingIndicator.setStyle("-fx-fill: #000");
    }

    public void addFloorFromOutside(int floor) {
        if (this.outsideList.isEmpty() || this.outsideList.peek() != floor) {
            this.outsideList.push(floor);
            this.setButtonToActive(this.outsideButtons, floor);
        }
    }

    private void move() {
        this.setSpeedForFloor();
        this.setHeight(this.getHeight() + this.getSpeed());
        this.updateFloorPointer();
    }

    private void updateFloorPointer() {
        float angle = this.MAX_ANGLE * translateHeightToFloor(this.getHeight()) / 4;
        this.floorPointerRotation.angleProperty().setValue(this.BASE_ANGLE + angle);
    }

    public boolean shouldStop() {
        if (this.shouldStopOverride) {
            this.shouldStopOverride = false;
            return true;
        } else {
            return translateFloorToHeight(this.targetFloor) == this.height;
        }
    }

    private void setSpeedForFloor() {
        int floorHeight = translateFloorToHeight(this.targetFloor);
        this.speed = this.height < floorHeight ? DEFAULT_SPEED : -DEFAULT_SPEED;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        shape.setY(height);
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    private static int translateFloorToHeight(int floor) {
        return - floor * 90;
    }

    private static float translateHeightToFloor(int height) {
        return - (float)height / 90;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
