import java.awt.*;

public class Bullet extends GameObject {
    private float speed;
    private float size;
    private boolean ofPlayer;

    /*** CONSTRUCTOR ***/
    public Bullet(float x, float y, float size, float speed, boolean ofPlayer) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.ofPlayer = ofPlayer;
        World.instance.addBullet(this);
    }

    /*** MOVE ***/
    public void move() {
        if (ofPlayer) {
            y -= speed * Time.deltaTime;
        } else {
            y += speed * Time.deltaTime;
        }
    }

    @Override
    public void drawObject(Graphics graphics) {
        if (ofPlayer)
            graphics.setColor(Color.WHITE);
        else
            graphics.setColor(Color.RED);

        graphics.fillRect((int) (x - size), (int) (y - size), (int) size * 2, (int) size * 2);
        move();
    }

    /*** GETTERS **/
    public float getSize() {
        return size;
    }

    public boolean isOfPlayer() {
        return ofPlayer;
    }
}
