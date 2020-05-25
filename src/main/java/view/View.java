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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Grid;
import org.tinylog.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the functions that load displays
 */
public class View {

    private static int COLUMNS = 7;
    private static int ROWS = 6;
    private static Contoller contoller = new Contoller();
    private static final int canvasHeight = 600;
    private static final int canvasWidth = 700;
    private static final double cellHeight = (double) canvasHeight / ROWS;
    private static final double cellWidth = (double) canvasWidth / COLUMNS;
    private static GraphicsContext gc;

    /**
     * This function displays the gaming board
     * @param grid is used for reading it's data
     * @return the scene with the gaming board
     */
    public static Parent createContent(Grid grid) {
        Pane pane = new GridPane();
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        gc = canvas.getGraphicsContext2D();
        drawBoard(gc, grid);
        pane.getChildren().addAll(canvas);
        pane.getChildren().addAll(makeColumns(grid));
        return pane;
    }

    /**
     * This function draws the boards outline and the discs
     * @param gc the layout where the function draws
     * @param grid the data on which upon it draws the discs
     */
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
        Logger.info("Outlines drawn");
        int offset = 3;
        int board[][] = grid.getBoard();
        for(int r = 0; r < ROWS; r++) {
            for(int c = 0; c < COLUMNS ; c++) {
                if(board[r][c] == 1) {
                    Logger.info("First player cell located coloring it red");
                    gc.setFill(Color.RED);
                    gc.fillOval(c * cellHeight, r * cellWidth, cellWidth - offset, cellHeight - offset);
                }

                if(board[r][c] == 2) {
                    Logger.info("Second player cell located coloring it yellow");
                    gc.setFill(Color.YELLOW);
                    gc.fillOval(c * cellHeight, r * cellWidth, cellWidth - offset, cellHeight - offset);
                }

            }
        }

    }

    /**
     * This function redraws the canvas after every disc insertment and also checks if someone won
     * @param grid the data of the game
     */
    private static void repaintCanvas(Grid grid) {
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        drawBoard(gc,grid);
        Logger.info("Checking if someone won");
        if(contoller.areFourConnected(grid.getBoard(),1)) {
            showStage(1);
            Logger.info("First Player Won");
        }
        if(contoller.areFourConnected(grid.getBoard(),2)) {
            showStage(2);
            Logger.info("Second Player Won");
        }
        if(contoller.isFull(grid) == true) {
            showStage(0);
            Logger.info("It's a draw better luck next time");
        }
    }

    /**
     * This function creates clickable columns over the actual columns and then it inserts the dics if the player clicks
     * @param grid the data of the game
     * @return rectangles for each column
     */
    private static List<Rectangle> makeColumns(Grid grid) {
        List<Rectangle> list = new ArrayList<>();

        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rect = new Rectangle(cellWidth, (ROWS) * cellHeight);
            rect.setTranslateX(x * (cellWidth));
            rect.setFill(Color.TRANSPARENT);

            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            final int column = x;
            rect.setOnMouseClicked(e -> {
                if(contoller.areFourConnected(grid.getBoard(),1) == false &&
                contoller.areFourConnected(grid.getBoard(),2) == false) {
                    repaintCanvas(contoller.addDisc(grid, column));
                    Logger.info(column + 1 + " Column clicked");
                }
            });

            list.add(rect);
        }

        return list;
    }

    /**
     * This function creates the menu
     */
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
                Logger.info("New game created");
                Grid grid = new Grid();
                Scene scene = new Scene(createContent(grid));
                stage.setScene(scene);
                stage.show();
            }

        };

        EventHandler<ActionEvent> loadGame= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Logger.info("Loaded game from JSON");
                Grid grid = contoller.loadGame();
                Logger.info("Displaying gaming board");
                Scene scene = new Scene(createContent(grid));
                stage.setScene(scene);
                stage.show();
            }
        };
        EventHandler<ActionEvent> exit= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        };
        exitButton.setOnAction(exit);
        File f = new File("game.json");
        if(f.exists())
        {
            Logger.info("game.json exists");
            continueGameButton.setDisable(false);
            continueGameButton.setOnAction(loadGame);
        }
        else {
            continueGameButton.setDisable(true);
            Logger.info("game.json doesn't exist");
        }
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

    /**
     * This function displays a stage with a congratulating message to the winning player
     * @param player the index of the player
     */
    public static void showStage(int player){
        Stage stage= new Stage();
        VBox comp = new VBox();
        Label label = new Label();
        if(player == 0) {
            label.setText("Congratulations it's a draw");
        }
        else {
            label.setText("Congratulations for winning player " + player);
        }
        Button exit = new Button();
        exit.setText("Exit");
        EventHandler<ActionEvent> backToMenu= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
