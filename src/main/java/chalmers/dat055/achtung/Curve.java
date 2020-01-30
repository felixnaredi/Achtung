package chalmers.dat055.achtung;

interface Curve extends Actor {
  public void addPitch(double rad);
  public void setPosition(double x, double y);
  public void setSpeed(double speed);
  public void setStrokeWidth(int width);
}
