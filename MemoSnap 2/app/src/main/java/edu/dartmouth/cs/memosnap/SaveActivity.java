package edu.dartmouth.cs.memosnap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayInputStream;


public class SaveActivity extends Activity {

    EditText mDateTime;
    EditText mType;
    String mNote;

    private Snap entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        entry = new Snap();

        mDateTime = (EditText) findViewById(R.id.save_datetime);
        mType = (EditText) findViewById(R.id.save_type);
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
            image.setLayoutParams(new LinearLayout.LayoutParams(800, 800));
            image.setVisibility(View.VISIBLE);
            byte[] byteArray = getIntent().getByteArrayExtra("Photo");
            Log.d("byteArray Length", Integer.toString(byteArray.length));

            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(
                        byteArray);
                Bitmap bmp = BitmapFactory.decodeStream(bis);
                image.setImageBitmap(bmp);
            } catch (Exception ex) {
            }

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
}
