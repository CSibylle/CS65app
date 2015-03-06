package edu.dartmouth.cs.memosnap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.ArrayList;

public class ActionTabsViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public static final int START = 0;
    public static final int HISTORY = 1;
    public static final int SETTINGS = 2;
    public static final String UI_TAB_START = "START";
    public static final String UI_TAB_HISTORY = "HISTORY";
    public static final String UI_TAB_SETTINGS = "SETTINGS";

    private int[] imageResId = {
            R.drawable.photosicon,
            R.drawable.notesicon,
            R.drawable.audiosicon,
    };

    Context context;


    public ActionTabsViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, Context context,String tabType){
        super(fm);
        this.fragments = fragments;
        this.context = context;

        if (tabType == "Social" ) {
            imageResId[0] = R.drawable.walltab;
            imageResId[1] = R.drawable.sharedtab;
            imageResId[2] = R.drawable.friendstab;
        }
        else {
            imageResId[0] = R.drawable.photosicon;
            imageResId[1] = R.drawable.notesicon;
            imageResId[2] = R.drawable.audiosicon;
        }
    }

    public Fragment getItem(int pos){
        return fragments.get(pos);
    }

    public int getCount(){
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}