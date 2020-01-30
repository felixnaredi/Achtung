package chalmers.dat055.achtung;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuApp extends Application {
  @Override
  public void start(Stage stage) {
    VBox menuBox = new VBox();
    menuBox.setAlignment(Pos.CENTER);

    menuBox.getChildren().addAll(Arrays.asList("Start Game", "Options", "Exit")
                                     .stream()
                                     .map((s) -> {
                                       Label l = new Label(s);
                                       l.addEventFilter(MouseEvent.MOUSE_ENTERED, (e) -> {
                                         l.setTextFill(Color.RED);
                                         l.cursorProperty().set(Cursor.HAND);
                                       });
                                       l.addEventFilter(MouseEvent.MOUSE_EXITED,
                                                        (e) -> l.setTextFill(Color.BLACK));
                                       return l;
                                     })
                                     .collect(Collectors.toList()));

    Scene scene = new Scene(menuBox, 377, 610);

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) { launch(); }
}