package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Controller {
    @FXML
    public Button inside0;
    @FXML
    public Button inside1;
    @FXML
    public Button inside2;
    @FXML
    public Button inside3;
    @FXML
    public Button inside4;
    @FXML
    public Button outside0;
    @FXML
    public Button outside1;
    @FXML
    public Button outside2;
    @FXML
    public Button outside3;
    @FXML
    public Button outside4;
    @FXML
    private Rectangle elevatorShape;

    private Elevator elevator;

    @FXML
    public void initialize() {
        Button[] insideButtons = {inside0, inside1, inside2, inside3, inside4};
        Button[] outsideButtons = {outside0, outside1, outside2, outside3, outside4};
        elevator = new Elevator(elevatorShape, insideButtons, outsideButtons);
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
    }

    @FXML
    private void handleSmartModeChoice(MouseEvent e) {
        elevator.setMode(Elevator.Mode.SMART);
    }
}
