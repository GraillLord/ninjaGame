package com.example.neo.mymmorpg;

/**
 * Created by neo on 2/25/2018.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.Random;

import static com.example.neo.mymmorpg.FragmentB.lvlIndex;
import static com.example.neo.mymmorpg.MainActivity.height;
import static com.example.neo.mymmorpg.MainActivity.width;

public class Battle extends GameView {

    // attributs
    public BackGround backGround;
    public ThrownObjects kunai;
    private Platforms[] platforms;
    private Enemies[] enemies;
    private Cursor cursor;
    private boolean atkLapse = false;
    private int origin = (height / 2 + height / 20);

    //level disign
    //todo
    //private int[] lvlFoes = { 6 };

    private int[][] foes_coords = {
            {6, 12, 13, 15, 17, 20},
            {10, 15, 20, 25, 30, 35, 40, 45, 50}
    };

    private double [][][] platforms_coords = {
            {       /*ground*/      {0,0.1,1.3},{1,0.5,1.3},{2,0.9,1.3},
                    /*platforms*/   {3,1,0.8},{3,2,0.8},{3,3,0.8}},
            {       /*ground*/      {0,0,0},{1,1,0},{1,2,0},
                    /*plateforms*/  {3,1,1},{3,1.5,1},{3,2,1},{3,2.5,0.8},{3,3,0.6},{3,3.5,0.5}}
    };

    // création de la surface de dessin
    public Battle(Context context) {
        super(context, null, -1);
        init();
    }

    public Battle(Context context, AttributeSet attrs) {
        super(context, attrs, -1);
        init();
    }

    public Battle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Random ran = new Random();
        // position de départ
        //if(chr.id == playerID, else bots ???)
        chr.setX(width/2-width/12);
        chr.setY(height/2+height/20);
        if (lvlIndex == 0)    backGround = new BackGround(this.getContext(), R.drawable.bg_lvl1);
        if (lvlIndex == 1)    backGround = new BackGround(this.getContext(), R.drawable.bg_lvl2);
        kunai = new ThrownObjects(this.getContext(), R.drawable.kunai);
        platforms = new Platforms[platforms_coords[lvlIndex].length];
        enemies = new Enemies[foes_coords[lvlIndex].length];
        isLeveling = true;

        for(int i=0; i<platforms.length; i++) {
            platforms[i] = new Platforms(this.getContext(), platforms_coords[lvlIndex][i][0]);
            platforms[i].setX(width/2*platforms_coords[lvlIndex][i][1]);
            platforms[i].setY(height/2*platforms_coords[lvlIndex][i][2]);
            //System.out.println(platforms[i].getX() + "  plate " + platforms[i].getY());
        }

        for(int i=0; i<enemies.length; i++) {
            enemies[i] = new Enemies(this.getContext(), i);
            //enemies[0].setY(height/2-height/5);
            enemies[i].setX(width/2*foes_coords[lvlIndex][i]);
            enemies[i].setY(height/2+height/20+5);
            //System.out.println(enemies[i].getX() + "  ene " + enemies[i].getY());
        }

        cursor = new Cursor(this.getContext());
    }
    // Fonction qui "dessine" un écran de jeu
    @Override
    public void doDraw(Canvas canvas) {
        if(canvas==null) {return;}

        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);

        //frames
        if (framejump >= 10) {
            if (chr.isJumping()) {
                chr.setJump(false);
            }
            origin = gravity(origin, chr);
            if (!chr.death) chr.setY(origin);
            framejump = 0;
        }
        if(frame >= 10) {
            if (chr.getLife() <= 0) {
                chr.death = true;
                chr.setY(origin);
            }

            for (Enemies enemy : enemies) {
                if (!chr.isLeft && chr.getY() <= enemy.getY() + enemy.getFoeH()
                        && chr.getY() >= enemy.getY() - enemy.getFoeH()/4
                        && chr.getX() <= enemy.getX() + enemy.getFoeW()/4
                        && chr.getX() >= enemy.getX() - enemy.getFoeW()) {
                    if (chr.isAttacking1())
                        enemy.loseLife(100);
                    if (chr.isAttacking2())
                        enemy.loseLife(200);
                }
                else if (chr.isLeft && chr.getY() <= enemy.getY() + enemy.getFoeH()
                        && chr.getY() >= enemy.getY() - enemy.getFoeH()/4
                        && chr.getX() <= enemy.getX() + enemy.getFoeW()
                        && chr.getX() >= enemy.getX() - enemy.getFoeW()/4) {
                    if (chr.isAttacking1())
                        enemy.loseLife(100);
                    if (chr.isAttacking2())
                        enemy.loseLife(200);
                }
                else if (chr.getY() <= enemy.getY() + 5
                        && chr.getY() >= enemy.getY() - 5
                        && ((chr.getX() >= enemy.getX() - enemy.getFoeW()*3 && !chr.isLeft)
                         || (chr.getX() <= enemy.getX() + enemy.getFoeW()*3 && chr.isLeft))
                        && chr.isLeft != enemy.isLeft) {
                    if (chr.isAttacking3() || chr.isAttacking4())
                        enemy.loseLife(50);
                }
            }
            if (chr.isAttacking1() || chr.isAttacking2() || chr.isAttacking3() || chr.isAttacking4()) {
                chr.setAtk1(false);
                chr.setAtk2(false);
                chr.setAtk3(false);
                chr.setAtk4(false);
            }
            frame = 0;
        }
        if (frame2 >= 15)
            frame2 = 0;
        if (frame3 >= 8) {
            frame3 = 0;
            atkLapse = !atkLapse;
            for (Enemies enemy : enemies)
                if (enemy.isAttacking() && !enemy.death) {
                    enemy.setAttacking(false);
                    if (chr.getY() <= enemy.getY() + enemy.getFoeH()/3
                            && chr.getY() >= enemy.getY() - enemy.getFoeH()/3
                            && chr.getX() <= enemy.getX() + enemy.getFoeW()/2
                            && chr.getX() >= enemy.getX() - enemy.getFoeW()/2)
                        chr.loseLife(40);
                }
        }
        if(frame4 >= 12) {
            frame4 = 0;
            for (Enemies enemy : enemies) {
                if (enemy.getLife() <= 0) {
                    enemy.death = true;
                    enemy.setY(height/2+height/20+30);
                }
            }
        }

        // on dessine les éléments
        backGround.draw(canvas);

        for (Platforms platform : platforms) {
            platform.draw(canvas);
            platform.setX(platform.getX() - lvlPos);
        }

        chr.draw(canvas);
        if (chr.isAttacking3() || chr.isAttacking4()) {
            if (!kunai.isLeft)
                kunai.setX(chr.getX()+frame*100);
            else
                kunai.setX(chr.getX()-frame*100);
            kunai.setY(chr.getY()+chr.getY()/10);
            kunai.draw(canvas);
        }

        cursor.draw(canvas);

        for (Enemies enemy : enemies) {
            enemy.setY(gravity(enemy.getY(), enemy));
            if (!enemy.death) {
                if (chr.getY() <= enemy.getY() + enemy.getFoeH()/3
                        && chr.getY() >= enemy.getY() - enemy.getFoeH()/3
                        && chr.getX() <= enemy.getX() + enemy.getFoeW()/2
                        && chr.getX() >= enemy.getX() - enemy.getFoeW()/2) {
                    enemy.setMove(false);
                    if (!atkLapse)
                        enemy.setAttacking(true);
                } else if (chr.getX() <= enemy.getX()) {
                    enemy.isLeft = true;
                    enemy.setMove(true);
                    enemy.setX(enemy.getX() - 10);
                } else if (chr.getX() >= enemy.getX()) {
                    enemy.isLeft = false;
                    enemy.setMove(true);
                    enemy.setX(enemy.getX() + 10);
                }
            }
            enemy.draw(canvas);
            enemy.setX(enemy.getX() - lvlPos*2);
        }

        lvlPos = 0;
        if (!chr.death && !chr.isJumping()) {
            origin = gravity(origin, chr);
            chr.setY(origin);
        }

        framejump++;
        frame++;
        frame2++;
        frame3++;
        frame4++;
    }

    // Gère les touchés sur l'écran

    private int gravity(int heights, Character obj) {
        for (Platforms platform : platforms) {
            if ((platform.getX() >= 50 && platform.getX() <= 250)
                    && (obj.getY() >= platform.getY() - 350)
                    && (obj.getY() <= platform.getY())) {
                if (framejump > 8 && obj.getY() >= platform.getY() - 350)
                    obj.setGround(true);
                heights = (int) platform.getY() - 300;
                framegravity = 0;
                break;
            }
            if (!obj.isJumping()) {
                if(heights < (height))
                    if ((platform.getX() >= 50 && platform.getX() <= 250)
                            && (platform.getY() - 300 - heights) < 0)
                        framegravity = 0;
                    else
                        heights +=  framegravity += 2;
                else
                    framegravity = 0;
            }
        }

        return heights;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = (int)event.getX();
        currentY = (int)event.getY();

        if (!chr.death) {
            switch (event.getAction()) {

                // code exécuté lorsque le doigt touche l'écran.
                case MotionEvent.ACTION_DOWN:
                    //si le doigt touche le curseur jump
                    if(!chr.isJumping() && !chr.isAttacking1() && !chr.isAttacking2()
                            && !chr.isAttacking3() && !chr.isAttacking4() && framegravity == 0) {
                        if(currentX >= cursor.getX()*16 &&
                                currentX <= cursor.getX()*16+cursor.getCursW()/2 &&
                                currentY >= cursor.getY()+cursor.getY()/15 &&
                                currentY <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                            framejump = 0;
                            chr.setJump(true);
                            chr.setGround(false);
                        }
                        else if(currentX >= cursor.getX()*12 &&
                                currentX <= cursor.getX()*12+cursor.getCursW()/2 &&
                                currentY >= cursor.getY()+cursor.getY()/15 &&
                                currentY <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                            frame = 0;
                            chr.setAtk1(true);
                        }
                        else if(currentX >= cursor.getX()*8 &&
                                currentX <= cursor.getX()*8+cursor.getCursW()/2 &&
                                currentY >= cursor.getY()+cursor.getY()/15 &&
                                currentY <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                            frame = 0;
                            kunai.isLeft = chr.isLeft;
                            chr.setAtk3(true);
                        }
                        else {
                            if(currentX <= chr.getX())
                                chr.isLeft = true;
                            else if(currentX >= chr.getX())
                                chr.isLeft = false;
                            chr.setRun(true);
                            chr.setMove(true);
                        }
                    }
                    else if(chr.isJumping() && !chr.isAttacking1() && !chr.isAttacking2()
                            && !chr.isAttacking3() && !chr.isAttacking4()) {
                        if(currentX >= cursor.getX()*12 &&
                                currentX <= cursor.getX()*12+cursor.getCursW()/2 &&
                                currentY >= cursor.getY()+cursor.getY()/15 &&
                                currentY <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                            frame = 0;
                            chr.setAtk2(true);
                        }
                        else if(currentX >= cursor.getX()*8 &&
                                currentX <= cursor.getX()*8+cursor.getCursW()/2 &&
                                currentY >= cursor.getY()+cursor.getY()/15 &&
                                currentY <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                            frame = 0;
                            kunai.isLeft = chr.isLeft;
                            chr.setAtk4(true);
                        }
                    }
                    break;

                //si le 2e doigt touche le curseur
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;

                // code exécuté lorsque le doight glisse sur l'écran.
                case MotionEvent.ACTION_MOVE:
                    int count = event.getPointerCount();
                    for (int i = 0; i < count; i++) {
                        if(!chr.isJumping() && !chr.isAttacking1() && !chr.isAttacking2()
                                && !chr.isAttacking3() && !chr.isAttacking4() && framegravity == 0) {
                            if(event.getX(i) >= cursor.getX()*16 &&
                                    event.getX(i) <= cursor.getX()*16+cursor.getCursW()/2 &&
                                    event.getY(i) >= cursor.getY()+cursor.getY()/15 &&
                                    event.getY(i) <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                                framejump = 0;
                                chr.setJump(true);
                                chr.setGround(false);
                            }
                            else if(event.getX(i) >= cursor.getX()*12 &&
                                    event.getX(i) <= cursor.getX()*12+cursor.getCursW()/2 &&
                                    event.getY(i) >= cursor.getY()+cursor.getY()/15 &&
                                    event.getY(i) <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                                frame = 0;
                                chr.setAtk1(true);
                            }
                            else if(event.getX(i) >= cursor.getX()*8 &&
                                    event.getX(i) <= cursor.getX()*8+cursor.getCursW()/2 &&
                                    event.getY(i) >= cursor.getY()+cursor.getY()/15 &&
                                    event.getY(i) <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                                frame = 0;
                                kunai.isLeft = chr.isLeft;
                                chr.setAtk3(true);
                            }
                        }
                        else if(chr.isJumping() && !chr.isAttacking1() && !chr.isAttacking2()
                                && !chr.isAttacking3() && !chr.isAttacking4()) {
                            if(event.getX(i) >= cursor.getX()*12 &&
                                    event.getX(i) <= cursor.getX()*12+cursor.getCursW()/2 &&
                                    event.getY(i) >= cursor.getY()+cursor.getY()/15 &&
                                    event.getY(i) <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                                frame = 0;
                                chr.setAtk2(true);
                            }
                            else if(event.getX(i) >= cursor.getX()*8 &&
                                    event.getX(i) <= cursor.getX()*8+cursor.getCursW()/2 &&
                                    event.getY(i) >= cursor.getY()+cursor.getY()/15 &&
                                    event.getY(i) <= (cursor.getY()+cursor.getY()/15)+cursor.getCursH()/2) {
                                frame = 0;
                                kunai.isLeft = chr.isLeft;
                                chr.setAtk4(true);
                            }
                        }
                    }
                    if(!chr.isJumping() && !chr.isAttacking1() && !chr.isAttacking2()
                            && !chr.isAttacking3() && !chr.isAttacking4()) {
                        if(currentX <= chr.getX())
                            chr.isLeft = true;
                        else if(currentX >= chr.getX())
                            chr.isLeft = false;
                        chr.setRun(true);
                        chr.setMove(true);
                    }
                    break;

                // lorsque le doigt quitte l'écran
                case MotionEvent.ACTION_UP:
                    chr.setMove(false);
                    chr.setRun(false);
                    break;

                // lorsque le 2e doigt quitte l'écran
                case MotionEvent.ACTION_POINTER_UP:
                    break;
            }
        }

        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        backGround.resize(w, h);
        chr.resize(w, h); // on définit la taille du perso selon la taille de l'écran
        kunai.resize(w, h);
        for (i = 0; i<platforms.length; i++)
            platforms[i].resize(w, h);
        for (i = 0; i<enemies.length; i++)
            enemies[i].resize(w, h);
        cursor.resize(w, h);
        /*Platforms*/
        taskPlate[0] = LoadGameImages(R.drawable.grd1, 10, platforms[0].getPlW(), platforms[0].getPlH());
        taskPlate[1] = LoadGameImages(R.drawable.grd2, 10, platforms[0].getPlW(), platforms[0].getPlH());
        taskPlate[2] = LoadGameImages(R.drawable.grd3, 10, platforms[0].getPlW(), platforms[0].getPlH());
        taskPlate[3] = LoadGameImages(R.drawable.platf, 10, platforms[0].getPlW(), platforms[0].getPlH());
        /*Charaters*/
        //idle
        task[0] = LoadGameImages(R.drawable.idle, 10, chr.getchrW(), chr.getchrH());
        //task[1] = LoadGameImages(R.drawable.cgirlidle, 10, chr.getchrW(), chr.getchrH());
        //run
        task[1] = LoadGameImages(R.drawable.run, 10, (chr.getchrW()*5/4), chr.getchrH());
        //task[3] = LoadGameImages(R.drawable.cgirlrun, 8, (chr.getchrW()*5/4), chr.getchrH());
        //jump
        task[2] = LoadGameImages(R.drawable.jump, 10, (chr.getchrW()*5/4), chr.getchrH());
        //task[5] = LoadGameImages(R.drawable.cgirljump, 10, (chr.getchrW()*5/4), chr.getchrH());
        //atk1
        task[3] = LoadGameImages(R.drawable.atk, 10, (chr.getchrW()*5/3), chr.getchrH());
        //task[7] = LoadGameImages(R.drawable.cgirlatk, 7, (chr.getchrW()*5/3), chr.getchrH());
        //atk2
        task[4] = LoadGameImages(R.drawable.jump_atk, 10, (chr.getchrW()*5/3), chr.getchrH());
        //atk3
        task[5] = LoadGameImages(R.drawable.thrw, 10, chr.getchrW(), chr.getchrH());
        task[6] = LoadGameImages(R.drawable.jump_throw, 10, chr.getchrW(), chr.getchrH());
        //death
        task[7] = LoadGameImages(R.drawable.dead, 10, chr.getchrW(), chr.getchrH());
        //task[9] = LoadGameImages(R.drawable.cgirlatk2, 3, (chr.getchrW()*5/3), chr.getchrH());
        /*Enemies*/
        //idle
        taskFoe[0] = LoadGameImages(R.drawable.zmidle, 15, enemies[0].getFoeW(), enemies[0].getFoeH());
        //walk
        taskFoe[1] = LoadGameImages(R.drawable.zmwalk, 10, enemies[0].getFoeW(), enemies[0].getFoeH());
        //atk
        taskFoe[2] = LoadGameImages(R.drawable.zmatk, 8, enemies[0].getFoeW(), enemies[0].getFoeH());
        //death
        taskFoe[3] = LoadGameImages(R.drawable.zmdead, 12, enemies[0].getFoeW(), enemies[0].getFoeH());
    }

} // class Battle
