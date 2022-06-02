public class Time {

    public static double deltaTime;
    public static double timeScale = 1;
    private static long lastTick;

    public static void startCompute() {
        lastTick = System.currentTimeMillis();
    }

    public static void continueCompute() {
        long currentTick = System.currentTimeMillis();
        deltaTime = (currentTick - lastTick) / 1000.0 * timeScale;
        lastTick = currentTick;
    }

}
