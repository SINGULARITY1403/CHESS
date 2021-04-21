package src.engine.player.AI;

import src.engine.board.Board;
import src.engine.board.Move;

public interface MoveStrategy {
    Move execute(Board board, int depth);
}
