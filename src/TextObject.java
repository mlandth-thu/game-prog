import java.awt.*;

public class TextObject extends GameObject {
    String text;

    private int x, y, size;
    private Font font;
    private Color color;

    public TextObject(String text, int x, int y, int size, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;

        font = new Font("", Font.ITALIC, size);
    }

    @Override
    void drawObject(Graphics graphics) {
        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString(text, x, y);
    }
}
