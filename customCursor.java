import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class customCursor {

    private ImageView cursorView;

    public Pane getC() {

        Image gif = new Image(getClass().getResource("/assets/gifs/sc cursor.gif").toExternalForm());
        cursorView = new ImageView(gif);
        cursorView.setFitWidth(32);
        cursorView.setFitHeight(32);

        Pane cursorLayer = new Pane(cursorView);
        cursorLayer.setPickOnBounds(false);

        return cursorLayer;
    }

    public ImageView getCImageView() {
        return cursorView;
    }
    
}
