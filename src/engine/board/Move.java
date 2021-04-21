package src.engine.board;

import src.engine.piece.Pawn;
import src.engine.piece.Piece;
import src.engine.piece.Rook;

public abstract class Move {
    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinatinationCoordinate;
    protected final boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();
    
    private Move(final Board board, final Piece movedPiece,final int destinatinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinatinationCoordinate = destinatinationCoordinate;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board,final int destinatinationCoordinate){
        this.board = board;
        this.movedPiece = null;
        this.destinatinationCoordinate = destinatinationCoordinate;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + this.destinatinationCoordinate;
        result = 31 * result + this.movedPiece.hashCode();
        result = 31 * result + this.movedPiece.getPiecePosition();
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
        return( getCurrentCoordinate() == XMove.getCurrentCoordinate() || getDestinationCoordinate() == XMove.getDestinationCoordinate() && getMovedPiece() == XMove.getMovedPiece() );

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

    public Board getBoard(){
        return this.board;
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

        @Override
        public boolean equals(final Object X){
            return this == X || X instanceof MajorMove && super.equals(X);
        }

        @Override 
        public String toString(){
            return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinatinationCoordinate);
        }
    }

    public static class MajorAttackMove extends AttackMove{
        public MajorAttackMove(final Board board, final Piece pieceMoved, final int destinatinationCoordinate, final Piece pieceAttacked){
            super(board, pieceMoved, destinatinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object X){
            return this == X || X instanceof MajorAttackMove && super.equals(X);
        }

        @Override 
        public String toString(){
            return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinatinationCoordinate);
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

        @Override
        public boolean equals(final Object X){
            return this == X || X instanceof PawnMove && super.equals(X);
        }

        @Override
        public String toString(){
            return BoardUtils.getPositionAtCoordinate(this.destinatinationCoordinate); 
        }

    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board,final Piece movedPiece,final int destinatinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinatinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object X){
            return this == X || X instanceof PawnAttackMove && super.equals(X);
        }

        @Override
        public String toString(){
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0,1) + "x" + BoardUtils.getPositionAtCoordinate(this.destinatinationCoordinate); 
        }

    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove {

        public PawnEnPassantAttackMove(final Board board,final Piece movedPiece,final int destinatinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinatinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object X){
            return this == X || X instanceof PawnEnPassantAttackMove && super.equals(X);
        }

        @Override
        public Board execute(){
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }

            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                if(!piece.equals(this.getAttackedPiece())){
                    builder.setPiece(piece);
                }
            }

            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }

    public static class PawnPromotion extends Move{
        final Move decoratedMove;
        final Pawn promotedPawn;
        public PawnPromotion(final Move decoratedMove) {
            super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
            this.decoratedMove = decoratedMove;
            this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
        }

        @Override
        public int hashCode() {
            return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
        }

        @Override
        public boolean equals(final Object X){
            return this == X || X instanceof PawnPromotion && (super.equals(X));
        }

        @Override
        public Board execute(){
            final Board pawnMovedBoard = this.decoratedMove.execute();
            final Board.Builder builder = new Board.Builder();

            for(final Piece piece : pawnMovedBoard.currentPlayer().getActivePieces()){
                if(!this.promotedPawn.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : pawnMovedBoard.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.promotedPawn.getPromotedPiece().movePiece(this));
            builder.setMoveMaker(pawnMovedBoard.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

        @Override
        public boolean isAttack(){
            return this.decoratedMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece(){
            return this.decoratedMove.getAttackedPiece();
        }

        @Override 
        public String toString(){
            return "";
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

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnJump && super.equals(other);
        }

        @Override
        public String toString(){
            return BoardUtils.getPositionAtCoordinate(this.destinatinationCoordinate); 
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

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + this.castleRook.hashCode();
            result = 31 * result + this.castleRookDestination;
            return result; 
        }

        @Override
        public boolean equals(final Object X){
            if(this == X){
                return true;
            }

            if(X == null || !(X instanceof CastleMove)){
                return false;
            }

            final CastleMove XMove = (CastleMove) X;
            return super.equals(XMove) && this.castleRook.equals(XMove.getCastleRook());
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

        @Override
        public boolean equals(final Object X){
            return this == X || X instanceof KingSideCastle && super.equals(X);
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

        @Override
        public boolean equals(final Object X){
            return this == X || X instanceof QueenSideCastle && super.equals(X);
        }

    }

    public static final class NullMove extends Move {

        public NullMove() {
            super(null, 65);
        }

        @Override
        public int getCurrentCoordinate() {
            return -1;
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
