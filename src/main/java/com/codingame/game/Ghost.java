package com.codingame.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.google.inject.Inject;

public class Ghost {
    private static enum State {
        INIT, ATTACK, FLEE, DEAD
    };
    
    @Inject World world;
    @Inject GraphicEntityModule entityManager;

    private int id;
    private Point previousPos;
    private Point pos;
    private Direction direction = Direction.DOWN;
    private State state = State.INIT;
    private Group entity;
    private static final int OFFSET_X = 0;
    private static final int OFFSET_Y = 0;

    Map<Direction, SpriteAnimation> directionSpriteMap = new HashMap<>();
    
    public int getId() {
        return id;
    }

    public void init(int id) {
        this.id = id;
        this.entity = entityManager.createGroup();
        for(Direction d : Direction.values()) {
            SpriteAnimation sprite = entityManager.createSpriteAnimation()
                .setLoop(true)
                .setStarted(true)
                .setDuration(100)
                .setScale(0.6)
                .setAnchorX(0.1)
                .setAnchorY(0.15)
                .setVisible(false);
            String[] images = new String[4];
            for(int i = 1; i <= 4; i++) {
                images[i - 1] = String.format("ghost%d/%s/%d.png", id, d.name().toLowerCase(), i);
                sprite.setImages(images);
            }
            directionSpriteMap.put(d, sprite);
            entity.add(sprite);
        }
    }
    
    private void updateDirectionAnimation() {
        for(SpriteAnimation spriteAnimation : directionSpriteMap.values()) {
            spriteAnimation.setVisible(false);
        }
        directionSpriteMap.get(direction).setVisible(true);
    }
    
    private Direction getDirection(Point from, Point to) {
        Direction dir = Direction.DOWN;
        
        if(to.x - from.x == -1 && to.y - from.y == 0) {
            dir = Direction.LEFT;
        } else if(to.x - from.x == 1 && to.y - from.y == 0) {
            dir = Direction.RIGHT;
        } else if(to.x - from.x == 0 && to.y - from.y == -1) {
            dir = Direction.UP;
        } else if(to.x - from.x == 0 && to.y - from.y == 1) {
            dir = Direction.DOWN;
        } else {
            throw new RuntimeException();
        }
        
        return dir;
    }

    public void update() {
        switch (state) {
        case INIT:
            pos = world.getGhostInitialPos(id);
            state = State.ATTACK;
            Point coordInit = world.posToCoord(pos);
            entity.setX(coordInit.x + OFFSET_X).setY(coordInit.y + OFFSET_Y);
            updateDirectionAnimation();
            break;
            
        case ATTACK:
            // According to my position, I check the next possible positions 
            Set<Point> nextPossiblePos = world.getNextPossiblePos(pos);
            
            // I never want to go backward, so I remove my previous position from the list
            if(previousPos != null) {
                nextPossiblePos.remove(previousPos);
            }
            
            // I first check what is the best target position to attack the nearest player
            Point attackPos = world.getPosToReachNearestPlayer(nextPossiblePos);
            
            // I have 2 chances on 3 to go to the correct position
            previousPos = pos;
            if(world.getRandom().nextInt(3) < 2) {
                pos = attackPos;
            } else {
                // Randomly chose among the other options
                Point[] arr = new Point[nextPossiblePos.size()];
                arr = nextPossiblePos.toArray(arr);
                pos = arr[world.getRandom().nextInt(arr.length)];
            }
            
            direction = getDirection(previousPos, pos);
            updateDirectionAnimation();
            
            Point coordAttack = world.posToCoord(pos);
            entity.setX(coordAttack.x + OFFSET_X).setY(coordAttack.y + OFFSET_Y);
            break;
            
        case FLEE:
            break;
        case DEAD:
            break;
        }
    }

    public Point getPos() {
        return pos;
    }

    public Point getPreviousPos() {
        return previousPos;
    }

    public void contact(Player player) {
        System.err.println("contact with " + player.getIndex());
        
    }

    public void vulnerable(int i) {
        // TODO Auto-generated method stub
        
    }
}
