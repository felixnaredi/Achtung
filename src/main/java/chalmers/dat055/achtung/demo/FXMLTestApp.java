package chalmers.dat055.achtung.demo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class FXMLTestApp extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(FXMLTestApp.class.getResource("fxmlapp_main.xml"));
    Scene scene = new Scene(root, 300, 300);
    scene.fillProperty().set(Color.BLUE);

    stage.setScene(scene);
    stage.setTitle("FXMLTestApp");
    stage.show();
  }
}