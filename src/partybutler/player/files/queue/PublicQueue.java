package partybutler.player.files.queue;

import partybutler.player.files.interfaces.MediaFile;

/**
 * Created by Administrator on 23.03.2014.
 */
public class PublicQueue {
    private static PublicQueue instance = new PublicQueue();

    private MediaFileQueue mediaFileQueue;

    private PublicQueue() {
        mediaFileQueue = new MediaFileQueue();
    }

    public void enqueue(MediaFile file) {
        mediaFileQueue.add(file);
    }

    public MediaFile getNextMediaFile() {
        return mediaFileQueue.poll();
    }

    public String getNextMediaTitle() {
        return mediaFileQueue.peek().getTitle();
    }

    public static PublicQueue getInstance() {
        return instance;
    }
}
