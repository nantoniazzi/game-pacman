package com.codingame.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codingame.game.fsm.FSM;
import com.codingame.game.fsm.FSMState;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.google.inject.Inject;

public class Ghost {
    private static final String STATE_INIT = "STATE_INIT";
    private static final String STATE_ATTACK = "STATE_ATTACK";
    private static final String STATE_FLEE = "STATE_FLEE";
    private static final String STATE_DEAD = "STATE_DEAD";

    private static final String EVENT_PLAYER_CONTACT = "EVENT_PLAYER_CONTACT";
    private static final String EVENT_PLAYER_EAT_SUPER_GUM = "EVENT_PLAYER_EAT_SUPER_GUM";

    @Inject World world;
    @Inject GraphicEntityModule entityManager;

    private int id;
    private Point previousPos = new Point(0, 0);
    private Point pos;
    private Direction direction = Direction.DOWN;
    private Group entity;
    private int vulnerableRemainingTurns = 0;
    private static final double DEFAULT_SCALE = 0.8;
    private static final int OFFSET_X = 0;
    private static final int OFFSET_Y = 0;
    private FSM fsm = new FSM();
    private Map<Direction, SpriteAnimation> attackDirectionSpriteMap = new HashMap<>();
    private Map<Direction, SpriteAnimation> deathDirectionSpriteMap = new HashMap<>();
    private Group attackAnimationGroup;
    private Group fleeAnimationGroup;
    private Group deathAnimationGroup;
    private SpriteAnimation fleeNormal;
    private SpriteAnimation fleeBlink;

    private List<Direction> initialPath;

    public Ghost() {
        fsm.addState(new FSMState(STATE_INIT) {
            @Override
            public void init() {
                attackAnimationGroup.setVisible(true);
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
                vulnerableRemainingTurns = 20;
                fleeAnimationGroup.setVisible(true);
                fleeNormal.setVisible(true);
                fleeBlink.setVisible(false);
            }

            @Override
            public void update() {
                vulnerableRemainingTurns--;

                if (vulnerableRemainingTurns == 5) {
                    fleeNormal.setVisible(false);
                    fleeBlink.setVisible(true);
                } else if (vulnerableRemainingTurns == 0) {
                    fsm.setState(STATE_ATTACK);
                }

                // According to my position, I check the next possible positions 
                Set<Point> nextPossiblePos = world.getNextPossiblePos(pos);

                // I never want to go backward, so I remove my previous position from the list
                if (previousPos != null) {
                    nextPossiblePos.remove(previousPos);
                }

                // I first check what is the best target position to flee from the nearest player
                Point fleePos = world.getPosToFleeNearestPlayer(nextPossiblePos);
                moveToPos(fleePos);
            }

            @Override
            public void exit() {
                fleeAnimationGroup.setVisible(false);
            }

            @Override
            public void event(String event, Object data) {
                if (EVENT_PLAYER_EAT_SUPER_GUM.equals(event)) {
                    vulnerableRemainingTurns = 10;
                } else if (EVENT_PLAYER_CONTACT.equals(event)) {
                    fsm.setState(STATE_DEAD);
                }
            }
        });

        fsm.addState(new FSMState(STATE_DEAD) {
            @Override
            public void init() {
                deathAnimationGroup.setVisible(true);
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
            }
        });
    }

    private boolean isInGhostRoom(Point pos) {
        return (pos.x >= 10 && pos.x <= 16 && pos.y >= 12 && pos.y <= 16);
    }

    private void moveToPos(Point newPos) {
        previousPos = pos;
        pos = newPos;
        direction = Direction.fromPoints(previousPos, pos);
        updateDirectionAnimation();

        Point ghostRoomOffset = new Point(0, 0);
        if (isInGhostRoom(pos)) {
            ghostRoomOffset = new Point(16, 14);
        }

        Point coord = world.posToCoord(pos);
        entity.setX(coord.x + OFFSET_X + ghostRoomOffset.x).setY(coord.y + OFFSET_Y + ghostRoomOffset.y);
    }

    public int getId() {
        return id;
    }

    public Point getPos() {
        return pos;
    }

    public Point getPreviousPos() {
        return previousPos;
    }

    public void init(int id, List<Direction> initialPath) {
        this.id = id;
        this.initialPath = initialPath;

        this.entity = entityManager.createGroup();
        attackAnimationGroup = entityManager.createGroup();
        for (Direction d : Direction.values()) {
            SpriteAnimation sprite = entityManager.createSpriteAnimation()
                    .setLoop(true)
                    .setStarted(true)
                    .setDuration(100)
                    .setScale(DEFAULT_SCALE)
                    .setAnchorX(0.1)
                    .setAnchorY(0.15)
                    .setVisible(false);
            String[] images = new String[4];
            for (int i = 1; i <= 4; i++) {
                images[i - 1] = String.format("ghost%d/%s/%d.png", id, d.name().toLowerCase(), i);
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
                .setAnchorX(0.1)
                .setAnchorY(0.15)
                .setVisible(false)
                .setImages("ghost/flee/normal/1.png", "ghost/flee/normal/2.png");

        fleeBlink = entityManager.createSpriteAnimation()
                .setLoop(true)
                .setStarted(true)
                .setDuration(200)
                .setScale(DEFAULT_SCALE)
                .setAnchorX(0.1)
                .setAnchorY(0.15)
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
                    .setAnchorX(0.1)
                    .setAnchorY(0.15)
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

        pos = world.getGhostInitialPos(id);
        moveToPos(pos);

        fsm.init(STATE_INIT);
    }

    public void update() {
        fsm.update();
    }

    public void contact(Player player) {
        fsm.sendEvent(EVENT_PLAYER_CONTACT, player);
    }

    public void vulnerable() {
        fsm.sendEvent(EVENT_PLAYER_EAT_SUPER_GUM, null);
    }

    private void updateDirectionAnimation() {
        for (SpriteAnimation spriteAnimation : attackDirectionSpriteMap.values()) {
            spriteAnimation.setVisible(false);
        }
        for (SpriteAnimation spriteAnimation : deathDirectionSpriteMap.values()) {
            spriteAnimation.setVisible(false);
        }
        attackDirectionSpriteMap.get(direction).setVisible(true);
        deathDirectionSpriteMap.get(direction).setVisible(true);
    }

    public boolean isVulnerable() {
        return vulnerableRemainingTurns > 0;
    }
}
