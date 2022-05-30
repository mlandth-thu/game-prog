public class Main {
    private static GraphicsSystem graphicsSystem;
    private  static World world;
    private static WaveManager waveManager;

    /*** MAIN ***/
    public static void main(String[] args) {
        init();
        run();
    }

    /*** INIT ***/
    private static void init() {
        InputHandler input = new InputHandler();

        Frame frame = new Frame(input);
        frame.setVisible(true);

        world = new World(input);
        UIManager uiManager = new UIManager();
        graphicsSystem = frame.getPanel();
        waveManager = new WaveManager();
    }

    /*** RUN ***/
    private static void run() {
        Time.startCompute();
        while (true) {
            Time.continueCompute();
            graphicsSystem.draw(world.getWorldObjects());
            world.handleActions();
            world.checkForCollision();
            waveManager.checkForNextWave();
        }
    }
}
