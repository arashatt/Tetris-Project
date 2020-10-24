import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.application.Application.launch;

public class Main extends Application {
    GameBoard gboard = new GameBoard();
    Brick br = new Brick(gboard);
    GridPane g = gboard.gpane;
    StackPane spane = new StackPane();
    Label lbl = new Label("Game over");
    Timer timer = new Timer();
    boolean pause = true;
    TimerTask task;
    Tetris tet = new Tetris();

    public Main() throws FileNotFoundException {
    }

    @Override
    synchronized public void start(Stage primaryStage) throws Exception {
        spane.getChildren().add(g);


        //spane.getChildren().add(button);
        Scene scene = new Scene(spane, gboard.Pixel * gboard.X, gboard.Y * gboard.Pixel);
        EventHandler<KeyEvent> even = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        e.consume();

                        if (e.getCode().equals(KeyCode.ENTER)) {
                            while (br.move(Brick.Direction.DOWN)) {
                            }
                        } else if (e.getCode().equals(KeyCode.RIGHT)) {
                            if (br.move(Brick.Direction.RIGHT)) {
                            }
                        } else if (e.getCode().equals(KeyCode.LEFT)) {
                            if (br.move(Brick.Direction.LEFT)) {
                            }

                        } else if (e.getCode().equals(KeyCode.UP)) {
                            if (br.rotate()) {
                            }

                        } else if (e.getCode().equals(KeyCode.P)) {
                            if (!pause) {
                                try {
                                    timer.schedule(task, 0, 700);

                                } catch (Exception e) {

                                    timer = new Timer();
                                    timer.schedule(task, 0, 700);
                                }
                                pause = true;
                            } else {
                                timer.cancel();

                                pause = false;
                            }
                        }
                    }
                });
            }
        };
        scene.setOnKeyPressed(even);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setFullScreen(false);
        task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        if (!br.gboard.state.equals(GameBoard.State.STOP)) {
                            if (br.move(Brick.Direction.DOWN)) {
                            } else {
                                br = new Brick(br.gboard);
                            }
                            br.gboard.state();
                            if (br.gboard.state.equals(GameBoard.State.STOP)) {
                                spane.getChildren().add(lbl);
                                lbl.setOpacity(0.6);
                                call();
                                return null;
                            }
                        }
                        return null;

                    }
                });
            }
        };
        task.scheduledExecutionTime();
        ExecutorService rc = Executors.newFixedThreadPool(1);

        timer.schedule(task, 0, 1000 /(int)(tet.level));


    }

    public static void main(String... args) throws Exception {

        launch(args);
    }


}
//new Runnable() {
//@Override
//public void run() {
//        br.move(Brick.Direction.DOWN);
//        if (br.move(Brick.Direction.DOWN)) {
//        } else {
//        br = new Brick(gboard);
//        }
//        //primaryStage.getScene().setRoot(br.gboard.gpane);
//        }
//        })


