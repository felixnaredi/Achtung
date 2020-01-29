package chalmers.dat055.achtung;

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;

class PolylineCurve extends CurveBase {
  private Paint mColor;
  private Stack<Stack<Double>> mPoints;
  private boolean mTrackingEnabled;
  private int mTrackingToggle;
  private int mStrokeWidth;

  public PolylineCurve(Paint color) {
    mColor = color;
    mStrokeWidth = 4;
    mPoints = new Stack<>();
    mTrackingEnabled = true;
    mTrackingToggle = 100;
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

    if (--mTrackingToggle < 0) {
      if (mTrackingEnabled) {
        mTrackingToggle = 17;
        Stack<Double> s = new Stack<>();
        s.push(x);
        s.push(y);
        mPoints.push(s);
      } else {
        mTrackingToggle = 100;
        mPoints.pop();
        setPosition(x, y);
      }
      mTrackingEnabled = !mTrackingEnabled;
    }

    if (mTrackingEnabled) {
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
  public void draw(Group group) {
    group.getChildren().addAll(getPolylines().collect(Collectors.toList()));
  }

  @Override
  public void setStrokeWidth(int width) {
    mStrokeWidth = width;
  }
}