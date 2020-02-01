package chalmers.dat055.achtung;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

class BitmapCurve extends CurveBase {
  private Canvas mTailCanvas;
  private Canvas mHeadCanvas;
  private Paint mSkinPaint;
  private Image mHeadImage;
  private int mStrokeWidth;

  public BitmapCurve(Paint paint, int width, int height) {
    mSkinPaint = paint;
    setStrokeWidth(Curve.defaultStrokeWidth());

    mTailCanvas = new Canvas(width, height);

    setTrackingToggler(new CountToggler(true, 100, 17) {
      @Override
      public void onToggleSet() {}

      @Override
      public void onToggleClear() {}
    });
  }

  @Override
  public void setStrokeWidth(int width) {
    mStrokeWidth = width;
    mHeadImage = ImageRenderer.drawImage(width, width, (g) -> {
      g.setFill(mSkinPaint);
      g.fillOval(0, 0, width, width);
    });
    mHeadCanvas = new Canvas(mStrokeWidth, mStrokeWidth);
  }

  @Override
  public void draw(Pane pane) {
    double x = getPosX();
    double y = getPosY();

    System.out.println(containsPixel(getToungeX(), getToungeY()));

    GraphicsContext headCtx = mHeadCanvas.getGraphicsContext2D();
    mHeadCanvas.relocate(x, y);
    headCtx.clearRect(0, 0, mHeadCanvas.getWidth(), mHeadCanvas.getHeight());
    headCtx.drawImage(
        mHeadImage, 0, 0, mStrokeWidth, mStrokeWidth, 0, 0, mStrokeWidth, mStrokeWidth);

    if (getTrackingEnabled()) {
      mTailCanvas.getGraphicsContext2D().drawImage(
          mHeadImage, 0, 0, mStrokeWidth, mStrokeWidth, x, y, mStrokeWidth, mStrokeWidth);
    }

    pane.getChildren().add(mTailCanvas);
    pane.getChildren().add(mHeadCanvas);
  }

  public boolean containsPixel(double x, double y) {
    Image img = ImageRenderer.fromCanvas(mTailCanvas);
    return !img.getPixelReader().getColor((int)x, (int)y).equals(Color.color(0, 0, 0, 0));
  }

  public double getToungeX() { return getPosX() + Math.cos(getPitch()) * mStrokeWidth; }
  public double getToungeY() { return getPosY() + Math.sin(getPitch()) * mStrokeWidth; }
}