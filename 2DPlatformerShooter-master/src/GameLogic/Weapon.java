package GameLogic;

import org.w3c.dom.css.Rect;

import java.awt.*;

public class Weapon extends Sprite {

    private double knockBack;
    private int ammo;
    private double bulletSpeed;
    private int damage;
    private int fireRate;
    private boolean shoot;
    private Player player;
    private GameManager gameState;
    private double maxSpeed;

    private boolean hasOwner;

    private int counter;

    public Weapon(int xLoc, int yLoc, int width, int height, double knockBack, int ammo, double bulletSpeed, int damage, int fireRate, double maxSpeed) {
        super(xLoc, yLoc, width, height);
        this.ammo = ammo;
        this.knockBack = knockBack;
        this.bulletSpeed= bulletSpeed;
        this.damage = damage;
        this.fireRate = fireRate;
        counter = fireRate;
        hasOwner = false;
        this.maxSpeed = maxSpeed;
        hitBox = new Rectangle(xLoc, yLoc, 20, 20);

    }

    public Weapon(int x, int width, int height, double knockBack, int ammo, double bulletSpeed, int damage, int fireRate, Player player, double maxSpeed) {
        super(0, 0, width, height);
        this.player = player;
        this.ammo = ammo;
        this.knockBack = knockBack;
        this.bulletSpeed= bulletSpeed;
        this.damage = damage;
        this.fireRate = fireRate;
        this.maxSpeed = maxSpeed;
        counter = fireRate;
        hasOwner = true;
        hitBox = new Rectangle(xLoc, yLoc, 20, 20);
    }

    public Weapon() {
        super(0,0,0,0);
    }


    @Override
    public void update(GameManager manager) {
        gameState = manager;

        if (player != null) {
            projectileHandler();
        }
    }


    //need to modify projectile spawns to better
    private void projectileHandler() {
        if (counter != 0) {
            counter--;
        }
        gameState.ammoUpdate();
        if (shoot && counter == 0 && ammo > 0 && player.getHP() > 0) {
            Projectile projectile;
            if (player.getDirection()) {
                projectile = new Projectile(player.getxLoc() + player.getWidth(),
                        player.getyLoc() + player.getHeight() / 2, true, damage, knockBack, bulletSpeed, player);
            } else {
                projectile = new Projectile(player.getxLoc() -  player.getWidth()/2,
                        player.getyLoc() + player.getHeight() / 2, false, damage, knockBack, bulletSpeed, player);
            }
            gameState.toAdd.add(projectile);
            counter = fireRate;
            ammo--;
        }
    }

    //causing runtime issues, should probably let player class handle picking up
//    private void handlePickUp() {
//        Player p = new Player(0,0,0,0);
//
//        for (Sprite element : gameState.sprites) {
//            if (element.hitBox.intersects(hitBox)) {
//                if (!element.equals(this) && element.getClass() != this.getClass() && element.getClass() == p.getClass()) {
//                    Player temp = (Player) element;
//                    gameState.toDelete.add(temp.getWeapon());
//                    temp.getWeapon().removePlayer();
//                    temp.setWeapon(this);
//                    //temp.refreshedWeapon(); // may not need to add weapon second time since it is already in the update list
//                    player = temp;
//                    }
//                }
//            }
//        }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        if (player == null) {
            g.fillRect(xLoc, yLoc, 20, 20);
        } else {
            if (player.getDirection()) {
                g.fillRect(player.getxLoc() + 6*player.getWidth()/8, player.getyLoc() + player.getHeight()/2, width, height);
            } else {
                g.fillRect(player.getxLoc() - 6*player.getWidth()/8, player.getyLoc() + player.getHeight()/2, width, height);
            }
        }

    }

    public int getAmmo() {
        return ammo;
    }


    public void shoot() {
        shoot = true;
    }

    public void noShoot() {
        shoot = false;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean hasOwner() {
        return hasOwner;
    }

    public void setHasOwner() {
        hasOwner = true;
    }

    public void setAmmo(int i) {
        ammo = i;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
