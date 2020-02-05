package chalmers.dat055.achtung.demo;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DuelController implements Initializable {
  @FXML private Label playButton;
  @FXML private Label pauseButton;

  private boolean mGameIsRunning;

  private void setGameIsRunning(boolean value) {
    mGameIsRunning = value;
    playButton.setText(value ? "Reset" : "Start");
    pauseButton.setDisable(!value);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setGameIsRunning(false);
    
    playButton.setOnMousePressed((e) -> setGameIsRunning(!mGameIsRunning));
  }
}
