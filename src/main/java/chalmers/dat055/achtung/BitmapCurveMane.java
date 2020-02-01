package chalmers.dat055.achtung;

import java.util.LinkedList;
import java.util.stream.Collectors;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * The mane region of a bitmap curve is a region from the head to a given length of images that no
 * one can collide with.
 *
 * <p> TODO: This class should be hidden from the user and only really interact in a symbiotic way
 * with the class {@code BitmapCurve}.
 */
abstract class BitmapCurveMane extends CurveBase {
  private Image mHeadImage;
  private LinkedList<Canvas> mCanvasNeck;

  public abstract Image makeHeadImage(int width);

  public BitmapCurveMane(int length) {
    super();

    int strokeWidth = Curve.defaultStrokeWidth();
    mHeadImage = makeHeadImage(strokeWidth);

    mCanvasNeck = new LinkedList<>();
    for (int i = 0; i < length - 1; ++i) {
      mCanvasNeck.add(null);
    }
    mCanvasNeck.addFirst(new Canvas(strokeWidth, strokeWidth));
    setStrokeWidth(strokeWidth);

    setTrackingToggler(new CountToggler(true, 100, 50) {
      @Override
      public void onToggleSet() {}

      @Override
      public void onToggleClear() {}
    });
  }

  public int getStrokeWidth() { return (int)mCanvasNeck.getFirst().getWidth(); }

  @Override
  public void setPosition(double x, double y) {
    super.setPosition(x, y);
    mCanvasNeck.getFirst().relocate(x, y);
  }

  @Override
  public void update() {
    super.update();

    int strokeWidth = getStrokeWidth();

    if (!getTrackingEnabled()) {
      mCanvasNeck.removeFirst();
      mCanvasNeck.addFirst(null);
    }

    Canvas c = mCanvasNeck.removeLast();

    if (c == null) {
      c = new Canvas(strokeWidth, strokeWidth);
      c.getGraphicsContext2D().drawImage(
          mHeadImage, 0, 0, strokeWidth, strokeWidth, 0, 0, strokeWidth, strokeWidth);
    }
    mCanvasNeck.addFirst(c);
    c.relocate(getPosX(), getPosY());
  }

  @Override
  public void setStrokeWidth(int width) {
    mHeadImage = makeHeadImage(width);

    mCanvasNeck =
        new LinkedList<Canvas>(mCanvasNeck.stream()
                                   .map((c) -> {
                                     if (c == null) {
                                       return null;
                                     }
                                     Canvas r = new Canvas(width, width);
                                     r.relocate(c.getLayoutX(), c.getLayoutY());
                                     r.getGraphicsContext2D().drawImage(
                                         mHeadImage, 0, 0, width, width, 0, 0, width, width);
                                     return r;
                                   })
                                   .collect(Collectors.toList()));
  }

  @Override
  public void draw(Pane pane) {
    mCanvasNeck.stream().filter((c) -> c != null).forEach(pane.getChildren()::add);
  }

  public Canvas getLast() { 
    return mCanvasNeck.getLast(); 
  }

  public double getToungeX() {
    double radius = getStrokeWidth() / 2;
    return getPosX() + radius + Math.cos(getPitch()) * radius;
  }

  public double getToungeY() {
    double radius = getStrokeWidth();
    return getPosY() + radius + Math.sin(getPitch()) * radius;
  }
}