package com.example.neo.mymmorpg;

/**
 * Created by neo on 12/31/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

import static com.example.neo.mymmorpg.Battle.frame2;
import static com.example.neo.mymmorpg.GameView.frame;
import static com.example.neo.mymmorpg.GameView.frame3;
import static com.example.neo.mymmorpg.GameView.frame4;
import static com.example.neo.mymmorpg.GameView.taskFoe;
import static com.example.neo.mymmorpg.MainActivity.height;
import static com.example.neo.mymmorpg.MainActivity.width;

class Enemies extends Character {
    private BitmapDrawable[] sprite = new BitmapDrawable[8];
    private int x, y;
    private int foeW, foeH;
    public boolean isLeft = false;
    private boolean move = false;
    private boolean run = false;
    private boolean jump = false;
    private boolean atk = false;
    private boolean ground = false;
    public boolean death = false;
    private int life;

    // A rectangle to define an area of the sprite sheet that represents 1 frame
    private Rect frameToDraw = new Rect(0, 0, foeW, foeH);

    // A rect that defines an area of the screen on which to draw
    private RectF whereToDraw = new RectF(x, y, x + foeW, y +foeH);

    private static final int INCREMENT = 5;
    public int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    private final Context mContext;

    public Enemies(final Context c, int index) {
        super(c);
        x=width/20; y=height/2+(index*height/4); // position de départ
        mContext=c; // sauvegarde du contexte
        life = 100;
    }

    public void gainLife(int amount) {
        life += amount;
    }

    public void loseLife(int amount) {
        life -= amount;
    }

    public void dammage(int amount) {
        //TODO
    }

    public boolean isMoving() {
        return move;
    }

    public boolean isRuning() { return run; }

    public boolean isJumping() { return jump; }

    public boolean isAttacking() { return atk; }

    public boolean isOnGround() { return ground; }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setRun(boolean run) { this.run = run; }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setAttacking(boolean atk) { this.atk = atk; }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran

        foeW = wScreen / 5;
        foeH = hScreen / 5;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setGround(boolean ground) { this.ground = ground; }

    public int getX() { return x; }

    public int getY() {
        return y;
    }

    public int getFoeW() {
        return foeW;
    }

    public int getFoeH() { return foeH; }

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
        for (int i=0; i<8; i++) {
            if (sprite[i] == null)
                sprite[i] = taskFoe[i/2].getBitmapDrawable()[i&1];
        }
        //dessine
        frameToDraw.set((int)(isLeft ? 9-frame : frame) * foeW, 0,
                (int)((isLeft ? 9-frame : frame) * foeW) + foeW, foeH);
        whereToDraw.set((int)x, y, (int)x + foeW, y + foeH);

        if (life <= 0) {
            if (death) frame4 = 11;
            frameToDraw.set((int)(isLeft ? 11-frame4 : frame4) * foeW, 0,
                    (int)((isLeft ? 11-frame4 : frame4) * foeW) + foeW, foeH);
            whereToDraw.set((int)x - foeW*5/8, y, (int)x + foeW*5/8,y + foeH);
            if(!isLeft) {
                if(sprite[6] == null) {return;}
                canvas.drawBitmap(sprite[6].getBitmap(), frameToDraw, whereToDraw,null);
            }
            else {
                if(sprite[7] == null) {return;}
                canvas.drawBitmap(sprite[7].getBitmap(), frameToDraw, whereToDraw,null);
            }
        }
        else if (isRuning()) {
            if(!isLeft) {
                if(sprite[0] == null) {return;}
                canvas.drawBitmap(sprite[0].getBitmap(), frameToDraw, whereToDraw, null);
            }
            else {
                if(sprite[1] == null) {return;}
                canvas.drawBitmap(sprite[1].getBitmap(), frameToDraw, whereToDraw, null);
            }
        }
        else if(isMoving()){
            frameToDraw.set((int)(isLeft ? 9-frame : frame) * foeW, 0,
                    (int)((isLeft ? 9-frame : frame) * foeW) + foeW, foeH);
            if(!isLeft) {
                if(sprite[2] == null) {return;}
                canvas.drawBitmap(sprite[2].getBitmap(), frameToDraw, whereToDraw, null);
            }
            else {
                if(sprite[3] == null) {return;}
                canvas.drawBitmap(sprite[3].getBitmap(), frameToDraw, whereToDraw, null);
            }
        }
        else if(isAttacking()) {
            frameToDraw.set((int)(isLeft ? 7-frame3 : frame3) * foeW, 0,
                    (int)((isLeft ? 7-frame3 : frame3) * foeW) + foeW, foeH);
            if(!isLeft) {
                if(sprite[4] == null) {return;}
                canvas.drawBitmap(sprite[4].getBitmap(), frameToDraw, whereToDraw, null);
            }
            else {
                if(sprite[5] == null) {return;}
                canvas.drawBitmap(sprite[5].getBitmap(), frameToDraw, whereToDraw, null);
            }
        }
        else {
            frameToDraw.set((int)(isLeft ? 14-frame2 : frame2) * foeW, 0,
                    (int)((isLeft ? 14-frame2 : frame2) * foeW) + foeW, foeH);
            if(!isLeft) {
                if(sprite[0] == null) {return;}
                canvas.drawBitmap(sprite[0].getBitmap(), frameToDraw, whereToDraw, null);
            }
            else {
                if(sprite[1] == null) {return;}
                canvas.drawBitmap(sprite[1].getBitmap(), frameToDraw, whereToDraw, null);
            }
        }
    }

} // public class Enemies
