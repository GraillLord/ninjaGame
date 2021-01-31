package com.example.neo.mymmorpg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by neo on 3/4/2018.
 */

public class ThrownObjects {
    private BitmapDrawable[] kunai = new BitmapDrawable[2]; // image d'un background donné
    private LoadImageTask task;
    private int x, y; // coordonnées x,y de la chr en pixel
    private int chrW, chrH; // largeur et hauteur de la chr en pixels
    private int wEcran, hEcran; // largeur et hauteur de l'écran en pixels
    public boolean isLeft = true; // 'true' si l'on fait commencer le perso vers la droite
    // sinon

    // pour déplacer la chr on ajoutera INCREMENT à ses coordonnées x et y
    private static final int INCREMENT = 5;
    public int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de la chr
    private final Context mContext;
    private int obj;


    // Constructeur de l'objet "chr"
    public ThrownObjects(final Context c, int obj) {
        this.obj = obj;
        x=0; y=0; // position de départ
        mContext=c; // sauvegarde du contexte
    }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    public void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        wEcran = wScreen;
        hEcran = hScreen;

        // on définit (au choix) la taille de la chr à 1/5ème de la largeur de l'écran
        chrW = wEcran / 10;
        chrH = hEcran / 20;
        task = (LoadImageTask) new LoadImageTask(obj, chrW, chrH).execute(mContext);
    }

    // définit la coordonnée X de la chr
    public void setX(int x) {
        this.x = x;
    }

    // définit la coordonnée Y de la chr
    public void setY(int y) {
        this.y = y;
    }

    // retourne la coordonnée X de la chr
    public int getX() {
        return x;
    }

    // retourne la coordonnée Y de la chr
    public int getY() {
        return y;
    }

    // retourne la largeur de la chr en pixel
    public int getchrW() {
        return chrW;
    }

    // retourne la hauteur de la chr en pixel
    public int getchrH() {
        return chrH;
    }

    // on dessine la chr, en x et y
    public void draw(Canvas canvas) {
        if (kunai[0] != null && kunai[1] != null) {
            if (!isLeft)
                canvas.drawBitmap(kunai[0].getBitmap(), x, y, null);
            else
                canvas.drawBitmap(kunai[1].getBitmap(), x, y, null);
        }
        else {
            kunai[0] = task.getBitmapDrawable()[0];
            kunai[1] = task.getBitmapDrawable()[1];
        }
    }
}
