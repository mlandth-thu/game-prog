import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        createPlayer();
        loadBack();

    }

    public void createPlayer() {
        player = new Player(50, 50, Color.WHITE, 150, InputHandler.instance);
        worldObjects.add(player);
    }

    private void loadBack() {
        try {
            back = ImageIO.read(getClass().getResource("resources/Back.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getBack() {
        return back;
    }

    public void createEnemy(EnemyType type, int currentWave) {
        Random r = new Random();
        Enemy enemy = null;
        switch (type) {
            case NORMAL:
                enemy = new Enemy(r.nextInt((int) worldMaxWidth), r.nextInt(500), 5, 50, 5, EnemyType.NORMAL, 50, currentWave);
                break;
            case ELITE:
                enemy = new Enemy(r.nextInt((int) worldMaxWidth), r.nextInt(500), 15, 100,30, EnemyType.ELITE, 100, currentWave);
                break;
            case BOSS:
                enemy = new Enemy(r.nextInt((int) worldMaxWidth), r.nextInt(500), 30, 200,60, EnemyType.BOSS,200, currentWave);
                break;

        }
        addEnemy(enemy);
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
            GameManager.instance.addScore(110);
            triggerDeath(((Enemy) gameObject).getType());

        } else if (gameObject instanceof Bullet) {
            bullets.remove(gameObject);
        }

        worldObjects.remove(gameObject);
    }

    public void removeAllEnemies() {
        for (Enemy e : enemies) {
            worldObjects.remove(e);
        }

        enemies.clear();
    }


    /*** COLLISION ***/
    public void checkForCollision() {   // currently checks if enemy is hit and instantly deletes them + plays destroy anim.
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
                            e.takeDamage();
                            bulletToDestroy = b;
                            DamageAnimation deA = new DamageAnimation((int) e.getX(), (int) e.getY(), 3);
                            if(e.getHealth() < 1) { //when enemy health reaches zero destroy it
                                enemyToDestroy = e;
                                DestroyAnimation dA = new DestroyAnimation((int) e.getX(), (int) e.getY(), 30);
                            }
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

    /** UPGRADES **/

    private void triggerDeath(EnemyType t) {
        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;
        switch (t) {
            case NORMAL:
                dropNormal(randomInt);
                break;
            case ELITE:
                dropElite(randomInt);
                break;
            case BOSS:
                dropBoss(randomInt);
                break;
        }
    }

    private void dropNormal(int randomInt) {
        Random r = new Random();
        List<UpgradeType> normalUpgrades = Arrays.asList(UpgradeType.SHOOT_DELAY, UpgradeType.HEALTH, UpgradeType.SPEED);
        if (randomInt < 5) {
            int index = r.nextInt(normalUpgrades.size());
            UpgradeType choice = normalUpgrades.get(index);
            System.out.print("dropNormal: ");
            doUpgrade(choice);
        }
    }

    private void dropElite(int randomInt) {
        System.out.println("dropElite");
        if (randomInt < 50) {

        }
        //TODO
    }

    private void dropBoss(int randomInt) {
        System.out.println("dropBoss");

        player.incHealth();
        //TODO
    }

    private void doUpgrade(UpgradeType choice) {
        System.out.print(choice);
        switch (choice) {
            case HEALTH:
                player.incHealth();
                System.out.println();
                break;
            case SPEED:
                player.incSpeed(1);
                System.out.println(" | speed: " +player.getSpeed());
                break;
            case SHOOT_DELAY:
                player.decShootDelay(0.5f);
                System.out.println(" | shoot_delay: " +player.getShootDelay());
                break;

            case BULLET_SIZE:

            case GUN_QUANTITY:

            case GRENADE:

            case NONE:
        }

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

    public Player getPlayer() {
        return player;
    }
}


