package com.ttulka.missionmars;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
public class Rover {

    enum Direction {
        N, E, S, W
    }

    private Point position;
    private Direction direction;

    /**
     * Executes commands `F` (forwards), `B` (backwards), `L` (left), `R` (right).
     * @param commands the array of commands
     */
    public void execute(@NonNull char[] commands) {
        for (char c : commands) {
            char command = Character.toUpperCase(c);
            switch (command) {
                case 'F':
                case 'B':
                    move(command);
                    break;
                case 'L':
                case 'R':
                    turn(command);
                    break;
            }
        }
    }

    private void move(char command) {
        int step = command == 'F' ? 1 : -1;
        switch (direction) {
            case N:
                position = new Point(position.x, position.y + step);
                break;
            case E:
                position = new Point(position.x + step, position.y);
                break;
            case S:
                position = new Point(position.x, position.y - step);
                break;
            case W:
                position = new Point(position.x - step, position.y);
                break;
        }
    }

    private void turn(char command) {
        switch (direction) {
            case N:
                direction = command == 'R' ? Direction.E : Direction.W;
                break;
            case E:
                direction = command == 'R' ? Direction.S : Direction.N;
                break;
            case S:
                direction = command == 'R' ? Direction.W : Direction.E;
                break;
            case W:
                direction = command == 'R' ? Direction.N : Direction.S;
                break;
        }
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class Point {
        public final int x;
        public final int y;
    }
}
