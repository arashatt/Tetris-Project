import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;


public class GameBoard {



     int level;
    final int X = 15;//coulumn
    final int Y = 20;//row
    final int Pixel = 20;
    State state;
    Color color;
    GridPane gpane;
    boolean[][] board = new boolean[Y][X];

    public GameBoard() {

        state = State.START;
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                board[i][j] = false;
            }

        }
        color = Color.WHEAT;//default color




        GridPane gpane = new GridPane();
        gpane.setVgap(0);
        gpane.setHgap(0);

        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                if (!board[i][j]) {
                    Rectangle r = new Rectangle(Pixel, Pixel);
                    r.setFill(color);
                    gpane.add(r, j, i);
                }
            }
        }
        gpane.setGridLinesVisible(false);
        this.gpane = gpane;
    }

    public enum State {
        START, STOP
    }

    synchronized public void editBoard() {
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                if (!board[i][j]) {
                    Rectangle r = new Rectangle(Pixel, Pixel);
                    r.setFill(color);
                    gpane.add(r, j, i);
                }
            }
        }

    }
    synchronized public void state(){
    for (int i = 0; i < X; i++) {
        if(board[0][i]){
            state = State.STOP;
        }
    }
}





}
