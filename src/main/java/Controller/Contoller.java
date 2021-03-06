package Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.Grid;
import org.tinylog.Logger;
import view.View;

import java.io.*;

/**
 * Contains the functions that change the model
 */
public class Contoller {
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static View view;


    /**
     * This function can return the same Grid if no new Disc can be inserted or a new Grid with one more Disc at the appropriate colum
     * @param grid Object containing the the game board and the next players id
     * @param column the column in which the player choose to insert the disc
     * @return either the same board and the same person or a new board and a new person
     */
    public static Grid addDisc(Grid grid, int column) {
        int[][] board = grid.getBoard().clone();
        if(board[ROWS - 1][column] == 0) {
            for(int i = 0; i < ROWS; i++) {
                if(board[i][column] == 0) {
                    board[i][column] = grid.getPlayerTurn();
                    Logger.info("the value that we insert is " + board[i][column] +
                            "in this position " + (i+1) + " row, " + column + 1 + " column" );
                    grid.setBoard(board);
                    if(grid.getPlayerTurn() == 1)
                        grid.setPlayerTurn(2);
                    else
                        grid.setPlayerTurn(1);

                    saveGame(grid);
                    return grid;
                }
            }

        }
        Logger.info("This column is full, disc can't be inserted");
        return grid;
    }

    /**
     * This checks if the player has won either vertically, horizontally, descending diagonally or ascending diagonally
     * @param board the board with the discs
     * @param player the player we are currently checking
     * @return returns a boolean value which can be either true or false if the player wins or loses
     */
    public static boolean areFourConnected(int[][] board, int player){

        Logger.info("Checking horizontally for any winning condition ");
        for (int j = 0; j < COLUMNS - 3 ; j++ ){
            for (int i = 0; i < ROWS; i++){
                if (board[i][j] == player && board[i][j+1] == player && board[i][j+2] == player && board[i][j+3] == player){
                    return true;
                }
            }
        }
        Logger.info("Checking vertically for any winning condition ");
        for (int i = 0; i < ROWS -3 ; i++ ){
            for (int j = 0; j < COLUMNS; j++){
                if (board[i][j] == player && board[i+1][j] == player && board[i+2][j] == player && board[i+3][j] == player){
                    return true;
                }
            }
        }
        Logger.info("Checking diagonally for any winning condition");
        for (int i=3; i < ROWS; i++){
            for (int j=0; j < COLUMNS-3; j++){
                if (board[i][j] == player && board[i-1][j+1] == player && board[i-2][j+2] == player && board[i-3][j+3] == player)
                    return true;
            }
        }
        Logger.info("Checking the other diagonal for any winning condition");
        for (int i=3; i < ROWS; i++){
            for (int j=3; j < COLUMNS; j++){
                if (board[i][j] == player && board[i-1][j-1] == player && board[i-2][j-2] == player && board[i-3][j-3] == player)
                    return true;
            }
        }
        return false;
    }

    /**
     * This function checks if the gaming board is full
     * @param grid the data of the game
     * @return true if it's full and false if it's not
     */
    public static boolean isFull(Grid grid) {
        int full = 0;
        int[][]board = grid.getBoard();
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                full = full + board[i][j];
            }
        }
        if(full == 63)
            return true;
        return false;
    }

    /**
     * This function saves the board and the current players index in a json file format
     * @param grid the Grid that we will save
     */
    public static void saveGame(Grid grid) {
        Gson gson = new GsonBuilder().create();
        try(Writer writer = new FileWriter("game.json")) {
           gson.toJson(grid,writer);
       } catch (IOException e) {
           e.printStackTrace();
       }
        gson.toJson(grid);
    }

    /**
     * This function loads the game from a JSON file
     * @return the board with the current player index
     */
    public static Grid loadGame() {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("game.json"));
            return gson.fromJson(reader, Grid.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
