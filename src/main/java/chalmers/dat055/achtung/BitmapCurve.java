package chalmers.dat055.achtung;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

class BitmapCurve extends CurveBase {
  private static final int HEAD_IMAGE_WIDTH = 12;

  private Canvas mTailCanvas;
  private Canvas mHeadCanvas;
  private WritableImage mHeadImage;
  private int mStrokeWidth;

  public BitmapCurve(Paint paint, int width, int height) {
    mTailCanvas = new Canvas(width, height);
    mHeadCanvas = new Canvas(HEAD_IMAGE_WIDTH, HEAD_IMAGE_WIDTH);

    mHeadImage = new WritableImage(HEAD_IMAGE_WIDTH, HEAD_IMAGE_WIDTH);
    mStrokeWidth = 5;

    PixelWriter writer = mHeadImage.getPixelWriter();
    Color color = (Color)paint;

    for (int x = 0; x < HEAD_IMAGE_WIDTH; ++x) {
      for (int y = 0; y < HEAD_IMAGE_WIDTH; ++y) {
        int d = (x - (HEAD_IMAGE_WIDTH / 2)) * (x - (HEAD_IMAGE_WIDTH / 2)) +
                (y - (HEAD_IMAGE_WIDTH / 2)) * (y - (HEAD_IMAGE_WIDTH / 2));
        if (d < 25) {
          writer.setColor(x, y, color);
        }
      }
    }

    setTrackingToggler(new CountToggler(true, 100, 17) {
      @Override
      public void onToggleSet() {}

      @Override
      public void onToggleClear() {}
    });
  }

  @Override
  public void setStrokeWidth(int width) {
    if (width > mHeadCanvas.getWidth()) {
      mHeadCanvas = new Canvas(width * 2, width * 2);
    }
    mStrokeWidth = width;
  }

  @Override
  public void draw(Pane pane) {
    double x = getPosX();
    double y = getPosY();

    GraphicsContext headCtx = mHeadCanvas.getGraphicsContext2D();
    mHeadCanvas.relocate(x, y);
    headCtx.clearRect(0, 0, mHeadCanvas.getWidth(), mHeadCanvas.getHeight());
    headCtx.drawImage(
        mHeadImage, 0, 0, HEAD_IMAGE_WIDTH, HEAD_IMAGE_WIDTH, 0, 0, mStrokeWidth, mStrokeWidth);

    if (getTrackingEnabled()) {
      mTailCanvas.getGraphicsContext2D().drawImage(
          mHeadImage, 0, 0, HEAD_IMAGE_WIDTH, HEAD_IMAGE_WIDTH, x, y, mStrokeWidth, mStrokeWidth);
    }

    pane.getChildren().add(mHeadCanvas);
    pane.getChildren().add(mTailCanvas);
  }
}