package partybutler.player.files.queue;

import partybutler.Constants;
import partybutler.player.files.interfaces.MediaFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Administrator on 23.03.2014.
 */
public class UserQueue {
    
    private static UserQueue instance = new UserQueue();
    private MediaFileQueue queue;
    private ArrayList<ActionListener> actionListeners;

    private UserQueue() {
        queue = new MediaFileQueue();
        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener listener){
        actionListeners.add(listener);
    }

    public void removeActionListener(ActionListener listener){
        actionListeners.remove(listener);
    }

    private void notifyActionListeners(int actionId){
        for(ActionListener listener : actionListeners){
            listener.actionPerformed(new ActionEvent(this,actionId,"Song queued into Private Queue"));
        }
    }

    /**
     * Makes sure the next MediaFile will be the first in queue
     * @param mediaFile
     */
    public void enqueueFirst(MediaFile mediaFile){
        queue.addMediaFileWithWishcount(mediaFile,queue.getMaxWishcount()+1);
        notifyActionListeners(Constants.LISTENER_ACTION_ID_ENQUEUED_FIRST);
    }

    /**
     * Makes sure that all of the mediafiles will come first
     * @param mediaFiles
     */
    public void enqueueAllFirst(ArrayList<MediaFile> mediaFiles){
        for (MediaFile file : mediaFiles){
            queue.addMediaFileWithWishcount(file, queue.getMaxWishcount()+1);
        }
        notifyActionListeners(Constants.LISTENER_ACTION_ID_ENQUEUED_FIRST);
    }

    public void enqueueLast(MediaFile mediaFile){
        queue.add(mediaFile);
        notifyActionListeners(Constants.LISTENER_ACTION_ID_ENQUEUED_LAST);
    }

    public void enqueueAllLast(ArrayList<MediaFile> mediaFiles){
        for(MediaFile file : mediaFiles){
            queue.add(file);
        }
        notifyActionListeners(Constants.LISTENER_ACTION_ID_ENQUEUED_LAST);
    }

    public MediaFile getNextMediaFile(){
        return queue.poll();
    }

    public ArrayList<MediaFile> getEnqueuedMediaFiles() {
        return queue.peekAll();
    }

    public static UserQueue getInstance() {
        return instance;
    }

}
