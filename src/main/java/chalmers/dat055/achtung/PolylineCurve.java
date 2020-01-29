package chalmers.dat055.achtung;

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;

class PolylineCurve implements Curve {
  private double mSpeed;
  private double mPitch;
  private double mX;
  private double mY;
  private Paint mColor;
  private Stack<Stack<Double>> mPoints;
  private boolean mTrackingEnabled;
  private int mTrackingToggle;
  private int mStrokeWidth;

  public PolylineCurve(Paint color) {
    mColor = color;
    mSpeed = 1;
    mPitch = 0;
    mX = 0;
    mY = 0;
    mStrokeWidth = 4;
    mPoints = new Stack<>();
    mTrackingEnabled = true;
    mTrackingToggle = 100;
  }

  @Override
  public void setSpeed(double newValue) {
    mSpeed = newValue;
  }

  @Override
  public void setPosition(double x, double y) {
    mX = x;
    mY = y;
    Stack<Double> s = new Stack<>();
    s.push(x);
    s.push(y);
    mPoints.push(s);
  }

  @Override
  public void addPitch(double rad) {
    mPitch += rad;
  }

  @Override
  public void update() {
    mX += Math.cos(mPitch) * mSpeed;
    mY += Math.sin(mPitch) * mSpeed;

    if (--mTrackingToggle < 0) {
      if (mTrackingEnabled) {
        mTrackingToggle = 17;
        Stack<Double> s = new Stack<>();
        s.push(mX);
        s.push(mY);
        mPoints.push(s);
      } else {
        mTrackingToggle = 100;
        mPoints.pop();
        setPosition(mX, mY);
      }
      mTrackingEnabled = !mTrackingEnabled;
    }

    if (mTrackingEnabled) {
      mPoints.peek().push(mX);
      mPoints.peek().push(mY);
    } else {
      Stack<Double> s = mPoints.peek();
      double y = s.pop();
      double x = s.pop();
      s.clear();
      s.push(x);
      s.push(y);
      s.push(mX);
      s.push(mY);
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