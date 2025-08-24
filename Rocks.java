
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import java.util.Random;

public class Rocks {
    private Polygon rock;
    private Point2D velocity;

    public Rocks(int x, int y) {
        RockGenerator rocks = new RockGenerator();
        this.rock = rocks.createRock();
        this.rock.setFill(Color.GREY);
        this.rock.setTranslateX(x);
        this.rock.setTranslateY(y);

        Random rnd = new Random();
        this.rock.setRotate(rnd.nextInt(178) + 1);

        this.velocity = new Point2D(0, 0);
    }

    public Polygon getRock() {
        return this.rock;
    }

    public void move() {
        this.rock.setTranslateX(this.rock.getTranslateX() + this.velocity.getX());
        this.rock.setTranslateY(this.rock.getTranslateY() + this.velocity.getY());
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.rock.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.rock.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.velocity = this.velocity.add(changeX, changeY);
    }
    
}
