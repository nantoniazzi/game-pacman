package com.codingame.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.codingame.game.fsm.FSM;
import com.codingame.game.fsm.FSMState;
import com.codingame.gameengine.core.AbstractPlayer;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.codingame.gameengine.module.entities.Text;
import com.google.inject.Inject;

import module.TooltipModule;

public class Player extends AbstractPlayer {

    public class InvalidActionException extends Exception {
        private static final long serialVersionUID = 4742197738867606487L;
    }

    private static final String STATE_ALIVE = "STATE_ALIVE";
    private static final String STATE_DYING = "STATE_DYING";
    private static final String STATE_DEAD = "STATE_DEAD";

    private static final String EVENT_MOVE_INTO_DIRECTION = "EVENT_MOVE_INTO_DIRECTION";
    private static final double DEFAULT_SCALE = 0.8;
    
    private static final int[] BONUS_POINTS = {200, 400, 800, 1600};

    @Inject GraphicEntityModule entityManager;
    @Inject GameManager<Player> gameManager;
    @Inject World world;
    @Inject TooltipModule tooltips;
    
    private Group parent;
    private int bonusPoints = 0;

    private class AnimationManager {
        private Map<String, SpriteAnimation> animations = new HashMap<>();
        private String currentAnimation = null;
        private Integer pausedAnimationDuration = null;
        
        public void addAnimation(String name, SpriteAnimation animation) {
            animations.put(name, animation);
        }
        
        public void startAnimation(String name) {
            for(Entry<String, SpriteAnimation> entry : animations.entrySet()) {
                if(entry.getKey().equals(name)) {
                    this.currentAnimation = name;
                    this.pausedAnimationDuration = null;
                    entry.getValue().setStarted(true);
                } else {
                    entry.getValue().setStarted(false);
                }
            }
        }
        
        public void pauseAnimation() {
            pausedAnimationDuration = animations.get(currentAnimation).getDuration(); 
            animations.get(currentAnimation).setDuration(100000);
        }
        
        public void unpauseAnimation() {
            if(pausedAnimationDuration != null) {
                animations.get(currentAnimation).setDuration(pausedAnimationDuration);
            }
        }

        public void commit(double t) {
            for(SpriteAnimation animation : animations.values()) {
                entityManager.commitEntityState(t, animation);
            }
        }
    }

    private int id;
    private Point previousPos;
    private Point pos;
    private Group entity;
    private AnimationManager animationManager = new AnimationManager();
    private Direction previousDirection = Direction.RIGHT;
    private Direction direction = Direction.RIGHT;
    private Double contactDelay = null;
    private static int combo = 0;
    
//    private SpriteAnimation idle;
//    private SpriteAnimation eat;
//    private SpriteAnimation die;
    private FSM fsm = new FSM();

    public Player() {
        fsm.addState(new FSMState(STATE_ALIVE) {
            @Override
            public void init() {
                System.err.println("alive");
            }

            @Override
            public void update() {
                Point newPos = direction.fromPoint(pos);

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

                    previousPos = pos;
                    pos = teleportExitPoints.get(1);
                }
                else if (!world.isCellTraversable(world.getCell(newPos)) && !(new Point(5, 14).equals(newPos))) {
                    previousPos = pos;
                    newPos = pos;
                    animationManager.startAnimation("idle");
                    animationManager.commit(0);
//                    idle.setStarted(true).setLoop(true);
//                    eat.setStarted(false).setLoop(false);
//                    die.setStarted(false);
//                    entityManager.commitEntityState(0, idle, eat, die);
                } else {
                    previousPos = pos;
                    pos = newPos;
                    Point coord = world.posToCoord(pos);

//                    idle.setStarted(false);
//                    eat.setStarted(true).setLoop(true);
//                    die.setStarted(false);
//                    entityManager.commitEntityState(0, idle, eat, die);
                    animationManager.startAnimation("eat");
                    animationManager.commit(0);
                    int x = coord.x;
                    int y = coord.y;

                    switch (direction) {
                    case LEFT:
                        entity.setScaleX(-1).setScaleY(1).setRotation(0);
                        break;
                    case RIGHT:
                        entity.setScaleX(1).setScaleY(1).setRotation(0);
                        break;
                    case UP:
                        entity.setScaleX(-1).setScaleY(1).setRotation(Math.PI / 2.0);
                        break;
                    case DOWN:
                        entity.setScaleX(1).setScaleY(1).setRotation(Math.PI / 2.0);
                        break;
                    }

                    if (direction != previousDirection) {
                        entityManager.commitEntityState(0, entity);
                    }

                    entity.setX(x).setY(y);
                }
            }

            @Override
            public void event(String event, Object dir) {
                if (EVENT_MOVE_INTO_DIRECTION.equals(event)) {
                    // only register the new direction if it leads to a valid position
                    Direction newDirection = (Direction) dir;
                    Point nextPos = newDirection.fromPoint(pos);
                    if(world.isCellTraversable(world.getCell(nextPos))) {
                        previousDirection = direction;
                        direction = newDirection;
                    } else {
                        gameManager.addToGameSummary(String.format("%s: Invalid Action %s.", getNicknameToken(), newDirection.name()));
                    }
                }
            }
        });

        fsm.addState(new FSMState(STATE_DYING) {
            @Override
            public void init() {
                int pauseDuration = 300;
                int animationDuration = 700;
                Point previousCoord = world.posToCoord(previousPos);
                int previousX = previousCoord.x;
                int previousY = previousCoord.y;
                entity.setX(previousX).setY(previousY);
                System.err.println(String.format("commit 0: %d %d", previousX, previousY));
                entityManager.commitEntityState(0, entity);

                Point nextCoord = world.posToCoord(pos);
                int nextX = nextCoord.x;
                int nextY = nextCoord.y;
                
                int contactX = previousX + (int)(((double)(nextX - previousX)) * contactDelay);
                int contactY = previousY + (int)(((double)(nextY - previousY)) * contactDelay);
                
                int moveDuration = (int)(200 * contactDelay);
                int frameDuration = moveDuration + pauseDuration + animationDuration;
                entity.setX(contactX).setY(contactY);
                System.err.println(String.format("commit %f: %d %d", (double)moveDuration / (double)frameDuration, contactX, contactY));
                entityManager.commitEntityState((double)moveDuration / (double)frameDuration, entity);

                entity.setX(contactX).setY(contactY);
                System.err.println(String.format("commit 1: %d %d", contactX, contactY));
                entityManager.commitEntityState(1, entity);

//                idle.setStarted(true).setLoop(true);
//                eat.setStarted(false);
//                die.setStarted(false);
//                entityManager.commitEntityState(0.0, idle, eat, die);
//                animationManager.startAnimation("idle");
                animationManager.pauseAnimation();
                animationManager.commit((double)moveDuration / (double)frameDuration);
                
                animationManager.startAnimation("die");
                animationManager.commit((double)(moveDuration + pauseDuration) / (double)frameDuration);

//                idle.setStarted(false);
//                eat.setStarted(false);
//                die.setStarted(true).setStarted(true);
//                entity.setX(x)
//                entityManager.commitEntityState(0.5, idle, eat, die);
                
                entity.setVisible(false);

                gameManager.setFrameDuration(frameDuration);
                fsm.setState(STATE_DEAD);
                deactivate("dead");
            }
        });

        fsm.addState(new FSMState(STATE_DEAD) {
            @Override
            public void init() {
                pos.x = -1;
                pos.y = -1;
//              entity.setVisible(false);
              gameManager.setFrameDuration(200);
            }
        });
    }

    @Override
    public int getExpectedOutputLines() {
        return 1;
    }

    public int getId() {
        return id;
    }

    public void init(Group parent, int id) {
        this.id = id;
        this.parent = parent;

        SpriteAnimation idle = entityManager
                .createSpriteAnimation()
                .setImages("pacman/idle.png")
                .setScaleX(DEFAULT_SCALE)
                .setScaleY(DEFAULT_SCALE)
                .setAnchor(0.5)
                .setStarted(true);
        SpriteAnimation eat = entityManager
                .createSpriteAnimation()
                .setImages("pacman/eat/2.png", "pacman/eat/3.png", "pacman/eat/3.png", "pacman/eat/2.png", "pacman/eat/1.png", "pacman/eat/1.png")
                .setScaleX(DEFAULT_SCALE)
                .setScaleY(DEFAULT_SCALE)
                //                .setStarted(false)
                .setLoop(true)
                //.setVisible(false)
                .setAnchor(0.5)
                .setDuration(200);
        SpriteAnimation die = entityManager
                .createSpriteAnimation()
                .setImages("pacman/die/1.png", "pacman/die/2.png", "pacman/die/3.png", "pacman/die/4.png", "pacman/die/5.png", "pacman/die/6.png",
                        "pacman/die/7.png", "pacman/die/8.png", "pacman/die/9.png", "pacman/die/10.png", "pacman/die/11.png", "pacman/die/12.png",
                        "pacman/die/13.png")
                .setScaleX(DEFAULT_SCALE)
                .setScaleY(DEFAULT_SCALE)
                //                .setStarted(true)
                //                .setLoop(true)
                //                .setVisible(false)
                .setAnchor(0.5)
                .setDuration(1000);
        
        entity = entityManager.createGroup(idle, eat, die).setZIndex(2);
        idle.setTint(getColorToken());
        eat.setTint(getColorToken());
        die.setTint(getColorToken());
        
        animationManager.addAnimation("idle", idle);
        animationManager.addAnimation("eat", eat);
        animationManager.addAnimation("die", die);

        pos = world.getPlayerInitialPos(id);
        Point coordInit = world.posToCoord(pos);
        entity
                .setX(coordInit.x)
                .setY(coordInit.y);

        parent.add(entity);
        
        fsm.init(STATE_ALIVE);
        tooltips.registerEntity(entity);
    }

    public Point getPos() {
        return pos;
    }

    public Point getPreviousPos() {
        return previousPos;
    }

    public void move(String directionStr) throws InvalidActionException {
        try {
            Direction dir = Direction.valueOf(directionStr);
            fsm.sendEvent(EVENT_MOVE_INTO_DIRECTION, dir);
        } catch (IllegalArgumentException e) {
            throw new InvalidActionException();
        }

    }

    public void update() {
        bonusPoints = 0;
        fsm.update();
        tooltips.updateExtraTooltipText(entity, String.format("x = %d, y = %d", pos.x, pos.y));
    }

    public void contact(Ghost ghost, double t) {
        this.contactDelay = t;
        if (ghost.isDangerous()) {
            fsm.setState(STATE_DYING);
            fsm.update();
        } else if (ghost.isVulnerable()) {
            System.err.println(String.format("%s %s", ghost.getPos().toString(), pos.toString()));
            gameManager.setFrameDuration(700);
            bonusPoints = BONUS_POINTS[combo++];
        }
    }

    public void eatGum() {
        setScore(getScore() + 10);
    }

    public void eatSuperGum() {
        combo = 0;
        setScore(getScore() + 50);
    }
    
    private void printPoints(int x, int y, int points, double delay) {
        setScore(getScore() + points);
        
        Text t = entityManager.createText(String.valueOf(points));
        parent.add(t);
        t.setAlpha(1)
         .setAnchor(0.5)
         .setFillColor(0xffffff)
         .setFontSize(20)
         .setAlpha(0)
         .setX(x)
         .setY(y)
         .setStrokeColor(getColorToken())
         .setFillColor(getColorToken())
         .setStrokeThickness(0.5)
         .setVisible(true);
        entityManager.commitEntityState(0, t, parent);
        
        t.setAlpha(0.8, Curve.IMMEDIATE);
        entityManager.commitEntityState(delay, t);
        
        t.setAlpha(1);
        t.setScale(2.0, Curve.ELASTIC);
        entityManager.commitEntityState(0.99, t);
        
        t.setAlpha(0);
        entityManager.commitEntityState(1, t);        
    }

    public void freeze(double delay) {
        if(STATE_ALIVE.equals(fsm.getCurrentStateId())) {
            animationManager.pauseAnimation();
            animationManager.commit(0);
            
            animationManager.unpauseAnimation();
            animationManager.commit(0.95);

            int newX = entity.getX();
            int newY = entity.getY();

            Point coord = world.posToCoord(previousPos);
            int previousX = coord.x;
            int previousY = coord.y;
            
            double freezeRatio = (delay * gameManager.getFrameDuration()) / 200.0;

            int freezeX = (int)(previousX + ((double)(newX - previousX) * freezeRatio));
            int freezeY = (int)(previousY + ((double)(newY - previousY) * freezeRatio));

            entity.setX(freezeX).setY(freezeY);
            if(bonusPoints > 0) {
                entity.setVisible(false);
                printPoints(freezeX, freezeY, bonusPoints, delay);
            }
            entityManager.commitEntityState(delay, entity);
            
            entity.setX(freezeX + 1).setY(freezeY + 1);
            entity.setVisible(true);
            entityManager.commitEntityState(1.0 - delay, entity);

            entity.setX(newX).setY(newY);
            entityManager.commitEntityState(1, entity);
        
        }
    }
}
