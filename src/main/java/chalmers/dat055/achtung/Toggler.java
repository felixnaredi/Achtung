package chalmers.dat055.achtung;

public abstract class Toggler {

  public abstract void updateState();
  public abstract void resetState(boolean state);
  public abstract boolean shouldToggle();
  public abstract void onToggleSet();
  public abstract void onToggleClear();

  private boolean mToggleState;

  public boolean getToggleState() { return mToggleState; }

  public Toggler(boolean state) { mToggleState = state; }

  private void toggle() {
    mToggleState = !mToggleState;
    if (mToggleState) {
      onToggleSet();
    } else {
      onToggleClear();
    }
  }

  public void update() {
    updateState();
    if (shouldToggle()) {
      toggle();
      resetState(mToggleState);
    }
  }
}