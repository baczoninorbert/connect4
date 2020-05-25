package model;

import java.util.Arrays;

public class Grid {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    private int[][] board;

    public Grid()
    {
        this.board = new int[ROWS][COLUMNS];
        this.playerTurn = 1;
    }


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
