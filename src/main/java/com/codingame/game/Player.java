package com.codingame.game;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import com.codingame.gameengine.core.AbstractPlayer;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.google.inject.Inject;

public class Player extends AbstractPlayer {

    public class InvalidActionException extends Exception {
        private static final long serialVersionUID = 4742197738867606487L;
    }

    private static final double DEFAULT_SCALE = 0.6;
    private static final int OFFSET_X = World.CELL_WIDTH / 2;
    private static final int OFFSET_Y = World.CELL_HEIGHT / 2;

    @Inject GraphicEntityModule entityManager;
    @Inject GameManager<Player> gameManager;
    @Inject World world;

    private int id;
    private Point previousPos;
    private Point pos;
    private Group entity;
    private Direction previousDirection = Direction.RIGHT;
    private Direction direction = Direction.RIGHT;

    private Sprite idle;
    private SpriteAnimation eat;
    private SpriteAnimation die;
    private boolean dying = false;
    private boolean dead = false;

    @Override
    public int getExpectedOutputLines() {
        return 1;
    }

    public int getId() {
        return id;
    }

    public void init(int id) {
        this.id = id;

        idle = entityManager
                .createSprite()
                .setImage("pacman/idle.png")
                .setScaleX(DEFAULT_SCALE)
                .setScaleY(DEFAULT_SCALE)
                .setAnchor(0.5)
                .setVisible(true);
        eat = entityManager
                .createSpriteAnimation()
                .setImages("pacman/eat/2.png", "pacman/eat/3.png", "pacman/eat/3.png", "pacman/eat/2.png", "pacman/eat/1.png", "pacman/eat/1.png")
                .setScaleX(DEFAULT_SCALE)
                .setScaleY(DEFAULT_SCALE)
                .setStarted(true)
                .setLoop(true)
                .setVisible(false)
                .setAnchor(0.5)
                .setDuration(150);
        die = entityManager
                .createSpriteAnimation()
                .setImages("pacman/die/1.png", "pacman/die/2.png", "pacman/die/3.png", "pacman/die/4.png", "pacman/die/5.png", "pacman/die/6.png",
                        "pacman/die/7.png", "pacman/die/8.png", "pacman/die/9.png", "pacman/die/10.png", "pacman/die/11.png", "pacman/die/12.png",
                        "pacman/die/13.png")
                .setScaleX(DEFAULT_SCALE)
                .setScaleY(DEFAULT_SCALE)
                .setStarted(true)
                .setLoop(true)
                .setVisible(false)
                .setAnchor(0.5)
                .setDuration(1000);
        entity = entityManager.createGroup(idle, eat, die);
        idle.setTint(getColorToken());
        eat.setTint(getColorToken());
        die.setTint(getColorToken());

        pos = world.getPlayerInitialPos(id);
        Point coordInit = world.posToCoord(pos);
        entity
                .setX(coordInit.x + OFFSET_X)
                .setY(coordInit.y + OFFSET_Y);

    }

    public void sendLevel() {
        // TODO Auto-generated method stub
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
            previousDirection = direction;
            direction = dir;
        } catch (IllegalArgumentException e) {
            throw new InvalidActionException();
        }

    }

    public void update() {
        if (dead) {
            entity.setVisible(false);
            gameManager.setFrameDuration(200);
            return;
        }

        if (dying) {
            idle.setVisible(false);
            eat.setVisible(false);
            die.setVisible(true).setStarted(true).setLoop(true).setDuration(1000);
            entityManager.commitEntityState(0.5, idle, eat, die);

            gameManager.setFrameDuration(2000);
            dead = true;
            return;
        }

        Point newPos = new Point(pos);
        switch (direction) {
        case LEFT:
            newPos.x--;
            break;
        case RIGHT:
            newPos.x++;
            break;
        case UP:
            newPos.y--;
            break;
        case DOWN:
            newPos.y++;
            break;
        }

        if (!world.isCellTraversable(world.getCell(newPos))) {
            newPos = pos;
            idle.setVisible(true);
            eat.setVisible(false);
            die.setVisible(false);
        } else {
            previousPos = pos;
            pos = newPos;
            Point coord = world.posToCoord(pos);

            idle.setVisible(false);
            eat.setVisible(true);
            die.setVisible(false);
            int x = coord.x + OFFSET_X;
            int y = coord.y + OFFSET_Y;

            switch (direction) {
            case LEFT:
                entity
                        .setScaleX(-1)
                        .setScaleY(1)
                        .setRotation(0);
                break;
            case RIGHT:
                entity
                        .setScaleX(1)
                        .setScaleY(1)
                        .setRotation(0);
                break;
            case UP:
                entity
                        .setScaleX(-1)
                        .setScaleY(1)
                        .setRotation(Math.PI / 2.0);
                break;
            case DOWN:
                entity
                        .setScaleX(1)
                        .setScaleY(1)
                        .setRotation(Math.PI / 2.0);
                break;
            }

            if (this.direction != previousDirection) {
                entityManager.commitEntityState(0, entity);
            }
            entity
                    .setX(x)
                    .setY(y);

        }

        //        Direction 
    }

    public void contact(Ghost ghost) {
        deactivate("dead");
        dying = true;
    }

    public void eatGum(int i) {
        // TODO Auto-generated method stub
        
    }

    public void eatSuperGum(int i) {
        // TODO Auto-generated method stub
        
    }
}
