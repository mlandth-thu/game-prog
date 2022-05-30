import java.awt.*;
import java.util.ArrayList;

public class UIManager {
    public static UIManager instance;

    private TextObject healthText;
    private TextObject waveText;
    private TextObject gameOverText;

    private ArrayList<TextObject> textObjectList;

    public UIManager() {
        instance = this;

        healthText = new TextObject("Health: ", 0, (int)World.instance.getWorldMaxHeight(), 25, Color.WHITE);
        waveText = new TextObject("Wave: ", 0, 25, 25, Color.WHITE);
        gameOverText = new TextObject("", 400, 500, 100, Color.WHITE);

        textObjectList = new ArrayList<>();
        textObjectList.add(healthText);
        textObjectList.add(waveText);
        textObjectList.add(gameOverText);
    }

    /*** SETTERS ***/
    public void setHealth(int currentHealth) {
        healthText.text = "Health: " + currentHealth;
    }

    public void setWave(int currentWave) {
        waveText.text = "Wave: " + currentWave;
    }

    public void showGameOverText() {
        gameOverText.text = "GAME OVER";
    }

    public ArrayList<TextObject> getTextObjectList() {
        return textObjectList;
    }
}
