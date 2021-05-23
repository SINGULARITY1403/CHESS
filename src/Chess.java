package src;

import src.gui.*;
import java.util.*;

public class Chess {
    
    public static void main (String[] args){

        String S;
        Scanner sc = new Scanner(System.in);

        S = sc.nextLine();

        if(S.equals("Multiplayer")){
            Login2.get().promptUser();
        }
        else if(S.equals("Singleplayer")){
            Login.get().promptUser();
        }

    }
}