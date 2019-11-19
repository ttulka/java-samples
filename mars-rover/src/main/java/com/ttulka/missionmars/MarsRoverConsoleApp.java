package com.ttulka.missionmars;

class MarsRoverConsoleApp {

    public static void main(String[] args) {
        Rover rover = new Rover(new Rover.Point(0, 0), Rover.Direction.N);
        rover.execute(new char[]{'F', 'F', 'F', 'R', 'F', 'F', 'L', 'B'});

        System.out.println(String.format("Rover is facing %s on [%d, %d].",
                                         rover.getDirection(), rover.getPosition().x, rover.getPosition().y));
    }
}
