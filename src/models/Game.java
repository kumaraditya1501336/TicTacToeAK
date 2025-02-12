package models;

import exceptions.MoreThanOneBotException;
import exceptions.PlayerCountMismatchException;
import strategies.winningstrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private Board board;
    private int nextMovePlayerIndex;
    private List<Move> moves;
    private GameState gameState;
    private Player winner;
    private List<WinningStrategy> winningStrategies;

    private Game (int dimension, List<WinningStrategy> winningStrategies, List<Player> players) {
        this.winningStrategies = winningStrategies;
        this.players = players;
        this.board = new Board(dimension);
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
        this.nextMovePlayerIndex = 0;
    }

    public static Builder getBuilder () {
        return new Builder();
    }

    public static class Builder {
        private int dimension;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinnerStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder addPlayer (Player player) {
            this.players.add (player);
            return this;
        }

        public Builder addWinningStrategies (WinningStrategy winningStrategy) {
            this.winningStrategies.add (winningStrategy);
            return this;
        }

        // TODO : Move validation logic in a separate class

        private void validateBotCount () throws MoreThanOneBotException {
            int botCount = 0;

            for (Player player : players) {
                if (player.getPlayerType().equals(PlayerType.BOT)) {
                    botCount++;
                }
            }

            if (botCount > 1) {
                throw new MoreThanOneBotException();
            }
        }

        private void validatePlayerCount () throws PlayerCountMismatchException {
            if (players.size() != dimension - 1) {
                throw new PlayerCountMismatchException();
            }
        }

        private void validateUniqueSymbolForPlayers () {

        }

        private void validate () throws MoreThanOneBotException, PlayerCountMismatchException {
            validateBotCount();
            validatePlayerCount();
            validateUniqueSymbolForPlayers();
        }

        public Game build () throws PlayerCountMismatchException, MoreThanOneBotException {
            validate();
            return new Game(dimension, winningStrategies, players);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    // TODO : Think about moving this to another class

    private boolean validateMove (Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        if (row < 0 || row >= board.getSize()) {
            return false;
        }

        if (col < 0 || col >= board.getSize()) {
            return false;
        }

        if (board.getBoard().get(row).get(col).getCellState() .equals(CellState.EMPTY)) {
            return true;
        }

        return false;
    }

    public void makeMove () {
        Player currentMovePlayer = players.get (nextMovePlayerIndex);
        System.out.println ("It is " + currentMovePlayer.getName() + "'s move. Make your move");

        Move move = currentMovePlayer.makeMove (board);

        System.out.println ("Player " + currentMovePlayer.getName() +
                        " trying to make a move at row: " + move.getCell().getRow()
                        + " and at column: " + move.getCell().getCol());

        if (!validateMove (move)) {
            System.out.println("!!! INVALID MOVE !!!");
            return;
        }

        int row = move.getCell ().getRow ();
        int col = move.getCell ().getCol ();

        Cell cellToChange = board.getBoard ().get (row).get (col);
        cellToChange.setPlayer (currentMovePlayer);
        cellToChange.setCellState (CellState.FILLED);

        Move finalMove = new Move (cellToChange, currentMovePlayer);
        moves . add (finalMove);

        nextMovePlayerIndex += 1;
        nextMovePlayerIndex %= players.size();

        if (checkWinner (board, finalMove)) {
            winner = currentMovePlayer;
            gameState = GameState.WIN;
        } else if (moves.size() == board.getSize()) {
            gameState = GameState.DRAW;
        }
    }

    private boolean checkWinner (Board board, Move move) {
        for (WinningStrategy winningStrategy : winningStrategies) {
            if (winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }

        return false;
    }

    public void undo () {
        if (moves.size() == 0) {
            System.out.println("No moves to undo!!!");
            return;
        }

        Move lastMove = moves.get (moves.size() - 1);
        moves.remove (lastMove);

        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        for (WinningStrategy winningStrategy : winningStrategies) {
            winningStrategy.handleUndo (board, lastMove);
        }

        nextMovePlayerIndex -= 1;
        nextMovePlayerIndex = (nextMovePlayerIndex + players.size()) % players.size();
    }

    public void printBoard () {
        board.printBoard ();
    }
}
