package com.example.jiricaga.downloadmanager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import downloadManager.DownloadManager;
import downloadManager.DownloadManagerListener;


public class MainActivity extends Activity implements View.OnClickListener, DownloadManagerListener {
    //UI referencess
    private Button button;
    private TextView textViewProgress;

    //logic
    DownloadManager downloadManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.buttonStartDownloading);
        button.setOnClickListener(this);
        textViewProgress = (TextView) findViewById(R.id.textViewProgress);
        downloadManager = DownloadManager.getInstance();
        downloadManager.addListener(this);
    }

    @Override
    public void onClick(View v) {
        File file = new File(Environment.getExternalStorageDirectory(),"obrazek1.jpg");
        String url = "http://stylonica.com/wp-content/uploads/2014/02/nature.jpg";

        File file2 = new File(Environment.getExternalStorageDirectory(),"obrazek2.jpg");
        String url2 = "http://stylonica.com/wp-content/uploads/2014/02/Beauty-of-nature-random-4884759-1280-800.jpg";

        downloadManager.addToDownload(url,file);
        downloadManager.addToDownload(url2,file2);
    }

    @Override
    public void onProgressUpdate(int countDownloadUrl, int percentage) {
        textViewProgress.setText("Downloading " + countDownloadUrl + " item, " + percentage + "%");
    }

    @Override
    public void onDownloadFinish(String url, File outFile, boolean successful) {
        Toast.makeText(this,"File " + outFile.getName() + " is download.",Toast.LENGTH_LONG).show();
    }
}
