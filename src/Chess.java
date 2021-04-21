package src;

import src.gui.Table;
import src.engine.board.Board;

public class Chess {
    
    public static void main (String[] args){

        Board board = Board.createStandardBoard();

        System.out.println(board);

        Table.get().show();
    }
}