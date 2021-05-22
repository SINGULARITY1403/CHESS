package src.gui;

import src.engine.Alliance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog {

    private Alliance user1Alliance;
    private Alliance computerAlliance;
    private JTextField user1Name = new JTextField();
    private static final String WHITE_ALLIANCE = "White";
    private static final String BLACK_ALLIANCE = "Black";

    private JSpinner searchDepthSpinner;

    private static final Login INSTANCE = new Login();

    public Login() {
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));
        final JRadioButton user1WhiteButton = new JRadioButton(WHITE_ALLIANCE);
        final JRadioButton user1BlackButton = new JRadioButton(BLACK_ALLIANCE);
        user1WhiteButton.setActionCommand(WHITE_ALLIANCE);
        final ButtonGroup whiteGroup = new ButtonGroup();
        whiteGroup.add(user1WhiteButton);

        final ButtonGroup blackGroup = new ButtonGroup();
        blackGroup.add(user1BlackButton);

        getContentPane().add(myPanel);
        myPanel.add(new JLabel("UserName 1"));
        myPanel.add(user1Name);
        myPanel.add(new JLabel("Choose Alliance"));
        myPanel.add(user1WhiteButton);
        myPanel.add(user1BlackButton);

        myPanel.add(new JLabel("Search"));
        this.searchDepthSpinner = addLabeledSpinner(myPanel, "Search Depth", new SpinnerNumberModel(1, 1, 4, 1));

        final JButton cancelButton = new JButton("Cancel");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user1Alliance = user1WhiteButton.isSelected() ? Alliance.WHITE : Alliance.BLACK;
                computerAlliance = user1WhiteButton.isSelected() ?  Alliance.BLACK : Alliance.WHITE;
                Login.this.setVisible(false);
                Table.get().show();
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cancel");
                Login.this.setVisible(false);
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

    public static Login get() {
        return INSTANCE;
    }

    private static JSpinner addLabeledSpinner(final Container c, final String label, final SpinnerModel model) {
        final JLabel l = new JLabel(label);
        c.add(l);
        final JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);
        return spinner;
    }

    int getSearchDepth() {
        return (Integer)this.searchDepthSpinner.getValue();
    }

    String getUser1Name() {
        return user1Name.getText();
    }

    Alliance getUser1Alliance() {
        return this.user1Alliance;
    }

    Alliance getComputerAlliance() {
        return this.computerAlliance;
    }

}
