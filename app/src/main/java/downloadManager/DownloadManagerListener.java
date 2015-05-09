package downloadManager;

import java.io.File;

/**
 * Created by jiricaga on 5/8/15.
 */

/**
 * Download manager listener, join this listener to class when
 * you can be information about download file.
 */
public interface DownloadManagerListener {
    public void onProgressUpdate(int countDownloadUrl, int percentage);
    public void onDownloadFinish(String url, File outFile,boolean successful);
}
