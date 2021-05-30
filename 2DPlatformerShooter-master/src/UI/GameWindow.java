package UI;

import GameLogic.GameLoop;
import Persistence.JsonReader;
import Persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static final int WIDTH = 640;
    private static final int HEIGHT = WIDTH / 12 * 9;
    private static final String TITLE = "Platformer Game";

    private mainMenuPanel mainMenuPanel;
    private GameLoop gameLoop;
    private EscapeMenu escape;

    private Container con1; //player1 health
    private Container con2; //player1 ammo
    private Container con3; //player2 health
    private Container con4; //player2 ammo

    private JPanel healthBar;
    private JProgressBar healthPro;

    private JPanel healthBar1;
    private JProgressBar healthPro1;

    private JPanel ammoBar;
    private JLabel ammoCount;

    private JPanel ammoBar1;
    private JLabel ammoCount1;

    public GameWindow() {
        this.setTitle(TITLE);

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        mainMenuPanel = new mainMenuPanel(this);
        this.add(mainMenuPanel);
        this.setVisible(true);
    }

    public void startGame(boolean s) {
        this.remove(mainMenuPanel);
        //mainMenuPanel = null;
        this.repaint();
        if (s) {
            gameLoop = new GameLoop(true, this);
            setUpCon(true);
            setupAmmo(true);
        } else {
            gameLoop = new GameLoop(false, this);
            setUpCon(false);
            setupAmmo(false);
        }
        this.add(gameLoop);
        this.setVisible(true);
        gameLoop.start();
    }

    private void setUpCon(boolean s) {
        con1 = this.getContentPane();
        healthBar = new JPanel();
        healthBar.setBounds(10,10,100,20);
        // healthBar.setBorder(BorderFactory.createEtchedBorder());
        healthBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        con1.add(healthBar);

        healthPro = new JProgressBar(0, 100);
        healthPro.setPreferredSize(new Dimension(100,20));
        healthPro.setValue(100);
        healthPro.setStringPainted(true);
        healthBar.add(healthPro);

        con3 = null;

        if(s == false) {
            con3 = this.getContentPane();
            healthBar1 = new JPanel();
            healthBar1.setBounds(515,10,100,20);
            healthBar1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            con3.add(healthBar1);

            healthPro1 = new JProgressBar(0, 100);
            healthPro1.setPreferredSize(new Dimension(100,20));
            healthPro1.setValue(100);
            healthPro1.setStringPainted(true);
            healthBar1.add(healthPro1);
        }
    }

    private void setupAmmo(boolean b) {
        con2 = this.getContentPane();
        ammoBar = new JPanel();
        ammoBar.setBounds(10,40,100,20);
        ammoBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        con2.add(ammoBar);

        ammoCount = new JLabel();
        ammoCount.setSize(new Dimension(100,20));
        ammoCount.setText("100");
        ammoBar.add(ammoCount);

        con4 = null;

        if(b == false) {
            con4 = this.getContentPane();
            ammoBar1 = new JPanel();
            ammoBar1.setBounds(515,40,100,20);
            ammoBar1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            con4.add(ammoBar1);

            ammoCount1 = new JLabel();
            ammoCount1.setSize(new Dimension(100,20));
            ammoCount1.setText("100");
            ammoBar1.add(ammoCount1);
        }
    }

    public void pressEsc() {
        con1.remove(healthBar);
        con1.validate();
        if(con3 != null) {
            con3.remove(healthBar1);
            con3.validate();
        }
        con2.remove(ammoBar);
        if(con4 != null) {
            con4.remove(ammoBar1);
            con4.validate();
        }
        gameLoop.thread.suspend(); // basically a block
        this.remove(gameLoop);
        gameLoop.resetPlayer();
        this.repaint();
        escape = new EscapeMenu(this);
        this.add(escape);
        this.setVisible(true);
    }

    public void mainMenu() {
        gameLoop.thread.resume();
        gameLoop.stop();
        //   gameLoop = null;
        this.remove(escape);
        // escape = null;
        this.repaint();
        mainMenuPanel = new mainMenuPanel(this);
        this.add(mainMenuPanel);
        this.setVisible(true);
    }

    public void resumeGame() {
        con1.add(healthBar);
        if (con3 != null) {
            con3.add(healthBar1);
        }
        con2.add(ammoBar);
        if(con4 != null) {
            con4.add(ammoBar1);
        }
        this.remove(escape);
        // escape = null;
        this.repaint();
        this.add(gameLoop);
        this.setVisible(true);
        gameLoop.requestFocus();
        gameLoop.thread.resume();
    }

    public void updateHealth(int hp, int hp1) {
        if(healthPro1 == null) {
            healthPro.setValue(hp);
        } else {
            healthPro.setValue(hp);
            healthPro1.setValue(hp1);
        }
        if(hp <= 0) {
            playerWon(false);
        }
        if(hp1 <= 0) {
            playerWon(true);
        }
    }

    public void updateAmmo(int ammo, int ammo1) {
        ammoCount.setText(Integer.toString(ammo));
        if(ammoCount1 != null) {
            ammoCount1.setText(Integer.toString(ammo1));
        }
    }

    private void playerWon(boolean s) {
        InternalFrame frame = new InternalFrame();
        frame.setBounds(500, 200, 500, 300);
        frame.setVisible(true);
        if (s) {
            JOptionPane.showMessageDialog(frame, "Player1 Won");
            returnToMenu();
        } else {
            JOptionPane.showMessageDialog(frame, "Player2 Won");
            returnToMenu();
        }

    }

    private void returnToMenu() {
        con1.remove(healthBar);
        if(con3 != null) {
            con3.remove(healthBar1);
        }
        con2.remove(ammoBar);
        if(con4 != null) {
            con4.remove(ammoBar1);
        }
        this.remove(gameLoop);
        this.repaint();
        mainMenuPanel = new mainMenuPanel(this);
        this.add(mainMenuPanel);
        this.setVisible(true);
    }

    public void load() {
        gameLoop.loadPlayers();
    }

    public void save() {
        gameLoop.savePlayers();
    }
}

