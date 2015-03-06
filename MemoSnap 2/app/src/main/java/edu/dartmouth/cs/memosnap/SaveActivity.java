package edu.dartmouth.cs.memosnap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class SaveActivity extends Activity {

    EditText mDateTime;
    EditText mType;
    String mNote;
    byte[] mPhotoByteArray;
    byte[] mRecordingByteArray;

    private Snap entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        mDateTime = (EditText) findViewById(R.id.save_datetime);
        mType = (EditText) findViewById(R.id.save_type);

        if (getIntent().getStringExtra("History") != null) {
            if (getIntent().getStringExtra("History").contentEquals("Camera") ||
                    getIntent().getStringExtra("History").contentEquals("Audio"))
                displayEntry();
        } else {
            entry = new Snap();

            if (getIntent().getStringExtra("DateTime") != null) {
                String dateTimeString = getIntent().getStringExtra("DateTime");
                mDateTime.setText(dateTimeString);
            }

            if (getIntent().getStringExtra("Type") != null) {
                String typeString = getIntent().getStringExtra("Type");
                mType.setText(typeString);
            }

            if (getIntent().getStringExtra("Type").contentEquals("Camera")) {
                ImageView image = (ImageView) findViewById(R.id.save_photo);
                image.setLayoutParams(new LinearLayout.LayoutParams(600, 600));
                image.setVisibility(View.VISIBLE);
                mPhotoByteArray = getIntent().getByteArrayExtra("Photo");
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(
                            mPhotoByteArray);
                    Bitmap bmp = BitmapFactory.decodeStream(bis);
                    image.setImageBitmap(bmp);
                } catch (Exception ex) {
                }

            }
            else if (getIntent().getStringExtra("Type").contentEquals("Audio")) {
                mRecordingByteArray = getIntent().getByteArrayExtra("Audio Byte Array");
            }

            if (getIntent().getStringExtra("Note") != null) {
                mNote = getIntent().getStringExtra("Note");

                if (getIntent().getStringExtra("History") != null) {
                    EditText name = (EditText) findViewById(R.id.editName);
                    name.setText(getIntent().getStringExtra("Name"));

                    EditText tag = (EditText) findViewById(R.id.editTag);
                    tag.setText(getIntent().getStringExtra("Tag"));
                }
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
        if (id == R.id.action_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_social) {
            Intent intent = new Intent(this, SocialActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileSettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveClicked(View v) {
        SnapDBHelper dbHelper = new SnapDBHelper(getApplicationContext());

        if (getIntent().getLongExtra("id", -1) != -1) {
            dbHelper.removeEntry(getIntent().getLongExtra("id", -1));
        }

        String name = ((EditText) findViewById(R.id.editName)).getText().toString();
        String tag = ((EditText) findViewById(R.id.editTag)).getText().toString();
        entry.setName(name);
        entry.setType(mType.getText().toString());
        entry.setDateTime(mDateTime.getText().toString());
        entry.setTag(tag);
        entry.setNote(mNote);
        entry.setPhoto(mPhotoByteArray);
        entry.setRecording(mRecordingByteArray);

        dbHelper.insertEntry(entry);

        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
        finish();
    }

    public void onCancelClicked(View v) {
        finish();
    }

    public void onShareClicked(View v) {
        Intent intent = new Intent(this, SocialActivity.class);
        startActivity(intent);
    }

    public void onPlayClicked(View v) {
        mRecordingByteArray = entry.getRecording();

        // Play the audio recording if it exists by creating a temp file
        if (mRecordingByteArray != null) {
            try {
                File temp = File.createTempFile("temp", "3gpp", getCacheDir());
                temp.deleteOnExit();

                FileOutputStream fos = new FileOutputStream(temp);
                fos.write(mRecordingByteArray);
                fos.close();

                MediaPlayer mediaPlayer = new MediaPlayer();
                FileInputStream fis = new FileInputStream(temp);
                mediaPlayer.reset();
                mediaPlayer.setDataSource(fis.getFD());
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    private void displayEntry() {
        Button save = (Button) findViewById(R.id.btnSave);
        Button cancel = (Button) findViewById(R.id.btnCancel);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);

        SnapDBHelper dbHelper = new SnapDBHelper(getApplicationContext());

        entry = dbHelper.fetchEntryByIndex(getIntent().getLongExtra("id", -1));

        mDateTime.setText(entry.getDateTime());
        mType.setText(entry.getType());

        TextView name = (TextView) findViewById(R.id.editName);
        TextView tag = (TextView) findViewById(R.id.editTag);

        name.setText(entry.getName());
        name.setCursorVisible(false);
        name.setFocusable(false);
        name.setFocusableInTouchMode(false);

        tag.setText(entry.getTag());
        tag.setCursorVisible(false);
        tag.setFocusable(false);
        tag.setFocusableInTouchMode(false);

        if (getIntent().getStringExtra("History") != null) {
            if (getIntent().getStringExtra("History").contentEquals("Camera")) {
                ImageView image = (ImageView) findViewById(R.id.save_photo);
                image.setLayoutParams(new LinearLayout.LayoutParams(600, 600));
                image.setVisibility(View.VISIBLE);
                mPhotoByteArray = entry.getPhoto();
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(
                            mPhotoByteArray);
                    Bitmap bmp = BitmapFactory.decodeStream(bis);
                    image.setImageBitmap(bmp);
                } catch (Exception ex) {
                }
            }
            else if (getIntent().getStringExtra("History").contentEquals("Audio")) {
                Button play = (Button) findViewById(R.id.play_btn);
                play.setVisibility(View.VISIBLE);
            }
        }

    }
}
