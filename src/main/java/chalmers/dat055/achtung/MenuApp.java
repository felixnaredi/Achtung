package chalmers.dat055.achtung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MenuApp extends Application {
  @Override
  public void start(Stage stage) {
    List<Curve> activeCurves = new ArrayList<>();

    VBox menuBox = new VBox();
    Pane drawPane = new Pane(menuBox);
    StackPane root = new StackPane(drawPane, menuBox);
    root.setBackground(new Background(
        new BackgroundFill(Color.color(0, 0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

    Scene scene = new Scene(root, 377, 610);
    scene.fillProperty().set(Color.color(0.15, 0.15, 0.15, 1.0));

    KeyServer keyServer = new KeyServer(scene);

    keyServer.addHoldListener(KeyCode.A, (x) -> {
      for (Curve curve : activeCurves) {
        curve.addPitch(0.08);
      }
    });
    keyServer.addHoldListener(KeyCode.S, (x) -> {
      for (Curve curve : activeCurves) {
        curve.addPitch(-0.08);
      }
    });

    AtomicBoolean windowWillClose = new AtomicBoolean(false);
    stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (x) -> windowWillClose.set(true));

    menuBox.setAlignment(Pos.CENTER);

    scene.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
      Curve curve = new BitmapCurve(
          Color.color(0.5, 1.0, 0.5, 1.0), (int)stage.getWidth(), (int)stage.getHeight());
      // Curve curve = new PolylineCurve(Color.color(0.0, 1.0, 0.5, 0.45));
      curve.setPosition(e.getX(), e.getY());
      curve.setSpeed(0.81);
      curve.setStrokeWidth(8);

      activeCurves.add(curve);
    });

    menuBox.getChildren().addAll(
        Arrays.asList("Start Game", "Settings", "Exit")
            .stream()
            .map((s) -> {
              Label l = new Label(s);
              l.setTextFill(Color.BEIGE);
              l.addEventFilter(MouseEvent.MOUSE_ENTERED, (e) -> {
                l.setTextFill(Color.RED);
                l.cursorProperty().set(Cursor.HAND);
              });
              l.addEventFilter(MouseEvent.MOUSE_PRESSED, (e) -> {
                l.setTextFill(Color.YELLOW);
                l.cursorProperty().set(Cursor.HAND);
              });
              l.addEventFilter(MouseEvent.MOUSE_EXITED, (e) -> l.setTextFill(Color.BEIGE));
              l.addEventFilter(MouseEvent.MOUSE_RELEASED, (e) -> l.setTextFill(Color.RED));
              return l;
            })
            .collect(Collectors.toList()));

    stage.setScene(scene);
    stage.show();

    new Thread(() -> {
      while (!windowWillClose.get()) {
        try {
          Thread.sleep(16);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
        keyServer.poll();

        Platform.runLater(() -> {
          drawPane.getChildren().clear();
          for (Curve curve : activeCurves) {
            curve.update();
            curve.draw(drawPane);
          }
        });
      }
    }).start();
  }

  public static void main(String[] args) { launch(); }
}