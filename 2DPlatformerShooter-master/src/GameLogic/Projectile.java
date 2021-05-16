package GameLogic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Projectile extends Sprite{

    private GameManager gameState;

    private int damage;

    private Player player;

    private boolean direction;

    private double knockBack;

    //true is positive (right), false is negative (left)
    public Projectile(int x, int y, boolean direction, int damage, double knockBack, double bulletSpeed, Player player) { //boolean determines which player projectile belongs to
        super(x,y, 15, 10);
        this.knockBack = knockBack;
        this.player = player;
        this.damage = damage;
        this.direction = direction;
        if (direction) {
            xVel = bulletSpeed;
        } else {
            xVel = bulletSpeed * -1;
        }
        String basepath = new File("").getAbsolutePath();
        try {
            image = ImageIO.read(new File(basepath+ "\\src\\Resources\\fireball.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Projectile() {
        super(0,0,0,0);
    }

    @Override
    public void update(GameManager manager) {
        gameState = manager;
        handleCollision();
        xLoc += xVel;
        hitBox.x = xLoc;
    }

    private void handleCollision() {

        HealthPack h = new HealthPack();

        for (Sprite element : gameState.sprites) {
            if (element.getClass() != h.getClass()) {
                if (element.hitBox.intersects(hitBox)) {
                    if (!element.equals(this) && element.getClass() != this.getClass() && player.equals(gameState.player1) && !element.equals(gameState.player1)) {
                        xVel = 0;
                        gameState.toDelete.add(this);
                        if (element.equals(gameState.player2)) {
                            gameState.player2.takeDamage(damage);
                            gameState.healthUpdate();
                            if (direction) {
                                gameState.player2.knockBack(knockBack);
                            } else {
                                gameState.player2.knockBack(knockBack * -1);
                            }
                        }
                    } else if (!element.equals(this) && element.getClass() != this.getClass() && player.equals(gameState.player2) && !element.equals(gameState.player2)) {
                        xVel = 0;
                        gameState.toDelete.add(this);
                        if (element.equals(gameState.player1)) {
                            gameState.player1.takeDamage(damage);
                            gameState.healthUpdate();
                            if (direction) {
                                gameState.player1.knockBack(knockBack);
                            } else {
                                gameState.player1.knockBack(knockBack * -1);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        //g.setColor(Color.red);
        //g.fillRect(xLoc, yLoc, width, height);

        g.drawImage(image, xLoc, yLoc, width, height, null);
    }
}
