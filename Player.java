
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Player {
    private Polygon character;
    private ImageView dropship;
    private Point2D velocity;

    public Player(int x, int y, int rotate) {
        this.character = new Polygon(0, 0, 60, 0, 60, 60, 0, 60);
        this.character.setRotate(rotate);
        this.character.setFill(Color.WHITE);
        this.character.setOpacity(0);
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        Image image = new Image(getClass().getResource("assets/sprites/player-dropship.png").toExternalForm());
        this.dropship = new ImageView(image);
        this.dropship.setFitWidth(60 * 2);
        this.dropship.setFitHeight(40 * 2);
        this.dropship.setTranslateX(x - 30);
        this.dropship.setTranslateY(y - 20);

        this.velocity = new Point2D(0, 0);
    }

    public Polygon getCharacter() {
        return character;
    }

    public ImageView dropship() {
        return dropship;
    }

    public double getX() {
        return character.getTranslateX();
    }

    public double getY() {
        return character.getTranslateY();
    }

    public void applyThrust(double ax, double ay) {
        this.velocity = this.velocity.add(ax, ay);
    }

    public void update() {

        character.setTranslateX(character.getTranslateX() + this.velocity.getX()); 
        character.setTranslateY(character.getTranslateY() + this.velocity.getY());

        dropship.setTranslateX(dropship.getTranslateX() + this.velocity.getX()); 
        dropship.setTranslateY(dropship.getTranslateY() + this.velocity.getY()); 

        
        velocity = velocity.multiply(0.95);
    }
}
