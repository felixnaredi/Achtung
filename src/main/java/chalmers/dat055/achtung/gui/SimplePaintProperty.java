package chalmers.dat055.achtung.gui;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.converter.PaintConverter;
import javafx.scene.paint.Paint;

/* 
 * TODO:
 *   This implementation can currently only add css meta data to an object and not static to the
 *   class.
 */

public class SimplePaintProperty<T extends Styleable> {
  private StyleableProperty<Paint[]> mPaintRef;
  private ObservableValue<Paint> mPaintProperty;
  private CssMetaData<T, Paint[]> mCssMetaData;

  @SuppressWarnings("unchecked")
  public SimplePaintProperty(String property) {
    mCssMetaData =
        new CssMetaData<T, Paint[]>(property, PaintConverter.SequenceConverter.getInstance()) {
          @Override
          public boolean isSettable(Styleable styleable) {
            return true;
          }

          @Override
          public StyleableProperty<Paint[]> getStyleableProperty(Styleable styleable) {
            return mPaintRef;
          }
        };

    mPaintRef = new SimpleStyleableObjectProperty<>(mCssMetaData);

    ObservableValue<Paint[]> ref = (ObservableValue<Paint[]>)mPaintRef;

    mPaintProperty = new ObservableValue<Paint>() {
      private Map<ChangeListener<Paint>, ChangeListener<Paint[]>> mChangeListeners =
          new HashMap<>();

      @Override
      public void addListener(InvalidationListener listener) {
        ref.addListener(listener);
      }

      @Override
      public void removeListener(InvalidationListener listener) {
        ref.removeListener(listener);
      }

      @Override
      public void addListener(ChangeListener<? super Paint> listener) {
        ChangeListener<Paint[]> refListener = new ChangeListener<Paint[]>() {
          @Override
          public void changed(
              ObservableValue<? extends Paint[]> observable, Paint[] oldValue, Paint[] newValue) {
            listener.changed(mPaintProperty, oldValue[0], newValue[0]);
          }
        };
        ref.addListener(refListener);
        mChangeListeners.put((ChangeListener<Paint>)listener, refListener);
      }

      @Override
      public void removeListener(ChangeListener<? super Paint> listener) {
        ref.removeListener(mChangeListeners.remove(listener));
      }

      @Override
      public Paint getValue() {
        return get();
      }
    };
  }

  public CssMetaData<T, Paint[]> getCssMetaData() { return mCssMetaData; }
  public ObservableValue<Paint> property() { return mPaintProperty; }
  public Paint get() { return mPaintRef.getValue()[0]; }
  public void set(Paint paint) { mPaintRef.setValue(new Paint[] {paint}); }
}