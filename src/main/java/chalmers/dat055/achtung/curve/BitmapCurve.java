package chalmers.dat055.achtung.curve;

import chalmers.dat055.achtung.ImageRenderer;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public abstract class BitmapCurve implements Curve {
  private Canvas mTailCanvas;
  private BitmapCurveMane mMane;

  public abstract Image makeHeadImage(int width);

  public BitmapCurve(int width, int height) {
    mTailCanvas = new Canvas(width, height);

    BitmapCurve self = this;
    mMane = new BitmapCurveMane(4) {
      @Override
      public Image makeHeadImage(int width) {
        return self.makeHeadImage(width);
      }
    };
  }

  public BitmapCurve(double width, double height) {
    this((int)Math.round(width), (int)Math.round(height));
  }

  @Override
  public void addPitch(double rad) {
    mMane.addPitch(rad);
  }

  @Override
  public void setPosition(double x, double y) {
    mMane.setPosition(x, y);
  }

  @Override
  public void setSpeed(double speed) {
    mMane.setSpeed(speed);
  }

  @Override
  public void setStrokeWidth(int width) {
    mMane.setStrokeWidth(width);
  }

  @Override
  public void update() {
    Canvas c = mMane.getLast();
    if (c != null) {
      int strokeWidth = mMane.getStrokeWidth();
      mTailCanvas.getGraphicsContext2D().drawImage(ImageRenderer.fromCanvas(c),
                                                   0,
                                                   0,
                                                   strokeWidth,
                                                   strokeWidth,
                                                   c.getLayoutX(),
                                                   c.getLayoutY(),
                                                   strokeWidth,
                                                   strokeWidth);
    }
    mMane.update();
  }

  @Override
  public void draw(Pane pane) {
    pane.getChildren().add(mTailCanvas);
    mMane.draw(pane);
  }
}