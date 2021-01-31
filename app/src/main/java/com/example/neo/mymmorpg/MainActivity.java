package com.example.neo.mymmorpg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //private ViewAnimator simpleViewAnimator;
    public static MediaPlayer mediaPlayer;
    public static int width, height;
    private ViewPager pager;

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FragmentA.newInstance();
                case 1: // Fragment # 1 - This will show FirstFragment different title
                    return FragmentB.newInstance();
                case 2: // Fragment # 2 - This will show SecondFragment
                    return FragmentC.newInstance();
                case 3: // Fragment # 3 - This will show SecondFragment
                    return FragmentD.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display mDisplay = this.getWindowManager().getDefaultDisplay();
        width = mDisplay.getWidth();
        height = mDisplay.getHeight();

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(4);
        pager.setCurrentItem(0);

        //mTextMessage = (TextView) v.findViewById(R.id.mytxtview);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onBackPressed() {
        if(FragmentA.gameView != null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Quit");
            alertDialog.setMessage("Are you sure you want to quit ?");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.show();
        }
    }

    private void createView(final int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //stuff that updates ui
                //todo
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pager.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_map:
                    pager.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_bag:
                    pager.setCurrentItem(2, false);
                    return true;
                case R.id.navigation_notifications:
                    pager.setCurrentItem(3, false);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer = MediaPlayer.create(this, R.raw.mainsong);
        //System.out.println("iicccccccccciiii " + mediaPlayer.getDuration());
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        try{
            if(mediaPlayer !=null && mediaPlayer.isPlaying()){
                Log.d("TAG------->", "player is running");
                mediaPlayer.stop();
                Log.d("Tag------->", "player is stopped");
                mediaPlayer.release();
                Log.d("TAG------->", "player is released");
            }
        } catch(Exception ignored) {}
    }

    // This method executes when the player stops the game
    @Override
    protected void onStop() {
        super.onStop();

        try{
            if(mediaPlayer !=null && mediaPlayer.isPlaying()){
                Log.d("TAG------->", "player is running");
                mediaPlayer.stop();
                Log.d("Tag------->", "player is stopped");
                mediaPlayer.release();
                Log.d("TAG------->", "player is released");
            }
        } catch(Exception ignored) {}
    }
}
