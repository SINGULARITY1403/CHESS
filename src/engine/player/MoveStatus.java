package src.engine.player;

public enum MoveStatus {
    DONE {
        @Override
        boolean isDone() {
            return true ;
        }
    },
    ILLEGAL_MOVE {
        @Override
        boolean isDone() {
            return false;
        }
    },
    CHECK {
        @Override
        boolean isDone() {
            return false;
        }
    };
    abstract boolean isDone();
}
