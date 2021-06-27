package com.emily.scrollingshooter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;

// be responsible for passing the references of Canvas and Paint to the HUD class's draw method.
// The Renderer class will also call the draw methods of all the other classes (when we have coded them). 
class Renderer {
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    //The constructor that follows initializes the SurfaceHolder and Paint objects in the usual way
    // using the SurfaceView reference that gets passed in from the GameEngine class
    Renderer(SurfaceView sh){
        mSurfaceHolder = sh.getHolder();
        mPaint = new Paint();
    }

    void draw(GameState gs, HUD hud, ParticleSystem ps) {
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 0, 0, 0));


            //the two empty if blocks will eventually handle the two possible states of drawing (gs.getDrawing())
            // and game over (gs.getGameOver()).
            if (gs.getDrawing()) {
                // Draw all the game objects here
            }

            if(gs.getGameOver()) {
                // Draw a background graphic here
            }
            // Draw a particle system explosion here
            if(ps.mIsRunning){
                ps.draw(mCanvas, mPaint);
            }

        // Now we draw the HUD on top of everything else
        //The call to hud.draw is made regardless because it always needs to be drawn; however, as you might recall,
            // the HUD draws itself slightly differently depending upon the current state of the game.
            hud.draw(mCanvas, mPaint, gs);
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

}