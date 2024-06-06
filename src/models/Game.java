package models;

import strategies.winningstrategy.WinnerStrategy;

import java.util.List;

public class Game {
    private List<Player> players;
    private Board board;
    private int nextMovePlayerIndex;
    private List<Move> moves;
    private GameState gameState;
    private Player winner;
    private List<WinnerStrategy> winnerStrategies;


}
