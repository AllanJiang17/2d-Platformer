package GameLogic;

import UI.GameWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameManager {

    private final GameWindow gameWindow;
    public ArrayList<Sprite> sprites = new ArrayList<>();
    public Player player1;
    public Player player2;
    public ArrayList<Sprite> toDelete = new ArrayList<>();
    public ArrayList<Sprite> toAdd = new ArrayList<>();

    //private boolean playerShoot1 = false; // old projectile handling
    //private boolean playerShoot2 = false;
    //private static final int COOLDOWN = 5;
    //private int counter1 = COOLDOWN;
    //private int counter2 = COOLDOWN;


    public GameManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }


    public void updateEach() {

        // projectileHandler();

        for (Sprite element : sprites) {
            element.update(this);
        }
        addElements();
        clearColliders();
    }

    private void addElements() {
        sprites.addAll(toAdd);
        toAdd = new ArrayList<>();
    }

    public void renderEach(Graphics g) {

        for (Sprite element : sprites) {
            element.render(g);
        }
    }

    public void clearColliders() {
        for (Sprite sprite : toDelete) {
            sprites.remove(sprite);
        }
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void healthUpdate() {
        if(player2 == null) {
            gameWindow.updateHealth(player1.HP, 1);
        } else {
            gameWindow.updateHealth(player1.HP, player2.HP);
        }
    }

    public void ammoUpdate() {
        if (player2 == null) {
            gameWindow.updateAmmo(player1.getWeapon().getAmmo(), 0);
        } else {
            gameWindow.updateAmmo(player1.getWeapon().getAmmo(), player2.getWeapon().getAmmo());
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            player1.keyUp = true;
        } else if (e.getKeyChar() == 'a') {
            player1.keyLeft = true;
            player1.direction = false;
        } else if (e.getKeyChar() == 's') {
            player1.keyDown = true;
        } else if (e.getKeyChar() == 'd') {
            player1.keyRight = true;
            player1.direction = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player2.keyUp = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player2.keyLeft = true;
            player2.direction = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player2.keyDown = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player2.keyRight = true;
            player2.direction = true;
        }
        if (e.getKeyChar() == 'j') {
            player1.getWeapon().shoot();
        }
        if (e.getKeyChar() == '2') {
            player2.getWeapon().shoot();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameWindow.pressEsc();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            player1.keyUp = false;
        } else if (e.getKeyChar() == 'a') {
            player1.keyLeft = false;
        } else if (e.getKeyChar() == 's') {
            player1.keyDown = false;
        } else if (e.getKeyChar() == 'd') {
            player1.keyRight = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player2.keyUp = false; //need to check for null field
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player2.keyLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player2.keyDown = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player2.keyRight = false;
        }
        if (e.getKeyChar() == 'j') {
            player1.getWeapon().noShoot();
        }

        if (e.getKeyChar() == '2') {
            player2.getWeapon().noShoot();
        }
    }

    public void addPlayer1(Player player) {
        this.player1 = player;
        sprites.add(player);
    }

    public void addPlayer2(Player player) {
        this.player2 = player;
        sprites.add(player);
    }

    public void resetPlayers() {

        player1.shoot = false;
        if (player2 != null) {
            player2.shoot = false;
        }

        player1.keyDown = false;
        player1.keyUp = false;
        player1.keyLeft = false;
        player1.keyRight = false;

        if (player2 != null) {
            player2.keyDown = false;
            player2.keyUp = false;
            player2.keyLeft = false;
            player2.keyRight = false;
        }
    }
}
