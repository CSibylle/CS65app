package edu.dartmouth.cs.memosnap;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AudioActivity extends Activity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private byte[] mFileByteArray;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    private boolean mRecording = true;
    private boolean mPlaying = true;

    private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        getActionBar().setDisplayShowHomeEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
            //progressBar.stopNestedScroll();
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setScaleX(70f);
            progressBar.setScaleY(70f);
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;

    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void onRecordClicked(View v) {
        onRecord(mRecording);
        Button recordButton = (Button) findViewById(R.id.btnRecord);
        TextView recordText = (TextView) findViewById(R.id.recordTextView);
        if (mRecording) {
            recordButton.setText("Stop recording");
            recordText.setText("Recording...");
        } else {
            recordButton.setText("Record");
            recordText.setText("Press RECORD to start recording.");
        }

        mRecording = !mRecording;
    }

    public void onPlayClicked(View v) {
        onPlay(mPlaying);
        Button playButton = (Button) findViewById(R.id.btnPlay);
        if (mPlaying) {
            playButton.setText("Stop playing");
        } else {
            playButton.setText("Playing");
        }

        mPlaying = !mPlaying;
    }

    public AudioActivity() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }


    public void onContinueClicked(View v) {
        Intent intent = new Intent(this, SaveActivity.class);
        intent.putExtra("Audio", "audio");
        intent.putExtra("Type", "Audio");

        try {
            FileInputStream fis = new FileInputStream(mFileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            mFileByteArray = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        intent.putExtra("Audio Byte Array", mFileByteArray);

        // Get the date and send it with the intent
        DateFormat df = new SimpleDateFormat("EEE, MMM d yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        intent.putExtra("DateTime", date);

        startActivity(intent);
    }

    public void onCancelClicked(View v) {
        finish();
    }
}
