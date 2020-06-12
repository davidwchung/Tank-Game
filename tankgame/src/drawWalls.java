import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class drawWalls{

    private BufferedImage img;
    private ArrayList<Rectangle> walls = new ArrayList<>();

    drawWalls(BufferedImage img, ArrayList<Rectangle> walls) {

        this.img = img;
        this.walls = walls;

    }

    void drawImage(Graphics g) {

        for (int i = 0; i < walls.size(); i++) {

            Graphics2D g2d = (Graphics2D) g;
            int x = (int) walls.get(i).getX();
            int y = (int) walls.get(i).getY();
            g2d.drawImage(this.img, x, y, null);
        }
    }

}
