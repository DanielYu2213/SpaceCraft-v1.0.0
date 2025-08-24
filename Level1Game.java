

import javafx.scene.ImageCursor;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Shape;
import java.util.Random;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Level1Game {

    private MediaPlayer mediaPlayer;
    private StackPane root;
    private Pane layout;       
    private Timeline timer;  
    private AnimationTimer gameLoop; 
    private Pane pauseOverlay; 
    private PauseTransition fadeOutTime;
    private Timeline rockSpawner;
    private Timeline overlordSpawner;
    private Timeline mutaSpawner;
    private Player player;

    private Media button;
    private MediaPlayer buttonSFX;

    private boolean isPaused = false;

    private boolean overlordSpawnerRunning = false;
    private boolean mutaSpawnerRunning = false;

    private Timeline overlordSFXPlayer;
    private Timeline mutaSFXPlayer;

    private Label dialogueLabel;

    private MediaLoader mediaLoader;

    private String[] overlordSFXPaths = {
        "/assets/audio/overlordsfx/overlord1_1.mp3",
        "/assets/audio/overlordsfx/overlord2_1.mp3",
        "/assets/audio/overlordsfx/overlord3_1.mp3",
        "/assets/audio/overlordsfx/overlord4_1.mp3",
        "/assets/audio/overlordsfx/overlord5_1.mp3",
        "/assets/audio/overlordsfx/overlord6_1.mp3",
        "/assets/audio/overlordsfx/overlord7_1.mp3"
    };

    private String[] mutaSFXPaths = {
        "/assets/audio/mutalisksfx/mutalisk1_1.mp3",
        "/assets/audio/mutalisksfx/mutalisk2_1.mp3",
        "/assets/audio/mutalisksfx/mutalisk3_1.mp3",
        "/assets/audio/mutalisksfx/mutalisk4_1.mp3",
        "/assets/audio/mutalisksfx/mutalisk5_1.mp3",
        "/assets/audio/mutalisksfx/mutalisk6_1.mp3",
        "/assets/audio/mutalisksfx/mutalisk7_1.mp3"
    };


    public Scene getScene(Stage stage) {

        button = new Media(getClass().getResource("/assets/audio/button.mp3").toExternalForm());

        Media media = new Media(getClass().getResource("/assets/audio/sctheme1.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        VolumeManager.register(mediaPlayer);
        mediaPlayer.play();

        Label timerLabel = new Label("Time: 00:00");
        timerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");

        dialogueLabel = new Label();
        dialogueLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10px");
        dialogueLabel.setPrefWidth(1400);
        dialogueLabel.setWrapText(true);
        dialogueLabel.setLayoutY(800);
        dialogueLabel.setAlignment(Pos.CENTER);

        Rectangle overlay = new Rectangle(1400, 900, Color.BLACK);

        Pane hud = new Pane();

        layout = new Pane();
        root = new StackPane();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), overlay);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);
        fadeIn.play();

        Scene scene = new Scene(root, 1400, 900);
        Image cursorImage = new Image(getClass().getResource("/assets/sprites/cursor.png").toExternalForm());
        scene.setCursor(new ImageCursor(cursorImage));
        scene.getStylesheets().add(getClass().getResource("resources/styles.css").toExternalForm());

        mediaLoader = new MediaLoader();

        mediaLoader.loadAndPlayVideo(stage, root, "/assets/video/level1Background.mp4", () -> {

        });


        //-----------------------------------------------------------------------------------------------------------//
        //Gameplay//
        
        player = new Player(700, 800, 270);

        ArrayList<Rocks> rocks = new ArrayList<>();
        ArrayList<Overlord> overlords = new ArrayList<>();
        ArrayList<Mutalisk> mutas = new ArrayList<>();

        Random rnd = new Random();

        rockSpawner = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            int count = (rnd.nextInt(2));

            for (int i = 0; i < count; i++) {
                Random random = new Random();
                Rocks rock = new Rocks(random.nextInt(1400), random.nextInt(100) - 120);
                int f = (rnd.nextInt(5) + 5);
                for (int x = 0; x < f; x++) {
                    rock.accelerate();
                }
                rocks.add(rock);
                layout.getChildren().add(rock.getRock());
            }
        }));

        rockSpawner.setCycleCount(Timeline.INDEFINITE);
        rockSpawner.play();

        final int[] seconds = { 0 };

        timer = new Timeline(new KeyFrame(Duration.seconds(1), te -> {
            seconds[0]++;
            int minutes = seconds[0] / 60;
            int secs = seconds[0] % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, secs));

            //-----------------------------------------------------------------------------------------------------------//
            //Spawners//

            if (seconds[0] == 34) {
                overlordSFXPlayer = new Timeline(new KeyFrame(Duration.seconds(3), se -> {
                int index = new Random().nextInt(overlordSFXPaths.length);

                    Media sound = new Media(getClass().getResource(overlordSFXPaths[index]).toExternalForm());
                    MediaPlayer sfx = new MediaPlayer(sound);
                    VolumeManager.register(sfx);
                    sfx.play();
                }));
                overlordSFXPlayer.setCycleCount(Timeline.INDEFINITE);
                overlordSFXPlayer.play();

                overlordSpawnerRunning = true;
                overlordSpawner = new Timeline(new KeyFrame(Duration.seconds(1), se -> {
                    int count = (1);

                    for (int i = 0; i < count; i++) {
                        Random random = new Random();
                        Overlord overlord = new Overlord(random.nextInt(1200) + 100, random.nextInt(100) - 120);

                        for (int x = 0; x < 15; x++) {
                            overlord.accelerate();
                        }
                        overlords.add(overlord);
                        layout.getChildren().add(overlord.getBox());
                        layout.getChildren().add(overlord.getSprite());
                    }
                }));
                overlordSpawner.setCycleCount(60);
                overlordSpawner.setOnFinished(se -> {
                    overlordSpawnerRunning = false;
                    if (overlordSFXPlayer != null) {
                        overlordSFXPlayer.stop();
                    }
                });
                overlordSpawner.play();
            }

            if (seconds[0] == 141) {
                overlordSFXPlayer = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
                int index = new Random().nextInt(overlordSFXPaths.length);

                    Media sound = new Media(getClass().getResource(overlordSFXPaths[index]).toExternalForm());
                    MediaPlayer sfx = new MediaPlayer(sound);
                    VolumeManager.register(sfx);
                    sfx.play();
                }));
                overlordSFXPlayer.setCycleCount(Timeline.INDEFINITE);
                overlordSFXPlayer.play();

                overlordSpawnerRunning = true;
                overlordSpawner = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> {
                    int count = (1);

                    for (int i = 0; i < count; i++) {
                        Random random = new Random();
                        Overlord overlord = new Overlord(random.nextInt(1200) + 100, random.nextInt(100) - 120);

                        for (int x = 0; x < 15; x++) {
                            overlord.accelerate();
                        }
                        overlords.add(overlord);
                        layout.getChildren().add(overlord.getBox());
                        layout.getChildren().add(overlord.getSprite());
                    }
                }));
                overlordSpawner.setCycleCount(29);
                overlordSpawner.setOnFinished(e -> {
                    overlordSpawnerRunning = false;
                    if (overlordSFXPlayer != null) {
                        overlordSFXPlayer.stop();
                    }
                });
                overlordSpawner.play();

            }

            if (seconds[0] == 184) {
                mutaSFXPlayer = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
                int index = new Random().nextInt(mutaSFXPaths.length);

                    Media sound = new Media(getClass().getResource(mutaSFXPaths[index]).toExternalForm());
                    MediaPlayer sfx = new MediaPlayer(sound);
                    VolumeManager.register(sfx);
                    sfx.play();
                }));
                mutaSFXPlayer.setCycleCount(Timeline.INDEFINITE);
                mutaSFXPlayer.play();

                mutaSpawnerRunning = true;
                mutaSpawner = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    int count = (rnd.nextInt(4));

                    for (int i = 0; i < count; i++) {
                        Random random = new Random();
                        Mutalisk muta = new Mutalisk(random.nextInt(1200) + 100, random.nextInt(100) - 120);

                        for (int x = 0; x < 40; x++) {
                            muta.accelerate();
                        }
                        mutas.add(muta);
                        layout.getChildren().add(muta.getBox());
                        layout.getChildren().add(muta.getSprite());
                    }
                }));
                mutaSpawner.setCycleCount(63);
                mutaSpawner.setOnFinished(e -> {
                    mutaSpawnerRunning = false;
                    if (mutaSFXPlayer != null) {
                        mutaSFXPlayer.stop();
                    }
                });
                mutaSpawner.play();
            }

            //-----------------------------------------------------------------------------------------------------------//
            //Dialogue//

            if (seconds[0] == 3) {
                showDialogue("Rykor: Char… nothing but ash and rocks.", Duration.seconds(3));
            }

            if (seconds[0] == 7) {
                showDialogue("Adjutant: Terrain unstable. Fragments detected. Recommend evasive flight path.", Duration.seconds(3));
            }
            
            if (seconds[0] == 12) {
                showDialogue("Rykor: Thanks, toaster.", Duration.seconds(2));
            }

            if (seconds[0] == 15) {
                showDialogue("Rykor: I’ll try not to land in another crater...", Duration.seconds(3));
            }

            if (seconds[0] == 30) {
                showDialogue("Adjutant: Zerg units detected.", Duration.seconds(3));
            }

            if (seconds[0] == 34) {
                showDialogue("Rykor: “Overlords !!!”", Duration.seconds(3));
            }

            if (seconds[0] == 60 + 45) {
                showDialogue("Rykor: “Okay, we're out...”", Duration.seconds(3));
            }

            if (seconds[0] == 60 + 50) {
                showDialogue("Adjutant: Zerg behavior remains unpredictable. Stay alert.", Duration.seconds(5));
            }

            if (seconds[0] == 60 + 60 + 15) {
                showDialogue("Adjutant: Increased bio-signature density. Multiple Overlords converging.", Duration.seconds(5));
            }

            if (seconds[0] == 60 + 60 + 21) {
                showDialogue("Rykor: You weren’t kidding.", Duration.seconds(3));
            }

            if (seconds[0] == 60 + 60 + 25) {
                showDialogue("Rykor: It’s a damn sky parade !", Duration.seconds(3));
            }

            if (seconds[0] == 60 + 60 + 55) {
                showDialogue("Adjutant: Warning, Zerg Flyers identified !", Duration.seconds(3));
            }

            if (seconds[0] == 60 + 60 + 60 ) {
                showDialogue("Adjutant: Mutalisks.", Duration.seconds(3));
            }

            
            if (seconds[0] == 60 + 60 + 60 + 60 + 15) {
                showDialogue("Rykor: Still breathing…", Duration.seconds(2));
            }

            if (seconds[0] == 60 + 60 + 60 + 60 + 18) {
                showDialogue("Rykor: barely.", Duration.seconds(2));
            }

        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), overlay);
        fadeOut.setFromValue(0);
        fadeOut.setToValue(1);

        fadeOutTime = new PauseTransition(Duration.seconds(265));
        fadeOutTime.setOnFinished(e -> {
            overlay.toFront();
            fadeOut.play();
        });
        fadeOutTime.play();

        fadeOut.setOnFinished(e -> {
            gameLoop.stop();
            Level1End end = new Level1End();
            Scene sceneEnd = end.getScene(stage);
            stage.setScene(sceneEnd);
        });

        //-----------------------------------------------------------------------------------------------------------//
        //Buttons and mechanics//

        //customCursor cursor = new customCursor();
        //Pane cursorPane = cursor.getC();
        //cursorPane.setMouseTransparent(true);
        //ImageView cursorImage = cursor.getCImageView();


        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);

            if (event.getCode() == KeyCode.ESCAPE && !isPaused) {

                pauseOverlay = escScreen(stage);
                root.getChildren().add(pauseOverlay);
                //cursorPane.toFront();

                if (overlordSpawnerRunning) {
                    overlordSpawner.pause();
                    overlordSFXPlayer.pause();
                }

                if (mutaSpawnerRunning) {
                    mutaSpawner.pause();
                    mutaSFXPlayer.pause();
                }

                rockSpawner.pause();
                fadeOutTime.pause();
                timer.pause();
                mediaPlayer.pause();
                mediaLoader.pause();
                gameLoop.stop();              
                isPaused = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    if (player.getCharacter().getTranslateX() >= 50) {
                        player.applyThrust(-0.1, 0);
                    }
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    if (player.getCharacter().getTranslateX() <= 1300) {
                        player.applyThrust(0.1, 0);
                    }
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    if (player.getCharacter().getTranslateY() >= 50) {
                        player.applyThrust(0, -0.1);
                    }
                }

                if (pressedKeys.getOrDefault(KeyCode.DOWN, false)) {
                    if (player.getCharacter().getTranslateY() <= 800) {
                        player.applyThrust(0, 0.1);
                    }
                }

                player.update();
                rocks.forEach(rock -> rock.move());
                overlords.forEach(overlord -> overlord.move());
                mutas.forEach(muta -> muta.move());

                for (Rocks rock : rocks) {
                    if (collideRock(player, rock)) {                      
                        root.getChildren().add(showDefeatScreen(stage));
                        stop();
                        break;
                    }
                }

                for (Overlord overlord : overlords) {
                    if (collideOver(player, overlord)) {                    
                        root.getChildren().add(showDefeatScreen(stage));
                        stop();
                        break;
                    }
                }

                for (Mutalisk muta : mutas) {
                    if (collideMuta(player, muta)) {                      
                        root.getChildren().add(showDefeatScreen(stage));
                        stop();
                        break;
                    }
                }
            }
        };

        gameLoop.start();

        hud.getChildren().addAll(dialogueLabel, overlay);
        root.getChildren().addAll(layout, hud);


        //root.setOnMouseMoved(e -> {
        //    cursorImage.setLayoutX(e.getSceneX());
        //    cursorImage.setLayoutY(e.getSceneY());
        //});

        //root.setOnMouseDragged(e -> {
        //   cursorImage.setLayoutX(e.getSceneX());
        //   cursorImage.setLayoutY(e.getSceneY());
        //});

        layout.getChildren().add(timerLabel);
        layout.getChildren().add(player.getCharacter());
        layout.getChildren().add(player.dropship());
        return scene;
    }

    //Dialogue 

    public void showDialogue(String text, Duration duration) {
        dialogueLabel.setText(text);
        dialogueLabel.toFront();

        PauseTransition pause = new PauseTransition(duration);
        pause.setOnFinished(e -> dialogueLabel.setText(""));
        pause.play();
    }

    // Collision detection methods
    public boolean collideRock(Player player, Rocks rock) {
        Shape collisionArea = Shape.intersect(player.getCharacter(), rock.getRock());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public boolean collideOver(Player player, Overlord overlord) {
        Shape collisionArea = Shape.intersect(player.getCharacter(), overlord.getBox());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public boolean collideMuta(Player player, Mutalisk muta) {
        Shape collisionArea = Shape.intersect(player.getCharacter(), muta.getBox());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    //-----------------------------------------------------------------------------------------------------------//
    //screens//

    //defeat screen 
    public Pane showDefeatScreen(Stage stage) {

        if (overlordSpawnerRunning == true) {
            overlordSpawner.pause();
            overlordSFXPlayer.stop();
        }

        if (mutaSpawnerRunning == true) {
            mutaSpawner.pause();
            mutaSFXPlayer.stop();
        }

        rockSpawner.pause();
        fadeOutTime.pause();
        timer.pause();
        mediaPlayer.pause();
        mediaLoader.pause();
        fadeOutTime.pause();

        Pane defeat = new Pane();
        defeat.setPrefSize(1400, 900);
        defeat.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");

        VBox box = new VBox(20);
        box.setPrefSize(400, 300);
        box.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-border-color:  #39ff14; -fx-border-width: 2;");
        box.setAlignment(Pos.CENTER);

        box.setLayoutX((1400 - 400) / 2.0);
        box.setLayoutY((900 - 300) / 2.0);

        Label end = new Label("Defeat.");
        end.setStyle("-fx-text-fill:  #39ff14; -fx-font-size: 36px;");

        Button restart = new Button("Restart");
        restart.getStyleClass().add("neon-button");
        Button backToMenu = new Button("Back to Menu");
        backToMenu.getStyleClass().add("neon-button");

        restart.setOnAction(e -> {
            Level1Game newGame = new Level1Game();
            Scene newScene = newGame.getScene(stage);
            stage.setScene(newScene);
            fadeOutTime.stop();
            fadeOutTime.play();
        });

        backToMenu.setOnAction(e -> {
            buttonSFX = new MediaPlayer(button);
            VolumeManager.register(buttonSFX);
            buttonSFX.play();

            mediaPlayer.pause();
            mediaLoader.pause();
            mediaPlayer.dispose();
            mediaLoader.dispose();
            App menu = new App();
            Scene menuScene = menu.getMainMenuScene(stage);
            stage.setScene(menuScene);

        });

        box.getChildren().addAll(end, restart, backToMenu);
        defeat.getChildren().add(box);
        deathAnimation();

        return defeat;
    }

    // ESC pause screen
    public Pane escScreen(Stage stage) {

        Pane esc = new Pane();
        esc.setPrefSize(1400, 900);
        esc.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");

        VBox box = new VBox(20);
        box.setPrefSize(400, 300);
        box.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-border-color:  #39ff14; -fx-border-width: 2;");
        box.setAlignment(Pos.CENTER);
        box.setLayoutX((1400 - 400) / 2.0);
        box.setLayoutY((900 - 300) / 2.0);

        Label pauseLabel = new Label("Paused");
        pauseLabel.setStyle("-fx-text-fill:  #39ff14; -fx-font-size: 36px;");

        Button resume = new Button("Resume");
        resume.getStyleClass().add("neon-button");
        resume.setOnAction(e -> {
            
            root.getChildren().remove(pauseOverlay);
            pauseOverlay = null;

            if (overlordSpawnerRunning == true) {
                overlordSpawner.play();
                overlordSFXPlayer.play();
            }

            if (mutaSpawnerRunning == true) {
                mutaSpawner.play();
                mutaSFXPlayer.play();
            }

            rockSpawner.play();
            fadeOutTime.play();
            timer.play();
            mediaPlayer.play();
            mediaLoader.play();
            gameLoop.start();

            isPaused = false;           
        });

        Button backToMenu = new Button("Back to Menu");
        backToMenu.getStyleClass().add("neon-button");
        backToMenu.setOnAction(e -> {

            buttonSFX = new MediaPlayer(button);
            VolumeManager.register(buttonSFX);
            buttonSFX.play();

            App menu = new App();
            Scene menuScene = menu.getMainMenuScene(stage);
            stage.setScene(menuScene);

        });

        box.getChildren().addAll(pauseLabel, resume, backToMenu);
        esc.getChildren().add(box);

        return esc;
    }

    public void deathAnimation() {
        Image image = new Image(getClass().getResource("assets/sprites/explotion-explode.gif").toExternalForm());
        ImageView explosion = new ImageView(image);

        Media media = new Media(getClass().getResource("assets/audio/dropshipDeath.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        VolumeManager.register(mediaPlayer);
        mediaPlayer.play();

        explosion.setTranslateX(player.getX());
        explosion.setTranslateY(player.getY());

        layout.getChildren().add(explosion);

        layout.getChildren().remove(player.dropship());

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            layout.getChildren().remove(explosion);
        });

        delay.play();
    }

}
