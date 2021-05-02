package src.engine.player.AI;

import src.engine.board.Board;
import src.engine.board.Move;
import src.engine.player.MoveTransition;

public class MiniMax implements MoveStrategy{

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;

    public MiniMax(final int searchDepth){
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public String toString(){
        return "MiniMax";
    }

    @Override
    public Move execute(Board board) {
        final long startTime = System.currentTimeMillis();

        Move bestMove = null;
        int lowestValue = Integer.MAX_VALUE; 
        int highestValue = Integer.MIN_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer() + "THINKING with depth = " + this.searchDepth);

        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                currentValue = board.currentPlayer().getAlliance().isWhite() ? 
                                min(moveTransition.getTransitionBoard(), this.searchDepth-1) :
                                max(moveTransition.getTransitionBoard(), this.searchDepth-1);
                if(board.currentPlayer().getAlliance().isWhite() && currentValue >= highestValue){
                    highestValue = currentValue;
                    bestMove = move;
                }
                else if(board.currentPlayer().getAlliance().isBlack() && currentValue <= lowestValue){
                    lowestValue = currentValue;
                    bestMove = move;
                }
            }
        }
        final long executionTime = System.currentTimeMillis() - startTime;
        return  bestMove;
    }

    public int min(final Board board,final int depth){
        if(depth == 0  || isEndGameScenario(board)){
            return this.boardEvaluator.evaulate(board, depth);
        }

        int lowestValue = Integer.MAX_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = max(moveTransition.getTransitionBoard(), depth -1);
                if(currentValue <= lowestValue){
                    lowestValue  = currentValue;
                }
            }
        }
        return lowestValue;
    }

    public int max(final Board board,final int depth){
        if(depth == 0 || isEndGameScenario(board)){
            return this.boardEvaluator.evaulate(board, depth);
        }

        int highestValue = Integer.MIN_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = min(moveTransition.getTransitionBoard(), depth -1);
                if(currentValue >= highestValue){
                    highestValue  = currentValue;
                }
            }
        }
        return highestValue;
    }

    private static Boolean isEndGameScenario(final Board board){
        return board.currentPlayer().isInCheckmate() || board.currentPlayer().isInStalemate();
    }
    
}
