package edu.dartmouth.cs.memosnap;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseFacebookUtils;


public class SocialActivity extends FragmentActivity {
    private Button mWallBtn;
    private Button mSharedBtn;
    private Button mFriendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        getActionBar().setDisplayShowHomeEnabled(true);

        mWallBtn = (Button) findViewById(R.id.wall);
        mSharedBtn = (Button) findViewById(R.id.shared);
        mFriendBtn = (Button) findViewById(R.id.friends);

        mWallBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mWallBtn.setBackgroundColor(Color.rgb(57, 149, 89));

                Fragment frag = new WallFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.socialFrags, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mSharedBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSharedBtn.setBackgroundColor(Color.rgb(97, 85, 181));
                Fragment frag = new SharedFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.socialFrags, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mFriendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mFriendBtn.setBackgroundColor(Color.rgb(65, 116, 162));
                Fragment frag = new FriendsFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.socialFrags, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
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

        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
