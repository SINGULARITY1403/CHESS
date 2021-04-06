package src.engine.board;

import src.engine.piece.Pawn;
import src.engine.piece.Piece;
import src.engine.piece.Rook;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinatinationCoordinate;

    public static final Move NULL_MOVE = new NullMove();
    
    private Move(final Board board, final Piece movedPiece,final int destinatinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinatinationCoordinate = destinatinationCoordinate;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + this.destinatinationCoordinate;
        result = 31 * result + this.movedPiece.hashCode();
        return result; 
    }

    @Override
    public boolean equals(final Object X){
        if(this == X){
            return true;
        }

        if(X == null || !(X instanceof Move)){
            return false;
        }

        final Move XMove = (Move) X;
        return( getDestinationCoordinate() == XMove.getDestinationCoordinate() && getMovedPiece() == XMove.getMovedPiece() );


    }

    public int getCurrentCoordinate() {
        return this.movedPiece.getPiecePosition();
    }

    public int getDestinationCoordinate(){
        return this.destinatinationCoordinate;
    }

    public Piece getMovedPiece(){
        return this.movedPiece;
    }

    public boolean isAttack(){
        return false;
    }

    public boolean isCastlingMove(){
        return false;
    }

    public Piece getAttackedPiece(){
        return null;
    }

    public Board execute() {
        final Board.Builder builder = new Board.Builder();

        for (final Piece piece : this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece)){
                builder.setPiece(piece);
            }
        }

        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }

        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board,final Piece movedPiece,final int destinatinationCoordinate) {
            super(board, movedPiece, destinatinationCoordinate);
        }

    }

    public static class AttackMove extends Move{

        final Piece attackedPiece; 

        public AttackMove(final Board board,final Piece movedPiece,final int destinatinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinatinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object X){
            if(this == X){
                return true;
            }

            if(X == null || !(X instanceof AttackMove)){
                return false;
            }

            final AttackMove XAttackMove = (AttackMove) X;
            return (super.equals(XAttackMove) && getAttackedPiece().equals(XAttackMove.getAttackedPiece()));
        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAttack(){
            return true;
        }

        @Override
        public Piece getAttackedPiece(){
            return this.attackedPiece;
        }



    }

    public static final class PawnMove extends Move {

        public PawnMove(final Board board,final Piece movedPiece,final int destinatinationCoordinate) {
            super(board, movedPiece, destinatinationCoordinate);
        }

    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board,final Piece movedPiece,final int destinatinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinatinationCoordinate, attackedPiece);
        }

    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove {

        public PawnEnPassantAttackMove(final Board board,final Piece movedPiece,final int destinatinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinatinationCoordinate, attackedPiece);
        }

    }

    public static final class PawnJump extends Move {

        public PawnJump(final Board board,final Piece movedPiece,final int destinatinationCoordinate) {
            super(board, movedPiece, destinatinationCoordinate);
        }

        @Override
        public Board execute(){
            final Board.Builder builder = new Board.Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!(this.movedPiece.equals(piece))){
                    builder.setPiece(piece);
                }
            }

            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }

            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }

    static abstract class CastleMove extends Move {

        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        public CastleMove(final Board board,final Piece movedPiece,final int destinatinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
            super(board, movedPiece, destinatinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook(){
            return this.castleRook;
        }

        @Override
        public  boolean isCastlingMove(){
            return true;
        }

        @Override
        public Board execute(){
            final Board.Builder builder = new Board.Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!(this.movedPiece.equals(piece)) && !this.castleRook.equals(piece)){
                    builder.setPiece(piece);
                }
            }

            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }

            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRook.getAlliance(), this.castleRookDestination));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }

    public static final class KingSideCastle extends CastleMove {

        public KingSideCastle(final Board board,final Piece movedPiece,final int destinatinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
            super(board, movedPiece, destinatinationCoordinate,  castleRook,  castleRookStart, castleRookDestination);
        }

        @Override
        public String toString(){
            return "O-O";
        }

    }

    public static final class QueenSideCastle extends CastleMove {

        public QueenSideCastle(final Board board,final Piece movedPiece,final int destinatinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
            super(board, movedPiece, destinatinationCoordinate,  castleRook,  castleRookStart, castleRookDestination);
        }

        @Override
        public String toString(){
            return "O-O-O";
        }

    }

    public static final class NullMove extends Move {

        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("Cannot Execute Null Move");
        }

    }
    
    public static class MoveFactory{
        
        private MoveFactory(){
            throw new RuntimeException("Not Instantiable");
        }

        public static Move createMove(final Board board,final int currentCoordinate,final int destinatinationCoordinate){
            for(final Move move : board.getAllLegalMoves()){
                if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinatinationCoordinate){
                    return move;
                }
            }
            return NULL_MOVE;
        }

    }

}
