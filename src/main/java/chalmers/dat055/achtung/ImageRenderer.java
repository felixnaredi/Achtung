package chalmers.dat055.achtung;

import java.util.function.Consumer;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ImageRenderer {
  public static Image drawImage(int width, int height, Consumer<GraphicsContext> drawFunction) {
    Canvas canvas = new Canvas(width, height);
    drawFunction.accept(canvas.getGraphicsContext2D());

    Scene scene = new Scene(new StackPane(canvas), width, height);
    scene.fillProperty().set(Color.color(0, 0, 0, 0));

    WritableImage img = new WritableImage(width, height);
    canvas.snapshot(null, img);

    return img;
  }

  public static Image fromCanvas(Canvas canvas) {
    int width = (int) canvas.getWidth();
    int height = (int) canvas.getHeight();

    double x = canvas.getLayoutX();
    double y = canvas.getLayoutY();
    
    Scene scene = new Scene(new StackPane(canvas), width, height);
    scene.fillProperty().set(Color.color(0, 0, 0, 0));

    WritableImage img = new WritableImage(width, height);
    canvas.snapshot(null, img);

    canvas.relocate(x, y);

    return img;
  }
}