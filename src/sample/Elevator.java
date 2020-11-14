package sample;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public class Elevator {
    private final int DEFAULT_SPEED = 5;
    private final int REFRESH_TIME = 50;
    private int height = 0;
    private int speed = 0;
    private int targetFloor;
    private Rectangle shape;
    Timer timer = new Timer();
    private boolean timerState = false;

    public Elevator(Rectangle shape) {
        this.shape = shape;
    }

    public void go() {
        int period = REFRESH_TIME;
        if (timerState) {
            timer.cancel();
            timerState=false;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(shouldStop()) {this.cancel();timerState=false;}
                    else {
                        timerState = true;
                        move();
                        if (period != REFRESH_TIME) {
                            timer.cancel();
                            timerState=false;
                            go();
                        }
                    }
                });
            }
        }, 0, period);
    }

    public void move() {
        this.setHeight(this.getHeight() + this.getSpeed());
    }

    public boolean shouldStop() {
        return translateFloorToHeight(this.targetFloor) == this.height;
    }

    public void setSpeedForFloor(int floor) {
        int floorHeight = translateFloorToHeight(floor);
        this.speed = this.height < floorHeight ? DEFAULT_SPEED : -DEFAULT_SPEED;
        this.targetFloor = floor;
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private static int translateFloorToHeight(int floor) {
        return - floor * 90;
    }
}
