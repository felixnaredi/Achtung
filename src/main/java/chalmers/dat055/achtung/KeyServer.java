package chalmers.dat055.achtung;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class KeyServer {
  private Set<KeyCode> mKeysHold;
  private Set<KeyCode> mKeysPressed;

  private Map<KeyCode, Consumer<Integer>> mKeyHoldMapping;
  private Map<KeyCode, Consumer<Integer>> mKeyPressMapping;

  public KeyServer(Scene scene) {
    mKeysPressed = new HashSet<>();
    mKeysHold = new HashSet<>();
    mKeyPressMapping = new HashMap<>();
    mKeyHoldMapping = new HashMap<>();

    scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
      KeyCode c = e.getCode();
      if (!mKeysHold.contains(c)) {
        mKeysPressed.add(c);
      }
      mKeysHold.add(c);
    });

    scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> mKeysHold.remove(e.getCode()));
  }

  public void poll() {
    for (KeyCode c : mKeysPressed) {
      Consumer<Integer> f = mKeyPressMapping.get(c);
      if (f == null) {
        continue;
      }
      f.accept(0);
    }

    mKeysPressed.clear();

    for (KeyCode c : mKeysHold) {
      Consumer<Integer> f = mKeyHoldMapping.get(c);
      if (f == null) {
        continue;
      }
      f.accept(0);
    }
  }

  /**
   * Adds a listener that triggers each poll a key is held.
   *
   * @param code Key code for the trigger key.
   * @param handler The function that triggers.
   */
  public void addHoldListener(KeyCode code, Consumer<Integer> handler) {
    mKeyHoldMapping.put(code, handler);
  }

  /**
   * Adds a listener that triggers the first poll a key was pressed.
   *
   * @param code Key code for the trigger key.
   * @param handler The function that triggers.
   */
  public void addPressListener(KeyCode code, Consumer<Integer> handler) {
    mKeyPressMapping.put(code, handler);
  }
}