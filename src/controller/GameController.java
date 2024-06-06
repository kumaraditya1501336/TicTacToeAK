package controller;

import exceptions.MoreThanOneBotException;
import exceptions.PlayerCountMismatchException;
import models.Game;
import models.Player;
import strategies.winningstrategy.WinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int dimensionsOfBoard,
                          List<Player> players,
                          List<WinningStrategy> winnerStrategies) throws PlayerCountMismatchException, MoreThanOneBotException {
        return null;
    }

    public Object getStatus(Game game) {
        return null;
    }

    public void makeMove() {
    }

    public void displayBoard() {
    }
}
