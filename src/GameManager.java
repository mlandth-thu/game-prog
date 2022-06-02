import java.util.Random;

public class GameManager {
    private int currentWave;
    private int score;
    int waveTime;
    public static GameManager instance;
    private boolean isPaused;

    /*** CONSTRUCTOR ***/
    public GameManager() {
        instance = this;

        currentWave = 1; // current bug, after going past first screen wave is already incremented by 1, e.g. first wave means currentWave = 2
        breakDelay = 5;
        currentBreakTime = breakDelay;
        waveTime = 3;
    }

    /*** BREAK BETWEEN WAVES ***/
    private float currentBreakTime;
    private final float breakDelay;

    public void createNewWave() { //rearanged to fix bug
        if (doBreak()) {
            createEnemies();
            displayWave();
            currentWave++;
        }
    }

    private boolean doBreak() {
        if (currentBreakTime > 0) {
            currentBreakTime -= Time.deltaTime;
            if (currentBreakTime >= 1)
                UIManager.instance.showWaveInfoText(currentWave, (int) currentBreakTime);
            return false;
        } else {
            currentBreakTime = breakDelay;
            UIManager.instance.hideWaveInfoText();
            waveTime = 3;
            return true;
        }
    }

    /*** CHECK FOR NEXT WAVE ***/
    public void checkForNextWave() {
        handlePauseGame();
        if (World.instance.getEnemies().size() < 1) {
            createNewWave();
        }
    }

    /*** ENEMY CREATION ***/
    private void createEnemies() {
        Random r = new Random();
        //BOSS
        int numOfBoss;
        if (currentWave % 10 == 0) { //currentWave % 10 == 0
            numOfBoss = currentWave / 10;
            World.instance.createEnemy(EnemyType.BOSS, currentWave);
        }
        //ELITE
        int numOfElite;
        if (currentWave % 5 == 0) {
            numOfElite = currentWave / 5;
            for (int i = 0; i < numOfElite; i++) {
                World.instance.createEnemy(EnemyType.ELITE, currentWave);
            }
        }
        //Normal
        int numOfNormal;
        if (currentWave <= 5) {
            numOfNormal = r.nextInt(3) + currentWave;
            for (int i = 0; i < numOfNormal; i++) {
                World.instance.createEnemy(EnemyType.NORMAL, currentWave);
            }
        } else if (5 < currentWave && currentWave <= 15) {
            numOfNormal = r.nextInt(6) + currentWave;
            for (int i = 0; i < numOfNormal; i++) {
                World.instance.createEnemy(EnemyType.NORMAL, currentWave);
            }
        } else if(16 < currentWave && currentWave <= 29) {
            numOfNormal = r.nextInt(9) + currentWave;
            for (int i = 0; i < numOfNormal; i++) {
                World.instance.createEnemy(EnemyType.NORMAL, currentWave);
            }
        } else if (currentWave > 30) {
            numOfNormal = r.nextInt(15) + currentWave;
            for (int i = 0; i < numOfNormal; i++) {
                World.instance.createEnemy(EnemyType.NORMAL, currentWave);
            }
        }

    }


    private void handlePauseGame() {
        if (InputHandler.instance.isPause) {
            Time.timeScale = 0;
            UIManager.instance.showPauseText();
        } else {
            Time.timeScale = 1;
            UIManager.instance.hidePauseText();
        }
        isPaused = InputHandler.instance.isPause;
    }

    public void addScore(int amount) {
        score += amount;
        UIManager.instance.setScore(score);
    }

    public void displayWave() {
        UIManager.instance.displayWave(currentWave);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void restartGame() {
        score = 0;
        addScore(score);
        currentWave = 1;
        World.instance.removeAllEnemies();
        UIManager.instance.hideGameOverText();
        UIManager.instance.hideRestartText();
        World.instance.createPlayer();
    }


}


