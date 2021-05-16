package GameLogic;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Sprite {
    
    protected int xLoc;
    protected int yLoc;
    protected double xVel;
    protected double yVel;

    protected int height; // used to draw the image
    protected int width;

    protected Rectangle hitBox; //usually same width and height however may choose to use smaller hitBox

    protected BufferedImage image;
    
    public Sprite(int x, int y, int width, int height) {
        xLoc = x;
        yLoc = y;

        this.height = height;
        this.width = width;
        
        xVel = 0;
        yVel = 0;

        hitBox = new Rectangle(x, y, width, height);
    }

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public double getxVel() {
        return xVel;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public abstract void update(GameManager manager);

    public abstract void render(Graphics g);
}
