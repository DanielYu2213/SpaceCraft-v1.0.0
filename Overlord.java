import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Overlord {
    private Polygon box;
    private Point2D velocity;
    private ImageView sprite;


    public Overlord(int x, int y) {
        this.box = new Polygon(0, 0, 60, 0, 60, 60, 0, 60);
        this.box.setFill(Color.WHITE);
        this.box.setOpacity(0);
        this.box.setTranslateX(x);
        this.box.setTranslateY(y);

        Random rnd = new Random();
        int rotation = rnd.nextInt(90) + 45 ;
        this.box.setRotate(rotation);

        if (rotation <= 90) {
            Image image = new Image(getClass().getResource("assets/sprites/overlord.gif").toExternalForm());
            this.sprite = new ImageView(image);
        } else {
            Image image = new Image(getClass().getResource("assets/sprites/overlord2.gif").toExternalForm());
            this.sprite = new ImageView(image);
        }

        this.sprite.setFitWidth(60 * 2);
        this.sprite.setFitHeight(50 * 2);
        this.sprite.setTranslateX(x - sprite.getFitWidth() / 2 + 30);
        this.sprite.setTranslateY((y)- sprite.getFitHeight() / 2 + 30);

        this.velocity = new Point2D(0, 0);
    }

    public Polygon getBox() {
        return this.box;
    }

    public ImageView getSprite() {
        return this.sprite;
    }

    public void move() {
        double newX = this.box.getTranslateX() + this.velocity.getX();
        double newY = this.box.getTranslateY() + this.velocity.getY();

        this.box.setTranslateX(newX);
        this.box.setTranslateY(newY);

        this.sprite.setTranslateX(newX - sprite.getFitWidth() / 2 + 30);
        this.sprite.setTranslateY(newY - sprite.getFitHeight() / 2 + 30);
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.box.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.box.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.velocity = this.velocity.add(changeX, changeY);
    }
    
}
