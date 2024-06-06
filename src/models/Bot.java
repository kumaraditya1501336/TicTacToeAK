package models;

import strategies.botplayingstrategy.BotPlayingStrategy;
import strategies.botplayingstrategy.BotPlayingStrategyFactory;

public class Bot extends Player {

    private BotDifficultiLevel botDifficultiLevel;
    private BotPlayingStrategy botPlayingStrategy;

    public Bot(Long id, String name, Symbol symbol, PlayerType playerType, BotDifficultiLevel botDifficultiLevel) {
        super(id, name, symbol, playerType);
        this.botDifficultiLevel = botDifficultiLevel;
        this.botDifficultiLevel = BotPlayingStrategyFactory.getBotPlayingStrategy(botDifficultiLevel);
    }
}
