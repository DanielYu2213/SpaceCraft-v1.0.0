

import javafx.stage.Stage;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class Level1 {


    public Scene getScene(Stage stage) {

        StackPane layout = new StackPane();

        MediaLoader mediaLoader = new MediaLoader();


        Rectangle overlay = new Rectangle(1400, 900, Color.BLACK);
        layout.getChildren().add(overlay);

        Button skip = new Button("Skip");
        skip.getStyleClass().add("grey-button");
        skip.setPrefSize(100, 50);

        StackPane.setAlignment(skip, Pos.BOTTOM_RIGHT);
        layout.getChildren().add(skip);

        layout.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(layout, 1400, 900);
        scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), overlay);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);

        fadeIn.play();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), overlay);
        fadeOut.setFromValue(0);
        fadeOut.setToValue(1);

        mediaLoader.loadAndPlayVideo(stage, layout, "/assets/video/level1_exposition.mp4", () -> {
            Level1Game game1 = new Level1Game();
            Scene game1Scene = game1.getScene(stage);
            stage.setScene(game1Scene);
        });

        skip.setOnAction(e -> {
            skip.setDisable(true);
            skip.setOpacity(0);

            fadeOut.setOnFinished(Event -> {
                mediaLoader.pause();
                mediaLoader.dispose();
                Level1Game game1 = new Level1Game();
                Scene game1Scene = game1.getScene(stage);
                stage.setScene(game1Scene);
            });

            fadeOut.play();
            
        });

        Image cursorImage = new Image(getClass().getResource("/assets/sprites/cursor.png").toExternalForm());
        scene.setCursor(new ImageCursor(cursorImage));


        return scene;

    }

}
