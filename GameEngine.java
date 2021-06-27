package com.emily.scrollingshooter;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.ArrayList;

//broadcaster
class GameEngine extends SurfaceView implements Runnable, GameStarter, GameEngineBroadcaster {

    private Thread mThread = null;
    private long mFPS;
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    UIController mUIController;
    private GameState mGameState;
    private SoundEngine mSoundEngine;
    HUD mHUD;
    Renderer mRenderer;
    ParticleSystem mParticleSystem;
    PhysicsEngine mPhysicsEngine;

    public GameEngine(Context context, Point size) {
        super(context);
        //calls a constructor, like any other code, but also passes in the this reference, which is a
        // GameEngineBroadCaster reference that the UIController class uses to call the addObserver method.
        mUIController = new UIController(this);
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mHUD = new HUD(size);
        mRenderer = new Renderer(this);
        mPhysicsEngine = new PhysicsEngine();
        mParticleSystem = new ParticleSystem();
        // Even just 10 particles look good
        // But why have less when you can have more
        mParticleSystem.init(1000);
    }

    // For the game engine broadcaster interface
    public void addObserver(InputObserver o) {
        inputObservers.add(o);
    }

    @Override
    public void run() {
         // will only execute when the GameState class informs us that the thread is running.
         while (mGameState.getThreadRunning()) {
             //records current time
             long frameStartTime = System.currentTimeMillis();
             //which checks that GameState is not paused before allowing the objects to be updated
             if (!mGameState.getPaused()) {
                 // Update all the game objects here in a new way
                 // This call to update will evolve with the project
                 if(mPhysicsEngine.update(mFPS, mParticleSystem)) {
                     // Player hit
                     deSpawnReSpawn();
                 }
             }
             // Update all the game objects here in a new way
             // Draw all the game objects here in a new way
             mRenderer.draw(mGameState, mHUD, mParticleSystem);
             
             // Measure the frames per second in the usual way
             //calculates how long all the updating and drawing took and assigns the result to the mFPS
             long timeThisFrame = System.currentTimeMillis() - frameStartTime;
             if (timeThisFrame >= 1) {
                 final int MILLIS_IN_SECOND = 1000;
                 mFPS = MILLIS_IN_SECOND / timeThisFrame;
             }
         }
    }

    // receives the details of any interaction with the screen.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // Handle the player's input here But in a new way
 
        // loops through all the InputObserver instances in the ArrayList instance and calls their handleInput
        // method (which they are guaranteed to have implemented). If there are 0, just 1, or 1,000 observers
        // in the ArrayList instance, the code will work just the same.
        for (InputObserver o : inputObservers) {
            o.handleInput(motionEvent, mGameState,mHUD.getControls());
        }

        
         
        return true;
    }

    //stop the thread that controls when the run method executes.
    public void stopThread() {
        // New code here soon
         mGameState.stopEverything();

        try {
            mThread.join();
        } catch (InterruptedException e) {
            Log.e("Exception","stopThread()" + e.getMessage());
        }
    }

    //start the thread that controls when the run method executes.
    public void startThread() {
        // New code here soon
          mGameState.startThread();

        mThread = new Thread(this);
        mThread.start();
    }

    public void deSpawnReSpawn() {
        // Eventually this will despawn and then respawn all the game objects

    }
   

}