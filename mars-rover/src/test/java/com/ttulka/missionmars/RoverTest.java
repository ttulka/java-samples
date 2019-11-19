package com.ttulka.missionmars;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoverTest {

    @Test
    void rover_ignores_unknown_commands() {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.N);
        rover.execute(new char[]{'X', '1'});

        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.N);
        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 0));
    }

    @Test
    void rover_moves_forwards_facing_north() {
        Rover rover = new Rover(new Rover.Point(1, 1), Rover.Direction.N);
        rover.execute(new char[]{'F'});

        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.N);
        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(1, 2));
    }

    @Test
    void rover_moves_backwards_facing_north() {
        Rover rover = new Rover(new Rover.Point(1, 1), Rover.Direction.N);
        rover.execute(new char[]{'B'});

        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.N);
        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(1, 0));
    }

    @Test
    void rover_moves_forwards_facing_east() {
        Rover rover = new Rover(new Rover.Point(1, 1), Rover.Direction.E);
        rover.execute(new char[]{'F'});

        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.E);
        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(2, 1));
    }

    @Test
    void rover_moves_backwards_facing_east() {
        Rover rover = new Rover(new Rover.Point(1, 1), Rover.Direction.E);
        rover.execute(new char[]{'B'});

        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.E);
        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 1));
    }

    @Test
    void rover_moves_forwards_facing_south() {
        Rover rover = new Rover(new Rover.Point(1, 1), Rover.Direction.S);
        rover.execute(new char[]{'F'});

        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.S);
        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(1, 0));
    }

    @Test
    void rover_moves_backwards_facing_south() {
        Rover rover = new Rover(new Rover.Point(1, 1), Rover.Direction.S);
        rover.execute(new char[]{'B'});

        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.S);
        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(1, 2));
    }

    @Test
    void rover_moves_forwards_facing_west() {
        Rover rover = new Rover(new Rover.Point(1, 1), Rover.Direction.W);
        rover.execute(new char[]{'F'});

        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.W);
        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 1));
    }

    @Test
    void rover_moves_backwards_facing_west() {
        Rover rover = new Rover(new Rover.Point(1, 1), Rover.Direction.W);
        rover.execute(new char[]{'B'});

        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.W);
        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(2, 1));
    }

    @Test
    void rover_turns_right_facing_north() {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.N);
        rover.execute(new char[]{'R'});

        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 0));
        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.E);
    }

    @Test
    void rover_turns_right_facing_east() {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.E);
        rover.execute(new char[]{'R'});

        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 0));
        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.S);
    }

    @Test
    void rover_turns_right_facing_south() {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.S);
        rover.execute(new char[]{'R'});

        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 0));
        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.W);
    }

    @Test
    void rover_turns_right_facing_west() {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.W);
        rover.execute(new char[]{'R'});

        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 0));
        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.N);
    }

    @Test
    void rover_turns_left_facing_north() {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.N);
        rover.execute(new char[]{'L'});

        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 0));
        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.W);
    }

    @Test
    void rover_turns_left_facing_east() {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.E);
        rover.execute(new char[]{'L'});

        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 0));
        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.N);
    }

    @Test
    void rover_turns_left_facing_south() {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.S);
        rover.execute(new char[]{'L'});

        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 0));
        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.E);
    }

    @Test
    void rover_turns_left_facing_west() {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.W);
        rover.execute(new char[]{'L'});

        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(0, 0));
        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.S);
    }

    @Test
    void rover_executes_multiple_commands() {
        Rover rover = new Rover(new Rover.Point(1, 1), Rover.Direction.N);
        rover.execute(new char[]{'R','F','L','B','L'});

        assertThat(rover.getPosition()).isEqualTo(new Rover.Point(2, 0));
        assertThat(rover.getDirection()).isEqualTo(Rover.Direction.W);
    }
}
