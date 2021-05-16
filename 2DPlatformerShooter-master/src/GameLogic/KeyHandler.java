package GameLogic;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

    private GameManager manager;

    public KeyHandler(GameManager gameManager) {
        manager = gameManager;
    }

    public void keyPressed(KeyEvent e) {
        manager.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        manager.keyReleased(e);
    }
}
