package chalmers.dat055.achtung;

public abstract class CurveBase implements Curve {
  private double mX;
  private double mY;
  private double mSpeed;
  private double mPitch;

  public double getPitch() { return mPitch; }
  public double getPosX() { return mX; }
  public double getPosY() { return mY; }
  public void setSpeed(double newValue) { mSpeed = newValue; }

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
  public void update() {
    mX += Math.cos(mPitch) * mSpeed;
    mY += Math.sin(mPitch) * mSpeed;
  }
}