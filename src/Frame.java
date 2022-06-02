import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
   private final GraphicsSystem panel;

    public Frame(InputHandler input) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.toFront();
        this.setUndecorated(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        panel = new GraphicsSystem(input);
        panel.setBackground(Color.BLACK);
        this.setContentPane(panel);

        setVisible(true);

    }

    public GraphicsSystem getPanel() {
        return panel;
    }
}
