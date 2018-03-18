package com.codingame.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codingame.game.fsm.FSM;
import com.codingame.game.fsm.FSMState;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.google.inject.Inject;

import module.TooltipModule;

public class Ghost {
    private static final String STATE_INIT = "STATE_INIT";
    private static final String STATE_ATTACK = "STATE_ATTACK";
    private static final String STATE_FLEE = "STATE_FLEE";
    private static final String STATE_DEAD = "STATE_DEAD";

    private static final String EVENT_PLAYER_CONTACT = "EVENT_PLAYER_CONTACT";
    private static final String EVENT_PLAYER_EAT_SUPER_GUM = "EVENT_PLAYER_EAT_SUPER_GUM";
    
    private static final int VULNERABLE_TURNS_MAX = 40;
    private static final int VULNERABLE_TURNS_BLINK = 10;
    

    @Inject World world;
    @Inject GraphicEntityModule entityManager;
    @Inject GameManager<Player> gameManager;
    @Inject TooltipModule tooltips;

    private char id;
    private Point previousPos = new Point(0, 0);
    private Point pos;
    private Point targetPos;
    private Direction direction = Direction.DOWN;
    private Group entity;
    private int vulnerableRemainingTurns = 0;
    private static final double DEFAULT_SCALE = 0.8;
    private FSM fsm = new FSM();
    private Map<Direction, SpriteAnimation> attackDirectionSpriteMap = new HashMap<>();
    private Map<Direction, SpriteAnimation> deathDirectionSpriteMap = new HashMap<>();
    private Group attackAnimationGroup;
    private Group fleeAnimationGroup;
    private Group deathAnimationGroup;
    private SpriteAnimation fleeNormal;
    private SpriteAnimation fleeBlink;
    private int stepPerCell = 1;
    private int fleeTurns = 0;
    private Double contactDelay = null;

    private List<String> debug = new ArrayList<>();

    private List<Direction> initialPath;

    public Ghost() {
        fsm.addState(new FSMState(STATE_INIT) {
            @Override
            public void init() {
                attackAnimationGroup.setVisible(true);
                entityManager.commitEntityState(0, attackAnimationGroup);
            }

            @Override
            public void update() {
                if (initialPath == null || initialPath.isEmpty()) {
                    fsm.setState(STATE_ATTACK);
                } else {
                    Direction nextDir = initialPath.remove(0);
                    moveToPos(nextDir.fromPoint(pos));

                    if (initialPath.isEmpty()) {
                        fsm.setState(STATE_ATTACK);
                    }
                }
            }
        });

        fsm.addState(new FSMState(STATE_ATTACK) {

            @Override
            public void init() {
                attackAnimationGroup.setVisible(true);
                entityManager.commitEntityState(0, attackAnimationGroup);
                vulnerableRemainingTurns = 0;
            }

            @Override
            public void update() {
                // According to my position, I check the next possible positions 
                Set<Point> nextPossiblePos = world.getNextPossiblePos(pos);

                // I never want to go backward, so I remove my previous position from the list
                if (previousPos != null) {
                    nextPossiblePos.remove(previousPos);
                }

                // I first check what is the best target position to attack the nearest player
                Point attackPos = world.getPosToReachNearestPlayer(nextPossiblePos);
                if(attackPos == null) {
                    System.err.println("something strange...");
                    return;
                }

                // I have 2 chances on 3 to go to the correct position
                if (world.getRandom().nextInt(3) < 2) {
                    moveToPos(attackPos);
                } else {
                    // Randomly chose among the other options
                    Point[] arr = new Point[nextPossiblePos.size()];
                    arr = nextPossiblePos.toArray(arr);
                    moveToPos(arr[world.getRandom().nextInt(arr.length)]);
                }
            }

            @Override
            public void exit() {
                attackAnimationGroup.setVisible(false);
                entityManager.commitEntityState(0, attackAnimationGroup);
            }

            @Override
            public void event(String event, Object data) {
                if (EVENT_PLAYER_EAT_SUPER_GUM.equals(event)) {
                    fsm.setState(STATE_FLEE);
                }
            }
        });

        fsm.addState(new FSMState(STATE_FLEE) {
            @Override
            public void init() {
                //                vulnerableRemainingTurns = 20;
                vulnerableRemainingTurns = VULNERABLE_TURNS_MAX;
                fleeTurns = 0;
                stepPerCell = 2;
                fleeAnimationGroup.setVisible(true);
                fleeNormal.setVisible(true);
                fleeBlink.setVisible(false);
                entityManager.commitEntityState(0, fleeAnimationGroup, fleeNormal, fleeBlink);
            }

            @Override
            public void update() {
                vulnerableRemainingTurns--;

                if (vulnerableRemainingTurns == VULNERABLE_TURNS_BLINK) {
                    fleeNormal.setVisible(false);
                    fleeBlink.setVisible(true);
                } else if (vulnerableRemainingTurns == 0) {
                    fsm.setState(STATE_ATTACK);
                }
                
                int step = (fleeTurns % stepPerCell) + 1;
                if(stepPerCell == 1 || stepPerCell > 1 && step == 1) {
                    // According to my position, I check the next possible positions 
                    Set<Point> nextPossiblePos = world.getNextPossiblePos(pos);
    
                    // I never want to go backward, so I remove my previous position from the list
                    if (previousPos != null) {
                        nextPossiblePos.remove(previousPos);
                    }
    
                    // I first check what is the best target position to flee from the nearest player
                    Point fleePos = world.getPosToFleeNearestPlayer(pos, nextPossiblePos);
                    targetPos = fleePos;
                    moveToPos(fleePos);
                } else {
                    moveToPos(targetPos);
                }
                
                fleeTurns++;
            }

            @Override
            public void exit() {
                stepPerCell = 1;
                fleeAnimationGroup.setVisible(false);
                entityManager.commitEntityState(0, fleeAnimationGroup);
            }

            @Override
            public void event(String event, Object data) {
                if (EVENT_PLAYER_EAT_SUPER_GUM.equals(event)) {
                    vulnerableRemainingTurns = VULNERABLE_TURNS_MAX;
                    fleeNormal.setVisible(true);
                    fleeBlink.setVisible(false);
                    entityManager.commitEntityState(0, fleeNormal, fleeBlink);
                } else if (EVENT_PLAYER_CONTACT.equals(event)) {
                    fsm.setState(STATE_DEAD);
                    fleeAnimationGroup.setVisible(false);
                    double freezeRatio = (contactDelay * 200) / gameManager.getFrameDuration();
                    entityManager.commitEntityState(freezeRatio, fleeAnimationGroup, deathAnimationGroup);

//                    deathAnimationGroup.setVisible(true);
                    
//                    fsm.update();
                }
            }
        });

        fsm.addState(new FSMState(STATE_DEAD) {
            @Override
            public void init() {
                deathAnimationGroup.setVisible(true);
                entityManager.commitEntityState(0, deathAnimationGroup);
            }

            @Override
            public void update() {
                if (pos.x == 13 && pos.y == 11) {
                    moveToPos(new Point(13, 12));
                } else if (pos.x == 13 && pos.y == 12) {
                    moveToPos(new Point(13, 13));
                } else if (pos.x == 13 && pos.y == 13) {
                    moveToPos(new Point(13, 12));
                    initialPath.add(Direction.UP);
                    fsm.setState(STATE_INIT);
                } else {
                    // According to my position, I check the next possible positions 
                    Set<Point> nextPossiblePos = world.getNextPossiblePos(pos);

                    // I never want to go backward, so I remove my previous position from the list
                    if (previousPos != null) {
                        nextPossiblePos.remove(previousPos);
                    }

                    // I first check what is the best target position to flee from the nearest player
                    Point nextPos = world.getPosToReachGhostHome(nextPossiblePos);
                    moveToPos(nextPos);
                }
            }

            @Override
            public void exit() {
                deathAnimationGroup.setVisible(false);
                entityManager.commitEntityState(0, deathAnimationGroup);
            }
        });
    }

    private boolean isInGhostRoom(Point pos) {
        return (pos.x >= 10 && pos.x <= 16 && pos.y >= 12 && pos.y <= 16);
    }

    private void moveToPos(Point newPos) {
        List<Point> teleportExitPoints = world.getTeleportExitPoints(newPos, direction);
        if (teleportExitPoints.size() > 0) {
            Point coord1 = world.posToCoord(newPos);
            entity.setX(coord1.x).setY(coord1.y);
            entityManager.commitEntityState(0.49, entity);
            
            Point coord2 = world.posToCoord(teleportExitPoints.get(0));
            entity.setX(coord2.x, Curve.IMMEDIATE).setY(coord2.y, Curve.IMMEDIATE);
            entityManager.commitEntityState(0.51, entity);

            Point coord3 = world.posToCoord(teleportExitPoints.get(1));
            entity.setX(coord3.x).setY(coord3.y);

            pos = teleportExitPoints.get(1);
            previousPos = new Point(pos);
            if(previousPos.x == 0) {
                previousPos.x--;
            } else {
                previousPos.x++;
            }
        } else {
            Point ghostRoomOffset = new Point(0, 0);
            if (isInGhostRoom(newPos)) {
                ghostRoomOffset = new Point(16, 14);
            }

            Point coord;
            
            int step = (fleeTurns % stepPerCell) + 1;
            if(step < stepPerCell) {
                System.err.println();
                coord = world.posToCoord(pos);
                double ratio = (double)step / (double)stepPerCell;
                Point previousCoord = world.posToCoord(pos);
                Point nextCoord = world.posToCoord(newPos);
                coord = new Point(previousCoord);
                coord.x += (nextCoord.x - previousCoord.x) * ratio;
                coord.y += (nextCoord.y - previousCoord.y) * ratio;
            } else {
                previousPos = pos;
                pos = newPos;
                coord = world.posToCoord(pos);
            }
            
            debug.add(String.format("previousPos = %d %d", previousPos.x, previousPos.y));
            debug.add(String.format("pos = %d %d", pos.x, pos.y));
            debug.add(String.format("step = %d / %d", step, stepPerCell));
            debug.add(String.format("move to: %d%s %d%s", coord.x, ghostRoomOffset.x > 0 ? String.valueOf(ghostRoomOffset.x) : "", coord.y, ghostRoomOffset.y > 0 ? String.valueOf(ghostRoomOffset.y) : ""));
            entity.setX(coord.x + ghostRoomOffset.x).setY(coord.y + ghostRoomOffset.y);
//            if(Math.abs(previousPos.x - newPos.x) == 1) {
                direction = Direction.fromPoints(previousPos, newPos);
//            }
            updateDirectionAnimation();
        }
    }

    public char getId() {
        return id;
    }

    public Point getPos() {
        return pos;
    }

    public Point getPreviousPos() {
        return previousPos;
    }

    public void init(Group parent, char id, List<Direction> initialPath) {
        this.id = id;
        this.initialPath = initialPath;

        this.entity = entityManager.createGroup().setZIndex(1);
        attackAnimationGroup = entityManager.createGroup();
        for (Direction d : Direction.values()) {
            SpriteAnimation sprite = entityManager.createSpriteAnimation()
                    .setLoop(true)
                    .setStarted(true)
                    .setDuration(100)
                    .setScale(DEFAULT_SCALE)
                    .setAnchor(0.5)
//                    .setAnchorX(0.1)
//                    .setAnchorY(0.15)
                    .setVisible(false);
            String[] images = new String[4];
            for (int i = 1; i <= 4; i++) {
                images[i - 1] = String.format("ghost%c/%s/%d.png", id, d.name().toLowerCase(), i);
                sprite.setImages(images);
            }
            attackDirectionSpriteMap.put(d, sprite);
            attackAnimationGroup.add(sprite);
        }

        fleeAnimationGroup = entityManager.createGroup().setVisible(false);
        fleeNormal = entityManager.createSpriteAnimation()
                .setLoop(true)
                .setStarted(true)
                .setDuration(100)
                .setScale(DEFAULT_SCALE)
                .setAnchor(0.5)
//                .setAnchorX(0.1)
//                .setAnchorY(0.15)
                .setVisible(false)
                .setImages("ghost/flee/normal/1.png", "ghost/flee/normal/2.png");

        fleeBlink = entityManager.createSpriteAnimation()
                .setLoop(true)
                .setStarted(true)
                .setDuration(200)
                .setScale(DEFAULT_SCALE)
                .setAnchor(0.5)
//                .setAnchorX(0.1)
//                .setAnchorY(0.15)
                .setVisible(false)
                .setImages("ghost/flee/blink/1.png", "ghost/flee/blink/2.png", "ghost/flee/blink/3.png", "ghost/flee/blink/4.png");
        fleeAnimationGroup.add(fleeNormal, fleeBlink);

        deathAnimationGroup = entityManager.createGroup().setVisible(false);
        for (Direction d : Direction.values()) {
            SpriteAnimation sprite = entityManager.createSpriteAnimation()
                    .setLoop(true)
                    .setStarted(true)
                    .setDuration(100)
                    .setScale(DEFAULT_SCALE)
                    .setAnchor(0.5)
//                    .setAnchorX(0.1)
//                    .setAnchorY(0.15)
                    .setVisible(false);
            String[] images = new String[2];
            for (int i = 1; i <= 2; i++) {
                images[i - 1] = String.format("ghost/death/%s/%d.png", d.name().toLowerCase(), i);
                sprite.setImages(images);
            }
            deathDirectionSpriteMap.put(d, sprite);
            deathAnimationGroup.add(sprite);
        }

        entity.add(attackAnimationGroup, fleeAnimationGroup, deathAnimationGroup);
        parent.add(entity);

        pos = world.getGhostInitialPos(id);
        moveToPos(pos);

        if(initialPath.isEmpty()) {
            fsm.init(STATE_ATTACK);
        } else {
            fsm.init(STATE_INIT);
        }
        
        tooltips.registerEntity(entity);
    }

    public void update() {
        debug.clear();
        fsm.update();
    }

    public void contact(Player player, double contactRatio) {
        this.contactDelay = contactRatio;
        fsm.sendEvent(EVENT_PLAYER_CONTACT, player);
    }

    public void vulnerable() {
        fsm.sendEvent(EVENT_PLAYER_EAT_SUPER_GUM, null);
    }

    private void updateDirectionAnimation() {
        for (SpriteAnimation spriteAnimation : attackDirectionSpriteMap.values()) {
            spriteAnimation.setVisible(false);
            entityManager.commitEntityState(0, spriteAnimation);
        }
        for (SpriteAnimation spriteAnimation : deathDirectionSpriteMap.values()) {
            spriteAnimation.setVisible(false);
            entityManager.commitEntityState(0, spriteAnimation);
        }
        attackDirectionSpriteMap.get(direction).setVisible(true);
        deathDirectionSpriteMap.get(direction).setVisible(true);
        entityManager.commitEntityState(0, attackDirectionSpriteMap.get(direction), deathDirectionSpriteMap.get(direction));
    }

    public boolean isDangerous() {
        return STATE_ATTACK.equals(fsm.getCurrentStateId());
    }

    public boolean isVulnerable() {
        return STATE_FLEE.equals(fsm.getCurrentStateId());
    }

    public void freeze(double delayBeforeFreeze) {
        debug.add(String.format("FREEEZE: %f", delayBeforeFreeze));
        int newX = entity.getX();
        int newY = entity.getY();

        Point coord = world.posToCoord(previousPos);
        int previousX = coord.x;
        int previousY = coord.y;
        
        double freezeRatio = (delayBeforeFreeze * gameManager.getFrameDuration()) / 200.0;

        int freezeX = (int)(previousX + ((double)(newX - previousX) * freezeRatio));
        int freezeY = (int)(previousY + ((double)(newY - previousY) * freezeRatio));

        debug.add(String.format("SET AT %f: %d %d", delayBeforeFreeze, freezeX, freezeY)); 
        entity.setX(freezeX).setY(freezeY);
        entityManager.commitEntityState(delayBeforeFreeze, entity);
        
        debug.add(String.format("SET AT %f: %d %d", 1.0 - delayBeforeFreeze, freezeX + 1, freezeY + 1)); 
        entity.setX(freezeX + 1).setY(freezeY + 1);
        entityManager.commitEntityState(1.0 - delayBeforeFreeze, entity);

        debug.add(String.format("SET AT 1: %d %d", newX, newY)); 
        entity.setX(newX).setY(newY);
        entityManager.commitEntityState(1, entity);
    }
    
    public void debug() {
        tooltips.updateExtraTooltipText(entity, String.join("\n", debug));
    }

    public String getState() {
        return fsm.getCurrentStateId();
    }
}
