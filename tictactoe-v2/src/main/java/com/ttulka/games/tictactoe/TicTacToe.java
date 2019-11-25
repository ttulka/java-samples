package com.ttulka.games.tictactoe;

import java.util.Arrays;

public class TicTacToe {

    /**
     * Status of the game.
     */
    public enum Status {
        RUNNING, O_WON, X_WON, FULL_BOARD
    }

    /**
     * Claim of a board field.
     */
    public enum Claim {
        EMPTY, O, X;
    }

    /**
     * Medium for printing a board row by row.
     */
    public interface PrintedBoard {
        /**
         * Print a claim.
         *
         * @param x     the x axis
         * @param y     the y axis
         * @param claim the claim
         */
        void print(int x, int y, Claim claim);
    }

    private final Claim[][] board;

    private Status status = Status.RUNNING;
    private boolean firstOnMove = true;

    public TicTacToe(int size) {
        board = new Claim[size][size];
        for (Claim[] row : board) {
            Arrays.fill(row, Claim.EMPTY);
        }
    }

    /**
     * Status of the game.
     *
     * @return the status
     */
    public Status status() {
        return status;
    }

    /**
     * Is the game running?
     *
     * @return true if the game is running, otherwise false
     */
    public boolean isRunning() {
        return status == Status.RUNNING;
    }

    /**
     * Who is on move?
     *
     * @return the claim for the current player
     */
    public Claim playerOnMove() {
        return firstOnMove ? Claim.O : Claim.X;
    }

    /**
     * Make a move to the position.
     *
     * @param x Position x
     * @param y Position y
     */
    public void move(int x, int y) {
        if (!isRunning()) {
            throw new IllegalStateException("It's over! " + status);
        }
        if (x < 0 || x >= board.length || y < 0 || y >= board.length) {
            throw new IndexOutOfBoundsException("Cannot move out of the board!");
        }
        if (board[x][y] != Claim.EMPTY) {
            throw new IllegalArgumentException("This field is already set!");
        }
        board[x][y] = firstOnMove ? Claim.O : Claim.X;

        if (isBoardFull()) {
            status = Status.FULL_BOARD;
        } else if (hasWon()) {
            status = firstOnMove ? Status.O_WON : Status.X_WON;
        }
        firstOnMove = !firstOnMove;
    }

    private boolean hasWon() {
        return hasWonDiagonal() || hasWonStraight();
    }

    private boolean hasWonStraight() {
        final int size = board.length;

        for (int i = 0; i < size; i++) {
            final Claim horizontalClaim = board[i][0];
            final Claim verticalClaim = board[0][i];

            boolean horizontalWon = horizontalClaim != Claim.EMPTY;
            boolean verticalWon = verticalClaim != Claim.EMPTY;

            for (int j = 1; j < size; j++) {
                horizontalWon = horizontalWon && board[i][j] == horizontalClaim;
                verticalWon = verticalWon && board[j][i] == verticalClaim;
            }
            if (horizontalWon || verticalWon) {
                return true;
            }
        }
        return false;
    }

    private boolean hasWonDiagonal() {
        final int size = board.length;

        final Claim leftClaim = board[0][0];
        final Claim rightClaim = board[0][size - 1];

        boolean leftWon = leftClaim != Claim.EMPTY;
        boolean rightWon = rightClaim != Claim.EMPTY;

        for (int i = 0; i < size; i++) {
            leftWon = leftWon && board[i][i] == leftClaim;
            rightWon = rightWon && board[i][size - 1 - i] == rightClaim;
        }
        return leftWon || rightWon;
    }

    private boolean isBoardFull() {
        return Arrays.stream(board).flatMap(Arrays::stream)
                .noneMatch(Claim.EMPTY::equals);
    }

    /**
     * Board data.
     *
     * @return the board data
     */
    public void print(PrintedBoard medium) {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                medium.print(x, y, board[x][y]);
            }
        }
    }
}
