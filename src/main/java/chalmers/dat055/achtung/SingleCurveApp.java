package chalmers.dat055.achtung;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SingleCurveApp extends Application {
  @Override
  public void start(Stage stage) {
    AtomicBoolean windowVisible = new AtomicBoolean();
    Group root = new Group();

    Curve curve = new BitmapCurve(Color.RED, 400, 400);
    curve.setPosition(50, 50);
    curve.setSpeed(0.7);
    curve.setStrokeWidth(7);

    Scene scene = new Scene(root, 400, 400);
    scene.fillProperty().set(Color.color(0.15, 0.15, 0.15, 1.0));

    KeyServer keyServer = new KeyServer(scene);
    keyServer.addHoldListener(KeyCode.A, (n) -> curve.addPitch(0.06981317007977318));
    keyServer.addHoldListener(KeyCode.S, (n) -> curve.addPitch(-0.06981317007977318));

    stage.setScene(scene);
    stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (e) -> windowVisible.set(false));
    stage.show();
    windowVisible.set(true);

    Thread runLoop = new Thread(() -> {
      while (windowVisible.get()) {
        try {
          Thread.sleep(16);
        } catch (Exception e) {
          e.printStackTrace();
        }

        keyServer.poll();
        curve.update();

        Platform.runLater(() -> {
          root.getChildren().clear();
          curve.draw(root);
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
