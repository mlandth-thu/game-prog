import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {

    public boolean isMovingRight;
    public boolean isMovingLeft;

    public boolean isShooting;

    @Override
    public void keyPressed(KeyEvent e) {
        /*** MOVEMENT ***/
        if (e.getKeyCode() == KeyEvent.VK_D)
            isMovingRight = true;
        if (e.getKeyCode() == KeyEvent.VK_A)
            isMovingLeft = true;

        /*** SHOOTING ***/
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            isShooting = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        /*** MOVEMENT ***/
        if (e.getKeyCode() == KeyEvent.VK_D)
            isMovingRight = false;
        if (e.getKeyCode() == KeyEvent.VK_A)
            isMovingLeft = false;

        /*** SHOOTING ***/
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            isShooting = false;
    }
}
