package com.qa.audiorecord;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String filePath;

    MyServer myServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long time = System.currentTimeMillis();
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + time + ".3gp";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(filePath);

        mediaPlayer = new MediaPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"IP: " + Uitls.getIpAddress(true),Toast.LENGTH_LONG).show();
        myServer = new MyServer(this);

        Thread thread = new Thread(myServer);
        thread.start();
        myServer.sendData();
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btnRecord:
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    Toast.makeText(this, "Recording...", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;

            case R.id.btnStopRecord:
                mediaRecorder.stop();
                mediaRecorder.release();
                Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnPlay:
                try {
                    mediaPlayer.setDataSource(filePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myServer.close();
    }
}
