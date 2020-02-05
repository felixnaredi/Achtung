package chalmers.dat055.achtung.demo;

import java.net.URL;
import java.util.ResourceBundle;

import chalmers.dat055.achtung.gui.PlayPauseButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DuelController implements Initializable {
  @FXML private Label mStartGameLabel;
  @FXML private PlayPauseButton mPlayPauseButton;

  private boolean mGameIsRunning;

  private void setGameIsRunning(boolean value) {
    mGameIsRunning = value;
    mStartGameLabel.setText(value ? "Reset" : "Start");
    mPlayPauseButton.setDisable(!value);
    mPlayPauseButton.setPaused(value);
  }

  private void setGameIsPaused(boolean value) {
    mPlayPauseButton.setPaused(value);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setGameIsRunning(false);
    setGameIsPaused(false);
    
    mStartGameLabel.setOnMousePressed((e) -> setGameIsRunning(!mGameIsRunning));
    mPlayPauseButton.setOnMousePressed((e) -> mPlayPauseButton.toggle());
  }
}
