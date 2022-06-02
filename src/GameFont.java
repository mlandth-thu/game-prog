import java.awt.*;
import java.io.File;
import java.io.IOException;

public  class GameFont {
    public static Font font;

    public static Font getGameFont(Font font, int size) {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/GameFont.ttf")).deriveFont(100,size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/GameFont.ttf")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return font;
    }
}
