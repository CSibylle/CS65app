package edu.dartmouth.cs.memosnap;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseFacebookUtils;

import java.util.ArrayList;

import edu.dartmouth.cs.memosnap.view.SlidingTabLayout;


public class SocialActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        getActionBar().setDisplayShowHomeEnabled(true);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // create a fragment list in order.
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new WallFragment());
        fragments.add(new SharedFragment());
        fragments.add(new FriendsFragment());

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles) and ViewPager (different pages of fragment) together.
        ActionTabsViewPagerAdapter myViewPageAdapter = new ActionTabsViewPagerAdapter(getFragmentManager(),
                fragments, this, "Social");
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
