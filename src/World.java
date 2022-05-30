import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static World instance;

    private Image back;
    private Player player;

    private List<GameObject> worldObjects;
    private List<Enemy> enemies;
    private List<Bullet> bullets;

    private double worldMaxWidth;
    private double worldMaxHeight;

    /*** CONSTRUCTOR ***/
    public World(InputHandler input) {
        worldObjects = new ArrayList<>();
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        instance = this;
        worldMaxWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        worldMaxHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        init(input);
    }

    /*** HANDLE ACTIONS ***/
    public void handleActions() {
        player.shoot();
        handleEnemyBullets();
    }

    private void handleEnemyBullets() {
        for (Enemy e : enemies) {
            e.shoot();
        }
    }

    /*** INIT ***/
    private void init(InputHandler input) {
        player = new Player(50, 50, Color.WHITE, 150, input);
        loadBack();
        worldObjects.add(player);
    }

    private void loadBack() {
        try {
            back = ImageIO.read(getClass().getResource("res/img/Back.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getBack() {
        return back;
    }

    public void createEnemies(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            Enemy enemy = new Enemy(r.nextInt((int) worldMaxWidth), r.nextInt(500), 50, 50);
            addEnemy(enemy);
        }
    }

    /*** GAMEOBJECTS ***/
    public List<GameObject> getWorldObjects() {
        return worldObjects;
    }


    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        worldObjects.add(enemy);
    }


    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        worldObjects.add(bullet);
    }

    public void destroyGameObject(GameObject gameObject) {
        if (gameObject instanceof Enemy) {
            enemies.remove(gameObject);

        } else if (gameObject instanceof Bullet) {
            bullets.remove(gameObject);
        }

        worldObjects.remove(gameObject);
    }

    /*** COLLISION ***/
    public void checkForCollision() {
        Bullet bulletToDestroy = null;
        Bullet bulletOfEnemyToDestroy = null;
        Enemy enemyToDestroy = null;
        for (Bullet b : bullets) {
            if (b.isOfPlayer()) {
                for (Enemy e : enemies) {
                    double dist = e.getWidth() + b.getSize();
                    double dx = Math.abs(e.x - b.x);
                    double dy = Math.abs(e.y - b.y);

                    if (dx < dist && dy < dist) {
                        if (dx * dx + dy * dy < dist * dist) {
                            bulletToDestroy = b;
                            enemyToDestroy = e;
                            DestroyAnimation dA = new DestroyAnimation((int)e.getX(), (int)e.getY(), 30);
                        }
                    }
                }
            } else {
                double dist = player.getWidth() + b.getSize();
                double dx = Math.abs(player.x - b.x);
                double dy = Math.abs(player.y - b.y);

                if (dx < dist && dy < dist) {
                    if (dx * dx + dy * dy < dist * dist) {
                        player.takeDamage();
                        bulletOfEnemyToDestroy = b;
                    }
                }
            }


        }
        destroyGameObject(bulletOfEnemyToDestroy);
        destroyGameObject(bulletToDestroy);
        destroyGameObject(enemyToDestroy);
    }

    /*** GETTER ***/
    public double getWorldMaxWidth() {
        return worldMaxWidth;
    }

    public double getWorldMaxHeight() {
        return worldMaxHeight;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
