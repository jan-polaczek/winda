package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Controller {
    @FXML
    private Button btnModeSimple;
    @FXML
    private Button btnModeSmart;
    @FXML
    private Button inside0;
    @FXML
    private Button inside1;
    @FXML
    private Button inside2;
    @FXML
    private Button inside3;
    @FXML
    private Button inside4;
    @FXML
    private Button outside0;
    @FXML
    private Button outside1;
    @FXML
    private Button outside2;
    @FXML
    private Button outside3;
    @FXML
    private Button outside4;
    @FXML
    private Rectangle elevatorShape;
    @FXML
    private Line floorPointer;
    @FXML
    private Circle waitingIndicator;

    private Elevator elevator;

    @FXML
    public void initialize() {
        Button[] insideButtons = {inside0, inside1, inside2, inside3, inside4};
        Button[] outsideButtons = {outside0, outside1, outside2, outside3, outside4};
        elevator = new Elevator(elevatorShape, insideButtons, outsideButtons, floorPointer, waitingIndicator);
        btnModeSimple.setStyle("-fx-background-color: #ffe500;  -fx-border-color: #aaaaaa;");
        elevator.go();
    }

    @FXML
    private void handleInsidePress(MouseEvent e) {
        Button btn = (Button) e.getSource();
        String id = btn.getId();
        int floor = Integer.parseInt(id.substring(id.length() - 1));
        elevator.addFloorFromInside(floor);
    }

    @FXML
    private void handleOutsidePress(MouseEvent e) {
        Button btn = (Button) e.getSource();
        String id = btn.getId();
        int floor = Integer.parseInt(id.substring(id.length() - 1));
        elevator.addFloorFromOutside(floor);
    }

    @FXML
    private void handleSimpleModeChoice(MouseEvent e) {
        elevator.setMode(Elevator.Mode.SIMPLE);
        btnModeSimple.setStyle("-fx-background-color: #ffe500;  -fx-border-color: #aaaaaa;");
        btnModeSmart.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #aaaaaa;");
    }

    @FXML
    private void handleSmartModeChoice(MouseEvent e) {
        elevator.setMode(Elevator.Mode.SMART);
        btnModeSmart.setStyle("-fx-background-color: #ffe500;  -fx-border-color: #aaaaaa;");
        btnModeSimple.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #aaaaaa;");
    }
}
