package edu.dartmouth.cs.memosnap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NoteActivity extends Activity {
    private Snap entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (getIntent().getStringExtra("History") != null) {
            long id = getIntent().getLongExtra("id", -1);

            SnapDBHelper dbHelper = new SnapDBHelper(getApplicationContext());

            entry = dbHelper.fetchEntryByIndex(id);

            EditText body = (LineEditText) findViewById(R.id.body);
            body.setText(entry.getNote());
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
        if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onContinueClicked(View v) {
        Intent intent = new Intent(this, SaveActivity.class);
        intent.putExtra("Type", "Note");

        // Get the date and send it with the intent
        DateFormat df = new SimpleDateFormat("EEE, MMM d yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        intent.putExtra("DateTime", date);

        EditText note = (LineEditText) findViewById(R.id.body);
        intent.putExtra("Note", note.getText().toString());

        // Editing from History
        if (getIntent().getStringExtra("History") != null) {
            intent.putExtra("NoteHistory", "history");
            Log.d("history", "coming from note history");
            intent.putExtra("id", entry.getId());
            intent.putExtra("Name", entry.getName());
            intent.putExtra("Tag", entry.getTag());
        }

        startActivity(intent);

        finish();
    }

    public void onCancelClicked(View v) {
        finish();
    }

    /* Custom class for EditText that creates the notepad design. */
    public static class LineEditText extends EditText{
        // we need this constructor for LayoutInflater
        public LineEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            mRect = new Rect();
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setColor(Color.BLUE);
        }

        private Rect mRect;
        private Paint mPaint;

        @Override
        protected void onDraw(Canvas canvas) {

            int height = getHeight();
            int line_height = getLineHeight();

            int count = height / line_height;

            if (getLineCount() > count)
                count = getLineCount();

            Rect r = mRect;
            Paint paint = mPaint;
            int baseline = getLineBounds(0, r);

            for (int i = 0; i < count; i++) {
                canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
                baseline += getLineHeight();

                super.onDraw(canvas);
            }

        }
    }
}
