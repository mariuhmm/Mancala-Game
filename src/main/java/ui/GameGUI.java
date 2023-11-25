import mancala.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private MancalaGame mancalaGame;
    private JButton[] pits;

    public GameGUI(MancalaGame game) {
        this.mancalaGame = game;
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mancala Game");
        setLayout(new BorderLayout());

        // Create the Mancala board panel
        JPanel boardPanel = new JPanel(new GridLayout(2, 6));

        // Create the pits
        pits = new JButton[12];
        for (int i = 0; i < 12; i++) {
            pits[i] = new JButton("Pit " + (i + 1) + ": " + mancalaGame.getBoard().getNumStones(i + 1));
            final int pitIndex = i + 1;
            pits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        mancalaGame.move(pitIndex);
                        updateUI();
                    } catch (InvalidMoveException ex) {
                        JOptionPane.showMessageDialog(GameGUI.this, "Invalid move. Please try again.");
                    }
                }
            });
            boardPanel.add(pits[i]);
        }

        // Create the player stores
        JLabel playerStore1 = new JLabel("Player 1 Store: " + mancalaGame.getPlayers().get(0).getStoreCount());
        JLabel playerStore2 = new JLabel("Player 2 Store: " + mancalaGame.getPlayers().get(1).getStoreCount());

        // Create a panel for player stores
        JPanel storePanel = new JPanel(new GridLayout(2, 1));
        storePanel.add(playerStore1);
        storePanel.add(playerStore2);

        // Add the components to the main frame
        add(boardPanel, BorderLayout.CENTER);
        add(storePanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateUI() {
        // Update pit labels based on the game state
        for (int i = 0; i < 12; i++) {
            pits[i].setText("Pit " + (i + 1) + ": " + mancalaGame.getBoard().getNumStones(i + 1));
        }

        // Update player store labels
        ((JLabel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).setText("Player 1 Store: " + mancalaGame.getPlayers().get(0).getStoreCount());
        ((JLabel) ((JPanel) getContentPane().getComponent(1)).getComponent(1)).setText("Player 2 Store: " + mancalaGame.getPlayers().get(1).getStoreCount());
    }

    public static void main(String[] args) {
        GameRules chosenRules = chooseGameType();

        MancalaGame mancalaGame = new MancalaGame(chosenRules);

        GameGUI mancalaGUI = new GameGUI(mancalaGame);
    }

    private static GameRules chooseGameType() {
        int choice = JOptionPane.showOptionDialog(null,
                "Choose the game type:",
                "Mancala Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Ayo", "Kalah"},
                "Ayo");

        if (choice == 0) {
            return new AyoRules();
        } else {
            return new KalahRules();
        }
    }
}
