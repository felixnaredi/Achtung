package chalmers.dat055.achtung;

public abstract class CountToggler extends Toggler {

  private int mSetCount;
  private int mClearCount;
  private int mCount;

  public CountToggler(boolean state, int setCount, int clearCount) {
    super(state);

    mSetCount = setCount;
    mClearCount = clearCount;
    resetState(state);
  }

  @Override
  public void updateState() {
    --mCount;
  }

  @Override
  public boolean shouldToggle() {
    return mCount < 0;
  }

  @Override
  public void resetState(boolean state) {
    mCount = state ? mSetCount : mClearCount;
  }

}