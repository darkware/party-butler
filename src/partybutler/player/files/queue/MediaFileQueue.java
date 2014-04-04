package partybutler.player.files.queue;

import org.apache.log4j.Logger;
import partybutler.player.files.interfaces.MediaFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by Administrator on 24.03.2014.
 */
public class MediaFileQueue implements Queue<MediaFile> {

    private HashMap<MediaFile, Integer> mediaMap = null;
    private HashMap<MediaFile, Long> entryTimeMap = null;

    public MediaFileQueue() {
        mediaMap = new HashMap<MediaFile, Integer>();
        entryTimeMap = new HashMap<MediaFile, Long>();
    }

    @Override
    public int size() {
        return mediaMap.size();
    }

    @Override
    public boolean isEmpty() {
        return mediaMap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mediaMap.containsKey(o);
    }

    /**
     * Returns an Iterator over the MediaFiles
     *
     * @return Iterator over the MediaFiles
     */
    @Override
    public Iterator<MediaFile> iterator() {
        return mediaMap.keySet().iterator();
    }


    /**
     * Converts the Queue to an MediaFile array
     *
     * @return Array of the MediaFiles
     */
    @Override
    public Object[] toArray() {
        return mediaMap.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new NotImplementedException();
    }

    /**
     * Adds MediaFile to the Queue with fixed Wishcount (overwrites previous wishcounts)
     * @param file MediaFile to be put in the Queue
     * @param wishcount Desired Wishcount
     */
    public void addMediaFileWithWishcount(MediaFile file, Integer wishcount){
        Logger.getRootLogger().debug("Media File (" + file.getTitle() + ") put in Queue: With fixed wishcount: " + wishcount);
        mediaMap.put(file, wishcount);
        entryTimeMap.put(file, System.currentTimeMillis());
    }

    /**
     * Returns the currently Maximum Wishcount
     * @return Maximum Wishcount in moment
     */
    public Integer getMaxWishcount(){
        Integer maxCount = 0;
        for(Integer count : mediaMap.values()){
            if(count > maxCount){
                maxCount = count;
            }
        }

        return maxCount;
    }

    /**
     * Returns the Media Files as an ArrayList
     * @return ArrayList of all enqueued MediaFiles
     */
    public ArrayList<MediaFile> peekAll() {
        //TODO: MAYBE CHANGE THIS MIGHT BE INNEFFICIENT (IT SURE IS)
        ArrayList<MediaFile> files = new ArrayList<>();

        for(int i = 0; i < mediaMap.keySet().size(); i++) {
            MediaFile maxWishFile = null;
            Integer maxWishCount = 0;

            Iterator<MediaFile> iterator = iterator();
            while (iterator.hasNext()) {
                MediaFile currentFile = iterator.next();
                if(!files.contains(currentFile)) {
                    Integer currentWishcount = mediaMap.get(currentFile);
                    if (currentWishcount > maxWishCount) {
                        maxWishCount = currentWishcount;
                        maxWishFile = currentFile;
                    } else if (currentWishcount == maxWishCount) {
                        Long maxWishTime = entryTimeMap.get(maxWishFile);
                        Long currentWishTime = entryTimeMap.get(currentFile);
                        if (currentWishTime < maxWishTime) {
                            maxWishCount = currentWishcount;
                            maxWishFile = currentFile;
                        }
                    }
                }
            }

            files.add(maxWishFile);
        }

        return files;
    }

    /**
     * Adds the MediaFile to the Queue
     *
     * @param mediaFile the MediaFile to be added to the queue
     * @return true if MediaFile wasn't in queue yet, false if it already was, but increases the wishcount of the given media
     */
    @Override
    public boolean add(MediaFile mediaFile) {
        Logger.getRootLogger().debug("Adding MediaFile (" + mediaFile.getTitle() + ") to Queue");
        if (mediaMap.containsKey(mediaFile)) {
            Integer newCount = mediaMap.get(mediaFile) + 1;
            Logger.getRootLogger().debug("Media File (" + mediaFile.getTitle() + ") already in Queue: Increasing wishcount to: " + newCount);
            mediaMap.put(mediaFile, newCount);
            return false;
        } else {
            Long currentTime = System.currentTimeMillis();
            Logger.getRootLogger().debug("Media File (" + mediaFile.getTitle() + ") not yet in Queue, adding it with wishcount 1 and Timestamp: " + currentTime);
            mediaMap.put(mediaFile, 1);
            entryTimeMap.put(mediaFile, currentTime);
            return true;
        }
    }

    /**
     * Removes Object from the Queue
     *
     * @param o object to be removed
     * @return true if object was in the queue, false otherwise
     */
    @Override
    public boolean remove(Object o) {
        Integer count = mediaMap.remove(o);
        entryTimeMap.remove(o);
        return count != null;
    }


    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    /**
     * Adds all MediaFiles to the queue
     *
     * @param c collection of @MediaFile
     * @return true if not a single MediaFile was in queue yet, false if at least one already was, but increases the wishcounts of the given media files
     */
    @Override
    public boolean addAll(Collection<? extends MediaFile> c) {
        boolean success = true;
        for (MediaFile file : c) {
            success = success && add(file);
        }
        return success;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        mediaMap.clear();
        entryTimeMap.clear();
    }

    /**
     * USE add instead
     *
     * @param mediaFile
     * @return
     */
    @Override
    public boolean offer(MediaFile mediaFile) {
        throw new NotImplementedException();
    }

    @Override
    public MediaFile remove() {
        throw new NotImplementedException();
    }

    /**
     * Returns the MediaFile with the most Wishcounts, otherwise the one with the longest Queuetime
     *
     * @return MediaFile with max Wishcounts or null if no songs are found
     */
    @Override
    public MediaFile poll() {
        MediaFile maxWishFile = null;
        Integer maxWishCount = 0;

        Iterator<MediaFile> iterator = iterator();
        while (iterator.hasNext()) {
            MediaFile currentFile = iterator.next();
            Integer currentWishcount = mediaMap.get(currentFile);
            if (currentWishcount > maxWishCount) {
                maxWishCount = currentWishcount;
                maxWishFile = currentFile;
            } else if (currentWishcount == maxWishCount) {
                Long maxWishTime = entryTimeMap.get(maxWishFile);
                Long currentWishTime = entryTimeMap.get(currentFile);
                if (currentWishTime < maxWishTime) {
                    maxWishCount = currentWishcount;
                    maxWishFile = currentFile;
                }
            }
        }
        if (maxWishFile != null) {
            Logger.getRootLogger().debug("Polling and Removing MediaFile from Queue: " + maxWishFile.getTitle() + " - Wishcount: " + maxWishCount + " Timestamp: " + entryTimeMap.get(maxWishFile));
            mediaMap.remove(maxWishFile);
            entryTimeMap.remove(maxWishFile);
        }
        return maxWishFile;
    }

    @Override
    public MediaFile element() {
        throw new NotImplementedException();
    }

    @Override
    public MediaFile peek() {
        MediaFile maxWishFile = null;
        Integer maxWishCount = 0;

        Iterator<MediaFile> iterator = iterator();
        while (iterator.hasNext()) {
            MediaFile currentFile = iterator.next();
            Integer currentWishcount = mediaMap.get(currentFile);
            if (currentWishcount > maxWishCount) {
                maxWishCount = currentWishcount;
                maxWishFile = currentFile;
            } else if (currentWishcount == maxWishCount) {
                Long maxWishTime = entryTimeMap.get(maxWishFile);
                Long currentWishTime = entryTimeMap.get(currentFile);
                if (currentWishTime < maxWishTime) {
                    maxWishCount = currentWishcount;
                    maxWishFile = currentFile;
                }
            }
        }
        return maxWishFile;
    }
}
