public class WaveManager {
    private int currentWave;

    /*** CONSTRUCTOR ***/
    public WaveManager() {
        currentWave = 0;
        breakDelay = 5;
        currentBreakTime = breakDelay;
    }

    /*** BREAK BETWEEN WAVES ***/
    private float currentBreakTime;
    private float breakDelay;
    public void createNewWave() {
        if (doBreak()) {
            currentWave++;
            World.instance.createEnemies(currentWave);
        }
    }

    private boolean doBreak() {
        if (currentBreakTime > 0) {
            currentBreakTime -= Time.deltaTime;
            return false;
        } else {
            currentBreakTime = breakDelay;
            return true;
        }
    }

    /*** CHECK FOR NEXT WAVE ***/
    public void checkForNextWave() {
        UIManager.instance.setWave(currentWave);
        if (World.instance.getEnemies().size() < 1) {
            createNewWave();
        }
    }
}
