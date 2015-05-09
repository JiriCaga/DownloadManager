package com.example.jiricaga.downloadmanager;
import java.io.File;

/**
 * Created by jiricaga on 5/8/15.
 */
public interface IDownloadManager {
    public IDownloadManager getInstance();
    public void addToDownload(String url, File outFile);
    public boolean calcelAllDownloading();
    public boolean calcelDownloading(String url);
}
