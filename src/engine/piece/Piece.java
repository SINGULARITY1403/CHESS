package src.engine.piece;

import src.engine.Alliance;
import src.engine.board.Move;
import src.engine.board.Board;

import java.util.*;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean FirstMove;
    private final int cachedHashcode;
   
    public Piece(final PieceType pieceType, final Alliance pieceAlliance, final int piecePosition, final Boolean isFirstMove) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.FirstMove = isFirstMove;
        this.cachedHashcode = generatedHashcode();
    }

    private int generatedHashcode() {
        int result  = pieceType.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + (isFirstMove() ? 1 : 0);
        return result;
    }

    public abstract List<Move> calculateLegalMoves(final Board board);
    public abstract Piece movePiece(Move move);

    public boolean isFirstMove(){
        return this.FirstMove ;
    }

    public Alliance getAlliance(){
        return this.pieceAlliance;
    }

    public Integer getPiecePosition() {
        return piecePosition;
    }

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public int getPieceValue(){
        return this.pieceType.getPieceValue();
    }

    @Override
    public boolean equals(final Object X){
        if(this == X){
            return true;
        }

        if(X == null || !(X instanceof Piece)){
            return false;
        }

        final Piece XPiece = (Piece) X;
        return piecePosition == XPiece.getPiecePosition() && pieceAlliance == XPiece.getAlliance() && pieceType == XPiece.getPieceType() && FirstMove == XPiece.isFirstMove();      
    }

    @Override
    public int hashCode(){
        return this.cachedHashcode;
    }

    public enum PieceType{
        PAWN("P", 100) {
            @Override
            public boolean isKing() {
                
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK( "R", 500) {
            @Override
            public boolean isKing() {
                
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        KNIGHT("N", 300) {
            @Override
            public boolean isKing() {
                
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B", 300) {
            @Override
            public boolean isKing() {
                
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        QUEEN("Q", 900) {
            @Override
            public boolean isKing() {
                
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K", 10000) {
            @Override
            public boolean isKing() {
                
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        private String pieceName;
        private int pieceValue;

        private PieceType(final String pieceName, final int pieceValue) {
            this.pieceName  = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public int getPieceValue(){
            return this.pieceValue;
        }

        public abstract boolean isKing();
        public abstract boolean isRook();
    }
}
