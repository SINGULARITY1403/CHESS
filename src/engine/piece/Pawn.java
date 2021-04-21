package src.engine.piece;

import src.engine.Alliance;
import src.engine.board.Board;
import src.engine.board.BoardUtils;
import src.engine.board.Move;

import java.util.*;

public final class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(final Alliance alliance, final int piecePosition) {
        super(PieceType.PAWN ,alliance, piecePosition, true);
    }

    public Pawn(final Alliance alliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.PAWN ,alliance, piecePosition, isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);
            
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && board.getPiece(candidateDestinationCoordinate) == null) {
                if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
                    legalMoves.add(new Move.PawnPromotion(new Move.PawnMove(board, this, candidateDestinationCoordinate)));
                }
                else {
                    legalMoves.add(new Move.PawnMove(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                     (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite()))) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if (board.getPiece(candidateDestinationCoordinate) == null &&
                    board.getPiece(behindCandidateDestinationCoordinate) == null) {
                    legalMoves.add(new Move.PawnJump(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                      (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
               
                if(board.getPiece(candidateDestinationCoordinate) != null) {
                    final Piece pieceOnCandidate = board.getPiece(candidateDestinationCoordinate);
                    if (this.pieceAlliance != pieceOnCandidate.getAlliance()) {
                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
                            legalMoves.add(new Move.PawnPromotion(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        }
                        else{
                            legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                            
                    }
                }
                else if(board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))){
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if(this.pieceAlliance != pieceOnCandidate.getAlliance()){
                        legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                } 
            } 
            else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                      (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()))) {
               
                if(board.getPiece(candidateDestinationCoordinate) != null) {
                    final Piece pieceOnCandidate = board.getPiece(candidateDestinationCoordinate);
                    if (this.pieceAlliance != pieceOnCandidate.getAlliance()) {
                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
                            legalMoves.add(new Move.PawnPromotion(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        }
                        else{
                            legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }           
                    }
                } 
                else if(board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))){
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if(this.pieceAlliance != pieceOnCandidate.getAlliance()){
                        legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                } 
            } 
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }

    public Piece getPromotedPiece() {
        return new Queen(this.pieceAlliance, this.piecePosition, false);
    }

}

