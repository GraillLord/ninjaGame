package com.example.neo.mymmorpg;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.neo.mymmorpg.MainActivity.mediaPlayer;

/**
 * Created by neo on 2/25/2018.
 */

public class FragmentB extends Fragment {
    // Store instance variables
    private Button[] current_lvl = new Button[2];
    public static Battle battle;
    public static int lvlIndex;

    // newInstance constructor for creating fragment with arguments
    public static FragmentB newInstance() {
        FragmentB fragmentFirst = new FragmentB();
        Bundle args = new Bundle();
        fragmentFirst.setArguments(args);

        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.levelmap, container, false);

        current_lvl[0] = (Button) v.findViewById(R.id.lv1);
        current_lvl[1] = (Button) v.findViewById(R.id.lv2);
        //todo //more lvls
        for (int i=0; i<2; i++) {
            current_lvl[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // On cré un objet "GameView" qui est le code principal du jeu
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    String btn_lvl = ((Button)view).getText().toString();
                    lvlIndex = java.lang.Character.getNumericValue(btn_lvl.charAt(btn_lvl.length()-1))-1;
                    battle = new Battle(getContext());
                    Intent intent = new Intent(getActivity(), LevelActivity.class);
                    startActivity(intent);
                }
            });
        }

        return v;
    }
}