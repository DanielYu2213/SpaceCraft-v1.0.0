import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MediaLoader {
    private int retryCount = 0;
    private MediaPlayer mediaPlayer;
    private boolean finished = false;

    public void loadAndPlayVideo(Stage stage, StackPane layout, String path, Runnable onFinish) {
        Media media = new Media(getClass().getResource(path).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        layout.getChildren().add(0, mediaView);

        mediaPlayer.setOnError(() -> {
            System.out.println("Video error: " + mediaPlayer.getError());
            mediaPlayer.dispose();

            if (retryCount < 20) {
                retryCount++;
                System.out.println("Retrying... attempt " + retryCount);
                loadAndPlayVideo(stage, layout, path, onFinish);
            } else {
                System.out.println("Giving up after 5 retries.");
            }
        });

        mediaPlayer.setOnReady(() -> {
            System.out.println("Video is ready, playing...");
            VolumeManager.register(mediaPlayer);
            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("Video finished.");
                pause();
                dispose();
                onFinish.run();  // <-- callback to continue after video
            });
        });

    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public boolean isFinished() {
        return finished;
    }

    public void pause() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    public void dispose() {
        if (mediaPlayer != null) mediaPlayer.dispose();
    }

    public void play() {
        if (mediaPlayer != null) mediaPlayer.play();
    }


}
