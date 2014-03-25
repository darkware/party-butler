package partybutler.player.files.web.youtube;

import com.github.axet.vget.info.VideoInfo;
import com.github.axet.vget.info.VideoInfoUser;
import org.apache.commons.io.FileUtils;
import partybutler.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 23.03.2014.
 */
public class YoutubeDownloader implements Runnable {

    private String youtubeUrl = null;
    private Runnable notifyRunnable = null;
    private File outputFile = null;
    private boolean success = false;
    private String title = null;

    public YoutubeDownloader() {
    }

    /**
     * Initializes the downloader
     *
     * @param youtubeUrl     - URL to the youtube video e.g. http://www.youtube.com/watch?v=C3Pxi1afKaQ
     * @param notifyRunnable - The runnable to be notified as soon as the download finishes
     */
    public void initializeDownloader(String youtubeUrl, Runnable notifyRunnable) {
        this.youtubeUrl = youtubeUrl;
        this.notifyRunnable = notifyRunnable;
    }

    @Override
    public void run() {

        try {
            URL url = new URL(Constants.YOUTUBE_DOWNLOAD_LINK + youtubeUrl);

            //Extract Video Title
            VideoInfo info = new VideoInfo(new URL(youtubeUrl));
            info.extract(new VideoInfoUser(), new AtomicBoolean(false), new Runnable() {
                @Override
                public void run() {

                }
            });

            String contentFileName = info.getTitle();
            title = contentFileName;
            outputFile = new File("data/"+contentFileName+".mp3");

            //Download File from Server
            FileUtils.copyURLToFile(url,outputFile,60000,60000);

            if (!success)
                notifyRunnable.run();
            success = true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns the path of the downloaded file
     *
     * @return downloaded file
     */
    public File getOutputFile() {
        return outputFile;
    }

    /**
     * Marks whether the download has been successful.
     *
     * @return - true if download successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the title of the youtube video
     *
     * @return video title
     */
    public String getTitle() {
        return title;
    }
}
