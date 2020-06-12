import java.awt.Rectangle;
import java.util.ArrayList;

public class Collision {

    private int[][] map;
    private ArrayList<Rectangle> solidWalls = new ArrayList<>();
    private ArrayList<Rectangle> destructibleWalls = new ArrayList<>();


    public Collision(TileLayer layer) {

        map = layer.getMap();

        // Scanning the map and creating Rectangles where there are walls
        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[0].length - 1; j++) {
                if (map[i][j] == 1) {
                    solidWalls.add(new Rectangle(j * 40, i * 40, 40,40));
                }
                else if (map[i][j] == 2) {
                    destructibleWalls.add(new Rectangle(j * 40, i * 40, 40,40));
                }
            }
        }

    }

    public void playerVBullet (Tank t1, Tank t2, Bullet[] bullets1, Bullet[] bullets2) {

        Rectangle t1_box = new Rectangle(t1.getX(), t1.getY(), t1.getWidth(), t1.getHeight());
        Rectangle t2_box = new Rectangle(t2.getX(), t2.getY(), t2.getWidth(), t2.getHeight());

        // Two for loops so a tank's own bullet can't hit itself
        for (int i = 0; i < bullets2.length; i++) {

            if (bullets2[i] != null) {

                Rectangle b_box = new Rectangle(bullets2[i].getX(),bullets2[i].getY(), bullets2[i].getWidth(), bullets2[i].getHeight());

                if (t1_box.intersects(b_box) && !t1.getStatus()) {
                    t1.hit();
                    bullets2[i] = null;
                }
            }
        }

        for (int i = 0; i < bullets1.length; i++) {

            if (bullets1[i] != null) {
                Rectangle b_box = new Rectangle(bullets1[i].getX(),bullets1[i].getY(), bullets1[i].getWidth(), bullets1[i].getHeight());

                if (t2_box.intersects(b_box) && !t2.getStatus()) {
                    t2.hit();
                    bullets1[i] = null;
                }
            }
        }

    }

    public boolean playerVsPowerUp (Tank t1, Tank t2, PowerUp powerUp, Boolean taken) {

        Rectangle t1_box = new Rectangle(t1.getX(), t1.getY(), t1.getWidth(), t1.getHeight());
        Rectangle t2_box = new Rectangle(t2.getX(), t2.getY(), t2.getWidth(), t2.getHeight());
        Rectangle p_box = new Rectangle(powerUp.getX(), powerUp.getY(), powerUp.getWidth(), powerUp.getHeight());

        if (powerUp.getTaken() == false) {
            if (t1_box.intersects(p_box) && !t1.getStatus()) {
                t1.powerUp();
                taken = true;
                powerUp.setTaken(true);
            }
        }

        if (powerUp.getTaken() == false) {
            if (t2_box.intersects(p_box) && !t2.getStatus()) {
                t2.powerUp();
                taken = true;
                powerUp.setTaken(true);
            }
        }

        return taken;

    }

    public void playerVsWall(Tank t1, Tank t2) {

        Rectangle t1_box = new Rectangle(t1.getX(), t1.getY(), t1.getWidth(), t1.getHeight());
        Rectangle t2_box = new Rectangle(t2.getX(), t2.getY(), t2.getWidth(), t2.getHeight());

        // Throws tank backwards if runs into wall, but creates vibrating effect
        for (int i = 0; i < solidWalls.size(); i++) {
            if (t1_box.intersects(solidWalls.get(i)) && !t1.getStatus()) {
                t1.setX(t1.getX() - (2 * t1.getVX()));
                t1.setY(t1.getY() - (2 * t1.getVY()));
            }
        }

        for (int i = 0; i < destructibleWalls.size(); i++) {
            if (t1_box.intersects(destructibleWalls.get(i)) && !t1.getStatus()) {
                t1.setX(t1.getX() - (2 * t1.getVX()));
                t1.setY(t1.getY() - (2 * t1.getVY()));
            }
        }

        for (int i = 0; i < solidWalls.size(); i++) {
            if (t2_box.intersects(solidWalls.get(i)) && !t2.getStatus()) {
                t2.setX(t2.getX() - (2 * t2.getVX()));
                t2.setY(t2.getY() - (2 * t2.getVY()));
            }
        }

        for (int i = 0; i < destructibleWalls.size(); i++) {
            if (t2_box.intersects(destructibleWalls.get(i)) && !t2.getStatus()) {
                t2.setX(t2.getX() - (2 * t2.getVX()));
                t2.setY(t2.getY() - (2 * t2.getVY()));
            }
        }

    }

    public void bulletVsWall(Bullet[] bullets1, Bullet[] bullets2) {

        for (int i = 0; i < bullets1.length; i++) {

            if (bullets1[i] != null) {
                Rectangle b_box = new Rectangle(bullets1[i].getX(),bullets1[i].getY(), bullets1[i].getWidth(), bullets1[i].getHeight());

                for (int j = 0; j < solidWalls.size(); j++) {
                    if (b_box.intersects((solidWalls.get(j)))) {
                        bullets1[i] = null;
                    }
                }

                for (int j = 0; j < destructibleWalls.size(); j++) {
                    if (b_box.intersects((destructibleWalls.get(j)))) {
                        bullets1[i] = null;
                        destructibleWalls.remove(j);
                    }
                }
            }
        }

        for (int i = 0; i < bullets2.length; i++) {

            if (bullets2[i] != null) {
                Rectangle b_box = new Rectangle(bullets2[i].getX(),bullets2[i].getY(), bullets2[i].getWidth(), bullets2[i].getHeight());

                for (int j = 0; j < solidWalls.size(); j++) {
                    if (b_box.intersects((solidWalls.get(j)))) {
                        bullets2[i] = null;
                    }
                }

                for (int j = 0; j < destructibleWalls.size(); j++) {
                    if (b_box.intersects((destructibleWalls.get(j)))) {
                        bullets2[i] = null;
                        destructibleWalls.remove(j);
                    }
                }
            }
        }
    }

    public ArrayList<Rectangle> getSolidWalls() {
        return solidWalls;
    }

    public ArrayList<Rectangle> getDestructibleWalls() {
        return destructibleWalls;
    }

}
