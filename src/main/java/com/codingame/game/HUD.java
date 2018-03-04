package com.codingame.game;

import java.util.HashMap;
import java.util.Map;

import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Rectangle;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HUD {
    private static final int AVATAR_SIZE = 120;
    private static final int AVATAR_PADDING = 20;
    private static final int AVATAR_BORDER = 5;
    private static final int AVATAR_TOTAL_SIZE = AVATAR_SIZE + 2*AVATAR_PADDING + 2*AVATAR_BORDER;
    private static final int AVATAR_MARGIN_BOTTOM = 20;
    private static final int SIDE_OFFSET = 150;
    private static final int TOP_OFFSET = 80;
    private static final int NICKNAME_FONT_SIZE = 40;
    private static final int SCORE_LABEL_FONT_SIZE = 60;
    private static final int SCORE_VALUE_FONT_SIZE = 70;
    private static final int PACMAN_SIZE = 80;
    private static final int PACMAN_MARGIN = 140;
    private static final String FONT_FAMILY = "Comic Sans MS";

    @Inject private GameManager<Player> gameManager;
    @Inject private GraphicEntityModule entityModule;

    private Map<Integer,Text> playerScoreMap = new HashMap<>();
    private Text readyText;
    
    private void initPlayer(Player player, int x) {
        Group group = entityModule.createGroup().setX(x).setY(TOP_OFFSET);
        Rectangle avatarBorder = entityModule.createRectangle()
                .setFillColor(0xffffff)
                .setLineColor(player.getColorToken())
                .setLineWidth(AVATAR_BORDER)
                .setX(AVATAR_BORDER)
                .setY(AVATAR_BORDER)
                .setWidth(AVATAR_SIZE + 2*AVATAR_PADDING)
                .setHeight(AVATAR_SIZE + 2*AVATAR_PADDING);
        
        Sprite avatar = entityModule.createSprite().setImage(player.getAvatarToken()).setBaseWidth(AVATAR_SIZE).setBaseHeight(AVATAR_SIZE)
                .setX(AVATAR_BORDER + AVATAR_PADDING).setY(AVATAR_BORDER + AVATAR_PADDING);
        
        Text nickname = entityModule.createText(player.getNicknameToken())
                .setFillColor(player.getColorToken())
                .setAnchorX(0.5)
                .setX(AVATAR_BORDER + AVATAR_PADDING + (AVATAR_SIZE / 2))
                .setY(AVATAR_TOTAL_SIZE + AVATAR_MARGIN_BOTTOM)
                .setFontSize(NICKNAME_FONT_SIZE)
                .setFontFamily(FONT_FAMILY);
        
        Sprite pacman = entityModule.createSprite()
                .setImage("pacman/idle.png")
                .setAnchorX(0.5)
                .setBaseWidth(PACMAN_SIZE)
                .setBaseHeight(PACMAN_SIZE)
                .setX(AVATAR_BORDER + AVATAR_PADDING + (AVATAR_SIZE / 2))
                .setY(AVATAR_TOTAL_SIZE + AVATAR_MARGIN_BOTTOM + NICKNAME_FONT_SIZE + PACMAN_MARGIN)
                .setTint(player.getColorToken());
                
        Text scoreLabel = entityModule.createText("SCORE")
                .setAnchorX(0.5)
                .setX(AVATAR_BORDER + AVATAR_PADDING + (AVATAR_SIZE / 2))
                .setY(AVATAR_TOTAL_SIZE + AVATAR_MARGIN_BOTTOM + NICKNAME_FONT_SIZE + 2*PACMAN_MARGIN + PACMAN_SIZE)
                .setFontSize(SCORE_LABEL_FONT_SIZE)
                .setFontFamily(FONT_FAMILY)
//                .setStrokeThickness(1)
                .setStrokeColor(0xffffff)
                .setFillColor(0xffffff);
        
        Text scoreValue = entityModule.createText("0")
                .setAnchorX(0.5)
                .setX(AVATAR_BORDER + AVATAR_PADDING + (AVATAR_SIZE / 2))
                .setY(AVATAR_TOTAL_SIZE + AVATAR_MARGIN_BOTTOM + NICKNAME_FONT_SIZE + 2*PACMAN_MARGIN + PACMAN_SIZE + SCORE_LABEL_FONT_SIZE + 10)
                .setFontSize(SCORE_VALUE_FONT_SIZE)
                .setFontFamily(FONT_FAMILY)
//                .setStrokeThickness(10.0)
                .setStrokeColor(player.getColorToken())
                .setFillColor(player.getColorToken());
        group.add(avatarBorder, avatar, nickname, pacman, scoreLabel, scoreValue);
        playerScoreMap.put(player.getId(), scoreValue);
    }

    public void init() {
        initPlayer(gameManager.getPlayer(0), SIDE_OFFSET);
        initPlayer(gameManager.getPlayer(1), entityModule.getWorld().getWidth() - SIDE_OFFSET - AVATAR_SIZE - 2*AVATAR_PADDING - 2*AVATAR_BORDER);
        
        readyText = entityModule.createText("GET READY!")
                .setAnchorX(0.5)
                .setX(entityModule.getWorld().getWidth() / 2)
                .setY(17 * World.CELL_HEIGHT)
                .setFontSize(World.CELL_HEIGHT)
                .setFillColor(0xffffff)
                .setFontFamily(FONT_FAMILY);
    }

    public void update() {
        readyText.setAlpha(0);
        for(Player player : gameManager.getActivePlayers()) {
            playerScoreMap.get(player.getId()).setText(String.valueOf(player.getScore()));
        }
    }
}
