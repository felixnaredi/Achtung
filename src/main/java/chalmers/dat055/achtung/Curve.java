package chalmers.dat055.achtung;

import javafx.scene.Group;

interface Curve {
  public void addPitch(double rad);
  public void setPosition(double x, double y);
  public void setSpeed(double speed);
  public void update();
  public void draw(Group group);
}
