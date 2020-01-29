package chalmers.dat055.achtung;

import chalmers.dat055.achtung.Curve;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
  private Thread runLoop;
  private KeyCode keyPressed;

  @Override
  public void start(Stage stage) {
    AtomicBoolean windowVisible = new AtomicBoolean();
    Group pane = new Group();

    Curve curve = new Curve(Color.RED);
    curve.setPosition(50, 50);
    curve.setSpeed(0.6);

    Scene scene = new Scene(pane, 400, 400);
    scene.fillProperty().set(Color.AQUA);

    stage.setScene(scene);
    stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (e) -> windowVisible.set(false));
    stage.show();
    windowVisible.set(true);

    scene.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> keyPressed = e.getCode());
    scene.addEventHandler(KeyEvent.KEY_RELEASED, (e) -> keyPressed = null);

    runLoop = new Thread(() -> {
      while (windowVisible.get()) {
        try {
          Thread.sleep(16);
        } catch (Exception e) {
          e.printStackTrace();
        }

        if (keyPressed != null) {
          if (keyPressed.equals(KeyCode.A)) {
            curve.addPitch(0.06981317007977318);
          } else if (keyPressed.equals(KeyCode.S)) {
            curve.addPitch(-0.06981317007977318);
          }
        }

        curve.update();

        Platform.runLater(() -> {
          pane.getChildren().clear();
          pane.getChildren().setAll(curve.getPolylines().collect(Collectors.toList()));
        });
      }
    });

    runLoop.start();
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    Platform.exit();
  }

  public static void main(String[] args) { launch(); }
}
