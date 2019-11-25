package com.ttulka.games.tictactoe;

import java.util.Scanner;

public class TicTacToeConsoleApplication {

    private static final int BOARD_SIZE = 3;

    public static void main(String[] args) {
        new TicTacToeConsoleApplication().play();
    }

    public void play() {
        Scanner in = new Scanner(System.in);
        BoardPrinter printer = new BoardPrinter(BOARD_SIZE);
        do {
            TicTacToe game = new TicTacToe(BOARD_SIZE);
            while (game.isRunning()) {
                game.print(printer::printClaim);
                System.out.print("\nPlayer on move: " + new PrintedClaim(game.playerOnMove()).asString());
                System.out.print("\nChoose your move [x y]: ");
                int x = in.nextInt();
                int y = in.nextInt();
                try {
                    game.move(x, y);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            game.print(printer::printClaim);
            printResults(game.status());

            System.out.print("\nEnter [Y] to play again, or any other key to exit: ");
        } while ("Y".equalsIgnoreCase(in.next()));
    }

    private void printResults(TicTacToe.Status status) {
        switch (status) {
            case O_WON:
                System.out.print("\nO player won!");
                break;
            case X_WON:
                System.out.print("\nX player won!");
                break;
            case FULL_BOARD:
                System.out.print("\nThe board is full - it's a tie!");
                break;
        }
    }

    static class BoardPrinter {

        private final int size;

        public BoardPrinter(int size) {
            this.size = size;
        }

        public void printClaim(int x, int y, TicTacToe.Claim claim) {
            if (x == 0 && y == 0) {
                System.out.print("\\ x ");
                for (int i = 0; i < size; i++) {
                    System.out.print(i + "  ");
                }
                System.out.print("\ny ┌");
                for (int i = 0; i < size; i++) {
                    System.out.print("───");
                }
                System.out.println();
            }
            if (x == 0 && y != 0) {
                System.out.println();
            }
            if (x == 0) {
                System.out.print(y + " │ ");
            }
            System.out.print(new PrintedClaim(claim).asString());
            System.out.print("  ");
        }
    }

    static class PrintedClaim {

        private final TicTacToe.Claim claim;

        public PrintedClaim(TicTacToe.Claim claim) {
            this.claim = claim;
        }

        public String asString() {
            return claim == TicTacToe.Claim.O ? "o" : claim == TicTacToe.Claim.X ? "×" : ".";
        }
    }
}
