package models;

import strategies.botplayingstrategy.BotPlayingStrategy;
import strategies.botplayingstrategy.BotPlayingStrategyFactory;

public class Bot extends Player {

    private BotDifficultiLevel botDifficultiLevel;
    private BotPlayingStrategy botPlayingStrategy;

    public Bot(Long id, String name, Symbol symbol, BotDifficultiLevel botDifficultiLevel) {
        super(id, name, symbol, PlayerType.BOT);
        this.botDifficultiLevel = botDifficultiLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botDifficultiLevel);
    }

    @Override
    public Move makeMove (Board board) {
        Move move = botPlayingStrategy.makeMove(board);
        move.setPlayer(this);

        return move;
    }
}
