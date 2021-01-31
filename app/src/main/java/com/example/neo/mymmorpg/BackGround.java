package com.example.neo.mymmorpg;

/**
 * Created by neo on 12/31/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import static com.example.neo.mymmorpg.GameView.isLeveling;
import static com.example.neo.mymmorpg.GameView.lvlPos;

class BackGround {
    private BitmapDrawable img; // image d'un background donné
    private LoadImageTask task;
    private int x, y; // coordonnées x,y de la chr en pixel
    private int chrW, chrH; // largeur et hauteur de la chr en pixels
    private int wEcran, hEcran; // largeur et hauteur de l'écran en pixels
    // sinon

    // pour déplacer la chr on ajoutera INCREMENT à ses coordonnées x et y
    private static final int INCREMENT = 5;
    public int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de la chr
    private final Context mContext;
    public int mBg;

    // Constructeur de l'objet "chr"
    public BackGround(final Context c, int mBg) {
        this.mBg = mBg;
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
        chrW = wEcran;
        chrH = hEcran;
        task = (LoadImageTask) new LoadImageTask(mBg, chrW, chrH).execute(mContext);
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
        if (isLeveling)
            canvas.scale(2, 1);
        if (img != null) {
            if (!isLeveling) {
                canvas.drawBitmap(img.getBitmap(), 0, 0, null);
                return;
            }
            System.out.println("POS " + x + " " + lvlPos);
            // decrement the far background
            if (lvlPos > 0)
                x -= 1;
            else if (lvlPos < 0)
                x += 1;
            // calculate the wrap factor for matching image draw
            int newFarX = img.getBitmap().getWidth() - (-x);
            // if we have scrolled all the way, reset to start
            if (newFarX <= 0 || newFarX >= img.getBitmap().getWidth()) {
                lvlPos = 0;
                x = -1;
                // only need one draw
                canvas.drawBitmap(img.getBitmap(), x, 0, null);
            } else {
                // need to draw original and wrap
                canvas.drawBitmap(img.getBitmap(), x, 0, null);
                canvas.drawBitmap(img.getBitmap(), newFarX, 0, null);
            }
        }
        else
            img = task.getBitmapDrawable()[0];
    }

} // public class bkgd