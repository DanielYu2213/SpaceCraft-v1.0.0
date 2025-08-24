import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;
import java.util.List;

public class VolumeManager {
    private static double volume = 0.1;
    private static final List<MediaPlayer> activePlayers = new ArrayList<>();

    public static void setVolume(double v) {
        volume = v;
        // Update all registered MediaPlayers
        for (MediaPlayer player : activePlayers) {
            player.setVolume(volume);
        }
    }

    public static double getVolume() {
        return volume;
    }

    public static void register(MediaPlayer player) {
        player.setVolume(volume);
        activePlayers.add(player);

        // Optional: remove when disposed/stopped
        player.setOnEndOfMedia(() -> activePlayers.remove(player));
        player.setOnStopped(() -> activePlayers.remove(player));
        player.setOnError(() -> activePlayers.remove(player));
    }

    public static void unregister(MediaPlayer player) {
        activePlayers.remove(player);
    }
}
