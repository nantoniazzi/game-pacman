package com.codingame.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
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
import com.codingame.gameengine.module.entities.Rectangle;
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
            "#    #.##### ## #####.#    #",
            "#    #.##    A     ##.#    #",
            "#    #.## ###--### ##.#    #",
            "######.## #      # ##.######",
            ".......1  #C B D #  2.......",
            "######.## #      # ##.######",
            "#    #.## ######## ##.#    #",
            "#    #.##          ##.#    #",
            "#    #.## ######## ##.#    #",
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

    private static final Map<Character, String> INITIAL_PATHS = new HashMap<>();
    
    static {
        INITIAL_PATHS.put('A', "");
        INITIAL_PATHS.put('B', "UUU");
        INITIAL_PATHS.put('C', "UDUDUDUDUDUDUDURRUU");
        INITIAL_PATHS.put('D', "UDUDUDUDUDUDUDUDUDUDUDUDUDUDULLUU");
    }
    
    @Inject private GraphicEntityModule graphicEntityModule;
    @Inject private GameManager<Player> gameManager;
    @Inject private Provider<Ghost> ghostProvider;
    @Inject private HUD hud;

    private String[] level = LEVEL1;
    private Random random = new Random();
    private Group group;
    private List<Ghost> ghosts = new ArrayList<>();
    private Map<String, Entity<?>> entities = new HashMap<>();
    private Map<String, List<Point>> teleportPoints = new HashMap<>();
    private int gumCount = 0;

    private String coordToKey(int column, int row) {
        return String.format("%d-%d", column, row);
    }
    
    private String buildTeleportKey(Point pos, Direction direction) {
        return String.format("%d %d %s", pos.x, pos.y, direction.name());
    }

    public Random getRandom() {
        return random;
    }

    private void initTeleportPoints() {
        teleportPoints.clear();
        for (int i = 0; i < level.length; i++) {
            if (level[i].charAt(0) == '.') {
                List<Point> listLeft = new ArrayList<>();
                listLeft.add(new Point(level[i].length(), i));
                listLeft.add(new Point(level[i].length() - 1, i));
                teleportPoints.put(buildTeleportKey(new Point(-1, i), Direction.LEFT), listLeft);

                List<Point> listRight = new ArrayList<>();
                listRight.add(new Point(-1, i));
                listRight.add(new Point(0, i));
                teleportPoints.put(buildTeleportKey(new Point(level[i].length(), i), Direction.RIGHT), listRight);
                
                Rectangle maskLeft = graphicEntityModule.createRectangle()
                        .setX(-CELL_WIDTH * 2)
                        .setY(i * CELL_HEIGHT - CELL_HEIGHT)
                        .setWidth(CELL_WIDTH * 2)
                        .setHeight(CELL_HEIGHT * 3)
                        .setFillColor(0)
                        .setLineColor(0)
                        .setZIndex(2);
                
                Rectangle maskRight = graphicEntityModule.createRectangle()
                        .setX(level[i].length() * CELL_WIDTH)
                        .setY(i * CELL_HEIGHT - CELL_HEIGHT)
                        .setWidth(CELL_WIDTH * 2)
                        .setHeight(CELL_HEIGHT * 3)
                        .setFillColor(0)
                        .setLineColor(0)
                        .setZIndex(2);

                group.add(maskLeft, maskRight);
            }
        }
    }
    
    private void initGumCount() {
        for (int i = 0; i < level.length; i++) {
            if (level[i].charAt(0) == '.' || level[i].charAt(0) == 'o') {
                gumCount++;
            }
        }
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
            player.init(group, playerId++);
        }

        for (char ghostId : getAllGhostIds()) {
            Ghost ghost = ghostProvider.get();
            ghosts.add(ghost);

            String initialPath = INITIAL_PATHS.get(ghostId);
            ghost.init(group, ghostId, Direction.pathToDirections(initialPath));
        }

        initTeleportPoints();
        initGumCount();

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

    public void sendLevel() {
        for (Player player : gameManager.getActivePlayers()) {
            player.sendInputLine(String.format("%d %d", level[0].length(), level.length));
            for(String line : level) {
                player.sendInputLine(line.replaceAll("[1-9A-Z]", " "));
            }
        }
    }
    
    private Player getOpponent(Player player) {
        for (Player p : gameManager.getPlayers()) {
            if(p != player) {
                return p;
            }
        }
        
        return null;
    }

    public void sendCharactersPositions() {
        for (Player player : gameManager.getActivePlayers()) {
            player.sendInputLine(String.format("%d %d", player.getPos().x, player.getPos().y));
            player.sendInputLine(String.format("%d %d", getOpponent(player).getPos().x, getOpponent(player).getPos().y));
            player.sendInputLine(String.valueOf(getAllGhostIds().size()));
            for(Ghost g : ghosts) {
                player.sendInputLine(String.format("%d %d %d %s", (g.getId() - 'A'), g.getPos().x, g.getPos().y, g.getState()));
            }
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

    public Point getPosToFleeNearestPlayer(Point currentPos, Set<Point> nextPossiblePos) {
//        Point furthestPos = null;
//        double furthestDist = 0;
//
//        for (Player player : gameManager.getActivePlayers()) {
//            for (Point pos : nextPossiblePos) {
//                double dist = pos.distance(player.getPos());
//                if (furthestPos == null || furthestDist < dist) {
//                    furthestPos = pos;
//                    furthestDist = dist;
//                }
//            }
//        }
//
//        return furthestPos;

        Player closestPlayer = null;
        double closestPlayerDist = 100000.0;
        for(Player player : gameManager.getActivePlayers()) {
            double dist = player.getPos().distance(currentPos);
            if(dist < closestPlayerDist) {
                closestPlayer = player;
                closestPlayerDist = dist;
            }
        }

        Point furthestPos = null;
        double furthestDist = 0;
        for (Point pos : nextPossiblePos) {
            double dist = pos.distance(closestPlayer.getPos());
            if (furthestPos == null || furthestDist < dist) {
                furthestPos = pos;
                furthestDist = dist;
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

    public Point getGhostInitialPos(char id) {
        Point pos = null;

        for (int j = 0; j < level.length; j++) {
            for (int i = 0; i < level[j].length(); i++) {
                if (getCell(i, j) == id) {
                    pos = new Point(i, j);
                    break;
                }
            }
        }
        return pos;
    }

    public Point getGhostPos(char id) {
        return ghosts.get(id - 1).getPos();
    }

    public Point getPlayerInitialPos(int id) {
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

    public Point posToCoord(Point pos) {
        return posToCoord(pos, 0.5, 0.5);
    }

    public Point posToCoord(Point pos, double anchorX, double anchorY) {
        return new Point((pos.x * CELL_WIDTH) + (int)(anchorX * CELL_WIDTH), (pos.y * CELL_HEIGHT) + (int)(anchorY * CELL_HEIGHT));
    }

    public void update(int turn) {
        gameManager.setFrameDuration(200);
        for (Player player : gameManager.getPlayers()) {
            player.update();
        }

        // move ghost
        for (Ghost ghost : ghosts) {
            ghost.update();
        }

        // check if a ghost and a player have swapped or if they are on the same cell
        double contactRatio = 0;
        boolean freeze = false;
        for (Ghost ghost : ghosts) {
            for (Player player : gameManager.getActivePlayers()) {
//                System.err.println(String.format("T%d P%d-G%d %s %s %s %s",turn, player.getId(), ghost.getId(), player.getPos().toString(), ghost.getPos().toString(), player.getPreviousPos().toString(), ghost.getPreviousPos().toString()));
                if (player.getPos().equals(ghost.getPos())) {
                    contactRatio = 1;
                    player.contact(ghost, contactRatio);
                    ghost.contact(player, contactRatio);
                    freeze = true;
                } else if (player.getPos().equals(ghost.getPreviousPos()) && ghost.getPos().equals(player.getPreviousPos())) {
                    contactRatio = 0.5;
                    player.contact(ghost, contactRatio);
                    ghost.contact(player, contactRatio);
                    freeze = true;
                }
            }
        }
        
        if(contactRatio > 0 && gameManager.getFrameDuration() > 200) {
//            gameManager.setFrameDuration(1200);
            double freezeRatio = (200 * contactRatio) / (double)gameManager.getFrameDuration();
            double unfreezeRatio = 1.0 - freezeRatio;
            System.err.println(String.format("freezeratio = %f %f", freezeRatio, unfreezeRatio));
            for(Ghost ghost : ghosts) {
                ghost.freeze(freezeRatio);
            }

            for (Player player : gameManager.getPlayers()) {
                player.freeze(freezeRatio);
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
                for (Ghost ghost : ghosts) {
                    ghost.vulnerable();
                }
            }
        }
        for (Ghost ghost : ghosts) {
            ghost.debug();
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
        gumCount--;
    }

    public List<Point> getTeleportExitPoints(Point newPos, Direction direction) {
        List<Point> list = teleportPoints.get(buildTeleportKey(newPos, direction));
        if (list == null) {
            return Collections.emptyList();
        }

        return list;
    }

    public int getRemainingGumCount() {
        return gumCount;
    }
}
