import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class PowerUp{


    private int x;
    private int y;
    private int angle;
    private int width;
    private int height;
    private boolean taken;

    private BufferedImage img;

    PowerUp(int x, int y, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width = 40;
        this.height = 40;
        this.img = img;

    }

    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

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

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean getTaken() {
        return taken;
    }





}
