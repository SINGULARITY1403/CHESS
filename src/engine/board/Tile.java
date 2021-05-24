package src.engine.board;

import java.util.*;

import src.engine.piece.Piece;

public abstract class Tile {
    protected int tileCoordinate;

    private Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();

    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleTiles();

    private static Map<Integer, EmptyTile> createAllPossibleTiles() {
        final Map<Integer, EmptyTile> emptyTiles = new HashMap<>();

        for(int i =0; i<64;i++){
            emptyTiles.put(i, new EmptyTile(i));
        }

        return Collections.unmodifiableMap(emptyTiles);

    }

    public static Tile createTile(final int Coordinate, final Piece piece){
        if(piece != null) return new OccupiedTile(Coordinate, piece);
        else return EMPTY_TILES.get(Coordinate);
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    public static final class OccupiedTile extends Tile{

        Piece pieceOnTile;

        OccupiedTile(final int tileCoordinate,final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString() {
            return getPiece().getAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }

    }

    public static final class EmptyTile extends Tile{

        EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

    }
    
}