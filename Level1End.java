

import javafx.stage.Stage;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.util.Duration;


public class Level1End {

    private Label dialogueLabel;

    public Scene getScene(Stage stage) {

        dialogueLabel = new Label();
        dialogueLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10px");
        dialogueLabel.setPrefWidth(1400);
        dialogueLabel.setWrapText(true);
        dialogueLabel.setLayoutY(800);
        dialogueLabel.setAlignment(Pos.CENTER);
        
        Label timerLabel = new Label("Time: 00:00");
        timerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");

        StackPane layout = new StackPane();

        MediaLoader mediaLoader = new MediaLoader();

        mediaLoader.loadAndPlayVideo(stage, layout, "/assets/video/level1Ending30.mp4", () -> {

        });


        Rectangle overlay = new Rectangle(1400, 900, Color.BLACK);

        //layout.getChildren().add(timerLabel);

        Pane hud = new Pane();

        hud.getChildren().addAll(dialogueLabel, overlay);
        layout.getChildren().addAll(hud);

        Scene scene = new Scene(layout, 1400, 900);
        Image cursorImage = new Image(getClass().getResource("/assets/sprites/cursor.png").toExternalForm());
        scene.setCursor(new ImageCursor(cursorImage));


        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), overlay);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);

        fadeIn.play();

        final int[] seconds = { 0 };

        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds[0]++;
            int minutes = seconds[0] / 60;
            int secs = seconds[0] % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, secs));

            //-----------------------------------------------------------------------------------------------------------//
            //Dialogue//

            if (seconds[0] == 3) {
                showDialogue("Adjutant: artificial structure detected.", Duration.seconds(3));
            }

            if (seconds[0] == 7) {
                showDialogue("Adjutant: Suspended platform, likely Terran origin.", Duration.seconds(3));
            }

            if (seconds[0] == 11) {
                showDialogue("Rykor: Wait… that’s a Starport ! ", Duration.seconds(2));
            }

            if (seconds[0] == 14) {
                showDialogue("Rykor: Landing for recon. ", Duration.seconds(2));
            }

            if (seconds[0] == 17) {
                showDialogue("Rykor: Maybe I can get myself a new ship... ", Duration.seconds(3));
            }

            if (seconds[0] == 21) {
                Media gong = new Media(getClass().getResource("/assets/audio/Gong Sound Effect.mp3").toExternalForm());
                MediaPlayer gongSFX = new MediaPlayer(gong);
                VolumeManager.register(gongSFX);
                gongSFX.play();
                layout.getChildren().add(showVictoryScreen(stage));
            }


        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        scene.getStylesheets().add(getClass().getResource("resources/styles.css").toExternalForm());

        return scene;

    }

    public void showDialogue(String text, Duration duration) {
        dialogueLabel.setText(text);

        PauseTransition pause = new PauseTransition(duration);
        pause.setOnFinished(e -> dialogueLabel.setText(""));
        pause.play();
    }

        //defeat screen 
    public Pane showVictoryScreen(Stage stage) {

        Pane pane = new Pane();
        pane.setPrefSize(1400, 900);
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");

        VBox box = new VBox(20);
        box.setPrefSize(400, 300);
        box.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-border-color:  #39ff14; -fx-border-width: 2;");
        box.setAlignment(Pos.CENTER);

        box.setLayoutX((1400 - 400) / 2.0);
        box.setLayoutY((900 - 300) / 2.0);

        Label victory = new Label("Victory !");
        victory.setStyle("-fx-text-fill:  #39ff14; -fx-font-size: 36px;");

        Button backToMenu = new Button("Back to Menu");
        backToMenu.getStyleClass().add("neon-button");

        backToMenu.setOnAction(e -> {
            
            Media media = new Media(getClass().getResource("/assets/audio/button.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            VolumeManager.register(mediaPlayer);
            mediaPlayer.play();

            App menu = new App();
            Scene menuScene = menu.getMainMenuScene(stage);
            stage.setScene(menuScene);

        });

        box.getChildren().addAll(victory, backToMenu);
        pane.getChildren().addAll(box);

        return pane;
    }

    
}
