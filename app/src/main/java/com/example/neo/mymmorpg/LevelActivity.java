package com.example.neo.mymmorpg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

import static com.example.neo.mymmorpg.FragmentB.battle;

/**
 * Created by neo on 2/25/2018.
 */

public class LevelActivity extends Activity {
    /** Called when the activity is first created. */
    private MediaPlayer mediaPlayer_lvl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battlemap);
        mediaPlayer_lvl = MediaPlayer.create(this, R.raw.mainsong);
        //System.out.println(mediaPlayer.getDuration());
        mediaPlayer_lvl.setLooping(true);
        mediaPlayer_lvl.start();
    }

    @Override
    public void onBackPressed() {
        if(battle != null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Quit");
            alertDialog.setMessage("Are you sure you want to quit ?\n" +
                    "Your progression will be lost.");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    GameView.isLeveling = false;
                    mediaPlayer_lvl.stop();
                    mediaPlayer_lvl.release();
                    finish();
                    System.gc();
                }
            });
            alertDialog.show();
        }
    }
}
