package com.example.neo.mymmorpg;

/**
 * Created by neo on 12/31/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import static com.example.neo.mymmorpg.MainActivity.width;
import static com.example.neo.mymmorpg.MainActivity.height;

class Cursor
{
    private BitmapDrawable bg_btn; // image du container
    private BitmapDrawable cursor_btn; // image du curseur
    private BitmapDrawable jump_btn; // image du boutton jump
    private BitmapDrawable atk1_btn; // image du boutton attack1
    private BitmapDrawable atk2_btn; // image du boutton attack2
    private LoadImageTask[] task = new LoadImageTask[5]; //load img threads
    private int x, y; // coordonnées x,y du curseur en pixel
    private int cursW, cursH; // largeur et hauteur du curseur en pixels

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image du curseur
    private final Context mContext;

    // Constructeur de l'objet "Cursor"
    public Cursor(final Context c)
    {
        x=width/20; y=height-height/4; // position de départ
        mContext=c; // sauvegarde du contexte
    }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    public void resize(int wScreen, int hScreen) {

        // on définit (au choix) la taille du curseur à 1/5ème de la largeur de l'écran
        cursW=wScreen/3;
        cursH=wScreen/4;

        task[0] = (LoadImageTask) new LoadImageTask(R.drawable.btn_bg, cursW*3, cursH).execute(mContext);
        task[1] = (LoadImageTask) new LoadImageTask(R.drawable.cursor, cursW, cursH).execute(mContext);
        task[2] = (LoadImageTask) new LoadImageTask(R.drawable.btn_atk1, cursW/2, cursH/2).execute(mContext);
        task[3] = (LoadImageTask) new LoadImageTask(R.drawable.btn_atk1, cursW/2, cursH/2).execute(mContext);
        task[4] = (LoadImageTask) new LoadImageTask(R.drawable.btn_atk2, cursW/2, cursH/2).execute(mContext);
    }
    // définit la coordonnée X du curseur
    public void setX(int x) {
        this.x = x;
    }

    // définit la coordonnée Y du curseur
    public void setY(int y) {
        this.y = y;
    }

    // retourne la coordonnée X du curseur
    public int getX() {
        return x;
    }

    // retourne la coordonnée Y du curseur
    public int getY() {
        return y;
    }

    // retourne la largeur du curseur en pixel
    public int getCursW() {
        return cursW;
    }

    // retourne la hauteur du curseur en pixel
    public int getCursH() {
        return cursH;
    }

    // on dessine le curseur, en x et y
    public void draw(Canvas canvas)
    {
        //dessine
        if (bg_btn != null)
            canvas.drawBitmap(bg_btn.getBitmap(), 0, y, null);
        else
            bg_btn = task[0].getBitmapDrawable()[1];
        if (cursor_btn != null)
            canvas.drawBitmap(cursor_btn.getBitmap(), x, y, null);
        else
            cursor_btn = task[1].getBitmapDrawable()[0];
        if (jump_btn != null)
            canvas.drawBitmap(jump_btn.getBitmap(), x*16, y+y/15, null);
        else
            jump_btn = task[2].getBitmapDrawable()[0];
        if (atk1_btn != null)
            canvas.drawBitmap(atk1_btn.getBitmap(), x*12, y+y/15, null);
        else
            atk1_btn = task[3].getBitmapDrawable()[0];
        if (atk2_btn != null)
            canvas.drawBitmap(atk2_btn.getBitmap(), x*8, y+y/15, null);
        else
            atk2_btn = task[4].getBitmapDrawable()[0];
    }

} // public class Cursor