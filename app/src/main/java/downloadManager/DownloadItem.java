package downloadManager;
import java.io.File;

/**
 * Created by jiricaga on 5/8/15.
 */

/**
 * Purpose this class is hold two information, output file
 * for save data and web url.
 */
public class DownloadItem {
    public final String url;
    public final File outFile;

    /**
     * Constructor
     * @param url web url
     * @param outFile file for save data from url
     */
    public DownloadItem(String url, File outFile) {
        this.url = url;
        this.outFile = outFile;
    }
}
