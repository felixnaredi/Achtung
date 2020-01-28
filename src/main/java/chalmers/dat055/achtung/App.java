package chalmers.dat055.achtung;

import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
  private Thread runLoop;
  private KeyCode keyPressed;

  @Override
  public void start(Stage stage) {
    AtomicBoolean windowVisible = new AtomicBoolean();
    Group pane = new Group();

    Polyline p = new Polyline();
    p.strokeProperty().set(Color.RED);
    p.setStrokeWidth(4);

    pane.getChildren().add(p);

    Scene scene = new Scene(pane, 400, 400);
    scene.fillProperty().set(Color.AQUA);

    stage.setScene(scene);
    stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (e) -> windowVisible.set(false));
    stage.show();
    windowVisible.set(true);

    double[] pitch = {0.0};

    scene.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> keyPressed = e.getCode());
    scene.addEventHandler(KeyEvent.KEY_RELEASED, (e) -> keyPressed = null);

    runLoop = new Thread(() -> {
      double x = 0;
      double y = 0;
      while (windowVisible.get()) {
        try {
          Thread.sleep(16);
        } catch (Exception e) {
          e.printStackTrace();
        }

        if (keyPressed != null) {
          if (keyPressed.equals(KeyCode.A)) {
            pitch[0] += 6.981317007977318e-2;
          } else if (keyPressed.equals(KeyCode.S)) {
            pitch[0] -= 6.981317007977318e-2;
          }
        }

        x += Math.cos(pitch[0]) * 0.5;
        y += Math.sin(pitch[0]) * 0.5;

        final double newX = x;
        final double newY = y;

        Platform.runLater(() -> p.getPoints().addAll(newX, newY));
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
