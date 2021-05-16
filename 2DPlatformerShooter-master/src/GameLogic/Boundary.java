package GameLogic;

import java.awt.*;

public class Boundary extends Sprite {
    public Boundary(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update(GameManager manager) {
        //do nothing
    }

    @Override
    public void render(Graphics g) {
       g.setColor(Color.black);
       g.fillRect(xLoc,yLoc, width, height);
    }
}

