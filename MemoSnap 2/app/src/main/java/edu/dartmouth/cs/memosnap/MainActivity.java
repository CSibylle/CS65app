package edu.dartmouth.cs.memosnap;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends Activity {
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final String URI_INSTANCE_STATE_KEY = "saved_uri";
    public static final int REQUEST_CODE_TAKE_FROM_CAMERA = 0;
    public static final int REQUEST_CODE_CROP = 1;
    public SignUpActivity signUpActivity;

    private Uri mProfileImageCaptureUri;
    private ImageView mProfileImageView;
    private boolean isTakenFromCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowHomeEnabled(true);

        signUpActivity = new SignUpActivity();
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
        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_TAKE_FROM_CAMERA:
                Intent intent = new Intent(this, SaveActivity.class);
                intent.putExtra("Type", "Camera");

                // Get the date and send it with the intent
                DateFormat df = new SimpleDateFormat("EEE, MMM d yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
                intent.putExtra("DateTime", date);

                startActivity(intent);
                break;
            default:
                break;

        }
    }

    public void openCam(View v) {
        // create intent to take picture on phone camera，
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // temporary image path and name
        mProfileImageCaptureUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "tmp_"
                + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                mProfileImageCaptureUri);
        intent.putExtra("return-data", true);
        try {
            startActivityForResult(intent, REQUEST_CODE_TAKE_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
        isTakenFromCamera = true;

    }
    public void writeNote(View v) {
        Intent intent = new Intent(this,NoteActivity.class);
        startActivity(intent);


    }
    public void recordAudio(View v) {
        Intent intent = new Intent(this,AudioActivity.class);
        startActivity(intent);

    }
}
