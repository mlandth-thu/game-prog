import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;

public class GraphicsSystem extends JPanel {
    //For Buffering
    private final GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    private final BufferedImage imageBuffer;
    private final Graphics graphics;
    private float yPixelStart = 4320;

    public GraphicsSystem(InputHandler keyInput) {
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        imageBuffer = graphicsConfig.createCompatibleImage(this.getWidth(), this.getHeight());
        graphics = imageBuffer.getGraphics();
        setLayout(null);

        addKeyListener(keyInput);
        setFocusable(true);

    }

    public void clear() {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
    }

    public void draw(List<GameObject> gameObjects) {
        clear();

        graphics.drawImage(World.instance.getBack(), 0, 0, (int)World.instance.getWorldMaxWidth(), (int) World.instance.getWorldMaxHeight(), null);
        BufferedImage tmpBack = World.instance.getBack();
        if (GameManager.instance.getFastMoving()){
            yPixelStart = (float) (yPixelStart - 200 * Time.deltaTime);
        }else {
            yPixelStart = (float) (yPixelStart - 80 * Time.deltaTime);
        }
        if (yPixelStart < 0){
            yPixelStart = 4320;
        }
        tmpBack = tmpBack.getSubimage(0,(int)yPixelStart, 3840, 2160);

        graphics.drawImage(tmpBack, 0, 0, (int)World.instance.getWorldMaxWidth(), (int) World.instance.getWorldMaxHeight(), null);


        for (GameObject d : gameObjects) {
            d.drawObject(graphics);
        }

        for (GameObject g : UIManager.instance.getTextObjectList()){
            g.drawObject(graphics);
       }

        redraw();
    }

    public void redraw() {
        this.getGraphics().drawImage(imageBuffer, 0,0, this);
    }
}

