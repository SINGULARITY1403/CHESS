package src.engine.player.AI;

import src.engine.board.Board;

public interface BoardEvaluator {
    int evaulate(Board board, int depth);
}
