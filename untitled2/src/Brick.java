import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Brick {
    static int count = 0;
    int x, y;//the starting tile of our brick ; x  is width and y is hight
    int hight = 3, width = 3;
    final boolean[][] squares = new boolean[hight][width];
    Color color = Color.GREEN;
    Freeze isFreeze;
    GameBoard gboard;

    public Brick(GameBoard gb) {
        gboard = gb;
        isFreeze = Freeze.NO;
        y = 0;
        this.x = (gboard.X - 3) / 2;
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                squares[i][j] = false;
            }
        }


        Random t = new Random();
        color = Color.rgb(t.nextInt(255), t.nextInt(255), t.nextInt(255));
        switch (t.nextInt(9)) {
            case 1:
                squares[0][0] = true;
                squares[0][1] = true;
                squares[1][1] = true;
                squares[1][2] = true;
                break;
            case 2:
                squares[0][0] = true;
                squares[1][0] = true;
                squares[2][0] = true;
                squares[2][1] = true;
                break;
            case 3:
                squares[0][0] = true;
                squares[1][0] = true;
                squares[1][1] = true;
                break;
            case 4:
                squares[0][0] = true;
                squares[0][1] = true;
                squares[0][2] = true;
                squares[1][0] = true;
                squares[1][2] = true;
                break;
            case 5:
                squares[0][0] = true;
                squares[1][0] = true;
                squares[2][0] = true;
                break;
            case 6:
                squares[0][1] = true;
                squares[1][0] = true;
                squares[1][1] = true;
                squares[1][2] = true;
                break;
            case 7:
                squares[0][0] = true;
                squares[0][1] = true;
                squares[1][0] = true;
                squares[1][1] = true;
                break;
            case 8:
                squares[0][1] = true;
                squares[1][1] = true;
                squares[2][1] = true;
                squares[2][2] = true;
                break;
            case 0:
                squares[0][1] = true;
                squares[0][2] = true;
                squares[1][0] = true;
                squares[1][1] = true;

                break;
        }
        gboard.editBoard();
        print();
    }

    synchronized public boolean rotate() {
        tempOnOrOff(false);
        gboard.editBoard();
        boolean[][] temp = new boolean[hight][width];

        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                temp[i][j] = false;
            }
        }
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                if (squares[i][j]) {
                    temp[j][Math.abs(width - 1 - i)] = true;
                }
            }
        }
        if(violating(temp) ){
            tempOnOrOff(true);
            print();
            return false;
        }
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                squares[i][j] = false;
            }
        }
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                if (temp[i][j]) {
                    squares[i][j] = true;
                }
            }
        }

        tempOnOrOff(true);
        print();

        return true;
    }

    synchronized public boolean move(Direction direction) {
        if (isFreeze.equals(Freeze.YES)) {
            System.out.println("nemishe bad jor");
            return false;
        }
        tempOnOrOff(false);
        if (!downable()) {
            tempOnOrOff(true);
            print();
            gboard.editBoard();
            print();
            isFreeze = Freeze.YES;
            System.out.println("nemishe");

            return false;
        }
        gboard.editBoard();
        switch (direction) {

            case DOWN:
                this.y += 1;
                break;
            case LEFT:
                if (LeftOrRightable(false)) {
                    x -= 1;
                } else {
                    print();

                    return false;
                }

                break;
            case RIGHT:
                if (LeftOrRightable(true)) {
                    x += 1;
                } else {
                    print();
                    return false;
                }
                break;

        }
        tempOnOrOff(true);
        print();
        return true;
    }


    synchronized public void print() {
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {


                if (squares[i][j]) {
                    Rectangle r = new Rectangle(gboard.Pixel, gboard.Pixel);
                    r.setFill(color);
                    gboard.gpane.add(r, x + j, y + i);
                }
            }
        }
    }


    synchronized public boolean LeftOrRightable(boolean LeftOrRight) {//true for right and false for left

        int n = LeftOrRight ? 1 : -1;
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                if (squares[i][j]) {
                    if (LeftOrRight) {
                        if ((x + j) == gboard.X - 1) return false;
                    } else {
                        if (x + j == 0) return false;
                    }
                    if (gboard.board[y + i][x + j + n]) {
                        System.out.printf("not %sable", LeftOrRight ? "left" : "right");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean violating(boolean sqrs[][]) {
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                if (sqrs[i][j]) {
                    if (x + j > gboard.X - 1 || x + j < 0) {

                        return true;
                    }

                    if (gboard.board[y+ i][x+j]) {
                        return true;
                    }
                }


            }

        }
        return false;
    }

    synchronized public boolean downable() {
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                if (squares[i][j]) {
                    if (y + i == gboard.Y - 1) {
                        isFreeze = Freeze.YES;
                        return false;


                    }
                    if (gboard.board[y + i + 1][x + j]) {
                        isFreeze = Freeze.YES;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    synchronized public void tempOnOrOff(boolean onOff) {//true for on and false for off
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                if (squares[i][j]) {
                    gboard.board[y + i][x + j] = onOff;
                }
            }
        }
    }

    public enum Direction {
        DOWN, LEFT, RIGHT
    }

    public enum Freeze {
        YES, NO
    }

}
