package model;

import java.util.Arrays;

/**
 * This class is the model for the gaming board and the players index
 * It also provides getters and setters for it's components
 */
public class Grid {
    /**
     * The number of rows that the board has
     */
    private static final int ROWS = 6;
    /**
     * The number of columns that the board has
     */
    private static final int COLUMNS = 7;

    /**
     * This is an array representing the gaming board
     */
    private int[][] board;

    /**
     * This constructor initializes an empty board with and points the index at the first player
     */
    public Grid()
    {
        this.board = new int[ROWS][COLUMNS];
        this.playerTurn = 1;
    }

    /**
     * Holds the players index
     */
    private int playerTurn;

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "board=" + Arrays.toString(board) +
                ", playerTurn=" + playerTurn +
                '}';
    }
}
