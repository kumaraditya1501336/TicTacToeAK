package controller;

import exceptions.MoreThanOneBotException;
import exceptions.PlayerCountMismatchException;
import models.Game;
import models.Player;
import strategies.winningstrategy.WinnerStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int dimensionsOfBoard,
                          List<Player> players,
                          List<WinnerStrategy> winnerStrategies) throws PlayerCountMismatchException, MoreThanOneBotException {
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
