package src.engine.board;

import java.util.*;


public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = Column(0);
    public static final boolean[] SECOND_COLUMN = Column(1);
    public static final boolean[] SEVENTH_COLUMN = Column(6);
    public static final boolean[] EIGHT_COLUMN = Column(7);
    
    public static final boolean[] FIRST_ROW = row(0);
    public static final boolean[] SECOND_ROW = row(8);
    public static final boolean[] THIRD_ROW = row(16);
    public static final boolean[] FOURTH_ROW = row(24);
    public static final boolean[] FIFTH_ROW = row(32);
    public static final boolean[] SIXTH_ROW = row(40);
    public static final boolean[] SEVENTH_ROW = row(48);
    public static final boolean[] EIGHTH_ROW = row(56);
    public static final List<String> ALGEBRIC_NOTATION = initializeAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private static List<String> initializeAlgebraicNotation() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }


    private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < NUM_TILES; i++) {
            positionToCoordinate.put(ALGEBRIC_NOTATION.get(i), i);
        }
        return Collections.unmodifiableMap(positionToCoordinate);
    }

    public static boolean[] Column(int columnNumber){
       final boolean[] Column = new boolean[64];
       do{
            Column[columnNumber] = true;
            
            columnNumber += 8;

        }while(columnNumber < 64);
        
        return Column;
    } 

    private static boolean[] row(int rowNumber) {
        final boolean[] row = new boolean[64];
        do{
                row[rowNumber] = true;
                
                rowNumber += 1;

            }while(rowNumber % NUM_TILES_PER_ROW != 0);
            
            return row;
    }

    public static boolean isValidTileCoordinate(int Coordinate){
        return Coordinate >= 0 && Coordinate < 64;
    }

    public static int getCoordinateAtPosition( final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public static String getPositionAtCoordinate(final int Coordinate){
        return ALGEBRIC_NOTATION.get(Coordinate);
    }

    public static boolean isThreatenedBoardImmediate(final Board board) {
        return board.whitePlayer().isInCheck() || board.blackPlayer().isInCheck();
    }

    public static boolean isEndGame(final Board board) {
        return board.currentPlayer().isInCheckmate() || board.currentPlayer().isInStalemate();
    }

}
