package com.ttulka.games.tictactoe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {

    @Test
    void three_in_a_row_wins() {
        Game game = new Game();
        game.move(0, 0);
        game.move(0, 1);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 0);

        assertThat(game.status()).isEqualTo(Game.Status.FIRST_WON);
    }

    @Test
    void three_in_a_column_wins() {
        Game game = new Game();
        game.move(0, 0);
        game.move(1, 0);
        game.move(0, 1);
        game.move(1, 1);
        game.move(0, 2);

        assertThat(game.status()).isEqualTo(Game.Status.FIRST_WON);
    }

    @Test
    void three_in_the_right_diagonal_wins() {
        Game game = new Game();
        game.move(0, 0);
        game.move(1, 0);
        game.move(1, 1);
        game.move(0, 2);
        game.move(2, 2);

        assertThat(game.status()).isEqualTo(Game.Status.FIRST_WON);
    }

    @Test
    void three_in_the_left_diagonal_wins() {
        Game game = new Game();
        game.move(2, 0);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 2);
        game.move(0, 2);

        assertThat(game.status()).isEqualTo(Game.Status.FIRST_WON);
    }

    @Test
    void second_wins() {
        Game game = new Game();
        game.move(2, 0);
        game.move(0, 0);
        game.move(2, 1);
        game.move(0, 1);
        game.move(1, 1);
        game.move(0, 2);

        assertThat(game.status()).isEqualTo(Game.Status.SECOND_WON);
    }

    @Test
    void board_is_full() {
        Game game = new Game();
        game.move(0, 0);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 0);
        game.move(2, 1);
        game.move(0, 1);
        game.move(0, 2);
        game.move(2, 2);
        game.move(1, 2);

        assertThat(game.status()).isEqualTo(Game.Status.FULL_BOARD);
    }

    @Test
    void cannot_move_when_the_board_is_full() {
        Game game = new Game();
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
        Game game = new Game();
        game.move(0, 0);
        game.move(0, 1);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 0);

        assertThrows(IllegalStateException.class, () -> game.move(2, 2));
    }

    @Test
    void cannot_move_to_an_already_set_field() {
        Game game = new Game();
        game.move(0, 0);

        assertThrows(IllegalArgumentException.class, () -> game.move(0, 0));
    }

    @Test
    void cannot_move_out_of_the_board() {
        Game game = new Game();

        assertThrows(IndexOutOfBoundsException.class, () -> game.move(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> game.move(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> game.move(3, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> game.move(0, 3));
    }

    @Test
    void after_a_wrong_move_the_players_are_not_changed() {
        Game game = new Game();
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

        assertThat(game.status()).isEqualTo(Game.Status.FIRST_WON);
    }
}
