import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Enemy extends GameObject {
    private Image texture;
    private float width, height;
    private int speed;

    /*** CONSTRUCTOR ***/
    public Enemy(int x, int y, float heigth, float width) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = heigth;

        this.speed = 20;
        shootDelay = 3;
        currentShootTime = shootDelay;
        canShoot = true;

        Random r = new Random();
        moveDelayTime = r.nextInt(5) + 5;
        currentMoveTime = moveDelayTime;
        moveDirection = getRandomMoveDirection();

        loadTexture();
    }

    private void loadTexture() {
        try {
            texture = ImageIO.read(getClass().getResource("Enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*** MOVEMENT ***/
    private void move() {
        switch (moveDirection) {
            case DOWN:
                if (y < World.instance.getWorldMaxHeight() - height - 400)
                    y += speed * 2 * Time.deltaTime;
                break;
            case UP:
                if (y > height)
                    y -= speed * 2 * Time.deltaTime;
                break;
            case LEFT:
                if (x > width)
                    x -= speed * 2 * Time.deltaTime;
                break;
            case RIGHT:
                if (x < World.instance.getWorldMaxWidth())
                    x += speed * 2 * Time.deltaTime;
                break;
        }
        waitForNextMove();
    }

    private float currentMoveTime;
    private float moveDelayTime;
    private Direction moveDirection;

    private void waitForNextMove() {
        if (currentMoveTime > 0) {
            currentMoveTime -= Time.deltaTime;
        } else {
            moveDirection = getRandomMoveDirection();
            currentMoveTime = moveDelayTime;
        }
    }

    private Direction getRandomMoveDirection() {
        Random r = new Random();
        int rdm = r.nextInt(3);
        moveDelayTime = r.nextInt(5) + 5;
        switch (rdm) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
        }
        return null;
    }

    /*** SHOOT ***/
    private boolean canShoot;
    private float shootDelay = 0.2f;
    private float currentShootTime;

    public void shoot() {
        if (canShoot) {
            Bullet b = new Bullet((float) x, (float) y + height, 5, 350, false);
            canShoot = false;
        }

        if (!canShoot) {
            if (currentShootTime > 0)
                currentShootTime -= Time.deltaTime;
            else {
                canShoot = true;
                Random r = new Random();
                shootDelay = r.nextInt(10);
                currentShootTime = shootDelay;
            }
        }
    }

    @Override
    public void drawObject(Graphics graphics) {
        graphics.drawImage(texture, (int) x - (int) (width / 2), (int) y, (int) width, (int) height, null);
        move();
    }

    /*** GETTER ***/
    public double getWidth() {
        return width;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
