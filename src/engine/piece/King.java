package src.engine.piece;

import src.engine.Alliance;
import src.engine.board.Board;
import src.engine.board.BoardUtils;
import src.engine.board.Move;
import src.engine.board.Tile;

import java.util.*;

public final class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public King(final Alliance alliance, final int piecePosition) {
        super(PieceType.KING ,alliance, piecePosition);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceOnTile = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceOnTile.getAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceOnTile));
                        }
                        break;
                    }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
        public String toString() {
            return PieceType.KING.toString();
        }

    private static boolean isFirstColumnExclusion(final int currentCandidate,
                                                  final int Offset) {
        return BoardUtils.FIRST_COLUMN[currentCandidate]
                && ((Offset == -9) || (Offset == -1) || (Offset == 7));
    }

    private static boolean isEighthColumnExclusion(final int currentCandidate,
                                                   final int Offset) {
        return BoardUtils.EIGHT_COLUMN[currentCandidate]
                && ((Offset == -7) || (Offset == 1) || (Offset == 9));
    }

    @Override
    public King movePiece(Move move) {
        return new King(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }
}