import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends GameObject {
    private Image texture;
    private double speed;
    private int health;
    private float width;
    private float height;
    private Color color;
    private InputHandler input;
    private double widthBorder;
    private boolean isDead;
    private float shootDelay = 0.2f;

    /*** CONSTRUCTOR ***/
    public Player(float width, float height, Color color, double speed, InputHandler input) {
        this.x = World.instance.getWorldMaxWidth() / 2;
        this.y = World.instance.getWorldMaxHeight() - 80;

        this.speed = speed;
        this.color = color;
        this.width = width;
        this.height = height;
        this.input = input;
        this.health = 3;

        widthBorder = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        currentShootTime = shootDelay;
        canShoot = true;

        loadTexture();
    }

    private void loadTexture() {
        try {
            texture = ImageIO.read(getClass().getResource("resources/RocketShip.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*** MOVE ***/
    private void move() {
        if (input.isMovingRight && x < World.instance.getWorldMaxWidth() - width * 0.5)
            x += 4 * speed * Time.deltaTime;

        if (input.isMovingLeft && x > width)
            x -= 4 * speed * Time.deltaTime;
    }

    /*** SHOOT ***/
    boolean canShoot;
    float currentShootTime;

    public void shoot() {
        if(isDead || GameManager.instance.isPaused()) return;

        if (input.isShooting && canShoot) {
            Bullet b = new Bullet((float) x, (float) y , 5, 350, true);
            canShoot = false;
        }

        if (!canShoot) {
            if (currentShootTime > 0)
                currentShootTime -= Time.deltaTime;
            else {
                canShoot = true;
                currentShootTime = shootDelay;
            }
        }
    }

    /*** Health ***/
    public void takeDamage() {
        if(isDead) return;

        health--;
        System.out.println("Hit!");
        checkForDeath();
    }

    private void checkForDeath() {
        if (health == 0) {
            System.out.println("GAME OVER");
            UIManager.instance.showGameOverText();
            UIManager.instance.doReStartTextAnimation();
            isDead = true;
            DestroyAnimation destroyAnimation = new DestroyAnimation((int)x,(int)y,30);
            World.instance.destroyGameObject(this);
        }
    }


    @Override
    public void drawObject(Graphics graphics) {
        graphics.drawImage(texture, (int) x - (int) (width / 2), (int) y, (int) width, (int) height, null);
        UIManager.instance.setHealth(health);
        move();
        //DEBUG HITBOXES
        //graphics.setColor(Color.GREEN);
        //graphics.drawRect((int)x,(int)y,(int)width,(int)height);
    }

    /*** GETTER ***/
    public boolean isDead() {
        return isDead;
    }

    public double getWidth() {
        return width;
    }

    public double getSpeed() {
        return speed;
    }

    public void incSpeed(double speed) {
        this.speed += speed;
    }

    public void incHealth() {
        health++;
    }

    public float getShootDelay() {
        return shootDelay;
    }

    public void decShootDelay(float shootDelay) {
        this.shootDelay -= shootDelay;
    }
}
