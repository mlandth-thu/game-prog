public class Main {
    private static GraphicsSystem graphicsSystem;
    private static World world;
    private static GameManager waveManager;
    private static InputHandler input;

    /*** MAIN ***/
    public static void main(String[] args) {
        init();
        run();
    }

    /*** INIT ***/
    private static void init() {
        input = new InputHandler();
        GameFont f = new GameFont();
        Frame frame = new Frame(input);
        frame.setVisible(true);

        world = new World(input);
        UIManager uiManager = new UIManager();
        graphicsSystem = frame.getPanel();
        waveManager = new GameManager();
    }

    /*** RUN ***/
    private static void run() {
        Time.startCompute();
        while (true) {
            handleStartGame();

            Time.continueCompute();
            graphicsSystem.draw(world.getWorldObjects());
            world.handleActions();
            world.checkForCollision();
            waveManager.checkForNextWave();

            handleRestartGame();
        }
    }

    /*** START GAME ***/
    private static boolean isStarted;
    private static void handleStartGame() {
        if (!isStarted) {
            while (true) {
                Time.continueCompute();
                graphicsSystem.draw(world.getWorldObjects());
                UIManager.instance.doStartTextAnimation();
                if (input.isShooting) {
                    System.out.println("pressed space");
                    isStarted = true;
                    UIManager.instance.hideTitleScreen();
                    UIManager.instance.hidestartText();
                    break;
                }
            }
        }
    }

    /*** RESTART GAME ***/
    private static void handleRestartGame() {
        if (World.instance.getPlayer().isDead()) {
            UIManager.instance.doReStartTextAnimation();
            if (InputHandler.instance.isShooting)
                GameManager.instance.restartGame();
        }
    }
}
