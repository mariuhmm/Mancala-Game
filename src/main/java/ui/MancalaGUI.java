package ui;
import mancala.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//mport java.awt.event.ActionListener;

public class MancalaGUI extends JFrame {

    //private GameRules rules;
    private MancalaGame mancalaGame;
    private final Saver saver;
    private JButton[] pits;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel store1Panel;
    private JPanel store2Panel;
    private static int saveCounter = 0;


    public static final int WIDTH = 900;
    public static final int HEIGHT = 500;

    public MancalaGUI(final MancalaGame game) {
        super();
        saver = new Saver();
        mancalaGame = game;
        initializeComponents();
        setVisible(true);
    }

    private static GameRules chooseGame() {
        final int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an option:",
                "Mancala Game",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{
                        "New Kalah Game",
                        "New Ayo Game"
                },
                "New Kalah Game");

        switch (choice) {
            case 0:
                return new KalahRules();
            case 1:
                return new AyoRules();
            default:
                return new KalahRules();
        }
    }

    private void saveUserProfile(int player){
        saver.saveObject(mancalaGame.getPlayers().get(player).getUserProfile(), mancalaGame.getPlayers().get(player).getUserProfile().getPlayerName() + ".ser");
        JOptionPane.showMessageDialog(this, "Player " + (player+1)+" Saved successfully!");
    }

    private void loadUserProfile(int player) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Profile");

        fileChooser.setFileFilter(new FileNameExtensionFilter("Serialized Files (*.ser)", "ser"));

        int userChoice = fileChooser.showOpenDialog(this);

        if(userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            UserProfile loadedProfile = (UserProfile) saver.loadObject(selectedFile.getAbsolutePath());

            if (loadedProfile != null) {
                mancalaGame.getPlayers().get(player).setUserProfile(loadedProfile);
                updatePlayer1Info();
                updatePlayer2Info();
                JOptionPane.showMessageDialog(this, "Profile loaded successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error loading the profile. Make sure the file is valid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveGameOption() {
        saver.saveObject(mancalaGame, "savedGame"+saveCounter+".ser");
        saveCounter++;
        JOptionPane.showMessageDialog(this, "Game saved successfully!");
    }

    private void loadGameOption() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Game");
        
        // only display serialized files
        fileChooser.setFileFilter(new FileNameExtensionFilter("Serialized Files (*.ser)", "ser"));

        int userChoice = fileChooser.showOpenDialog(this);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            MancalaGame loadedGame = (MancalaGame) saver.loadObject(selectedFile.getAbsolutePath());

            if (loadedGame != null) {
                mancalaGame = loadedGame;
                updateUI();
                JOptionPane.showMessageDialog(this, "Game loaded successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error loading the game. Make sure the file is valid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void exitGameOption() {
        int choice = JOptionPane.showOptionDialog(
                this,
                "Do you want to save the players and game before exiting?",
                "Exit Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Save and Exit", "Exit Without Saving"},
                "Save and Exit");

        switch (choice) {
            case 0:
                saveGameOption();
                saveUserProfile(0);
                saveUserProfile(1);
                dispose();
                startNewGame();
                break;
            case 1:
                dispose();
                startNewGame();
                break;
        }
    }


    private void initializeComponents() {
        setSize(WIDTH, HEIGHT);
        setTitle("Mancala Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        initializePlayerOnePanel(mainPanel);
        initializePlayerTwoPanel(mainPanel);

        initializeStorePanels(mainPanel);
    
        initializeBoardPanel(mainPanel);

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> chooseGame());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenu fileMenu2 = new JMenu("Profile");
        menuBar.add(fileMenu2);

        JMenuItem saveProfile1Item = new JMenuItem("Save Player 1");
        saveProfile1Item.addActionListener(e -> saveUserProfile(0));
        fileMenu2.add(saveProfile1Item);

        JMenuItem saveProfile2Item = new JMenuItem("Save Player 2");
        saveProfile2Item.addActionListener(e -> saveUserProfile(1));
        fileMenu2.add(saveProfile2Item);

        JMenuItem loadProfile1Item = new JMenuItem("Load Player 1");
        loadProfile1Item.addActionListener(e -> loadUserProfile(0));
        fileMenu2.add(loadProfile1Item);

        JMenuItem loadProfile2Item = new JMenuItem("Load Player 2");
        loadProfile2Item.addActionListener(e -> loadUserProfile(1));
        fileMenu2.add(loadProfile2Item);
        
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        saveGameItem.addActionListener(e -> saveGameOption());
        fileMenu.add(saveGameItem);

        JMenuItem loadGameItem = new JMenuItem("Load Game");
        loadGameItem.addActionListener(e -> loadGameOption());
        fileMenu.add(loadGameItem);

        JMenuItem exitGameItem = new JMenuItem("Exit Game");
        exitGameItem.addActionListener(e -> exitGameOption());
        fileMenu.add(exitGameItem);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void initializePlayerOnePanel(JPanel mainPanel) {
        player1Panel = new JPanel();
        player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.Y_AXIS));

        JLabel currentPlayerLabel = new JLabel("Current Player: " + mancalaGame.getCurrentPlayer().getName());
        JLabel playerLabel = new JLabel(mancalaGame.getPlayers().get(0).getUserProfile().getPlayerName());
        JLabel kalahGamesLabel = new JLabel("Kalah Games: " + mancalaGame.getPlayers().get(0).getUserProfile().getKalahGames());
        JLabel kalahWinsLabel = new JLabel("Kalah Wins: " + mancalaGame.getPlayers().get(0).getUserProfile().getKalahWins());
        JLabel kalahLossesLabel = new JLabel("Kalah Losses: " + mancalaGame.getPlayers().get(0).getUserProfile().getKalahLosses());
        JLabel ayoGamesLabel = new JLabel("Ayo Games: " + mancalaGame.getPlayers().get(0).getUserProfile().getAyoGames());
        JLabel ayoWinsLabel = new JLabel("Ayo Wins: " + mancalaGame.getPlayers().get(0).getUserProfile().getAyoWins());
        JLabel ayoLossesLabel = new JLabel("Ayo Losses: " + mancalaGame.getPlayers().get(0).getUserProfile().getAyoLosses());

        
        player1Panel.add(playerLabel);
        player1Panel.add(kalahGamesLabel);
        player1Panel.add(kalahWinsLabel);
        player1Panel.add(kalahLossesLabel);
        player1Panel.add(ayoGamesLabel);
        player1Panel.add(ayoWinsLabel);
        player1Panel.add(ayoLossesLabel);
        player1Panel.add(currentPlayerLabel);
        

        mainPanel.add(player1Panel, BorderLayout.SOUTH);
    }

    private void initializePlayerTwoPanel(JPanel mainPanel) {
        player2Panel = new JPanel();
        player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.Y_AXIS));

        JLabel playerLabel = new JLabel(mancalaGame.getPlayers().get(1).getUserProfile().getPlayerName());
        JLabel kalahGamesLabel = new JLabel("Kalah Games: " + mancalaGame.getPlayers().get(1).getUserProfile().getKalahGames());
        JLabel kalahWinsLabel = new JLabel("Kalah Wins: " + mancalaGame.getPlayers().get(1).getUserProfile().getKalahWins());
        JLabel kalahLossesLabel = new JLabel("Kalah Losses: " + mancalaGame.getPlayers().get(1).getUserProfile().getKalahLosses());
        JLabel ayoGamesLabel = new JLabel("Ayo Games: " + mancalaGame.getPlayers().get(1).getUserProfile().getAyoGames());
        JLabel ayoWinsLabel = new JLabel("Ayo Wins: " + mancalaGame.getPlayers().get(1).getUserProfile().getAyoWins());
        JLabel ayoLossesLabel = new JLabel("Ayo Losses: " + mancalaGame.getPlayers().get(1).getUserProfile().getAyoLosses());

        player2Panel.add(playerLabel);
        player2Panel.add(kalahGamesLabel);
        player2Panel.add(kalahWinsLabel);
        player2Panel.add(kalahLossesLabel);
        player2Panel.add(ayoGamesLabel);
        player2Panel.add(ayoWinsLabel);
        player2Panel.add(ayoLossesLabel);

        mainPanel.add(player2Panel, BorderLayout.NORTH);
    }

    private void updatePlayer1Info() {
        Player player = mancalaGame.getPlayers().get(0);
        UserProfile userProfile = player.getUserProfile();
        
        JLabel playerLabel = new JLabel(mancalaGame.getPlayers().get(0).getUserProfile().getPlayerName());
        JLabel kalahGamesLabel = new JLabel("Kalah Games: " + userProfile.getKalahGames());
        JLabel kalahWinsLabel = new JLabel("Kalah Wins: " + userProfile.getKalahWins());
        JLabel kalahLossesLabel = new JLabel("Kalah Losses: " + userProfile.getKalahLosses());
        JLabel ayoGamesLabel = new JLabel("Ayo Games: " + userProfile.getAyoGames());
        JLabel ayoWinsLabel = new JLabel("Ayo Wins: " + userProfile.getAyoWins());
        JLabel ayoLossesLabel = new JLabel("Ayo Losses: " + userProfile.getAyoLosses());
        JLabel currentPlayerLabel = new JLabel("Current Player: " + mancalaGame.getCurrentPlayer().getName());
        
        player1Panel.removeAll();
        player1Panel.add(playerLabel);
        player1Panel.add(kalahGamesLabel);
        player1Panel.add(kalahWinsLabel);
        player1Panel.add(kalahLossesLabel);
        player1Panel.add(ayoGamesLabel);
        player1Panel.add(ayoWinsLabel);
        player1Panel.add(ayoLossesLabel);
        player1Panel.add(currentPlayerLabel);

        player1Panel.revalidate();
        player1Panel.repaint();
    }

    private void updatePlayer2Info() {
        Player player = mancalaGame.getPlayers().get(1);
        UserProfile userProfile = player.getUserProfile();
        
        JLabel kalahGamesLabel = new JLabel("Kalah Games: " + userProfile.getKalahGames());
        JLabel kalahWinsLabel = new JLabel("Kalah Wins: " + userProfile.getKalahWins());
        JLabel kalahLossesLabel = new JLabel("Kalah Losses: " + userProfile.getKalahLosses());
        JLabel ayoGamesLabel = new JLabel("Ayo Games: " + userProfile.getAyoGames());
        JLabel ayoWinsLabel = new JLabel("Ayo Wins: " + userProfile.getAyoWins());
        JLabel ayoLossesLabel = new JLabel("Ayo Losses: " + userProfile.getAyoLosses());

        player2Panel.removeAll();
        player2Panel.add(new JLabel(player.getUserProfile().getPlayerName()));
        player2Panel.add(kalahGamesLabel);
        player2Panel.add(kalahWinsLabel);
        player2Panel.add(kalahLossesLabel);
        player2Panel.add(ayoGamesLabel);
        player2Panel.add(ayoLossesLabel);
        player2Panel.add(ayoWinsLabel);

        player2Panel.revalidate();
        player2Panel.repaint();
    }

    private void initializeStorePanels(JPanel mainPanel) {
        JLabel player1Store = new JLabel("Player 1 Store: " + mancalaGame.getBoard().getStoreCount(1));
        JLabel player2Store = new JLabel("Player 2 Store: " + mancalaGame.getBoard().getStoreCount(2));

        store1Panel = new JPanel(new BorderLayout());
        store1Panel.add(player1Store);

        store2Panel = new JPanel(new BorderLayout());
        store2Panel.add(player2Store);

        mainPanel.add(store1Panel, BorderLayout.EAST);
        mainPanel.add(store2Panel, BorderLayout.WEST);
    }

    private void currentPlayerLabel(JPanel mainPanel){
        JLabel currentPlayerLabel = new JLabel("Current Player: " + mancalaGame.getCurrentPlayer().getName());
        player1Panel.add(currentPlayerLabel);
        
    }

    private void initializeBoardPanel(JPanel mainPanel) {
        JPanel boardPanel = new JPanel(new GridLayout(2, 6));
        pits = new JButton[13];

        // Initialize buttons for the pits in the grid
        initializePitButtons(boardPanel);

        mainPanel.add(boardPanel, BorderLayout.CENTER);
    }

    private void initializePitButtons(JPanel boardPanel) {
        for (int i = 12; i >= 7; i--) {
            pits[i] = new JButton("Pit " + (i) + ": " + mancalaGame.getBoard().getNumStones(i));
            final int pitIndex = i;
            pits[i].addActionListener(event -> moveAction(pitIndex));
            boardPanel.add(pits[i]);
        }

        for (int i = 1; i <= 6; i++) {
            pits[i] = new JButton("Pit " + (i) + ": " + mancalaGame.getBoard().getNumStones(i));
            final int pitIndex = i;
            pits[i].addActionListener(event -> moveAction(pitIndex));
            boardPanel.add(pits[i]);
        }
        
    }

    private void moveAction(int pitNum) {
        try {
            updatePlayer1Info();
            int stonesRemaining = mancalaGame.move(pitNum);
            updateUI();

            // Check if the game is over
            if (mancalaGame.isGameOver()) {
                if(mancalaGame.getBoard() instanceof KalahRules){
                    mancalaGame.getPlayers().get(0).getUserProfile().kalahGames();
                    mancalaGame.getPlayers().get(1).getUserProfile().kalahGames();
                }else{
                    mancalaGame.getPlayers().get(0).getUserProfile().ayoGames();
                    mancalaGame.getPlayers().get(1).getUserProfile().ayoGames();
                }
                displayWinner();
            } else {
                // Check for extra turn and switch player accordingly
                if (mancalaGame.getBoard() instanceof KalahRules && ((KalahRules) mancalaGame.getBoard()).extraTurn()) {
                    JOptionPane.showMessageDialog(MancalaGUI.this, "Extra turn for " + mancalaGame.getCurrentPlayer().getName());
                } else {
                    switchPlayer();
                    updatePlayer1Info();
                }
            }
        } catch (InvalidMoveException ex) {
            JOptionPane.showMessageDialog(MancalaGUI.this, "Invalid move. Please try again.");
        }
    }


    private void displayWinner() {
        try {
            Player winner = mancalaGame.getWinner();
            if(mancalaGame.getBoard() instanceof KalahRules){
                winner.getUserProfile().kalahWins();
                if(winner==mancalaGame.getPlayers().get(0)){
                    mancalaGame.getPlayers().get(1).getUserProfile().kalahLoss();
                }else{
                    mancalaGame.getPlayers().get(0).getUserProfile().kalahLoss();
                }
            }else{
                winner.getUserProfile().ayoWins();
                if(winner==mancalaGame.getPlayers().get(0)){
                    mancalaGame.getPlayers().get(1).getUserProfile().ayoLoss();
                }else{
                    mancalaGame.getPlayers().get(0).getUserProfile().ayoLoss();
                }
            }
            if (winner != null) {
                JOptionPane.showMessageDialog(MancalaGUI.this, "Game over! " + winner.getName() + " wins!");
                updatePlayer1Info();
                updatePlayer2Info();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void switchPlayer() {
        if (mancalaGame.getCurrentPlayer() == mancalaGame.getPlayers().get(0)) {
            mancalaGame.setCurrentPlayer(mancalaGame.getPlayers().get(1));
        } else {
            mancalaGame.setCurrentPlayer(mancalaGame.getPlayers().get(0));
        }
    }

    private void updateUI() {
        // Update pit labels based on the game state
        for (int i = 12; i >= 7; i--) {
            pits[i].setText("Pit " + i + ": " + mancalaGame.getBoard().getNumStones(i));
        }

        for (int i = 1; i <= 6; i++) {
            pits[i].setText("Pit " + i + ": " + mancalaGame.getBoard().getNumStones(i));
        }

        JLabel player1Store = new JLabel("Player 1 Store: " + mancalaGame.getBoard().getStoreCount(1));
        JLabel player2Store = new JLabel("Player 2 Store: " + mancalaGame.getBoard().getStoreCount(2));

        store1Panel.removeAll();
        store2Panel.removeAll();

        store1Panel.add(player1Store);

        store2Panel.add(player2Store);

        store1Panel.revalidate();
        store1Panel.repaint();

        store2Panel.revalidate();
        store2Panel.repaint();
    }

    private static void startNewGame(){
        GameRules chosenRules = chooseGame();
        MancalaGame mancalaGame = new MancalaGame();
        mancalaGame.setBoard(chosenRules);
        MancalaGUI gui = new MancalaGUI(mancalaGame);
    }

    public static void main(String[] args) {
        startNewGame();
    }
}