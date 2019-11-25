package com.ttulka.games.tictactoe;

import java.util.Arrays;

public class Game {

    public enum Status {
        ON, FIRST_WON, SECOND_WON, FULL_BOARD
    }

    public final static byte O = 1;
    public final static byte X = 2;

    private final byte[][] board = new byte[3][3];

    private Status status = Status.ON;
    private boolean firstOnMove = true;

    /**
     * Status of the game.
     * @return the status
     */
    public Status status() {
        return status;
    }

    /**
     * Make a move to the position.
     * @param x Position x
     * @param y Position y
     */
    public void move(int x, int y) {
        if (status != Status.ON) {
            throw new IllegalStateException("It's over!");
        }
        if (x > board.length || x < 0 || y > board.length || y < 0) {
            throw new IndexOutOfBoundsException("Cannot move out of the board!");
        }
        if (board[x][y] != 0) {
            throw new IllegalArgumentException("This field is already set!");
        }
        board[x][y] = firstOnMove ? O : X;

        if (isBoardFull()) {
            status = Status.FULL_BOARD;
        } else if (hasWon()) {
            status = firstOnMove ? Status.FIRST_WON : Status.SECOND_WON;
        }
        firstOnMove = !firstOnMove;
    }

    private boolean hasWon() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][0] == board[i][2]
                || board[0][i] != 0 && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return true;
            }
        }
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[0][0] == board[2][2]
            || board[2][0] != 0 && board[2][0] == board[1][1] && board[2][0] == board[0][2]) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Board data.
     * @return the board data
     */
    public byte[][] boardData() {
        byte[][] copy = new byte[board.length][];
        for (int i = 0; i < board.length; i++) {
            copy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return copy;
    }
}
