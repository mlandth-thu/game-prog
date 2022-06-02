import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DamageAnimation extends GameObject {
    private ArrayList<ParticleDamage> particleDestroyList;
    private int numberOfParticles;

    public DamageAnimation(int x, int y, int numberOfParticles) {
        this.x = x;
        this.y = y;
        this.numberOfParticles = numberOfParticles;

        particleDestroyList = new ArrayList<>();
        createParticles();

        //World.instance.getWorldObjects().add(this);
    }

    private void createParticles() {
        for (int i = 0; i < numberOfParticles; i++) {
            particleDestroyList.add(new ParticleDamage((int)x,(int)y, 2, Color.orange, getRandomDirection()));
        }
    }

    private Direction getRandomDirection() {
        Random r = new Random();
        int rdm = r.nextInt(7);
        switch (rdm) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            case 4:
                return Direction.LEFTUP;
            case 5:
                return Direction.RIGHTUP;
            case 6:
                return Direction.LEFTDOWN;
            case 7:
                return Direction.RIGHTDOWN;
        }
        return null;
    }

    @Override
    void drawObject(Graphics graphics) {
       /* for (Particle p : particleList) {
            p.drawObject(graphics);
        } */
    }
}
