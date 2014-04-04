package partybutler.player.files.queue;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import partybutler.Constants;
import partybutler.player.CurrentSongInformation;
import partybutler.player.files.interfaces.MediaFile;
import partybutler.player.media.PartyPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 28.03.2014.
 */
public class QueueManager {

    private static QueueManager instance = new QueueManager();
    private PartyPlayer player = PartyPlayer.getInstance();
    private ArrayList<ActionListener> actionListeners;

    private QueueManager() {
        actionListeners = new ArrayList<>();
        PublicQueue.getInstance().addActionListener(publicQueueListener);
        UserQueue.getInstance().addActionListener(userQueueListener);
    }

    public void addActionListener(ActionListener listener){
        actionListeners.add(listener);
    }

    public void removeActionListener(ActionListener listener){
        actionListeners.remove(listener);
    }

    private void notifyActionListeners(){
        for(ActionListener listener : actionListeners){
            listener.actionPerformed(new ActionEvent(this,0,"Queue changed"));
        }
    }

    public MediaFile getNextMediaFileInQueue(){
        MediaFile nextFile = UserQueue.getInstance().getNextMediaFile();
        if(nextFile == null){
            nextFile = PublicQueue.getInstance().getNextMediaFile();
        }
        return nextFile;
    }

    public ArrayList<MediaFile> getQueuedMediaFiles(){
        ArrayList<MediaFile> mediaFiles = new ArrayList<>();
        mediaFiles.addAll(UserQueue.getInstance().getEnqueuedMediaFiles());
        mediaFiles.addAll(PublicQueue.getInstance().getEnqueuedMediaFiles());
        return mediaFiles;
    }

    public void playAndRemoveFromQueue(MediaFile file){
        playMediaFile(file);
    }

    private void playMediaFile(MediaFile file) {
        if(file != null) {
            if (player.isRunning()) {
                player.stop();
            }
            player.setMediaFile(file);
            player.start();
        }
        CurrentSongInformation.getInstance().setNowPlaying(file);
    }

    public static QueueManager getInstance() {
        return instance;
    }


    private ActionListener publicQueueListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            notifyActionListeners();
        }
    };

    private ActionListener userQueueListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getID() == Constants.LISTENER_ACTION_ID_ENQUEUED_FIRST){
                playMediaFile(getNextMediaFileInQueue());
            }

            notifyActionListeners();
        }
    };

    private BasicPlayerListener playerListener = new BasicPlayerListener() {
        @Override
        public void opened(Object o, Map map) {

        }

        @Override
        public void progress(int i, long l, byte[] bytes, Map map) {

        }

        @Override
        public void stateUpdated(BasicPlayerEvent basicPlayerEvent) {
            if(basicPlayerEvent.getCode() == BasicPlayerEvent.EOM){
                playMediaFile(getNextMediaFileInQueue());
            }
        }

        @Override
        public void setController(BasicController basicController) {

        }
    };
}
