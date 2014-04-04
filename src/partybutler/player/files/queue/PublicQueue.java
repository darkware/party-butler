package partybutler.player.files.queue;

import partybutler.player.files.interfaces.MediaFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Administrator on 23.03.2014.
 */
public class PublicQueue {
    private static PublicQueue instance = new PublicQueue();

    private MediaFileQueue mediaFileQueue;

    private ArrayList<ActionListener> actionListeners;

    private PublicQueue() {
        mediaFileQueue = new MediaFileQueue();
        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener listener){
        actionListeners.add(listener);
    }

    public void removeActionListener(ActionListener listener){
        actionListeners.remove(listener);
    }

    private void notifyActionListeners(){
        for(ActionListener listener : actionListeners){
            listener.actionPerformed(new ActionEvent(this,0,"Song queued into Public Queue"));
        }
    }

    public void enqueue(MediaFile file) {
        mediaFileQueue.add(file);
        notifyActionListeners();
    }

    public MediaFile getNextMediaFile() {
        return mediaFileQueue.poll();
    }

    public String getNextMediaTitle() {
        return mediaFileQueue.peek().getTitle();
    }

    public MediaFileQueue getMediaFileQueue() {
        return mediaFileQueue;
    }

    public ArrayList<MediaFile> getEnqueuedMediaFiles() {
        return mediaFileQueue.peekAll();
    }

    public static PublicQueue getInstance() {
        return instance;
    }
}
