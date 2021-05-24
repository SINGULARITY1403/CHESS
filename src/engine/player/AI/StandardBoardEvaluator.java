package src.engine.player.AI;

import src.engine.board.Board;
import src.engine.board.Move;
import src.engine.piece.Piece;
import src.engine.player.Player;

public final class StandardBoardEvaluator implements BoardEvaluator{

    private static final int CHECK_BONUS = 50;
    private static final int CHECKMATE_BONUS = 100;
    private static final int DEPTH_BONUS = 100;
    private static final int CASTLED_BONUS = 60;
    private final static int ATTACK_MULTIPLIER = 1;
    private final static int MOBILITY_MULTIPLIER = 5;
    private static final StandardBoardEvaluator INSTANCE = new StandardBoardEvaluator();

    public static StandardBoardEvaluator get() {
        return INSTANCE;
    }
    
    @Override
    public int evaulate(final Board board, final int depth) {

        return scorePlayer(board, board.whitePlayer(), depth) - scorePlayer(board, board.blackPlayer(), depth) ;
    }

    private int scorePlayer(final Board board, final Player player, final int depth) {
        return pieceValue(player) + mobility(player) + check(player) + checkmate(player, depth) + castled(player) + attacks(player); 
    }

    private static int castled(Player player) {
        return player.isCastled() ? CASTLED_BONUS : 0;
    }

    private static int checkmate(final Player player, final int depth) {
        return player.getOpponent().isInCheckmate() ? CHECKMATE_BONUS * depthBonus(depth): 0;
    }

    private static int depthBonus(int depth) {
        return depth ==0 ? 1 :  DEPTH_BONUS * depth;
    }

    private static int mobilityRatio(final Player player) {
        return (int)((player.getLegalMoves().size() * 10.0f) / player.getOpponent().getLegalMoves().size());
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private static int mobility(final Player player) {
        return MOBILITY_MULTIPLIER * mobilityRatio(player);
    }

    private static int pieceValue(Player player) {
        int pieceValueScore = 0;
        for(final Piece piece : player.getActivePieces()){
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }

    private static int attacks(final Player player) {
        int attackScore = 0;
        for(final Move move : player.getLegalMoves()) {
            if(move.isAttack()) {
                final Piece movedPiece = move.getMovedPiece();
                final Piece attackedPiece = move.getAttackedPiece();
                if(movedPiece.getPieceValue() <= attackedPiece.getPieceValue()) {
                    attackScore++;
                }
            }
        }
        return attackScore * ATTACK_MULTIPLIER;
    }

}
