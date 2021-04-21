package src.engine.piece;

import src.engine.Alliance;
import src.engine.board.Board;
import src.engine.board.BoardUtils;
import src.engine.board.Move;
import src.engine.board.Tile;

import java.util.*;

public final class Rook extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -8, -1, 1, 8 };

    public Rook(final Alliance alliance, final int piecePosition) {
        super(PieceType.ROOK ,alliance, piecePosition, true);
    }

    public Rook(final Alliance alliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.ROOK ,alliance, piecePosition, isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
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
        return PieceType.ROOK.toString();
    }

    private static boolean isColumnExclusion(final int Offset,final int candidateDestinationCoordinate) {
        return (BoardUtils.FIRST_COLUMN[candidateDestinationCoordinate] && (Offset == -1)) ||
               (BoardUtils.EIGHT_COLUMN[candidateDestinationCoordinate] && (Offset == 1));
    }

    @Override
    public Rook movePiece(Move move) {
        return new Rook(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }
}