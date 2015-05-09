package downloadManager;

import android.os.AsyncTask;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jiricaga on 5/8/15.
 * Purpose this class is download one file from url to file in mobile.
 */
public class DownloadTask extends AsyncTask<Void,Integer,Boolean> {
    private DownloadItem item;
    private DownloadTaskListener listener;

    DownloadTask(DownloadItem item, DownloadTaskListener listener){
         this.item = item;
         this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(item.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(item.outFile);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                {
                    int percentage = (int) (total * 100 / fileLength);
                    publishProgress(percentage);
                }
                Thread.sleep(100);
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean successful) {
        listener.onDownloadFinish(item.url,item.outFile,successful);
    }
}
