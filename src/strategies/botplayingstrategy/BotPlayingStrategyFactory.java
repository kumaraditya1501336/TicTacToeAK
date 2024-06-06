package strategies.botplayingstrategy;

import models.BotDifficultiLevel;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy (BotDifficultiLevel botDifficultiLevel) {
        //TODO : Add other bot playing strategy (if-else)
        return new EasyBotPlayingStrategy();
    }
}
