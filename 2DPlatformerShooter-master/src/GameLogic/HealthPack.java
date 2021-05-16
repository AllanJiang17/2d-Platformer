package GameLogic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class HealthPack extends Sprite{

    private int heal;
    private int counter;
    private int coolDown;
    private boolean used = false;

    public HealthPack(int x, int y, int width, int height, int healAmount, int coolDown) {
        super(x, y, width, height);
        heal = healAmount;
        this.coolDown = coolDown;
        String basepath = new File("").getAbsolutePath();
        try {
            image = ImageIO.read(new File(basepath + "\\src\\Resources\\healthPack.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public HealthPack() {
        super(0,0,0,0);
    }

    @Override
    public void update(GameManager manager) {
        if (used) {
            counter--;
        }
        if (counter == 0) {
            used = false;
        }

    }

    @Override
    public void render(Graphics g) {
        if (!used) {
            g.drawImage(image, xLoc, yLoc, width, height, null);
        }
    }

    public int getHp() {
        return heal;
    }

    public void takeHealthPack() {
        used = true;
        counter = coolDown;
    }

    public boolean checkUsed() {
        return used;
    }

    public void switchLoc() {
        yLoc = 340;

        Random rand = new Random();
        int randInt = rand.nextInt(621);

        xLoc = randInt;

        int randInt1 = rand.nextInt(2);

        if(300 <= xLoc && xLoc < 400) {
            if (randInt1 == 1) {
                yLoc = 340;
            } else {
                yLoc = 200;
            }
        } else if (180 <= xLoc && xLoc <= 280) {
            if (randInt1 == 1) {
                yLoc = 340;
            } else {
                yLoc = 270;
            }
        } else if (400 <= xLoc && xLoc <= 500) {
            yLoc = 300;
        }
        hitBox.setRect(xLoc,yLoc,width,height);
    }
}

