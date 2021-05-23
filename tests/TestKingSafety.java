package tests;

import org.junit.Test;

import src.engine.Alliance;
import src.engine.board.Board;
import src.engine.board.Board.Builder;
import src.engine.piece.King;
import src.engine.piece.Pawn;

public class TestKingSafety {

    @Test
    public void test1() {
        final Builder builder = new Builder();
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
    }

}