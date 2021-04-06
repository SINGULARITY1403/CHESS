package src.engine.board;

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

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

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

}
