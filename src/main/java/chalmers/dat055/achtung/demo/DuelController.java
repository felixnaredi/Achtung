package chalmers.dat055.achtung.demo;

import java.net.URL;
import java.util.ResourceBundle;

import chalmers.dat055.achtung.gui.PlayPauseButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class DuelController implements Initializable {
  @FXML private Label mStartGameLabel;
  @FXML private PlayPauseButton mPlayPauseButton;

  private boolean mGameIsRunning;

  private void setGameIsRunning(boolean value) {
    mGameIsRunning = value;
    mStartGameLabel.setText(value ? "Reset" : "Start");
    mPlayPauseButton.setDisable(!value);
  }

  private void setGameIsPaused(boolean value) {
    mPlayPauseButton.setPaused(value);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setGameIsRunning(false);
    setGameIsPaused(false);

    mPlayPauseButton.setFill(Color.color(0.6, 0.6, 0.6, 1.0));
    
    mStartGameLabel.setOnMousePressed((e) -> setGameIsRunning(!mGameIsRunning));
    mPlayPauseButton.setOnMousePressed((e) -> mPlayPauseButton.toggle());
  }
}
