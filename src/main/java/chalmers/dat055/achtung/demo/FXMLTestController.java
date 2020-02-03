package chalmers.dat055.achtung.demo;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class FXMLTestController implements Initializable {
  @FXML private Label helloLabel;
  @FXML private Label worldLabel;
  @FXML private Label markLabel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    for (Label l : Arrays.asList(helloLabel, worldLabel, markLabel)) {
      l.addEventFilter(MouseEvent.MOUSE_ENTERED, (e) -> System.out.println(l.textProperty().get()));
      l.cursorProperty().set(Cursor.HAND);
      l.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> System.out.println("new phone who dis?"));
    }
  }
}