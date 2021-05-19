package src;

import src.gui.Table;
import src.gui.Table2;
import src.engine.board.Board;
import java.util.*;

public class Chess {
    
    public static void main (String[] args){

        String S;

        Board board = Board.createStandardBoard();

        System.out.println(board);

        Scanner sc = new Scanner(System.in);

        S = sc.nextLine();

        if(S.equals("Multiplayer")){
            Table2.get().show();
        }
        else if(S.equals("Singleplayer")){
            Table.get().show();
        }

    }
}