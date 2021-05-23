package src.engine.piece;

import java.util.*;

import src.engine.Alliance;
import src.engine.board.Board;
import src.engine.board.BoardUtils;
import src.engine.board.Move;
import src.engine.board.Tile;

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVES_COORDINATE = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final Alliance alliance, final int piecePosition) {
        super(PieceType.KNIGHT ,alliance, piecePosition, true);
    }

    public Knight(final Alliance alliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.KNIGHT ,alliance, piecePosition, isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVES_COORDINATE){
            if( isFirst(this.piecePosition, currentCandidateOffset) || isSecond(this.piecePosition, currentCandidateOffset)
                    || isSeventh(this.piecePosition, currentCandidateOffset) || isEight(this.piecePosition, currentCandidateOffset) ){
                    continue;
                }
            candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }  
                else{
                    final Piece pieceOnTile = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceOnTile.getAlliance();
                    if(this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestinationCoordinate, pieceOnTile));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    private static boolean isFirst(final int Coordinate, final int Offset){
        return BoardUtils.FIRST_COLUMN[Coordinate] && ((Offset == -17) || (Offset == -10) || (Offset == 6) || (Offset == 15));

    }

    private static boolean isSecond(final int Coordinate, final int Offset){
        return BoardUtils.SECOND_COLUMN[Coordinate] && ((Offset == -10) || (Offset == 6));
        
    }

    private static boolean isSeventh(final int Coordinate, final int Offset){
        return BoardUtils.SEVENTH_COLUMN[Coordinate] && ((Offset == -6) || (Offset == 10));
        
    }

    private static boolean isEight(final int Coordinate, final int Offset){
        return BoardUtils.EIGHT_COLUMN[Coordinate] && ((Offset == -15) || (Offset == -6) || (Offset == 10) || (Offset == 17));
        
    }

    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }
    
}
