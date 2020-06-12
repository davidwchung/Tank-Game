import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Tank{

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private int width;
    private int height;
    private int health;
    private int lives;
    private long lastShotTime;
    private long currentTime;
    private long timeSinceLastShot;
    private int ammo = 0;
    private boolean isDead;

    private Bullet[] bullets = new Bullet[30];
    BufferedImage rocket = null;
    BufferedImage livesimg = null;

    private final int R = 2;
    private final int ROTATIONSPEED = 4;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.width = 50;
        this.height = 50;
        this.img = img;
        this.angle = angle;
        this.health = 100;
        this.lives = 3;
        this.isDead = false;
        getRocketImage();
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.shootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.shootPressed = false;
    }

    public void getRocketImage() {
        try {

            // Use to pull in resources when running locally
//            rocket = read(new File("Insert File Location of: Rocket.png"));
            //TODO Add file location of Rocket.png
            rocket = read(new File("Insert File Location of: Rocket.png"));
//            livesimg = read(new File("Insert File Location of: Lives.png"));
            //TODO Add file location of Lives.png
            livesimg = read(new File("Insert File Location of: Lives.png"));

            // Use to pull in resources when running jar
//            rocket = read(new File("resources\\Rocket.png"));
//            livesimg = read(new File("resources\\Lives.png"));

        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.shootPressed) {
            this.shootRocket();
        }

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
            vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            x += vx;
            y += vy;
            checkBorder();
    }

    private void shootRocket() {

        currentTime = System.nanoTime();

        timeSinceLastShot = (currentTime - lastShotTime) / 1000000;

        // If loop prevents multiple bullets from being fired from button press
        if (timeSinceLastShot > 50) {

            if (ammo < 30) {
                bullets[ammo] = new Bullet(
//                        this.x + 25 + (25 * (int) Math.round(R * Math.cos(Math.toRadians(angle)))),
                        this.x + 25,
                        //this.y + 25 + ( 25 * (int) Math.round(R * Math.sin(Math.toRadians(angle)))),
                        this.y + 25,
                        0, 0, angle, rocket);
                ammo++;
            }
        }

        lastShotTime = System.nanoTime();
    }

    public Bullet[] getBullets() {
        return bullets;
    }

    public void hit() {
        if (health == 100) {
            health = 50;
        }
        else if (health == 50 && lives > 0){
            lives--;
            health = 100;
        }
        else if (health == 50 && lives == 0) {
            health = 0;
            isDead = true;
        }
    }

    private void checkBorder() {
        if (x < 50) {
            x = 50;
        }
        if (x >= TRE.SCREEN_WIDTH - 100) {
            x = TRE.SCREEN_WIDTH - 100;
        }
        if (y < 50) {
            y = 50;
        }
        if (y >= TRE.SCREEN_HEIGHT - 100) {
            y = TRE.SCREEN_HEIGHT - 100;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        for (int i = 0; i < lives; i++) {
            g2d.drawImage(this.livesimg, this.x + (i * 15), this.y - 20, null);
        }

        if (health == 100) {
            g2d.setColor(Color.green);
            g2d.fillRect(this.x, this.y + 55, 50, 10);
            g2d.setColor(Color.BLACK);
        }

        else if (health == 50) {
            g2d.setColor(Color.green);
            g2d.fillRect(this.x, this.y + 55, 25, 10);
            g2d.setColor(Color.red);
            g2d.fillRect(this.x + 25, this.y + 55, 25, 10);
            g2d.setColor(Color.BLACK);
        }

        else if (health == 0) {
            g2d.setColor(Color.red);
            g2d.fillRect(this.x, this.y + 55, 50, 10);
            g2d.setColor(Color.BLACK);
        }

    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getVX() {
        return (int) Math.round(R * Math.cos(Math.toRadians(angle)));
    }

    public int getVY() {
        return vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getAmmo() {
        return 30 - ammo;
    }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
    }

    public void powerUp() {
        lives = lives + 1;
    }

    // True = dead, False = alive
    public boolean getStatus() {
        return isDead;
    }

}
