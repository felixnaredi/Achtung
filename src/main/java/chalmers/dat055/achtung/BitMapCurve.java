package chalmers.dat055.achtung;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

class BitmapCurve implements Curve {
  private double mSpeed;
  private double mPitch;
  private double mX;
  private double mY;
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

    for (int x = 0; x < 12; ++x) {
      for (int y = 0; y < 12; ++y) {
        int d = (x - 6) * (x - 6) + (y - 6) * (y - 6);
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
  public void addPitch(double rad) {
    mPitch += rad;
  }

  @Override
  public void setPosition(double x, double y) {
    mX = x;
    mY = y;
  }

  @Override
  public void setSpeed(double speed) {
    mSpeed = speed;
  }

  @Override
  public void update() {
    mX += Math.cos(mPitch) * mSpeed;
    mY += Math.sin(mPitch) * mSpeed;

    if (--mTrackingToggle < 0) {
      mTrackingToggle = mTrackingEnabled ? 17 : 100;
      mTrackingEnabled = !mTrackingEnabled;
    }
  }

  @Override
  public void draw(Group group) {
    GraphicsContext headCtx = mHeadCanvas.getGraphicsContext2D();
    mHeadCanvas.relocate(mX, mY);
    headCtx.clearRect(0, 0, mHeadCanvas.getWidth(), mHeadCanvas.getHeight());
    headCtx.drawImage(mHeadImage, 0, 0, 12, 12, 0, 0, mStrokeWidth, mStrokeWidth);

    if (mTrackingEnabled) {

      mTailCanvas.getGraphicsContext2D().drawImage(
          mHeadImage, 0, 0, 12, 12, mX, mY, mStrokeWidth, mStrokeWidth);
    }

    group.getChildren().add(mHeadCanvas);
    group.getChildren().add(mTailCanvas);
  }
}