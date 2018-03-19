import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

class Player {

    int width;
    int height;
    String[] level;
    Scanner in = new Scanner(System.in);

    Point myPreviousPos;
    Point myPos;
    Point opponentPos;
    Random random = new Random();

    class Ghost {
        int id;
        Point pos;
        String state;
    }

    Ghost ghosts[];

    void init() {
        int width = in.nextInt();
        int height = in.nextInt();
        //System.err.println(String.format("%d %d", width, height));
        //System.err.flush();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        level = new String[height];
        for (int i = 0; i < height; i++) {
            level[i] = in.nextLine();
            //System.err.println(String.format("%s", level[i]));
            //System.err.flush();
        }
    }

    void updateTurn() {
        int meX = in.nextInt();
        int meY = in.nextInt();
        myPos = new Point(meX, meY);
        //System.err.println(String.format("me: %d %d", meX, meY));
        //System.err.flush();

        int opponentX = in.nextInt();
        int opponentY = in.nextInt();
        opponentPos = new Point(opponentX, opponentY);
        //System.err.println(String.format("opp: %d %d", opponentX, opponentY));
        //System.err.flush();

        int ghostCount = in.nextInt();
        ghosts = new Ghost[ghostCount];
        //System.err.println(String.format("ghost count: %d", ghostCount));
        //System.err.flush();

        for (int i = 0; i < ghostCount; i++) {
            Ghost g = new Ghost();
            ghosts[i] = g;

            g.id = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            g.pos = new Point(x, y);
            g.state = in.next();
            //System.err.println(String.format("ghost %d (%d, %d) - %s", g.id, x, y, g.state));
            //System.err.flush();
        }

        List<Point> nextPossiblePos = getNextPossiblePos(myPos);
        nextPossiblePos.remove(new Point(5, 14));
        nextPossiblePos.remove(new Point(22, 14));
        if (myPreviousPos != null) {
            nextPossiblePos.remove(myPreviousPos);
        }

        ////System.err.println("next possible choices");
        for (Point p : nextPossiblePos) {
            ////System.err.println(String.format("%d %d", p.x, p.y));
        }
        ////System.err.println();
        Point fleePos = getPosToFleeNearestGhost(myPos, nextPossiblePos);
        if (random.nextBoolean()) {
            fleePos = nextPossiblePos.get(random.nextInt(nextPossiblePos.size()));
        }
        ////System.err.println(String.format("CHOICE: %d %d => %f", fleePos.x, fleePos.y, myPos.distance(fleePos)));

        Direction dir = Direction.fromPoints(myPos, fleePos);

        for (Ghost g : ghosts) {
            if (g.pos == fleePos || g.pos == dir.fromPoint(fleePos) || g.pos == dir.fromPoint(dir.fromPoint(fleePos))) {
                dir = dir.opposite();
                break;
            }
        }

        System.out.println(dir.name());
        myPreviousPos = myPos;
    }

    public static void main(String args[]) {
        Player player = new Player();
        player.init();

        while (true) {
            player.updateTurn();
        }
    }

    private Character getCell(int x, int y) {
        Character cell = ' ';
        if (x >= 0 && y >= 0 && y < level.length && x < level[0].length()) {
            return level[y].charAt(x);
        }

        return cell;
    }

    public Character getCell(Point pos) {
        return getCell(pos.x, pos.y);
    }

    public Set<Character> getAllGhostIds() {
        Set<Character> ids = new HashSet<>();
        for (int j = 0; j < level.length; j++) {
            for (int i = 0; i < level[j].length(); i++) {
                char cell = getCell(i, j);
                if (cell >= 'A' && cell <= 'Z') {
                    ids.add(cell);
                }
            }
        }

        return ids;
    }

    public boolean isCellTraversable(Character cell) {
        if (cell != null && cell != '#' && cell != '-') {
            return true;
        }

        return false;
    }

    public List<Point> getNextPossiblePos(Point pos) {
        List<Point> nextPos = new ArrayList<>();
        Point leftPos = new Point(pos.x - 1, pos.y);
        Point upPos = new Point(pos.x, pos.y - 1);
        Point rightPos = new Point(pos.x + 1, pos.y);
        Point downPos = new Point(pos.x, pos.y + 1);

        if (isCellTraversable(getCell(leftPos))) {
            nextPos.add(leftPos);
        }

        if (isCellTraversable(getCell(upPos))) {
            nextPos.add(upPos);
        }

        if (isCellTraversable(getCell(rightPos))) {
            nextPos.add(rightPos);
        }

        if (isCellTraversable(getCell(downPos))) {
            nextPos.add(downPos);
        }

        return nextPos;
    }

    public Point getPosToFleeNearestGhost(Point myPos, List<Point> nextPossiblePos) {
        Ghost closestGhost = null;
        double closestGhostDist = 100000.0;
        for (Ghost g : ghosts) {
            double dist = g.pos.distance(myPos);
            if (dist < closestGhostDist) {
                closestGhost = g;
                closestGhostDist = dist;
            }
        }

        Point furthestPos = null;
        double furthestDist = 0;
        for (Point pos : nextPossiblePos) {
            double dist = pos.distance(closestGhost.pos);
            if (furthestPos == null || furthestDist < dist) {
                furthestPos = pos;
                furthestDist = dist;
            }
        }

        return furthestPos;
    }

    enum Direction {
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
            for (int i = 0; i < str.length(); i++) {
                switch (str.charAt(i)) {
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
            switch (this) {
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

        public Direction opposite() {
            Direction ret = this;

            switch (this) {
            case LEFT:
                ret = RIGHT;
                break;

            case RIGHT:
                ret = LEFT;
                break;

            case UP:
                ret = DOWN;
                break;

            case DOWN:
                ret = UP;
                break;
            }

            return ret;
        }
    };

}