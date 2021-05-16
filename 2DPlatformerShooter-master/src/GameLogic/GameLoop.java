package GameLogic;

import UI.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameLoop extends Canvas implements Runnable {

    private static final int WIDTH = 640;
    private static final int HEIGHT = WIDTH / 12 * 9;

    private int updateNum;

    public Thread thread;
    public boolean running = false;

    private BufferedImage backgroundImage;

    private final GameManager gameManager;

    //in constructor is where we add elements to the game manager

    public GameLoop(boolean s, GameWindow gameWindow) {

        gameManager = new GameManager(gameWindow);
        if (s) {
            gameManager.addPlayer1(new Player(30, 30, 20, 40));
        } else {
            gameManager.addPlayer1(new Player(30, 30, 20, 40));
            gameManager.addPlayer2(new Player(100, 100, 20, 40));
        }
        setUpGame();
        this.addKeyListener(new KeyHandler(gameManager));
        //new UI.GameWindow(WIDTH, HEIGHT, TITLE, this);
    }

    private void setUpGame() {
        int WALL_WIDTH = 100;
        int WALL_HEIGHT = 25;
        for (int i = 0; i < WIDTH; i += WALL_WIDTH - 20) {
            gameManager.addSprite(new Platform(i, 360, WALL_WIDTH, WALL_HEIGHT));
        }

        gameManager.addSprite(new Boundary(0,0, 10, HEIGHT));
        gameManager.addSprite(new Boundary(WIDTH - 20, 0, 5, HEIGHT));

        gameManager.addSprite(new Platform(300, 220, WALL_WIDTH, WALL_HEIGHT));
        gameManager.addSprite(new Platform(180, 290, WALL_WIDTH, WALL_HEIGHT));
        gameManager.addSprite(new Platform(400, 320, WALL_WIDTH, WALL_HEIGHT));

        gameManager.addSprite(new Weapon(310, 200, 30, 8, 20.0, 100, 15.0, 5, 20, 3));
        gameManager.addSprite(new HealthPack(350, 340, 20, 20, 30, 200));

        String basepath = new File("").getAbsolutePath();

        try {
            backgroundImage = ImageIO.read(new File(basepath + "\\src\\Resources\\background.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //other game loop, unrestricted frame rate, however highly taxing on the CPU
    //doesn't work with current implementation of resume, likely due to the dependence on timings
//   @Override
//    public void run() {
//        this.requestFocus();
//        long lastTime = System.nanoTime();
//        double numTicks = 60.0;
//        double timePerTick = 1000000000 / numTicks; //divide 1 sec in nano secs by 60 ticks;
//        double delta = 0;
//        long timer = System.currentTimeMillis();
//        int frames = 0;
//
//        while(running) {
//            long now = System.nanoTime();
//            delta += (now - lastTime) / timePerTick; // this controls it so that we only call update 60 times in 1 second as long as rendering and ticking do not take 500 ms
//            lastTime = now;
//            while (delta >= 1) { // if delta less than 1 (amount of time it took to render and update previous) we do not update which allows for frames to render uncapped
//                update();
//                delta -= 1;
//            }
//
//            if (running) {
//                render();
//            }
//
//            frames++;
//
//            if (System.currentTimeMillis() - timer > 1000) {
//                timer += 1000;
//                System.out.println("FPS: " + frames + "  " + "Updates: " + updateNum);
//                frames = 0;
//                updateNum = 0;
//            }
//        }
//        stop();
//
//    }


    // restricted frame rate and update time, game engine is effected by frame rate
    @Override
    public void run() {
        this.requestFocus();
        long now;
        long updateTime;
        long wait;
        int frames = 0;
        long timer = System.currentTimeMillis();

        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (running) {
            now = System.nanoTime();

            update();
            render();

            frames++;

            updateTime = System.nanoTime() - now;
            wait = (OPTIMAL_TIME - updateTime) / 1000000;

            if (wait > 0) {

                try {
                    thread.sleep(wait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + "  " + "Updates: " + updateNum);
                frames = 0;
                updateNum = 0;
            }

        }
    }

    private void render() {
        this.setVisible(true);

        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = buffer.getDrawGraphics();

        //g.setColor(Color.blue);
        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);

        //g.fillRect(0,0, WIDTH, HEIGHT);

        gameManager.renderEach(g);

        g.dispose();
        buffer.show();


//        Graphics g = getGraphics();
//
//        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
//        gameManager.renderEach(g);

    }

    private void update() {
        gameManager.updateEach();
        updateNum++;
    }

    public void resetPlayer() {
       gameManager.resetPlayers();
    }
}
