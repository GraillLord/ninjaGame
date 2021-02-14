package com.example.neo.mymmorpg;

/**
 * Created by neo on 12/31/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private GameLoopThread gameLoopThread;
    public static LoadImageTask[] task = new LoadImageTask[10]; //load img threads des persos
    public static LoadImageTask[] taskPlate = new LoadImageTask[5]; //load img threads des Platforms
    public static LoadImageTask[] taskFoe = new LoadImageTask[5]; //load img threads des Ennemies
    public BackGround backGround;
    public Character chr;
    public static int currentX, currentY;
    public static int framegravity = 0; //frame pour la gravité
    public static int framejump = 0; //frame pour le saut chr
    public static int frame = 0; //frame pour le sprite chr
    public static int frame2 = 0; //frame pour le sprite enemies
    public static int frame3 = 0; //frame pour le sprite enemies
    public static int frame4 = 0; //frame pour le sprite enemies
    public static int lvlPos = 0;
    public static boolean isLeveling;

    // création de la surface de dessin
    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
        gameLoopThread = new GameLoopThread(this);
        backGround = new BackGround(this.getContext(), R.drawable.bg_home);
        chr = new Character(this.getContext());
        isLeveling = false;
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas == null) { return; }

        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);

        // on dessine les éléments
        backGround.draw(canvas);
        /*!!!!Changements!!!*/
        //todo
        if(frame >= 10)
            frame = 0;
        chr.draw(canvas);
        frame++;
    }

    // Fonction appelée par la boucle principale (gameLoopThread)
    // On gère ici le déplacement des objets
    public void update() {
        chr.moveWithCollisionDetection();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus GameLoopThread si cela n'est pas fait
        if(gameLoopThread.getState()==Thread.State.TERMINATED) {
            gameLoopThread=new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée juste avant que l'objet ne soit détruit.
    // on tente ici de stopper le processus de gameLoopThread
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            }
            catch (InterruptedException ignored) {}
        }
    }

    // Gère les touchés sur l'écran
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = (int)event.getX();
        currentY = (int)event.getY();

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                if(currentX <= chr.getX())
                    chr.isLeft = true;
                else if(currentX >= chr.getX())
                    chr.isLeft = false;
                chr.setRun(true);
                chr.setMove(true);
                break;

            //si le 2e doigt touche le curseur
            case MotionEvent.ACTION_POINTER_DOWN:
                break;

            // code exécuté lorsque le doight glisse sur l'écran.
            case MotionEvent.ACTION_MOVE:
                if(currentX <= chr.getX())
                    chr.isLeft = true;
                else if(currentX >= chr.getX())
                    chr.isLeft = false;
                chr.setRun(true);
                chr.setMove(true);
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

        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    public LoadImageTask LoadGameImages(int ressource, int framecount, int w, int h) {
        //Asynctask Thread
        return (LoadImageTask) new LoadImageTask(ressource, w * framecount, h)
                .execute(getContext());
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        backGround.resize(w, h);
        chr.resize(w, h); // on définit la taille de la chr selon la taille de l'écran
        //idle
        task[0] = LoadGameImages(R.drawable.idle, 10, chr.getchrW(), chr.getchrH());
        //run
        task[1] = LoadGameImages(R.drawable.run, 10, (chr.getchrW()*5/4), chr.getchrH());
        //jump
        task[2] = LoadGameImages(R.drawable.jump, 10, (chr.getchrW()*5/4), chr.getchrH());
        //atk1
        task[3] = LoadGameImages(R.drawable.atk, 10, (chr.getchrW()*5/3), chr.getchrH());
        //atk2
        task[4] = LoadGameImages(R.drawable.jump_atk, 10, (chr.getchrW()*5/3), chr.getchrH());
        //atk3
        task[5] = LoadGameImages(R.drawable.thrw, 10, chr.getchrW(), chr.getchrH());
        task[6] = LoadGameImages(R.drawable.jump_throw, 10, chr.getchrW(), chr.getchrH());
        //death
        task[7] = LoadGameImages(R.drawable.dead, 10, chr.getchrW(), chr.getchrH());
    }

} // class GameView
