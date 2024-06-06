package strategies.winningstrategy;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColumnWinningStrategy implements WinningStrategy {

    private Map<Integer, Map<Symbol, Integer>> counts = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        counts.put(col, counts.getOrDefault(col, new HashMap<>()));

        Map<Symbol, Integer> colMap = counts.get(col);

        colMap.put(symbol, colMap.getOrDefault(symbol, 0) + 1);

        if (colMap.get(symbol) == board.getSize()) {
            return true;
        }

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol, Integer> colMap = counts.get(col);

        colMap.put(symbol, colMap.get(symbol) - 1);
    }
}
