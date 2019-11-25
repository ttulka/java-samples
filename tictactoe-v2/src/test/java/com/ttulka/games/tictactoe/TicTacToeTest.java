package com.ttulka.games.tictactoe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicTacToeTest {

    @Test
    void same_claims_in_a_row_wins() {
        TicTacToe game = new TicTacToe(3);
        game.move(0, 0);
        game.move(0, 1);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 0);

        assertThat(game.status()).isEqualTo(TicTacToe.Status.O_WON);
    }

    @Test
    void same_claims_in_a_column_wins() {
        TicTacToe game = new TicTacToe(3);
        game.move(0, 0);
        game.move(1, 0);
        game.move(0, 1);
        game.move(1, 1);
        game.move(0, 2);

        assertThat(game.status()).isEqualTo(TicTacToe.Status.O_WON);
    }

    @Test
    void same_claims_in_the_right_diagonal_wins() {
        TicTacToe game = new TicTacToe(3);
        game.move(0, 0);
        game.move(1, 0);
        game.move(1, 1);
        game.move(0, 2);
        game.move(2, 2);

        assertThat(game.status()).isEqualTo(TicTacToe.Status.O_WON);
    }

    @Test
    void same_claims_the_left_diagonal_wins() {
        TicTacToe game = new TicTacToe(3);
        game.move(2, 0);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 2);
        game.move(0, 2);

        assertThat(game.status()).isEqualTo(TicTacToe.Status.O_WON);
    }

    @Test
    void second_wins() {
        TicTacToe game = new TicTacToe(3);
        game.move(2, 0);
        game.move(0, 0);
        game.move(2, 1);
        game.move(0, 1);
        game.move(1, 1);
        game.move(0, 2);

        assertThat(game.status()).isEqualTo(TicTacToe.Status.X_WON);
    }

    @Test
    void board_is_full() {
        TicTacToe game = new TicTacToe(3);
        game.move(0, 0);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 0);
        game.move(2, 1);
        game.move(0, 1);
        game.move(0, 2);
        game.move(2, 2);
        game.move(1, 2);

        assertThat(game.status()).isEqualTo(TicTacToe.Status.FULL_BOARD);
    }

    @Test
    void cannot_move_when_the_board_is_full() {
        TicTacToe game = new TicTacToe(3);
        game.move(0, 0);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 0);
        game.move(2, 1);
        game.move(0, 1);
        game.move(0, 2);
        game.move(2, 2);
        game.move(1, 2);

        assertThrows(IllegalStateException.class, () -> game.move(0, 0));
    }

    @Test
    void cannot_move_when_already_won() {
        TicTacToe game = new TicTacToe(3);
        game.move(0, 0);
        game.move(0, 1);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 0);

        assertThrows(IllegalStateException.class, () -> game.move(2, 2));
    }

    @Test
    void cannot_move_to_an_already_set_field() {
        TicTacToe game = new TicTacToe(3);
        game.move(0, 0);

        assertThrows(IllegalArgumentException.class, () -> game.move(0, 0));
    }

    @Test
    void cannot_move_out_of_the_board() {
        TicTacToe game = new TicTacToe(3);

        assertThrows(IndexOutOfBoundsException.class, () -> game.move(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> game.move(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> game.move(3, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> game.move(0, 3));
    }

    @Test
    void after_a_wrong_move_the_players_are_not_changed() {
        TicTacToe game = new TicTacToe(3);
        game.move(0, 0);
        game.move(0, 1);
        game.move(1, 0);
        game.move(1, 1);

        try {
            game.move(0, 0);
        } catch (Exception ignore) {
        }
        try {
            game.move(-1, 3);
        } catch (Exception ignore) {
        }

        game.move(2, 0);

        assertThat(game.status()).isEqualTo(TicTacToe.Status.O_WON);
    }
}