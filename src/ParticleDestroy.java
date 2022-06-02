import java.awt.*;
import java.util.Random;

public class ParticleDestroy extends GameObject {
    private final int size;
    private final Direction moveDir;
    private final Color color;
    private final int speed;

    public ParticleDestroy(int x, int y, int size, Color color, Direction moveDir) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.moveDir = moveDir;
        Random r = new Random();
        speed = r.nextInt(400) + 50;

        World.instance.getWorldObjects().add(this);

    }

    /*** MOVE ***/
    private void move() {
        switch (moveDir) {
            case UP:
                y -= speed * Time.deltaTime;
                break;
            case DOWN:
                y += speed * Time.deltaTime;
                break;
            case LEFT:
                x -= speed * Time.deltaTime;
                break;
            case RIGHT:
                x += speed * Time.deltaTime;
                break;
            case LEFTUP:
                x -= speed * Time.deltaTime;
                y -= speed * Time.deltaTime;
                break;
            case LEFTDOWN:
                x -= speed * Time.deltaTime;
                y += speed * Time.deltaTime;
                break;
            case RIGHTUP:
                x += speed * Time.deltaTime;
                y -= speed * Time.deltaTime;
                break;
            case RIGHTDOWN:
                x += speed * Time.deltaTime;
                y += speed * Time.deltaTime;
                break;
        }
    }

    @Override
    void drawObject(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillOval((int) x, (int) y, size, size);
        move();
    }
}
