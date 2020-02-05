package chalmers.dat055.achtung.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.css.converter.PaintConverter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PlayPauseButton extends Canvas {
  private boolean mIsPaused;

  public PlayPauseButton() {
    super();

    setBackgroundColor(Color.BLACK);
    backgroundColorProperty().addListener((paint) -> draw());
  }

  private static final StyleablePropertyFactory<PlayPauseButton> FACTORY =
      new StyleablePropertyFactory<>(Canvas.getClassCssMetaData());

  private static final CssMetaData<PlayPauseButton, Paint[]> BACKGROUND_COLOR =
      new CssMetaData<PlayPauseButton, Paint[]>("-fx-background-color",
                                                PaintConverter.SequenceConverter.getInstance(),
                                                new Paint[] {Color.BLACK}) {
        @Override
        public boolean isSettable(PlayPauseButton node) {
          return true;
        }

        @Override
        public StyleableProperty<Paint[]> getStyleableProperty(PlayPauseButton node) {
          return node.mBackgroundColor;
        }
      };

  private final StyleableProperty<Paint[]> mBackgroundColor =
      new SimpleStyleableObjectProperty<>(BACKGROUND_COLOR, this, "backgroundColor");

  public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
    List<CssMetaData<? extends Styleable, ?>> temp = new ArrayList<>(FACTORY.getCssMetaData());
    temp.add(BACKGROUND_COLOR);
    return Collections.unmodifiableList(temp);
  }

  @Override
  public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
    return PlayPauseButton.getClassCssMetaData();
  }

  // Typical JavaFX property implementation
  public ObservableValue<Paint[]> backgroundColorProperty() {
    return (ObservableValue<Paint[]>)mBackgroundColor;
  }

  public final Paint getBackgroundColor() { return mBackgroundColor.getValue()[0]; }
  
  public final void setBackgroundColor(Paint paint) {
    mBackgroundColor.setValue(new Paint[] {paint});
  }

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
    mIsPaused = value;
    draw();
  }

  public void toggle() { setPaused(!mIsPaused); }
}