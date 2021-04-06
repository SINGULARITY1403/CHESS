package src.gui;

import src.engine.board.Board;
import src.engine.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public final class Table
{

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board chessBoard;
   
    private Color lightTileColor = Color.decode("#FFFFFF");
    private Color darkTileColor = Color.decode("#606060");

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String defaultPieceImagepath="art/";

    public Table()
    {
        this.gameFrame = new JFrame("CHESS");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar(){
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu filesMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        
        openPGN.addActionListener(e -> {System.exit(0);});

        filesMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> {;System.exit(0);});
        filesMenu.add(exitMenuItem);

        return filesMenu;
    }

   private class BoardPanel extends JPanel 
{

        final List<TilePanel> boardTiles;

      BoardPanel() 
      {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) 
              {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
 }

    private class TilePanel extends JPanel 
{

        private final int tileId;

        TilePanel(final BoardPanel boardPanel,
                  final int tileId) 
{
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            validate();
        }

       
        private void assignTilePieceIcon(final Board board)
        {
            this.removeAll();
            if(board.getPiece(this.tileId) != null) {
                try{
                    final BufferedImage image = ImageIO.read(new File( defaultPieceImagepath +
                            board.getPiece(this.tileId).getAlliance().toString().substring(0, 1) + "" +
                            board.getPiece(this.tileId).toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private void assignTileColor() 
        {
            if (BoardUtils.FIRST_ROW[this.tileId] ||
                BoardUtils.THIRD_ROW[this.tileId] ||
                BoardUtils.FIFTH_ROW[this.tileId] ||
                BoardUtils.SEVENTH_ROW[this.tileId] ) {
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