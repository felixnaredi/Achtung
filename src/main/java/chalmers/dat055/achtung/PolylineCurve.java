package chalmers.dat055.achtung;

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;

class PolylineCurve extends CurveBase {
  private Paint mColor;
  private Stack<Stack<Double>> mPoints;
  private int mStrokeWidth;

  public PolylineCurve(Paint color) {
    mColor = color;
    mStrokeWidth = 4;
    mPoints = new Stack<>();

    setTrackingToggler(new CountToggler(true, 100, 17) {
      @Override
      public void onToggleSet() {
        mPoints.pop();
        setPosition(getPosX(), getPosY());
      }

      @Override
      public void onToggleClear() {
        Stack<Double> s = new Stack<>();
        s.push(getPosX());
        s.push(getPosY());
        mPoints.push(s);
      }
    });
  }

  @Override
  public void setPosition(double x, double y) {
    super.setPosition(x, y);

    Stack<Double> s = new Stack<>();
    s.push(x);
    s.push(y);
    mPoints.push(s);
  }

  @Override
  public void update() {
    super.update();

    double x = getPosX();
    double y = getPosY();

    if (getTrackingEnabled()) {
      mPoints.peek().push(x);
      mPoints.peek().push(y);
    } else {
      Stack<Double> s = mPoints.peek();
      double oldY = s.pop();
      double oldX = s.pop();
      s.clear();
      s.push(oldX);
      s.push(oldY);
      s.push(x);
      s.push(y);
    }
  }

  private Stream<Polyline> getPolylines() {
    return mPoints.stream().map((stack) -> {
      Polyline p = new Polyline();
      p.setStroke(mColor);
      p.setStrokeWidth(mStrokeWidth);
      p.getPoints().addAll(stack);
      return p;
    });
  }

  @Override
  public void draw(Pane pane) {
    pane.getChildren().addAll(getPolylines().collect(Collectors.toList()));
  }

  @Override
  public void setStrokeWidth(int width) {
    mStrokeWidth = width;
  }
}