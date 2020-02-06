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

  private static double ICON_SCALE_FACTOR = 0.73;

  private void drawPlayIcon() {
    GraphicsContext g = getGraphicsContext2D();
    g.save();

    double[] xPoints = {1.0, -0.5, -0.5};
    double[] yPoints = {0.0, 0.8660254037844388, -0.8660254037844388};
    double w = getWidth();
    double h = getHeight();
    
    g.setFill(getIconFillColor());
    g.setStroke(getIconStrokeColor());
    g.setLineWidth(2 / (w * ICON_SCALE_FACTOR));
    g.scale(w / 2, h / 2);
    g.translate(1, 1);
    g.scale(ICON_SCALE_FACTOR, ICON_SCALE_FACTOR);

    getGraphicsContext2D().fillPolygon(xPoints, yPoints, 3);
    getGraphicsContext2D().strokePolygon(xPoints, yPoints, 3);

    g.restore();
  }

  private void drawPauseIcon() {
    GraphicsContext g = getGraphicsContext2D();
    g.save();

    double w = getWidth();
    double h = getHeight();

    g.setFill(getIconFillColor());
    g.setStroke(getIconStrokeColor());
    g.setLineWidth(1.0 / (w * ICON_SCALE_FACTOR));
    g.scale(w, h);
    g.translate(0.125, 0.125);
    g.scale(ICON_SCALE_FACTOR, ICON_SCALE_FACTOR);

    g.fillRect(1.0 / 7.0, 0.125, 2.0 / 7.0, 0.75);
    g.strokeRect(1.0 / 7.0, 0.125, 2.0 / 7.0, 0.75);

    g.fillRect(4.0 / 7.0, 0.125, 2.0 / 7.0, 0.75);
    g.strokeRect(4.0 / 7.0, 0.125, 2.0 / 7.0, 0.75);

    g.restore();
  }

  private void draw() {
    GraphicsContext g = getGraphicsContext2D();
    g.clearRect(0, 0, getWidth(), getHeight());

    g.setFill(getBackgroundColor());
    g.setStroke(getStrokeColor());

    double lw = g.getLineWidth();
    double w = getWidth() - lw;
    double h = getHeight() - lw;
    g.fillOval(lw / 2, lw / 2, w, h);
    g.strokeOval(lw / 2, lw / 2, w, h);

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