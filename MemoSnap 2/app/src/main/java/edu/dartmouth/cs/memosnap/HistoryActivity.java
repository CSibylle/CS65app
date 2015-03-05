package edu.dartmouth.cs.memosnap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import edu.dartmouth.cs.memosnap.view.SlidingTabLayout;


public class HistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getActionBar().setDisplayShowHomeEnabled(true);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // create a fragment list in order.
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new SavedShotsFragment());
        fragments.add(new SavedNotesFragment());
        fragments.add(new SavedAudioFragment());

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles) and ViewPager (different pages of fragment) together.
        ActionTabsViewPagerAdapter myViewPageAdapter = new ActionTabsViewPagerAdapter(getFragmentManager(),
                fragments, this,"History");
        viewPager.setAdapter(myViewPageAdapter);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab, 0);
        slidingTabLayout.setViewPager(viewPager);

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

}