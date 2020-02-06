package chalmers.dat055.achtung.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.css.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class PlayPauseButton extends Canvas {
  private boolean mIsPaused;
  private StyleableProperty<Paint> mBackgroundColor;
  private StyleableProperty<Paint> mStrokeColor;
  private StyleableProperty<Paint> mIconFillColor;
  private StyleableProperty<Paint> mIconStrokeColor;

  public PlayPauseButton() {
    super();

    mBackgroundColor = new SimpleStyleableObjectProperty<>(FACTORY.createPaintCssMetaData(
        "-achtung-background-color", (self) -> self.mBackgroundColor));
    backgroundColorProperty().addListener((obs, ov, nv) -> draw());

    mStrokeColor = new SimpleStyleableObjectProperty<>(
        FACTORY.createPaintCssMetaData("-achtung-stroke-color", (self) -> self.mStrokeColor));
    strokeColorProperty().addListener((obs, ov, nv) -> draw());

    mIconFillColor = new SimpleStyleableObjectProperty<>(
        FACTORY.createPaintCssMetaData("-achtung-icon-fill-color", (self) -> self.mIconFillColor));
    iconFillColorProperty().addListener((obs, ov, nv) -> draw());

    mIconStrokeColor = new SimpleStyleableObjectProperty<>(FACTORY.createPaintCssMetaData(
        "-achtung-icon-stroke-color", (self) -> self.mIconStrokeColor));
    iconStrokeColorProperty().addListener((obs, ov, nv) -> draw());
  }

  private static final StyleablePropertyFactory<PlayPauseButton> FACTORY =
      new StyleablePropertyFactory<>(Canvas.getClassCssMetaData());

  @Override
  public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
    List<CssMetaData<? extends Styleable, ?>> temp = new ArrayList<>(FACTORY.getCssMetaData());

    temp.add(mBackgroundColor.getCssMetaData());
    temp.add(mStrokeColor.getCssMetaData());
    temp.add(mIconFillColor.getCssMetaData());
    temp.add(mIconStrokeColor.getCssMetaData());

    return Collections.unmodifiableList(temp);
  }

  private void drawPlayIcon() {
    double w = getWidth();
    double h = getHeight();

    getGraphicsContext2D().fillPolygon(
        new double[] {(1.0 * w + w) / 2, (-0.5 * w + w) / 2, (-0.5 * w + w) / 2},
        new double[] {
            (0.0 * h + h) / 2, (0.8660254037844388 * h + h) / 2, (-0.8660254037844384 * h + h) / 2},
        3);
    getGraphicsContext2D().strokePolygon(
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
    g.strokeRect(w / 7, h * 0.125, 2 * w / 7, h * 0.75);
    g.strokeRect(4 * w / 7, h * 0.125, 2 * w / 7, h * 0.75);
  }

  private void draw() {
    GraphicsContext g = getGraphicsContext2D();
    g.clearRect(0, 0, getWidth(), getHeight());
    
    g.setFill(getBackgroundColor());
    g.setStroke(getStrokeColor());
    g.fillOval(0, 0, getWidth(), getHeight());
    g.strokeOval(0, 0, getWidth(), getHeight());

    g.setFill(getIconFillColor());
    g.setStroke(getIconStrokeColor());
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

  //
  // Property setters and getters
  //

  @SuppressWarnings("unchecked")
  public ObservableValue<Paint> backgroundColorProperty() {
    return (ObservableValue<Paint>)mBackgroundColor;
  }
  public final Paint getBackgroundColor() { return mBackgroundColor.getValue(); }
  public final void setBackgroundColor(Paint paint) { mBackgroundColor.setValue(paint); }

  @SuppressWarnings("unchecked")
  public ObservableValue<Paint> strokeColorProperty() {
    return (ObservableValue<Paint>)mStrokeColor;
  }
  public final Paint getStrokeColor() { return mStrokeColor.getValue(); }
  public final void setStrokeColor(Paint paint) { mStrokeColor.setValue(paint); }

  @SuppressWarnings("unchecked")
  public ObservableValue<Paint> iconFillColorProperty() {
    return (ObservableValue<Paint>)mIconFillColor;
  }
  public final Paint getIconFillColor() { return mIconFillColor.getValue(); }
  public final void setIconFillColor(Paint paint) { mIconFillColor.setValue(paint); }

  @SuppressWarnings("unchecked")
  public ObservableValue<Paint> iconStrokeColorProperty() {
    return (ObservableValue<Paint>)mIconStrokeColor;
  }
  public final Paint getIconStrokeColor() { return mIconStrokeColor.getValue(); }
  public final void setIconStrokeColor(Paint paint) { mIconStrokeColor.setValue(paint); }
}