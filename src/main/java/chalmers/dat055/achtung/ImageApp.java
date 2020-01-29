package chalmers.dat055.achtung;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ImageApp extends Application {
  @Override
  public void start(Stage stage) {
    final AtomicBoolean windowWillClose = new AtomicBoolean(false);
    final int width = 250;
    final int height = 250;

    Group root = new Group();
    root.getChildren().add(new Label("Hello!"));

    WritableImage image = new WritableImage(width, height);
    PixelWriter writer = image.getPixelWriter();

    int i = 0;
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        Color color = i < 6 ? Color.color(1.0, 0.0, 0.0, 0.5)
                            : i < 12 ? Color.color(1.0, 1.0, 0.0, 0.5) : Color.BLUE;
        writer.setColor(x, y, color);
        i = (i + 1) % 18;
      }
    }

    Canvas canvas = new Canvas(400, 400);
    canvas.getGraphicsContext2D().drawImage(image, 0, 0, width, height, 25, 25, width, height);
    root.getChildren().add(canvas);

    Scene scene = new Scene(root, 400, 400);
    scene.fillProperty().set(Color.color(0.4, 0.0, 0.4, 1.0));
    stage.setScene(scene);
    stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (e) -> windowWillClose.set(true));
    stage.show();

    Thread th = new Thread(() -> {
      double t = 0;

      while (!windowWillClose.get()) {
        try {
          Thread.sleep(16);
        } catch (Exception e) {
          e.printStackTrace();
        }

        t += 0.01;
        final double r = t;
        Platform.runLater(
            () -> scene.fillProperty().set(Color.color(0.4, Math.abs(Math.sin(r)), 0.4, 1.0)));
      }
    });
    th.start();
  }

  public static void main(String[] args) { launch(); }
}
