package com.emily.scrollingshooter;
import android.content.Context;
import android.content.SharedPreferences;

final class GameState {

    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;
    // This object will have access to the deSpawnReSpawn
    // method in GameEngine- once it is initialized
    private GameStarter gameStarter;
    private int mScore;
    private int mHighScore;
    private int mNumShips;
    // This is how we will make all the high scores persist
    private SharedPreferences.Editor mEditor;

    GameState(GameStarter gs, Context context){
        // This initializes the gameStarter reference
        gameStarter = gs;
        // Get the current high score
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore", Context.MODE_PRIVATE);
        // Initialize the mEditor ready
        mEditor = prefs.edit();
        // Load high score from a entry in the file labeled "hiscore"
        // if not available highscore set to zero 0
        mHighScore = prefs.getInt("hi_score", 0);
    }

    private void endGame(){
        // sets mGameover and mPaused to true so that any parts of our code that query to find out these
        // states can know the current state as well.
        mGameOver = true;
        mPaused = true;
        //k tests whether mScore is higher than mHighScore, which would mean the player achieved a new
        // high score. If they have, then the value of mScore is assigned to mHighScore.
        if(mScore > mHighScore){
            mHighScore = mScore;
        // Save high score
            mEditor.putInt("hi_score", mHighScore);
            mEditor.commit();
        }
    }

    void startNewGame(){
        mScore = 0;
        mNumShips = 3;
        // Don't want to be drawing objects while deSpawnReSpawn is clearing them and spawning them again
        stopDrawing();
        gameStarter.deSpawnReSpawn();
        resume();
        // Now we can draw again
        startDrawing();
    }

    // The se variable is an instance of the SoundEngine class, which was passed in as a parameter to the
    // loseLife method (also highlighted). The code playPlayerExplode() method will play a nice explosion sound effect.
    void loseLife(SoundEngine se){
        mNumShips--;
        se.playPlayerExplode();
        // block checks whether this latest catastrophe leaves mNumShips at 0 and if it does, it pauses
        // and then ends the game by calling the pause and endGame methods.
        if(mNumShips == 0){
            pause();
            endGame();
        }
    }

    int getNumShips(){
        return mNumShips;
    }

    void increaseScore(){
        mScore++;
    }

    int getScore(){
        return mScore;
    }

    int getHighScore(){
        return mHighScore;
    }

    void pause(){
        mPaused = true;
    }

    void resume(){
        mGameOver = false;
        mPaused = false;
    }

    void stopEverything(){
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    boolean getThreadRunning(){
        return mThreadRunning;
    }

    void startThread(){
        mThreadRunning = true;
    }

    private void stopDrawing(){
        mDrawing = false;
    }

    private void startDrawing(){
        mDrawing = true;
    }

    boolean getDrawing() {
        return mDrawing;
    }

    boolean getPaused(){
        return mPaused;
    }

    boolean getGameOver(){
        return mGameOver;
    }

}
