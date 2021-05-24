package src.engine.piece;

import src.engine.Alliance;
import src.engine.board.Board;
import src.engine.board.BoardUtils;
import src.engine.board.Move;
import src.engine.board.Tile;

import java.util.*;

public final class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final Alliance alliance, final int piecePosition) {
        super(PieceType.BISHOP ,alliance, piecePosition, true);
    }

    public Bishop(final Alliance alliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.BISHOP ,alliance, piecePosition, isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirst(currentCandidateOffset, candidateDestinationCoordinate) ||
                    isEight(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                    else {
                        final Piece pieceOnTile = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceOnTile.getAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestinationCoordinate, pieceOnTile));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

    private static boolean isFirst(final int Offset, final int candidateDestinationCoordinate) {
        return (BoardUtils.FIRST_COLUMN[candidateDestinationCoordinate] &&
                ((Offset == -9) || (Offset == 7)));
    }

    private static boolean isEight(final int Offset, final int candidateDestinationCoordinate) {
        return BoardUtils.EIGHT_COLUMN[candidateDestinationCoordinate] &&
                        ((Offset == -7) || (Offset == 9));
    }

    @Override
    public Bishop movePiece(Move move) {
        return new Bishop(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }

}