import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet{


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int width;
    private int height;
    private int angle;
    private int damage;
    private boolean show;

    private final int R = 3;

    private BufferedImage img;

    Bullet(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.width = 20;
        this.height = 10;
        this.img = img;
        this.angle = angle;
        this.damage = 50;
        this.show = true;

    }

    public void update() {

        this.moveForwards();

    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    private void checkBorder() {
        if (x < 30) {
            setShow(false);
        }
        if (x >= TRE.SCREEN_WIDTH - 70) {
            setShow(false);
        }
        if (y < 30) {
            setShow(false);
        }
        if (y >= TRE.SCREEN_HEIGHT - 70) {
            setShow(false);
        }
    }

    private void setShow(boolean show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    void drawImage(Graphics g) {
        if (show) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
