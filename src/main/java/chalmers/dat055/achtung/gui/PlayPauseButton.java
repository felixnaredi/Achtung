package chalmers.dat055.achtung.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PlayPauseButton extends Canvas {
  private boolean mIsPaused;
  private SimplePaintProperty<PlayPauseButton> mBackgroundColor;

  public PlayPauseButton() {
    super();

    mBackgroundColor = new SimplePaintProperty<>("-fx-background-color");
    setBackgroundColor(Color.BLACK);
    backgroundColorProperty().addListener((paint) -> draw());
  }

  private static final StyleablePropertyFactory<PlayPauseButton> FACTORY =
      new StyleablePropertyFactory<>(Canvas.getClassCssMetaData());

  @Override
  public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
    List<CssMetaData<? extends Styleable, ?>> temp = new ArrayList<>(FACTORY.getCssMetaData());
    temp.add(mBackgroundColor.getCssMetaData());
    return Collections.unmodifiableList(temp);
  }

  public ObservableValue<Paint> backgroundColorProperty() { return mBackgroundColor.property(); }
  public final Paint getBackgroundColor() { return mBackgroundColor.get(); }
  public final void setBackgroundColor(Paint paint) { mBackgroundColor.set(paint); }

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
    GraphicsContext g = getGraphicsContext2D();
    g.clearRect(0, 0, getWidth(), getHeight());
    g.setFill(getBackgroundColor());

    if (mIsPaused) {
      drawPauseIcon();
    } else {
      drawPlayIcon();
    }
  }

  public void setPaused(boolean value) {
    if (mIsPaused == value) {
      return;
    }
    mIsPaused = value;
    draw();
  }

  public void toggle() { setPaused(!mIsPaused); }
}