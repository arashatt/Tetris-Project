import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Tetris {

    GameBoard gameBoard;
    int score, heart, level;

VBox buttons = new VBox();
ImageView image = new ImageView(new Image(new FileInputStream("tetris.jpg")));

    Button newGame = new Button("New Game");
    Button scoreTable = new Button("Score Table");
    Button setting = new Button("Setting");
    Button exit = new Button("Exit");

    int[] arr = new int[5];
    ArrayList<Integer> ar = new ArrayList<Integer>();


    public Tetris() throws FileNotFoundException {
         level= 1;
        image.setFitHeight(500);
        image.setFitWidth(500);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(image,newGame,scoreTable,setting,exit);
buttons.prefHeight(200);
buttons.prefWidth(500);
exit.setOnAction(e->{System.exit(1);});
    }
    public VBox show(){

        VBox temp = new VBox(buttons);
        return temp;
    }
}