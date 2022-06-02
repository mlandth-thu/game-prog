import java.awt.*;

public class TextObject extends GameObject {
    String text;

    private int x, y, size;
    private Font font;
    private Color color;
    private boolean isCentered;

    public TextObject(String text, int x, int y, int size, Color color, boolean isCentered) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.isCentered = isCentered;
        font = GameFont.getGameFont(font, size);
        //font = GameFont.font;

    }

    @Override
    void drawObject(Graphics graphics) {
        graphics.setFont(font);
        graphics.setColor(color);
        int newX;
        int newY;
        if (isCentered) {
            newX = x - (int) graphics.getFontMetrics(font).getStringBounds(text, graphics).getWidth() / 2;
            newY = y - (int) graphics.getFontMetrics(font).getStringBounds(text, graphics).getHeight() / 2;
        } else {
            newX = x;
            newY = y;
        }
        graphics.drawString(text, newX, newY);
    }
}
