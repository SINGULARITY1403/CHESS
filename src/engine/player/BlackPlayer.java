package src.engine.player;

import java.util.*;

import src.engine.Alliance;
import src.engine.board.Board;
import src.engine.board.Move;
import src.engine.board.Tile;
import src.engine.piece.Piece;
import src.engine.piece.Rook;

public class BlackPlayer extends Player{
    public BlackPlayer(Board board, Collection<Move> blackStandardLegalMoves, Collection<Move> whiteStandardLegalMoves){
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,final Collection<Move> opponentLegals) {

        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){
                final Tile rookTile = this.board.getTile(7);

                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){ 
                    if((Player.calculateAttacksOnTile(5, opponentLegals)).isEmpty() && (Player.calculateAttacksOnTile(6, opponentLegals)).isEmpty() && rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new Move.KingSideCastle(this.board, this.playerKing, 6, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }

            if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()){
                final Tile rookTile = this.board.getTile(0);

                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if((Player.calculateAttacksOnTile(1, opponentLegals)).isEmpty() && (Player.calculateAttacksOnTile(2, opponentLegals)).isEmpty() && (Player.calculateAttacksOnTile(3, opponentLegals)).isEmpty() && rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new Move.QueenSideCastle(this.board, this.playerKing, 2, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }

    @Override
    public String toString() {
        return "Black";
    }

}
