package src.gui;

import src.engine.board.Board;
import src.engine.board.BoardUtils;
import src.engine.board.Move;
import src.engine.board.Tile;
import src.engine.board.Move.MoveFactory;
import src.engine.piece.Piece;
import src.engine.player.MoveTransition;
import src.engine.player.Player;
import src.engine.Alliance;
import src.gui.Login;

import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static javax.swing.SwingUtilities.*;

public class Table2 extends Observable{

    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    private final MoveLog moveLog;
    private final UserNamePanel userNamePanel;
    private final UserNamePanel userNamePanel2;
    private Board chessBoard;
    private Tile destinationTile;
    private Tile sourceTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;
    private boolean highlightLegalMoves;
    String panelName1 = Login2.get().getUser1Name();
    String panelName2 = Login2.get().getUser2Name();

    private Color lightTileColor = Color.decode("#FFFFFF");
    private Color darkTileColor = Color.decode("#606060");

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(700, 700);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(500, 450);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(15, 15);
    private static String defaultPieceImagesPath = "Images folder/";

    private static final Table2 INSTANCE = new Table2(Login2.get().getUser1Alliance(), Login2.get().getUser2Alliance());

    private Table2(final Alliance user1Alliance, final Alliance user2Alliance) {
        this.gameFrame = new JFrame("CHESS");
        this.gameFrame.setUndecorated(true);
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setLayout(new BorderLayout());
        this.chessBoard = Board.createStandardBoard();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.userNamePanel = new UserNamePanel(Login2.get().getUser1Name());
        this.userNamePanel2 = new UserNamePanel(Login2.get().getUser2Name());
        this.addObserver(new TableGameWatcher());
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(this.userNamePanel, BorderLayout.SOUTH);
        this.gameFrame.add(this.userNamePanel2, BorderLayout.NORTH);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
        this.gameFrame.setLocationRelativeTo(null);
    }

    public static Table2 get() {
        return INSTANCE;
    }

    private Board getGameBoard() {
        return this.chessBoard;
    }

    private MoveLog getMoveLog() {
        return this.moveLog;
    }

    private BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    private GameHistoryPanel getGameHistoryPanel() {
        return this.gameHistoryPanel;
    }

    private TakenPiecesPanel getTakenPiecesPanel() {
        return this.takenPiecesPanel;
    }

    private UserNamePanel getUserNamePanel1(){
        return this.userNamePanel;
    }

    private UserNamePanel getUserNamePanel2(){
        return this.userNamePanel2;
    }


    private boolean getHighlightLegalMoves() {
        return this.highlightLegalMoves;
    }

    public void show() {
        Table2.get().getMoveLog().clear();
        Table2.get().getGameHistoryPanel().redo(chessBoard, Table2.get().getMoveLog());
        Table2.get().getTakenPiecesPanel().redo(Table2.get().getMoveLog());
        Table2.get().getBoardPanel().drawBoard(Table2.get().getGameBoard());
    }

    private void populateMenuBar(final JMenuBar tableMenuBar) {
        tableMenuBar.add(Exit());
        tableMenuBar.add(createFlipboard());
        tableMenuBar.add(createHighlightMenuItem());
    }

    private JMenuItem Exit() {

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Table2.get().gameFrame.dispose();
            }
        });

        return exitMenuItem;
    }

    private JMenuItem createFlipboard() {

        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");

        flipBoardMenuItem.addActionListener(e -> {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessBoard);
            Table2.get().panelName1 = Table2.get().panelName1 + Table2.get().panelName2;  
            Table2.get().panelName2 = Table2.get().panelName1.substring(0, Table2.get().panelName1.length() - Table2.get().panelName2.length());  
            Table2.get().panelName1 = Table2.get().panelName1.substring(Table2.get().panelName2.length());  
            Table2.get().getUserNamePanel1().swap(panelName1);
            Table2.get().getUserNamePanel2().swap(panelName2);
        });

        return flipBoardMenuItem;
    }

    private JMenuItem createHighlightMenuItem() {

        final JCheckBoxMenuItem legalMoveHighlighter = new JCheckBoxMenuItem("Highlight Legal Moves", false);

        legalMoveHighlighter.addActionListener(e -> highlightLegalMoves = legalMoveHighlighter.isSelected());    

        return legalMoveHighlighter;
    }

    public void updateGameBoard(final Board board){
        this.chessBoard = board;
    }

    private void moveMadeUpdate(final PlayerType playerType){
        setChanged();
        notifyObservers(playerType);
    }

    private void GameOver(final Player player){
        JFrame frame = new JFrame("GAME OVER");
        if (player.isInCheckmate()){
            JOptionPane.showMessageDialog(frame, player.getOpponent().getAlliance() + " WON !!!");
        }
        else if(player.isInStalemate()){
            JOptionPane.showMessageDialog(frame,  " DRAW !!!");
        }
    }

    private static class TableGameWatcher implements Observer {
        @Override
        public void update(final Observable o, final Object arg){

            if(Table2.get().getGameBoard().currentPlayer().isInCheckmate()){
                System.out.println("Game Over, " + Table2.get().getGameBoard().currentPlayer().getAlliance() + " is in CheckMate !" );
                Table2.get().GameOver(Table2.get().getGameBoard().currentPlayer());
            }

            if(Table2.get().getGameBoard().currentPlayer().isInStalemate()){
                System.out.println("Game Over, " + Table2.get().getGameBoard().currentPlayer().getAlliance() + " is in StaleMate !" );
                Table2.get().GameOver(Table2.get().getGameBoard().currentPlayer());
            }
        }
    }

    private class BoardPanel extends JPanel {

        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel boardTile : boardDirection.traverse(boardTiles)) {
                boardTile.drawTile(board);
                add(boardTile);
            }
            validate();
            repaint();
        }

    }

    private class UserNamePanel extends JPanel {

        JLabel J = new JLabel();

        UserNamePanel(final String string){
            super(new BorderLayout());
            setVisible( true );
            setPreferredSize(new Dimension(50, 50));
            setBorder(BorderFactory.createLineBorder(Color.black));
            setBackground(Color.decode("#FFFFFF"));
            J.setText(string);
            J.setHorizontalAlignment(SwingConstants.CENTER);
            add(J);
            validate();
        }

        void swap(String Str){
            removeAll();
            J.setText(Str);
            J.setHorizontalAlignment(SwingConstants.CENTER);
            add(J);
            validate();
            repaint();
        }
        
    }

    enum PlayerType{
        HUMAN
    }

    enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();

    }

    public static class MoveLog {

        private final List<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return this.moves;
        }

        void addMove(final Move move) {
            this.moves.add(move);
        }

        public int size() {
            return this.moves.size();
        }

        void clear() {
            this.moves.clear();
        }

        Move removeMove(final int index) {
            return this.moves.remove(index);
        }

        boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }

    }

    private class TilePanel extends JPanel {

        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent event) {

                    if (isRightMouseButton(event)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    } else if (isLeftMouseButton(event)) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        } 
                        else {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getTransitionBoard();
                                moveLog.addMove(move);
                            }
                            sourceTile = null;
                            humanMovedPiece = null;
                            destinationTile = null;
                        }
                    }
                    invokeLater(() -> {
                        gameHistoryPanel.redo(chessBoard, moveLog);
                        takenPiecesPanel.redo(moveLog);
                        Table2.get().moveMadeUpdate(PlayerType.HUMAN);
                        boardPanel.drawBoard(chessBoard);
                    });
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                }
            });
            validate();
        }

        void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegals(board);
            validate();
            repaint();
        }

        private void highlightLegals(final Board board) {
            if (Table2.get().getHighlightLegalMoves()) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestinationCoordinate() == this.tileId) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("Images folder/green_dot.png")))));
                        }
                        catch (final IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if(humanMovedPiece != null && humanMovedPiece.getAlliance() == board.currentPlayer().getAlliance()) {
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if(board.getPiece(this.tileId) != null) {
                try{
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath +
                            board.getPiece(this.tileId).getAlliance().toString().substring(0, 1) + "" +
                            board.getPiece(this.tileId).toString() +".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch(final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.FIRST_ROW[this.tileId] ||
                BoardUtils.THIRD_ROW[this.tileId] ||
                BoardUtils.FIFTH_ROW[this.tileId] ||
                BoardUtils.SEVENTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(BoardUtils.SECOND_ROW[this.tileId] ||
                      BoardUtils.FOURTH_ROW[this.tileId] ||
                      BoardUtils.SIXTH_ROW[this.tileId]  ||
                      BoardUtils.EIGHTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}