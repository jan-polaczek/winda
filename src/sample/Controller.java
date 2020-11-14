package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Controller {
    @FXML
    private Rectangle elevatorShape;

    private Elevator elevator;

    @FXML
    public void initialize() {
        elevator = new Elevator(elevatorShape);
    }

    @FXML
    private void handleInsidePress(MouseEvent e) {
        Button btn = (Button) e.getSource();
        String id = btn.getId();
        int floor = Integer.parseInt(id.substring(id.length() - 1));
        elevator.setSpeedForFloor(floor);
        elevator.go();
    }
}
