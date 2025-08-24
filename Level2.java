import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class Level2 {

    public Scene getScene(Stage stage) {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(layout, 1400, 900);
        return scene;

    }
}