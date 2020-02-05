package chalmers.dat055.achtung.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class PlayPauseButton extends Canvas {
  private boolean mIsPaused;

  private void drawPlayIcon() {
    double w = getWidth();
    double h = getHeight();

    getGraphicsContext2D().fillPolygon(
        new double[] {(1.0 * w + w) / 2, (-0.5 * w + w) / 2, (-0.5 * w + w) / 2},
        new double[] {
            (0.0 * h + h) / 2, (0.8660254037844388 * h + h) / 2, (-0.8660254037844384 * h + h) / 2},
        3);
  }

  private void drawPauseIcon() {
    double w = getWidth();
    double h = getHeight();

    GraphicsContext g = getGraphicsContext2D();
    g.fillRect(w / 7, h * 0.125, 2 * w / 7, h * 0.75);
    g.fillRect(4 * w / 7, h * 0.125, 2 * w / 7, h * 0.75);
  }

  private void draw() {
    getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
    if (mIsPaused) {
      drawPauseIcon();
    } else {
      drawPlayIcon();
    }
  }

  public void setPaused(boolean value) {
    mIsPaused = value;
    draw();
  }

  public void toggle() { setPaused(!mIsPaused); }

  public void setFill(Paint paint) {
    getGraphicsContext2D().setFill(paint);
    draw();
  }
}