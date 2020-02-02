package chalmers.dat055.achtung.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import chalmers.dat055.achtung.ImageRenderer;
import chalmers.dat055.achtung.KeyServer;
import chalmers.dat055.achtung.MenuItemListener;
import chalmers.dat055.achtung.curve.BitmapCurve;
import chalmers.dat055.achtung.curve.Curve;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MenuApp extends Application {
  private static Canvas makeMenuItem(String title, Consumer<Void> trigger) {
    Canvas c = new Canvas(132, 32);
    GraphicsContext g = c.getGraphicsContext2D();
    g.setFont(new Font(28));

    if (trigger == null) {
      g.setStroke(Color.GREY);
      g.strokeText(title, 0, 25);
    } else {
      g.setStroke(Color.RED);
      g.strokeText(title, 0, 25);

      new MenuItemListener() {
        @Override
        public void onTrigger(InputEvent event) {
          Platform.runLater(() -> {
            g.clearRect(0, 0, c.getWidth(), c.getHeight());
            g.setFill(Color.RED);
            g.setStroke(Color.YELLOW);
            g.fillText(title, 0, 25);
            g.strokeText(title, 0, 25);
          });
          trigger.accept(null);
        }

        @Override
        public void onRelease(InputEvent event) {          
          onFocus(event);
        }

        @Override
        public void onLeave(InputEvent event) {
          Platform.runLater(() -> {
            g.clearRect(0, 0, c.getWidth(), c.getHeight());
            g.setStroke(Color.RED);
            g.strokeText(title, 0, 25);
          });
        }

        @Override
        public void onFocus(InputEvent event) {
          Platform.runLater(() -> {
            g.clearRect(0, 0, c.getWidth(), c.getHeight());
            g.setFill(Color.YELLOW);
            g.setStroke(Color.RED);
            g.fillText(title, 0, 25);
            g.strokeText(title, 0, 25);
          });
        }
      }.setCanvas(c);
    }

    return c;
  }

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
      Curve curve = new BitmapCurve(scene.getWidth(), scene.getHeight()) {
        @Override
        public Image makeHeadImage(int width) {
          return ImageRenderer.drawImage(width, width, (g) -> {
            g.setFill(Color.BURLYWOOD);
            g.fillOval(0, 0, width, width);
          });
        }
      };

      curve.setPosition(e.getX(), e.getY());
      curve.setSpeed(0.81);
      curve.setStrokeWidth(9);

      activeCurves.add(curve);
    });

    menuBox.getChildren().addAll(
        MenuApp.makeMenuItem("Start Game", (x) -> System.out.println("Get ready to RUMBLE!")),
        MenuApp.makeMenuItem("Settings", null),
        MenuApp.makeMenuItem("Lobby", (x) -> System.out.println("Found no lobby")),
        MenuApp.makeMenuItem("Exit", (x) -> stage.close()));

    stage.setScene(scene);
    stage.show();

    new AnimationTimer() {
      @Override
      public void handle(long now) {
        keyServer.poll();

        drawPane.getChildren().clear();
        activeCurves.forEach((c) -> {
          c.update();
          c.draw(drawPane);
        });
      }
    }.start();
  }

  public static void main(String[] args) { launch(); }
}