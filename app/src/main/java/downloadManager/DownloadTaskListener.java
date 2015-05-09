package downloadManager;

import java.io.File;

/**
 * Created by jiricaga on 5/8/15.
 */

/**
 * Download task listener, join this listener to class when
 * you can be information about download file.
 */
public interface DownloadTaskListener {
    public void onProgressUpdate(int percentage);
    public void onDownloadFinish(String url, File outFile, boolean successful);
}
