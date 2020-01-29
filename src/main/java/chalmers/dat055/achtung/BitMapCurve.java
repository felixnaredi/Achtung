package chalmers.dat055.achtung;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

class BitmapCurve extends CurveBase {
  private static final int HEAD_IMAGE_WIDTH = 12;

  private Canvas mTailCanvas;
  private Canvas mHeadCanvas;
  private WritableImage mHeadImage;
  private int mStrokeWidth;
  private boolean mTrackingEnabled;
  private int mTrackingToggle;

  public BitmapCurve(Paint paint, int width, int height) {
    mTrackingEnabled = true;
    mTrackingToggle = 100;

    mTailCanvas = new Canvas(width, height);
    mHeadCanvas = new Canvas(8, 8);

    mHeadImage = new WritableImage(12, 12);
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
  }

  @Override
  public void setStrokeWidth(int width) {
    if (width > mHeadCanvas.getWidth()) {
      mHeadCanvas = new Canvas(width * 2, width * 2);
    }
    mStrokeWidth = width;
  }

  @Override
  public void update() {
    super.update();

    if (--mTrackingToggle < 0) {
      mTrackingToggle = mTrackingEnabled ? 17 : 100;
      mTrackingEnabled = !mTrackingEnabled;
    }
  }

  @Override
  public void draw(Group group) {
    double x = getPosX();
    double y = getPosY();

    GraphicsContext headCtx = mHeadCanvas.getGraphicsContext2D();
    mHeadCanvas.relocate(x, y);
    headCtx.clearRect(0, 0, mHeadCanvas.getWidth(), mHeadCanvas.getHeight());
    headCtx.drawImage(
        mHeadImage, 0, 0, HEAD_IMAGE_WIDTH, HEAD_IMAGE_WIDTH, 0, 0, mStrokeWidth, mStrokeWidth);

    if (mTrackingEnabled) {
      mTailCanvas.getGraphicsContext2D().drawImage(
          mHeadImage, 0, 0, HEAD_IMAGE_WIDTH, HEAD_IMAGE_WIDTH, x, y, mStrokeWidth, mStrokeWidth);
    }

    group.getChildren().add(mHeadCanvas);
    group.getChildren().add(mTailCanvas);
  }
}