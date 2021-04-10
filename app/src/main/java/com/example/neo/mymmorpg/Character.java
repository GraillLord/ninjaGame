package com.example.neo.mymmorpg;

/**
 * Created by neo on 12/31/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

import static com.example.neo.mymmorpg.GameView.currentY;
import static com.example.neo.mymmorpg.GameView.frame;
import static com.example.neo.mymmorpg.GameView.framejump;
import static com.example.neo.mymmorpg.GameView.isLeveling;
import static com.example.neo.mymmorpg.GameView.lvlPos;
import static com.example.neo.mymmorpg.GameView.task;
import static com.example.neo.mymmorpg.MainActivity.height;
import static com.example.neo.mymmorpg.MainActivity.width;

class Character {
    private BitmapDrawable[] sprite = new BitmapDrawable[16]; // image de la chr
    protected int x, y; // coordonnées x,y de la chr en pixel
    protected int chrW, chrH; // largeur et hauteur de la chr en pixels
    protected int wEcran, hEcran; // largeur et hauteur de l'ecran en pixels
    protected boolean move = false;
    protected boolean run = false;
    protected boolean jump = false;
    protected boolean atk1 = false;
    protected boolean atk2 = false;
    protected boolean atk3 = false;
    protected boolean atk4 = false;
    public boolean isLeft = false; // 'true' si l'on fait commencer le perso vers la droite
    public boolean death = false;
    protected boolean ground = true;
    protected int baseJump;
    private int life;

    // A rectangle to define an area of the sprite sheet that represents 1 frame
    private Rect frameToDraw = new Rect(0, 0, chrW, chrH);

    // A rect that defines an area of the screen on which to draw
    private RectF whereToDraw = new RectF(x, y, x + chrW, y + chrH);

    // pour déplacer la chr on ajoutera INCREMENT à ses coordonnées x et y
    private static final int INCREMENT = 5;
    public int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de la chr
    private final Context mContext;

    // Constructeur de l'objet "chr"
    public Character(final Context c) {
        x=width/2; y=height/2-(height/10); // position de départ
        mContext=c; // sauvegarde du contexte
        life = 100;
    }

    public void gainLife(int life) { this.life+=life; }

    public void loseLife(int life) { this.life-=life; }

    public void dammage(int dmg) {
        //TODO
    }

    // on attribue à l'objet "chr" l'image passée en paramètre
    // w et h sont sa largeur et hauteur définis en pixels

    public boolean isMoving() {
        return move;
    }

    public boolean isRuning() { return run; }

    public boolean isJumping() { return jump; }

    public boolean isAttacking1() { return atk1; }

    public boolean isAttacking2() { return atk2; }

    public boolean isAttacking3() { return atk3; }

    public boolean isAttacking4() { return atk4; }

    public boolean isOnGround() { return ground; }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setRun(boolean run) { this.run = run; }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setAtk1(boolean atk1) {
        this.atk1 = atk1;
    }

    public void setAtk2(boolean atk2) {
        this.atk2 = atk2;
    }

    public void setAtk3(boolean atk3) {
        this.atk3 = atk3;
    }

    public void setAtk4(boolean atk4) {
        this.atk4 = atk4;
    }

    protected void setGround(boolean ground) { this.ground = ground; }

    protected void setBaseJump(int y) { this.baseJump = y; }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran

        // on définit (au choix) la taille de la chr à 1/5ème de la largeur de l'écran
        wEcran = wScreen;
        hEcran = hScreen;

        chrW = wScreen / 6;
        chrH = hScreen / 5;
    }

    // définit la coordonnée X de la chr
    public void setX(int x) {
        this.x = x;
    }

    // définit la coordonnée Y de la chr
    public void setY(int y) { this.y = y; }

    // retourne la coordonnée X de la chr
    protected int getX() {
        return x;
    }

    // retourne la coordonnée Y de la chr
    protected int getY() {
        return y;
    }

    // retourne la largeur de la chr en pixel
    public int getchrW() {
        return chrW;
    }

    // retourne la hauteur de la chr en pixel
    public int getchrH() { return chrH; }

    public int getLife() { return life; }

    public int getBaseJump() { return baseJump; }

    // déplace la chr en détectant les collisions avec les bords de l'écran
    public void moveWithCollisionDetection() {
        // on incrémente les coordonnées X et Y
        if(isJumping()) {
            if(framejump < 8)
                y-=speedY*20;
        }
        if(!death && (isRuning() && !isAttacking1()
                && !isAttacking2()
                && !isAttacking3()
                && !isAttacking4())
                || (isRuning() && isJumping())) {

            if(isLeveling) {
                if(isLeft)
                    lvlPos -= speedX * 3;
                else
                    lvlPos += speedX * 3;
            }
            else {
                if (isLeft)
                    x -= speedX * 3;
                else
                    x += speedX * 3;
            }
            if(y > currentY)
                y -= speedY * 3;
            else if(y < currentY)
                y += speedY * 3;
        }

        // si on ne doit pas déplacer la chr (lorsqu'elle est sous le doigt du joueur)
        // on quitte
        if(!isMoving()) {return;}

        // si x passe à gauche de l'écran, on inverse le déplacement
        if(x<0) {
            x = 0;
        }

        // si y passe à dessus de l'écran, on inverse le déplacement
        if(y<0) {
            y = 0;
        }

        // si x dépasse la largeur de l'écran, on inverse le déplacement
        if(x+chrW > wEcran) {
            x = wEcran-chrW;
            return;
        }

        // si y dépasse la hauteur l'écran, on inverse le déplacement
        if(y+chrH > hEcran) {
            y = hEcran-chrH;
            return;
        }
    }

    // on dessine la chr, en x et y
    void draw(Canvas canvas)
    {
        if (isLeveling)
            canvas.scale((float)0.5, 1);

        //add load screen ???
        for (int i=0; i<=15; i++) {
            if (sprite[i] == null)
                sprite[i] = task[i/2].getBitmapDrawable()[i&1];
        }

        //todo
        //dessine
        if (life <= 0) {
            //death position
            frameToDraw.set((int)(isLeft ? 0 : 9) * chrW, 0,
                    (int)((isLeft ? 0 : 9) * chrW) + chrW, chrH);
            if(!isLeft) {
                if(sprite[14] == null) {return;}
                whereToDraw.set((int)x, y, (int)x + (2 * chrW*9/10),y + chrH);
                canvas.drawBitmap(sprite[14].getBitmap(), frameToDraw, whereToDraw,null);
            }
            else {
                if(sprite[15] == null) {return;}
                whereToDraw.set((int)x - chrW*9/10, y, (int)x + chrW*9/10,y + chrH);
                canvas.drawBitmap(sprite[15].getBitmap(), frameToDraw, whereToDraw,null);
            }
        }
        //running
        else if(isRuning() && !isJumping() && !isAttacking1() && !isAttacking2()
                && !isAttacking3() && !isAttacking4()) {
            frameToDraw.set((int)(isLeft ? 9-frame : frame) * chrW*5/4, 0,
                    (int)((isLeft ? 9-frame : frame) * (chrW*5/4)) + (chrW*5/4), chrH);
            whereToDraw.set((int)x, y, (int)x + (chrW*5/4), y + chrH);
            if(!isLeft) {
                if(sprite[2] == null) {return;}
                canvas.drawBitmap(sprite[2].getBitmap(), frameToDraw, whereToDraw, null);
            }
            else {
                if(sprite[3] == null) {return;}
                canvas.drawBitmap(sprite[3].getBitmap(), frameToDraw, whereToDraw, null);
            }
        }
        //sword atk
        else if(isJumping() || isAttacking1() || isAttacking2() || isAttacking3() || isAttacking4()) {
            if(isAttacking1()) {
                frameToDraw.set((int)(isLeft ? 9-frame : frame) * (chrW*5/3), 0,
                        (int)((isLeft ? 9-frame : frame) * (chrW*5/3)) + (chrW*5/3), chrH);
                if(!isLeft) {
                    if(sprite[6] == null) {return;}
                    whereToDraw.set((int)x, y-(chrH*1/10), (int)x + 2*chrW, y + (chrH*11/10));
                    canvas.drawBitmap(sprite[6].getBitmap(), frameToDraw, whereToDraw, null);
                }
                else {
                    if(sprite[7] == null) {return;}
                    whereToDraw.set((int)x - chrW, y-(chrH*1/10), (int)x + chrW, y + (chrH*11/10));
                    canvas.drawBitmap(sprite[7].getBitmap(), frameToDraw, whereToDraw,null);
                }
            }
            //jump sword atk
            else if(isAttacking2()) {
                frameToDraw.set((int)(isLeft ? 9-frame : frame) * (chrW*5/3), 0,
                        (int)((isLeft ? 9-frame : frame) * (chrW*5/3)) + (chrW*5/3), chrH);
                whereToDraw.set((int)x, y-(chrH*1/10), (int)x + (chrW*3/2), y + (chrH*11/10));
                if(!isLeft) {
                    canvas.drawBitmap(sprite[8].getBitmap(), frameToDraw, whereToDraw,null);
                    if(sprite[8] == null) {return;}
                }
                else {
                    if(sprite[9] == null) {return;}
                    canvas.drawBitmap(sprite[9].getBitmap(), frameToDraw, whereToDraw,null);
                }
            }
            //shuriken
            else if(isAttacking3()) {
                frameToDraw.set((int)(isLeft ? 9-frame : frame) * chrW, 0,
                        (int)((isLeft ? 9-frame : frame) * chrW) + chrW, chrH);
                whereToDraw.set((int)x - chrW*5/8, y, (int)x + chrW*5/8,y + chrH);
                if(!isLeft) {
                    if(sprite[10] == null) {return;}
                    canvas.drawBitmap(sprite[10].getBitmap(), frameToDraw, whereToDraw,null);
                }
                else {
                    if(sprite[11] == null) {return;}
                    canvas.drawBitmap(sprite[11].getBitmap(), frameToDraw, whereToDraw,null);
                }
            }
            //jump shuriken atk
            else if(isAttacking4()) {
                frameToDraw.set((int)(isLeft ? 9-frame : frame) * chrW, 0,
                        (int)((isLeft ? 9-frame : frame) * chrW) + chrW, chrH);
                whereToDraw.set((int)x - chrW*5/8, y, (int)x + chrW*5/8,y + chrH);
                if(!isLeft) {
                    if(sprite[12] == null) {return;}
                    canvas.drawBitmap(sprite[12].getBitmap(), frameToDraw, whereToDraw,null);
                }
                else {
                    if(sprite[13] == null) {return;}
                    canvas.drawBitmap(sprite[13].getBitmap(), frameToDraw, whereToDraw,null);
                }
            }
            //jump
            else {
                frameToDraw.set((int)(isLeft ? 9-framejump : framejump) * (chrW*5/4), 0,
                        (int)((isLeft ? 9-framejump : framejump) * (chrW*5/4)) + (chrW*5/4), chrH);
                whereToDraw.set((int)x, y-(chrH*1/10), (int)x + (chrW*4/3), y + (chrH*11/10));
                if(!isLeft) {
                    if(sprite[4] == null) {return;}
                    canvas.drawBitmap(sprite[4].getBitmap(), frameToDraw, whereToDraw, null);
                }
                else {
                    if(sprite[5] == null) {return;}
                    canvas.drawBitmap(sprite[5].getBitmap(), frameToDraw, whereToDraw, null);
                }
            }
        }
        //idle
        else {
            frameToDraw.set((int)(isLeft ? 9-frame : frame) * chrW, 0,
                    (int)((isLeft ? 9-frame : frame) * chrW) + chrW, chrH);
            whereToDraw.set((int)x, y, (int)x + chrW, y + chrH);
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

} // public class Character