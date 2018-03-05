package com.codingame.game;

import java.util.Properties;

import com.codingame.game.Player.InvalidActionException;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.GameManager;
import com.google.inject.Inject;

public class Referee extends AbstractReferee {
    @Inject private GameManager<Player> gameManager;
    @Inject private World world;
    
    @Override
    public Properties init(Properties params) {
        gameManager.setFrameDuration(200);
        long seed = 373620243; //Long.valueOf((String) params.get("seed"));
        System.err.println(seed);
        world.init(seed);
        return params;
    }

    private int countdown = -400;
    private int checkWinner() {
        return countdown++;
    }

    @Override
    public void gameTurn(int turn) {
        System.err.println("TURN " + turn);
        for (Player player : gameManager.getActivePlayers()) {
            player.sendLevel();
            player.execute();
        }

        for (Player player : gameManager.getActivePlayers()) {
            // Read inputs
            String directionStr = "";
            try {
                directionStr = player.getOutputs().get(0);
                player.move(directionStr);
            } catch (InvalidActionException e) {
                player.deactivate(player.getNicknameToken() + " invalid direction: " + directionStr);
                player.setScore(-1);
                gameManager.endGame();
            } catch (TimeoutException e) {
                player.deactivate(player.getNicknameToken() + " timeout!");
                player.setScore(-1);
                gameManager.endGame();
            }

            // check winner
            int winner = checkWinner();
            if (winner > 0) {
                gameManager.addToGameSummary(GameManager.formatSuccessMessage(player.getNicknameToken() + " won!"));

                gameManager.getPlayer(winner - 1).setScore(1);
                gameManager.endGame();
            }
        }
        
        world.update();
        
        if(gameManager.getActivePlayers().size() == 0) {
            gameManager.endGame();
        }
    }
}
