/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class TRE extends JPanel  {


    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jf;
    private Tank t1;
    private Tank t2;
    private drawWalls drawWall;
    private PowerUp powerUp;
    private Bullet[] t1Bullets = new Bullet[30];
    private Bullet[] t2Bullets = new Bullet[30];
    private Collision collision;
    private boolean powerUpTaken;
    private boolean gameOver;
    private BufferedImage gameover = null;
    private BufferedImage destructibleWall = null;
    private TileLayer layer = new TileLayer(new int[][] {

            // 0 = Floor
            // 1 = Solid Wall
            // 2 = Destructible Wall

            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,0,0,2,1,1,1,1,2,0,0,0,0,0,2,2,2,2,0,0,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,1},
            {1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,2,2,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,2,2,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1},
            {1,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
            {1,0,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}}

    );

    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE();
        trex.init();
        try {

            while (!trex.gameOver) {

                // Checks both tanks are still alive
                if (trex.t1.getStatus() || trex.t2.getStatus()) {
                    trex.gameOver = true;
                }

                // Update Tank position
                trex.t1.update();
                trex.t2.update();

                trex.t1Bullets = trex.t1.getBullets();
                trex.t2Bullets = trex.t2.getBullets();

                // Updating bullet location
                for (int i = 0; i < trex.t1Bullets.length; i++) {
                    if (trex.t1Bullets[i] != null) {
                        trex.t1Bullets[i].update();
                    }
                }

                for (int i = 0; i < trex.t2Bullets.length; i++) {
                    if (trex.t2Bullets[i] != null) {
                        trex.t2Bullets[i].update();
                    }
                }

                // Collision Checking
                trex.collision.playerVBullet(trex.t1, trex.t2, trex.t1Bullets, trex.t2Bullets);
                trex.collision.bulletVsWall(trex.t1Bullets, trex.t2Bullets);
                trex.collision.playerVsWall(trex.t1, trex.t2);
                trex.powerUpTaken = trex.collision.playerVsPowerUp(trex.t1, trex.t2, trex.powerUp, trex.powerUpTaken);

                // Draw everything
                trex.repaint();
                System.out.println(trex.t1);
                System.out.println(trex.t2);
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

    }

    private void init() {
        this.jf = new JFrame("Tank Game");
        this.world = new BufferedImage(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        gameOver = false;

        BufferedImage t1img = null, t2img = null, pimg = null;

        try {

            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */

            // Use to pull in resources when running locally
//            t1img = read(new File("Insert File Location of: tank1.png"));
            // TODO Insert File Location of tank1.png
            t1img = read(new File("Insert File Location of: tank1.png"));
//            t2img = read(new File("Insert File Location of: tank1.png"));
            // TODO Insert File Location of tank1.png
            t2img = read(new File("Insert File Location of: tank1.png"));
//            pimg = read(new File("Insert File Location of: Life.png"));
            // TODO Insert File Location of Life.png
            pimg = read(new File("Insert File Location of: Life.png"));
//            gameover = ImageIO.read(new File("Insert File Location of: GameOver.jpg"));
            // TODO Insert File Location of GameOver.jpg
            gameover = ImageIO.read(new File("Insert File Location of: GameOver.jpg"));
//            destructibleWall = ImageIO.read(new File("Insert File Location of: DestructibleWallCropped.png"));
            // TODO Insert File Location of DestructibleWallCropped.png
            destructibleWall = ImageIO.read(new File("Insert File Location of: DestructibleWallCropped.png"));

            // Use to pull in resources when running jar
//            t1img = read(new File("resources\\tank1.png"));
//            t2img = read(new File("resources\\tank1.png"));
//            pimg = read(new File("resources\\Life.png"));
//            gameover = ImageIO.read(new File("resources\\GameOver.jpg"));
//            destructibleWall = ImageIO.read(new File("resources\\DestructibleWallCropped.png"));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        t1 = new Tank(100, 100, 0, 0, 0, t1img);
        t2 = new Tank(SCREEN_WIDTH - 150, SCREEN_HEIGHT - 150, 0, 0, 180, t2img);

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        for (int i = 0; i < t1Bullets.length; i++) {
            t1Bullets[i] = null;
        }
        for (int i = 0; i < t2Bullets.length; i++) {
            t2Bullets[i] = null;
        }

        powerUp = new PowerUp (620, 460, 0, pimg);
        collision = new Collision(layer);
        drawWall = new drawWalls(destructibleWall, collision.getDestructibleWalls());
        powerUpTaken = false;

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);

        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);

        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();

        super.paintComponent(g2);

        // Only runs while both tanks are still alive
        if (!gameOver) {

            // Draw Background
            layer.DrawLayer(buffer);

            // Draw PowerUp
            if (powerUpTaken == false) {
                this.powerUp.drawImage(buffer);
            }

            this.drawWall.drawImage(buffer);

            // Drawing tanks
            this.t1.drawImage(buffer);
            this.t2.drawImage(buffer);

            // Drawing bullets from Tank 1
            for (int i = 0; i < t1Bullets.length; i++) {
                if (this.t1Bullets[i] != null) {
                    this.t1Bullets[i].drawImage(buffer);
                }
            }

            // Drawing bullets from Tank 2
            for (int i = 0; i < t2Bullets.length; i++) {
                if (this.t2Bullets[i] != null) {
                    this.t2Bullets[i].drawImage(buffer);
                }
            }

            g2.drawImage(world,0,0,null);

            // Left split screen
            if (t1.getX() <= SCREEN_WIDTH * 0.25) {
                g2.drawImage(world.getSubimage(0, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT), 0, 0, null);
            } else if (t1.getX() >= SCREEN_WIDTH * 0.75) {
                g2.drawImage(world.getSubimage(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT), 0, 0, null);
            } else {
                g2.drawImage(world.getSubimage(t1.getX() - 320, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT), 0, 0, null);
            }

            // Right split screen
            if (t2.getX() <= SCREEN_WIDTH * 0.25) {
                g2.drawImage(world.getSubimage(0, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT), SCREEN_WIDTH / 2, 0, null);
            } else if (t2.getX() >= SCREEN_WIDTH * 0.75) {
                g2.drawImage(world.getSubimage(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT), SCREEN_WIDTH / 2, 0, null);
            } else {
                g2.drawImage(world.getSubimage(t2.getX() - 320, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT), SCREEN_WIDTH / 2, 0, null);
            }

            // Create MiniMap
            BufferedImage MiniMap = new BufferedImage(world.getWidth(), world.getHeight(), BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = new AffineTransform();
            at.scale(0.25, 0.25);
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            MiniMap = scaleOp.filter(world, MiniMap);

            // Draw MiniMap
            g2.drawImage(MiniMap, 480, 720, null);

            g2.setColor(Color.WHITE);

            // MiniMap Left Border
            g2.drawLine(478, 719, 478, 960);
            g2.drawLine(479, 719, 479, 960);

            //MiniMap Top Border
            g2.drawLine(478, 718, 802, 718);
            g2.drawLine(478, 719, 802, 719);

            //MiniMap Right Border
            g2.drawLine(801, 719, 801, 960);
            g2.drawLine(802, 719, 802, 960);

            // SplitScreen Border
            g2.drawLine(639, 0, 639, 719);
            g2.drawLine(640, 0, 640, 719);

            // Display Player 1's Tank Information
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("TimesRoman", Font.BOLD, 24));
            g2.drawString("Ammo: " + t1.getAmmo(), 310, 810);
            g2.drawString("Lives: " + t1.getLives(), 310, 840);
            g2.drawString("Health: " + t1.getHealth(), 310, 870);
            if (t1.getHealth() == 100) {
                g2.setColor(Color.green);
                g2.fillRect(310, 880, 150, 25);
                g2.setColor(Color.BLACK);
            } else if (t1.getHealth() == 50) {
                g2.setColor(Color.green);
                g2.fillRect(310, 880, 75, 25);
                g2.setColor(Color.red);
                g2.fillRect(385, 880, 75, 25);
                g2.setColor(Color.BLACK);
            } else {
                g2.setColor(Color.red);
                g2.fillRect(310, 880, 150, 25);
                g2.setColor(Color.BLACK);
            }

            // Display Player 2's Tank Information
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("TimesRoman", Font.BOLD, 24));
            g2.drawString("Ammo: " + t2.getAmmo(), 820, 810);
            g2.drawString("Lives: " + t2.getLives(), 820, 840);
            g2.drawString("Health: " + t2.getHealth(), 820, 870);
            if (t2.getHealth() == 100) {
                g2.setColor(Color.green);
                g2.fillRect(820, 880, 150, 25);
                g2.setColor(Color.BLACK);
            } else if (t2.getHealth() == 50) {
                g2.setColor(Color.green);
                g2.fillRect(820, 880, 75, 25);
                g2.setColor(Color.red);
                g2.fillRect(895, 880, 75, 25);
                g2.setColor(Color.BLACK);
            } else {
                g2.setColor(Color.red);
                g2.fillRect(820, 880, 150, 25);
                g2.setColor(Color.BLACK);
            }

//            int[][] test = layer.getMap();

//            for (int i = 1; i < test.length - 1; i++) {
//                for (int j = 1; j < test[0].length - 1; j++) {
//                    if (test[i][j] == 1) {
//                        g2.setColor(Color.green);
//                        g2.drawRect(j * 40, i * 40, 40,40);
//                    }
//                    else if (test[i][j] == 2) {
//                        g2.setColor(Color.red);
//                        g2.drawRect(j * 40, i * 40, 40,40);
//                    }
//                }
//            }

        }

        // Game Over Screen, only runs when one of the tanks has died
        else {
            g2.setColor(Color.black);
            g2.fillRect(0,0,1280,960);
            g2.drawImage(gameover, 215,240, null);
        }

    }


}
