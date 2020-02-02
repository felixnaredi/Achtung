package chalmers.dat055.achtung;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;

public interface MenuItemListener {
  public void onFocus(InputEvent event);
  public void onLeave(InputEvent event);
  public void onTrigger(InputEvent event);
  public void onRelease(InputEvent event);

  default public void setCanvas(Canvas canvas) {
    canvas.addEventFilter(MouseEvent.MOUSE_ENTERED, (e) -> {
      canvas.cursorProperty().set(Cursor.HAND);
      onFocus(e);
    });
    canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, (e) -> {
      canvas.cursorProperty().set(Cursor.HAND);
      onTrigger(e);
    });
    canvas.addEventFilter(MouseEvent.MOUSE_EXITED, (e) -> onLeave(e));
    canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, (e) -> onRelease(e));
  }
}