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
        //        long seed = -930797100;
//        long seed = 1398541364;
                long seed = Long.valueOf((String) params.get("seed"));
        System.err.println(seed);
        world.init(seed);
        world.sendLevel();

        return params;
    }

    private boolean checkEndGame() {
        return (world.getRemainingGumCount() == 0) || (gameManager.getActivePlayers().size() == 0);
    }

    @Override
    public void gameTurn(int turn) {
        System.err.println("TURN " + turn);
        world.sendCharactersPositions();

        for (Player player : gameManager.getActivePlayers()) {
            player.execute();
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
        }

        world.update(turn);

        if (checkEndGame()) {
            gameManager.endGame();
        }
    }

    @Override
    public void onEnd() {
        Player p0 = gameManager.getPlayer(0);
        Player p1 = gameManager.getPlayer(1);
        if (p0.getScore() == p1.getScore()) {
            gameManager.addToGameSummary(GameManager.formatSuccessMessage("tie game!"));
        } else {
            Player winner = p0.getScore() > p1.getScore() ? p0 : p1;
            gameManager.addToGameSummary(GameManager.formatSuccessMessage(winner.getNicknameToken() + " win!"));
        }
    }
}
