package com.codingame.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public enum Direction {
    LEFT, RIGHT, DOWN, UP;

    public static Direction fromPoints(Point from, Point to) {
        Direction dir = Direction.DOWN;

        if (to.x - from.x < 0 && to.y - from.y == 0) {
            dir = Direction.LEFT;
        } else if (to.x - from.x > 0 && to.y - from.y == 0) {
            dir = Direction.RIGHT;
        } else if (to.x - from.x == 0 && to.y - from.y < 0) {
            dir = Direction.UP;
        } else if (to.x - from.x == 0 && to.y - from.y > 0) {
            dir = Direction.DOWN;
        } else {
            dir = Direction.DOWN;
        }

        return dir;
    }
    
    public static List<Direction> pathToDirections(String str) {
        List<Direction> directions = new ArrayList<>();
        for(int i = 0; i < str.length(); i++) {
            switch(str.charAt(i)) {
            case 'U':
                directions.add(Direction.UP);
                break;
            case 'D':
                directions.add(Direction.DOWN);
                break;
            case 'L':
                directions.add(Direction.LEFT);
                break;
            case 'R':
                directions.add(Direction.RIGHT);
                break;
            }
        }
        
        return directions;
    }

    public Point fromPoint(Point from) {
        Point ret = new Point(from);
        switch(this) {
            case LEFT:
                ret.x--;
                break;

            case RIGHT:
                ret.x++;
                break;

            case UP:
                ret.y--;
                break;

            case DOWN:
                ret.y++;
                break;
        }
        
        return ret;
    }
};
