package src.gui;

import src.engine.Alliance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login2 extends JDialog {

    private Alliance user1Alliance;
    private Alliance user2Alliance;
    private JTextField user1Name = new JTextField();
    private JTextField user2Name = new JTextField();
    private static final String WHITE_ALLIANCE = "White";
    private static final String BLACK_ALLIANCE = "Black";

    private static final Login2 INSTANCE = new Login2();

    public Login2() {
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));
        final JRadioButton user1WhiteButton = new JRadioButton(WHITE_ALLIANCE);
        final JRadioButton user2WhiteButton = new JRadioButton(WHITE_ALLIANCE);
        final JRadioButton user1BlackButton = new JRadioButton(BLACK_ALLIANCE);
        final JRadioButton user2BlackButton = new JRadioButton(BLACK_ALLIANCE);
        user1WhiteButton.setActionCommand(WHITE_ALLIANCE);
        final ButtonGroup whiteGroup = new ButtonGroup();
        whiteGroup.add(user1WhiteButton);
        whiteGroup.add(user2WhiteButton);

        final ButtonGroup blackGroup = new ButtonGroup();
        blackGroup.add(user1BlackButton);
        blackGroup.add(user2BlackButton);

        getContentPane().add(myPanel);
        myPanel.add(new JLabel("UserName 1"));
        myPanel.add(user1Name);
        myPanel.add(new JLabel("Choose Alliance"));
        myPanel.add(user1WhiteButton);
        myPanel.add(user1BlackButton);
        myPanel.add(new JLabel("UserName 2"));
        myPanel.add(user2Name);
        myPanel.add(new JLabel("Choose Alliance"));
        myPanel.add(user2WhiteButton);
        myPanel.add(user2BlackButton);

        final JButton cancelButton = new JButton("Cancel");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user1Alliance = user1WhiteButton.isSelected() ? Alliance.WHITE : Alliance.BLACK;
                user2Alliance = user2WhiteButton.isSelected() ? Alliance.WHITE : Alliance.BLACK;
                Login2.this.setVisible(false);
                Table2.get().show();
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cancel");
                Login2.this.setVisible(false);
            }
        });

        myPanel.add(cancelButton);
        myPanel.add(okButton);

        pack();
        setVisible(false);
    }

    public void promptUser() {
        setVisible(true);
        repaint();
    }

    String getUser1Name() {
        return user1Name.getText();
    }

    String getUser2Name() {
        return user2Name.getText();
    }

    Alliance getUser1Alliance() {
        return this.user1Alliance;
    }

    Alliance getUser2Alliance() {
        return this.user2Alliance;
    }

    public static Login2 get() {
        return INSTANCE;
    }

}