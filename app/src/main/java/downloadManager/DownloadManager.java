package downloadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jiricaga on 5/8/15.
 */
public class DownloadManager implements DownloadTaskListener{
    private boolean running; //variable information if actually pass downloading file from web
    private Queue<DownloadItem> downloadQueue;
    private List<DownloadManagerListener> listeners;
    private DownloadItem actualDownloading;
    private static DownloadManager manager;

    //remove commments please

    /**
     * Private constructor
     * Prevent create own instance managers.
     */
    private DownloadManager(){
        downloadQueue = new LinkedBlockingQueue<DownloadItem>(10);
        listeners=new ArrayList<DownloadManagerListener>(10);
        actualDownloading = null;
        running=false;
    }

    /**
     * Get manager instance, because manager can be in application only one.
     * @return
     */
    public static DownloadManager getInstance(){
        if(manager==null)
            manager = new DownloadManager();
        return manager;
    }

    /**
     * Add item to download queue.
     * @param url web url
     * @param file out put file for save from web
     */
    public void addToDownload(String url, File file){
        DownloadItem item= new DownloadItem(url,file);
        downloadQueue.add(item);
        if(!running)
            startDownloading();
    }

    public void addListener(DownloadManagerListener l){
        listeners.add(l);
    }

    public void removeListener(DownloadManagerListener l){
        listeners.remove(l);
    }

    /**
     * Get first item from download queue and start async
     * task for downloading file.
     */
    private void startDownloading(){
        actualDownloading = downloadQueue.remove();
        DownloadTask task = new DownloadTask(actualDownloading,this);
        task.execute();
        running = true;
    }

    /**
     * Method stop actually downloaded task in async task
     */
    private void stopDownloading(){
        //TO DO ...
    }

    @Override
    public void onProgressUpdate(int percentage) {
        for(DownloadManagerListener l:listeners)
            l.onProgressUpdate(downloadQueue.size()+1,percentage);
    }

    @Override
    public void onDownloadFinish(String url, File outFile, boolean successfull) {
        for(DownloadManagerListener l:listeners)
            l.onDownloadFinish(url,outFile,successfull);

        //check queue and if is not empty start download other item
        if(!downloadQueue.isEmpty())
        {
            startDownloading();
        }
        else
        {
            running = false;
            actualDownloading = null;
        }
    }
}
