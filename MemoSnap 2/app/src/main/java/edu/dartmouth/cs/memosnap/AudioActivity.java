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
import android.widget.TextView;

import java.io.IOException;


public class AudioActivity extends Activity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    private boolean mRecording = true;
    private boolean mPlaying = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        getActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
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


    public void onSaveClicked(View v) {
        Intent intent = new Intent(this, SaveActivity.class);
        intent.putExtra("Audio", "audio");
        startActivity(intent);
    }

    public void onCancelClicked(View v) {
        finish();
    }
}
