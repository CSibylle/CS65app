package edu.dartmouth.cs.memosnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class SaveActivity extends Activity {

    EditText mDateTime;
    EditText mType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

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
    public void onSaveClicked(View v) {
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
