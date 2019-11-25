package com.ttulka.games.tictactoe;

import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        do {
            Game game = new Game();
            while (game.status() == Game.Status.ON) {
                System.out.print("Choose your move: ");
                int x = in.nextInt();
                int y = in.nextInt();

                try {
                    game.move(x, y);
                } catch (Exception e) {
                    System.out.print(e.getMessage() + "\n");
                }
                printBoard(game.boardData());
            }

            printResults(game.status());

            System.out.print("Press Y to play again or any other key to exit: ");
        } while ("Y".equals(in.next()));
    }

    private static void printResults(Game.Status status) {
        switch (status) {
            case FIRST_WON:
                System.out.println("First player won!");
                break;
            case SECOND_WON:
                System.out.println("Second player won!");
                break;
            case FULL_BOARD:
                System.out.println("The board is full - it's a tie!");
                break;
        }
    }

    private static void printBoard(byte[][] board) {
        for (int y = board.length - 1; y >= 0 ; y--) {
            for (int x = 0; x < board.length; x++) {
                System.out.print(board[x][y] == Game.O ? "o" : board[x][y] == Game.X ? "x" : ".");
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
