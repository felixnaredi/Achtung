package chalmers.dat055.achtung.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DuelApp extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    stage.setScene(
        new Scene(FXMLLoader.load(DuelApp.class.getResource("duelapp_main.xml")), 611, 611));
    stage.setTitle("Duel");
    stage.show();
  }
}
