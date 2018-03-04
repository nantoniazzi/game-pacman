package com.codingame.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class World {
    public static final int CELL_WIDTH = 34;
    public static final int CELL_HEIGHT = 34;

    private static final String[] LEVEL1 = {
            "############################",
            "#............##............#",
            "#.####.#####.##.#####.####.#",
            "#o####.#####.##.#####.####o#",
            "#.####.#####.##.#####.####.#",
            "#..........................#",
            "#.####.##.########.##.####.#",
            "#.####.##.########.##.####.#",
            "#......##....##....##......#",
            "######.##### ## #####.######",
            "     #.##### ## #####.#     ",
            "     #.##    1     ##.#     ",
            "     #.## ###--### ##.#     ",
            "######.## #      # ##.######",
            ".....#A   #3 2 4 #   B#.....",
            "######.## #      # ##.######",
            "     #.## ######## ##.#     ",
            "     #.##          ##.#     ",
            "     #.## ######## ##.#     ",
            "######.## ######## ##.######",
            "#............##............#",
            "#.####.#####.##.#####.####.#",
            "#.####.#####.##.#####.####.#",
            "#o..##................##..o#",
            "###.##.##.########.##.##.###",
            "###.##.##.########.##.##.###",
            "#......##....##....##......#",
            "#.##########.##.##########.#",
            "#.##########.##.##########.#",
            "#..........................#",
            "############################",
    };

    @Inject private GraphicEntityModule graphicEntityModule;
    @Inject private GameManager<Player> gameManager;
    @Inject private Provider<Ghost> ghostProvider;
    @Inject private HUD hud;
    
    private String[] level = LEVEL1; 
    private Random random = new Random();
    private Group group;
    private List<Ghost> ghosts = new ArrayList<>();
    private Map<String, Entity<?>> entities = new HashMap<>();
    private String[] initialsPath = {
            "",
            "UUU",
            "UDUDUDUDUDUDUDURRUU",
            "UDUDUDUDUDUDUDUDUDUDUDUDUDUDULLUU",
    };
    
    private String coordToKey(int column, int row) {
        return String.format("%d-%d", column, row);
    }

    public Random getRandom() {
        return random;
    }

    public void init(long seed) {
        random.setSeed(seed);

        graphicEntityModule.createSprite()
                .setImage("Background.jpg")
                .setAnchor(0);

        Sprite level1 = graphicEntityModule.createSprite()
                .setImage("level1.png")
                .setAnchor(0)
                .setBaseWidth(CELL_WIDTH * level[0].length())
                .setBaseHeight(CELL_HEIGHT * level.length);

        group = graphicEntityModule.createGroup();
        group.add(level1);
        group.setX((graphicEntityModule.getWorld().getWidth() / 2) - (int) (CELL_WIDTH * level[0].length() * 0.5));
        group.setY(10);

        int playerId = 1;
        for (Player player : gameManager.getActivePlayers()) {
            player.init(playerId++);
        }

        for (int ghostId : getAllGhostIds()) {
            Ghost ghost = ghostProvider.get();
            ghosts.add(ghost);

            String initialPath = initialsPath[ghostId - 1];
            ghost.init(ghostId, Direction.pathToDirections(initialPath));
        }
        
        hud.init();

        for (int rowIndex = 0; rowIndex < level.length; rowIndex++) {
            String row = level[rowIndex];
            for (int columnIndex = 0; columnIndex < row.length(); columnIndex++) {
                Entity<?> entity = null;
                char cell = row.charAt(columnIndex);
                switch (cell) {
                //                case '#':
                //                    entity = graphicEntityModule
                //                            .createRectangle()
                //                            .setLineWidth(0)
                //                            .setWidth(CELL_WIDTH)
                //                            .setHeight(CELL_HEIGHT)
                //                            .setFillColor(0x0000ff)
                //                            .setX(columnIndex * CELL_WIDTH)
                //                            .setY(rowIndex * CELL_HEIGHT);
                //                    break;

                case '.':
                    entity = graphicEntityModule
                            .createCircle()
                            .setLineWidth(0)
                            .setRadius((int) (CELL_WIDTH * 0.1))
                            .setFillColor(0xffffff)
                            .setX(columnIndex * CELL_WIDTH + CELL_WIDTH / 2)
                            .setY(rowIndex * CELL_HEIGHT + CELL_HEIGHT / 2);
                    break;

                case 'o':
                    entity = graphicEntityModule
                            .createCircle()
                            .setLineWidth(0)
                            .setRadius((int) (CELL_WIDTH * 0.25))
                            .setFillColor(0xffffff)
                            .setX(columnIndex * CELL_WIDTH + CELL_WIDTH / 2)
                            .setY(rowIndex * CELL_HEIGHT + CELL_HEIGHT / 2);
                    break;
                }

                if (entity != null) {
                    entities.put(coordToKey(columnIndex, rowIndex), entity);
                    group.add(entity);
                }
            }
        }
    }

    private Character getCell(int x, int y) {
        Character cell = null;
        if (x >= 0 && y >= 0 && y < level.length && x < level[0].length()) {
            return level[y].charAt(x);
        }

        return cell;
    }

    public Character getCell(Point pos) {
        return getCell(pos.x, pos.y);
    }

    public Set<Integer> getAllGhostIds() {
        Set<Integer> ids = new HashSet<>();
        for (int j = 0; j < level.length; j++) {
            for (int i = 0; i < level[j].length(); i++) {
                char cell = getCell(i, j);
                if (cell >= '1' && cell <= '9') {
                    int id = Integer.valueOf(String.valueOf(cell));
                    if (id >= 1 && id <= 9) {
                        ids.add(id);
                    }
                }
            }
        }

        return ids;
    }

    public Player getNearestPlayer(Point from) {
        Player nearestPlayer = null;
        double nearestDist = 0;
        for (Player p : gameManager.getActivePlayers()) {
            double dist = from.distance(p.getPos());
            if (nearestPlayer == null || nearestDist > dist) {
                nearestPlayer = p;
                nearestDist = dist;
            }
        }

        return nearestPlayer;
    }

    public boolean isCellTraversable(Character cell) {
        if (cell != null && cell != '#' && cell != '-') {
            return true;
        }

        return false;
    }

    public Set<Point> getNextPossiblePos(Point pos) {
        Set<Point> nextPos = new HashSet<>();
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

    public Point getPosToReachNearestPlayer(Set<Point> nextPossiblePos) {
        Point nearestPos = null;
        double nearestDist = 0;

        for (Player player : gameManager.getActivePlayers()) {
            for (Point pos : nextPossiblePos) {
                double dist = pos.distance(player.getPos());
                if (nearestPos == null || nearestDist > dist) {
                    nearestPos = pos;
                    nearestDist = dist;
                }
            }
        }

        return nearestPos;
    }

    public Point getPosToFleeNearestPlayer(Set<Point> nextPossiblePos) {
        Point furthestPos = null;
        double furthestDist = 0;

        for (Player player : gameManager.getActivePlayers()) {
            for (Point pos : nextPossiblePos) {
                double dist = pos.distance(player.getPos());
                if (furthestPos == null || furthestDist < dist) {
                    furthestPos = pos;
                    furthestDist = dist;
                }
            }
        }

        return furthestPos;
    }
    
    public Point getPosToReachGhostHome(Set<Point> nextPossiblePos) {
        Point nextPos = null;
        double nextDist = 0;

        for (Point pos : nextPossiblePos) {
            double dist = pos.distance(new Point(13, 11));
            if (nextPos == null || nextDist > dist) {
                nextPos = pos;
                nextDist = dist;
            }
        }

        return nextPos;
    }

    public Point getGhostInitialPos(int id) {
        Point pos = null;

        for (int j = 0; j < level.length; j++) {
            for (int i = 0; i < level[j].length(); i++) {
                if (getCell(i, j) == String.valueOf(id).charAt(0)) {
                    pos = new Point(i, j);
                    break;
                }
            }
        }
        return pos;
    }

    public Point getGhostPos(int id) {
        return ghosts.get(id - 1).getPos();
    }

    public Point getPlayerInitialPos(int id) {
        Point pos = null;

        for (int j = 0; j < level.length; j++) {
            for (int i = 0; i < level[j].length(); i++) {
                if (getCell(i, j) == 'A' + (id - 1)) {
                    pos = new Point(i, j);
                    break;
                }
            }
        }
        return pos;
    }

    public Point posToCoord(Point pos) {
        return new Point(group.getX() + (pos.x * CELL_WIDTH), group.getY() + (pos.y * CELL_HEIGHT));
    }

    public void update() {
        for (Player player : gameManager.getPlayers()) {
            player.update();
        }

        // move ghost
        for (Ghost ghost : ghosts) {
            ghost.update();
        }
        
        // check if a ghost and a player have swapped or if they are on the same cell
        for (Ghost ghost : ghosts) {
            for (Player player : gameManager.getActivePlayers()) {
                //                System.err.println(String.format("P%d-G%d %s %s %s %s", player.getId(), ghost.getId(), player.getPos().toString(), ghost.getPos().toString(), player.getPreviousPos().toString(), ghost.getPreviousPos().toString()));
                if (player.getPos().equals(ghost.getPos())
                        || (player.getPos().equals(ghost.getPreviousPos()) && ghost.getPos().equals(player.getPreviousPos()))) {
                    player.contact(ghost);
                    ghost.contact(player);
                }
            }
        }

        // check if a player eats a gum
        for (Player player : gameManager.getActivePlayers()) {
            if (getCell(player.getPos()) == '.') {
                player.eatGum();
                eatGum(player.getPos());
            } else if (getCell(player.getPos()) == 'o') {
                player.eatSuperGum();
                eatGum(player.getPos());
                for(Ghost ghost : ghosts) {
                    ghost.vulnerable();
                }
            }
        }
        
        hud.update();
    }

    private void eatGum(Point pos) {
        String line = level[pos.y];
        line = String.format("%s %s", line.substring(0, pos.x), line.substring(pos.x + 1));
        level[pos.y] = line;
        Entity<?> gum = entities.get(coordToKey(pos.x, pos.y));
        gum.setAlpha(1.0);
        graphicEntityModule.commitEntityState(0.5, gum);
        gum.setAlpha(0);
    }
}
