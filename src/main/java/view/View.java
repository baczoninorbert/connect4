package view;

import Controller.Contoller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import model.Grid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class View {

    private Pane discRoot = new Pane();
    private static int COLUMNS = 7;
    private static int ROWS = 6;
    private int TILE_SIZE = 80;
    private static Contoller contoller = new Contoller();
    private Image imageEmpty;
    private Image imagePlayer1;
    private Image imagePlayer2;
    private static final int canvasHeight = 600;
    private static final int canvasWidth = 700;
    private static final double cellHeight = (double) canvasHeight / ROWS;
    private static final double cellWidth = (double) canvasWidth / COLUMNS;
    private static GraphicsContext gc;
    public static Parent createContent(Grid grid) {
        Pane pane = new GridPane();
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        gc = canvas.getGraphicsContext2D();
        drawBoard(gc, grid);
        pane.getChildren().addAll(canvas);
        //Shape gridShape = makeGrid(grid);
        pane.getChildren().addAll(makeColumns(grid));

        return pane;
    }

    private static void drawBoard(GraphicsContext gc, Grid grid) {

        gc.setFill(Color.rgb(25, 130, 255));
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        gc.setFill(Color.BLACK);

        for(int i = ROWS - 1; i >= 0; i--) {
            gc.strokeLine(0, i * cellHeight, canvasWidth, i * cellHeight);
        }

        for(int i = 0; i <= COLUMNS; i++) {
            gc.strokeLine(i * cellWidth, 0, i * cellWidth, canvasHeight);
        }

        int offset = 3;
        int board[][] = grid.getBoard();

        for(int r = 0; r < ROWS; r++) {
            for(int c = 0; c < COLUMNS ; c++) {

                if(board[r][c] == 1) {
                    gc.setFill(Color.RED);
                    gc.fillOval(c * cellHeight, r * cellWidth, cellWidth - offset, cellHeight - offset);
                }

                if(board[r][c] == 2) {
                    gc.setFill(Color.YELLOW);
                    gc.fillOval(c * cellHeight, r * cellWidth, cellWidth - offset, cellHeight - offset);
                }

            }
        }

    }

    private static void repaintCanvas(Grid grid) {
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        drawBoard(gc,grid);
        if(contoller.areFourConnected(grid.getBoard(),1))
            showStage(1);
        if(contoller.areFourConnected(grid.getBoard(),2))
            showStage(2);
    }
    private Shape makeGrid(Grid grid) {
        Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);
        int[][]board = grid.getBoard();
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Circle circle = new Circle(TILE_SIZE / 2);
                circle.setCenterX(TILE_SIZE / 2);
                circle.setCenterY(TILE_SIZE / 2);
                circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
                circle.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);

                if(board[x][y] == 0)
                    circle.setFill(Color.WHITE);

                if(board[x][y] == 1)
                    circle.setFill(Color.RED);

                if(board[x][y] == 2)
                    circle.setFill(Color.BLUE);
                System.out.println(circle.getFill());

                shape = Shape.subtract(circle, shape);
            }
        }
        return shape;
    }


    public void draw(Grid grid) {
        for(int i = ROWS - 1 ; i >= 0; i--) {
            for(int j = 0; j < COLUMNS; j++) {
                if(grid.getBoard()[j][i] == 0) {

                }
            }
        }

    }

    private static List<Rectangle> makeColumns(Grid grid) {
        List<Rectangle> list = new ArrayList<>();

        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rect = new Rectangle(cellWidth, (ROWS) * cellHeight);
            rect.setTranslateX(x * (cellWidth));
            rect.setFill(Color.TRANSPARENT);

            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            final int column = x;
            System.out.println(column);
            rect.setOnMouseClicked(e -> {
                repaintCanvas(contoller.addDisc(grid, column));

                System.out.println("nyugger" + column);
                for(int j = 0; j < 7; j++) {
                    for (int i = 0; i < 6; i++)
                        System.out.print(grid.getBoard()[i][j] + " ");
                    System.out.println();
                }
                System.out.println("\n");
            });

            list.add(rect);
        }

        return list;
    }

    public void winnerMessage(int player) {
        Stage stage = new Stage();
        VBox vBox = new VBox();


    }

    public static void createMenu() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Button newGameButton = new Button();
        Button continueGameButton = new Button();
        Button exitButton = new Button();
        EventHandler<ActionEvent> newGame = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Grid grid = new Grid();
                Scene scene = new Scene(createContent(grid));
                stage.setScene(scene);
                stage.show();
            }

        };

        EventHandler<ActionEvent> loadGame= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Grid grid = contoller.loadGame();
                Scene scene = new Scene(createContent(grid));
                stage.setScene(scene);
                stage.show();
            }
        };
        File f = new File("game.json");
        if(f.exists())
        {
            continueGameButton.setDisable(false);
            continueGameButton.setOnAction(loadGame);
        }
        else
            continueGameButton.setDisable(true);
        newGameButton.setOnAction(newGame);
        newGameButton.setText("New Game");
        continueGameButton.setText("Continue Game");
        exitButton.setText("Exit Game");
        vbox.getChildren().addAll(newGameButton,continueGameButton,exitButton);
        vbox.setMargin(newGameButton,new Insets(20,20,20,20));
        vbox.setMargin(continueGameButton,new Insets(20,20,20,20));
        vbox.setMargin(exitButton,new Insets(20,20,20,20));
        Scene scene = new Scene(vbox,canvasHeight,canvasWidth);
        stage.setScene(scene);
        stage.show();

    }

    public static void showStage(int player){
        Stage stage= new Stage();
        VBox comp = new VBox();
        Label label = new Label();
        label.setText("Congratulations for winning player " + player);
        Button exit = new Button();
        EventHandler<ActionEvent> backToMenu= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage newStage = new Stage();
                Grid grid = new Grid();
                File f = new File("game.json");
                f.delete();
                stage.close();
                createMenu();
            }
        };
        exit.setOnAction(backToMenu);
        comp.setAlignment(Pos.CENTER);
        comp.getChildren().add(label);
        comp.getChildren().add(exit);

        Scene stageScene = new Scene(comp, 300, 300);
        stage.setScene(stageScene);
        stage.show();
    }
}
