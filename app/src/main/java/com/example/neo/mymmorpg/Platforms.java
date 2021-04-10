package com.example.neo.mymmorpg;

/**
 * Created by neo on 12/31/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

import static com.example.neo.mymmorpg.GameView.taskPlate;
import static com.example.neo.mymmorpg.MainActivity.height;
import static com.example.neo.mymmorpg.MainActivity.width;

class Platforms {
    private BitmapDrawable[] sprite = new BitmapDrawable[10];
    private double x, y;
    private int plW, plH;
    private double type;
    public boolean isLeft = false;
    private boolean move = false;
    private int life;

    // A rectangle to define an area of the sprite sheet that represents 1 frame
    private Rect frameToDraw = new Rect(0, 0, plW, plH);

    // A rect that defines an area of the screen on which to draw
    private RectF whereToDraw = new RectF((float)x, (float)y, (float)x + plW, (float)y + plH);

    private static final int INCREMENT = 1;
    public int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    private final Context mContext;

    public Platforms(final Context c, double type) {
        x=width; y=height; // position de départ
        mContext=c; // sauvegarde du contexte
        life = 100;
        this.type = type;
    }

    //public void gainLife(int amount) {
    //    life += amount;
    //}

    //public void loseLife(int amount) {
    //    life -= amount;
    //}

    //public void dammage(int amount) {
        //TODO
    //}

    public boolean isMoving() {
        return move;
    }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran

        plW = wScreen / 5;
        plH = hScreen / 5;
    }

    public void setX(double x) { this.x = x; }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() { return x; }

    public double getY() {
        return y;
    }

    public int getPlW() { return plW; }

    public int getPlH() { return plH; }

    public double getType() { return type;}

    public int getLife() { return life; }

    public void moveWithCollisionDetection() {
        if(!isMoving()) {return;}

        // on incrémente les coordonnées X et Y
        /*if(isRuning()){
            if(x > currentX)
                x-=speedX*2;
            else if(x < currentX)
                x+=speedX*2;
            if(y > currentY)
                y-=speedY*2;
            else if(y < currentY)
                y+=speedY*2;
        }*/
    }

    void draw(Canvas canvas) {
        for (int i = 0; i < 10; i++) {
            if (sprite[i] == null)
                sprite[i] = taskPlate[i / 2].getBitmapDrawable()[i & 1];
        }
        //dessine
        frameToDraw.set(0, 0, plW * 10, plH);
        if (type != 0)
            whereToDraw.set((float) x, (float) y, (float) x + plW, (float) y + plH);

        //groud1
        if (type == 0) {
            whereToDraw.set((float) x, (float) y, (float) x + plW*8, (float) y + plH*8);
            if (!isLeft) {
                if (sprite[0] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[0].getBitmap(), frameToDraw, whereToDraw, null);
            } else {
                if (sprite[1] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[1].getBitmap(), frameToDraw, whereToDraw, null);
            }
            //ground2
        } else if (type == 1) {
            if (!isLeft) {
                if (sprite[2] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[2].getBitmap(), frameToDraw, whereToDraw, null);
            } else {
                if (sprite[3] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[3].getBitmap(), frameToDraw, whereToDraw, null);
            }
            //ground3
        } else if (type == 2) {
            if (!isLeft) {
                if (sprite[4] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[4].getBitmap(), frameToDraw, whereToDraw, null);
            } else {
                if (sprite[5] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[5].getBitmap(), frameToDraw, whereToDraw, null);
            }
            //platform1
        } else if (type == 3) {
            if (!isLeft) {
                if (sprite[6] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[6].getBitmap(), frameToDraw, whereToDraw, null);
            } else {
                if (sprite[7] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[7].getBitmap(), frameToDraw, whereToDraw, null);
            }
        } else if (type == 4) {
            if (!isLeft) {
                if (sprite[8] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[8].getBitmap(), frameToDraw, whereToDraw, null);
            } else {
                if (sprite[9] == null) {
                    return;
                }
                canvas.drawBitmap(sprite[9].getBitmap(), frameToDraw, whereToDraw, null);
            }
        }
    }

} // public class Platforms
