import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class App extends Application {

    private MediaPlayer player;
    private Scene scene;
    private Scene scene2;
    private Scene scene3;
    private StackPane layout;
    private StackPane layout2;
    private StackPane layout3;

    @Override
    public void start(Stage stage) throws IOException {
        //Level1End end = new Level1End();
        //Scene endScene = end.getScene(stage);

        scene = getMainMenuScene(stage);
        stage.setScene(scene);
        stage.setTitle("SpaceCraft");
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("");

        stage.getIcons().add(new Image(getClass().getResource("/assets/sprites/bc.png").toExternalForm()));
        stage.show();
    }

    public Scene getMainMenuScene(Stage stage) {

        Label v = new Label("SpaceCraft V 0.1");
        v.setStyle("-fx-text-fill:rgb(255, 255, 255); -fx-font-size: 13px;");

        Label v2 = new Label("SpaceCraft V 0.1");
        v2.setStyle("-fx-text-fill:rgb(255, 255, 255); -fx-font-size: 13px;");

        Label v3 = new Label("SpaceCraft V 0.1");
        v3.setStyle("-fx-text-fill:rgb(255, 255, 255); -fx-font-size: 13px;");
        
        Image gif = new Image(getClass().getResource("/assets/gifs/1bbbef16ac4ffcba1c2b97c3b2e4564c.gif").toExternalForm());
        ImageView gifView = new ImageView(gif);
        ImageView gifView2 = new ImageView(gif);
        ImageView gifView3 = new ImageView(gif);

        Image title = new Image(getClass().getResource("/assets/text/935fc6702b0594d2a181dafa16238b22.png").toExternalForm());
        ImageView titleView = new ImageView(title);
        titleView.setFitWidth(500);

        gifView.setFitWidth(1600);
        gifView.setFitHeight(1600);
        gifView.setPreserveRatio(false);

        gifView2.setFitWidth(1600);
        gifView2.setFitHeight(1600);
        gifView2.setPreserveRatio(false);

        gifView3.setFitWidth(1600);
        gifView3.setFitHeight(1600);
        gifView3.setPreserveRatio(false);

        VBox vbox = new VBox(10);
        Button startGame = new Button("New Game");
        startGame.getStyleClass().add("neon-button");
        startGame.setPrefSize(250, 50);

        Button settings = new Button("Settings");
        settings.getStyleClass().add("neon-button");
        settings.setPrefSize(250, 50);

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("neon-button");
        exitButton.setPrefSize(250, 50);

        VBox vbox2 = new VBox(10);

        Button level1 = new Button("Episode I");
        level1.getStyleClass().add("neon-button");
        level1.setPrefSize(150, 50);

        Button level2 = new Button("Episode II");
        level2.getStyleClass().add("neon-button");
        level2.setPrefSize(150, 50);

        Button level3 = new Button("Episode III");
        level3.getStyleClass().add("neon-button");
        level3.setPrefSize(150, 50);

        Button backToMenu = new Button("Back to menu");
        backToMenu.getStyleClass().add("neon-button");
        backToMenu.setPrefSize(150, 50);

        Button backToMenu2 = new Button("Back to menu");
        backToMenu2.getStyleClass().add("neon-button");
        backToMenu2.setPrefSize(150, 50);

        VBox vbox3 = new VBox(10);

        Label settingsLabel = new Label("Settings");
        settingsLabel.setStyle("-fx-text-fill:  #39ff14; -fx-font-size: 36px;");

        Label volumeLabel = new Label("Volume");
        volumeLabel.setStyle("-fx-text-fill:  #39ff14; -fx-font-size: 24px;");

        Slider volumeSlider = new Slider(0, 0.4, VolumeManager.getVolume());
        volumeSlider.setMaxWidth(700);
        volumeSlider.setMajorTickUnit(0.02);
        volumeSlider.setMinorTickCount(0);
        volumeSlider.setBlockIncrement(0.02);
        volumeSlider.setSnapToTicks(true);
        volumeSlider.setShowTickMarks(true);

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue();
            VolumeManager.setVolume(volume);

        });

        vbox.getChildren().addAll(titleView, startGame, settings, exitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setOpacity(0);

        vbox2.getChildren().addAll(level1, level2, level3, backToMenu);
        vbox2.setAlignment(Pos.CENTER);

        vbox3.getChildren().addAll(settingsLabel, volumeLabel, volumeSlider, backToMenu2);
        vbox3.setAlignment(Pos.CENTER);

        layout = new StackPane();
        StackPane.setAlignment(v, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(v2, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(v3, Pos.BOTTOM_RIGHT);
        layout.getChildren().addAll(gifView, vbox, v);

        layout2 = new StackPane();
        layout2.getChildren().addAll(gifView2, vbox2, v2);

        layout3 = new StackPane();
        layout3.getChildren().addAll(gifView3, vbox3, v3);

        scene = new Scene(layout, 1400, 900);
        scene2 = new Scene(layout2, 1400, 900);
        scene3 = new Scene(layout3, 1400, 900);

        scene.getStylesheets().add(getClass().getResource("resources/styles.css").toExternalForm());
        scene2.getStylesheets().add(getClass().getResource("resources/styles.css").toExternalForm());
        scene3.getStylesheets().add(getClass().getResource("resources/styles.css").toExternalForm());

        Image cursorImage = new Image(getClass().getResource("/assets/sprites/cursor.png").toExternalForm());
        scene.setCursor(new ImageCursor(cursorImage));
        scene2.setCursor(new ImageCursor(cursorImage));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), vbox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        Media media = new Media(getClass().getResource("/assets/audio/StarcraftMain.mp3").toExternalForm());
        player = new MediaPlayer(media);
        VolumeManager.register(player);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        Media button = new Media(getClass().getResource("/assets/audio/button.mp3").toExternalForm());

        exitButton.setOnAction(e -> stage.close());

        startGame.setOnAction(e -> {
            MediaPlayer buttonSFX = new MediaPlayer(button);
            VolumeManager.register(buttonSFX);
            buttonSFX.play();
            stage.setScene(scene2);
        });

        settings.setOnAction(e -> {
            MediaPlayer buttonSFX = new MediaPlayer(button);
            VolumeManager.register(buttonSFX);
            buttonSFX.play();
            stage.setScene(scene3);
        });

        backToMenu.setOnAction(e -> {
            MediaPlayer buttonSFX = new MediaPlayer(button);
            VolumeManager.register(buttonSFX);
            buttonSFX.play();
            stage.setScene(scene);
        });

        backToMenu2.setOnAction(e -> {
            MediaPlayer buttonSFX = new MediaPlayer(button);
            VolumeManager.register(buttonSFX);
            buttonSFX.play();
            stage.setScene(scene);
        });

        level1.setOnAction(e -> {
            Rectangle overlay = new Rectangle(1400, 900, Color.BLACK);
            overlay.setOpacity(0);
            layout2.getChildren().add(overlay);

            MediaPlayer buttonSFX = new MediaPlayer(button);
            VolumeManager.register(buttonSFX);
            buttonSFX.play();

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), overlay);
            fadeOut.setFromValue(0);
            fadeOut.setToValue(1);

            fadeOut.setOnFinished(event -> {
                player.pause();
                player.dispose();
                Level1 levelOne = new Level1();
                Scene levelOneScene = levelOne.getScene(stage);
                stage.setScene(levelOneScene);

            });

            fadeOut.play();
        });

        level2.setDisable(true);
        level3.setDisable(true);

        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
