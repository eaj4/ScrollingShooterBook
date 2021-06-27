package com.emily.scrollingshooter;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

class UIController implements InputObserver {

    //call the addObserver method using the GameEngineBroadcaster instance, b, that was passed in as a
    // parameter. A reference to this class will now be safely tucked away in the inputObservers ArrayList
    // in the GameEngine class.
    public UIController(GameEngineBroadcaster b){
        b.addObserver(this);
    }

    //This is the method that GameEngine will call each time that the onTouchEvent method receives a new
    // MotionEvent reference from the OS.
    @Override
    public void handleInput(MotionEvent event, GameState gameState, ArrayList<Rect> buttons) {

        //the MotionEvent class holds data about multiple touches and more advanced data on things
        // such as movements on the screen and even more besides. Locked away inside the MotionEvent reference
        // are multiple coordinates of multiple event types.

        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);
        //This code gets an int value, which represents the type of event that occurred. It is now stored in the
        // eventType variable, ready to be compared in the next line of code.
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;

        //will execute for either ACTION_UP or ACTION_POINTER_UP. These are the only two event types we need to respond to.
        if(eventType == MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP) {

            //checks to see whether the coordinates of the touch are inside the pause button:
            if (buttons.get(HUD.PAUSE).contains(x, y)){
                // Player pressed the pause button
                // Respond differently depending
                // upon the game's state
                // If the game is not paused
                if (!gameState.getPaused()) {
                    // Pause the game
                    gameState.pause();
                }

                // If game is over start a new game
                else if (gameState.getGameOver()) {
                    gameState.startNewGame();
                }

                // Paused and not game over
                else if (gameState.getPaused() && !gameState.getGameOver()) {
                    gameState.resume();
                }
            }
        }
    }

}