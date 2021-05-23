package src.engine.player;

import src.engine.board.Board;
import src.engine.board.Move;

public class MoveTransition {

    private final Board transitionBoard;
    private final MoveStatus moveStatus;
    private final Board fromBoard;
    private final Move transitionMove;
    
    public MoveTransition(final Board fromBoard,
                            final Board transitionBoard,
                            final MoveStatus moveStatus,
                            final Move transitionMove) {
        this.fromBoard = fromBoard;
        this.transitionMove = transitionMove;
        this.moveStatus = moveStatus;
        this.transitionBoard = transitionBoard;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getTransitionBoard(){
        return this.transitionBoard;
    }

    public Board getFromBoard() {
        return this.fromBoard;
    }

    public Move getTransitionMove() {
        return this.transitionMove;
    }
    
}
