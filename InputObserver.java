package com.emily.scrollingshooter;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;


//The ArrayList reference will contain the position of each of the buttons on the screen. Code the interface
// as follows:
interface InputObserver {
    void handleInput(MotionEvent event, GameState gs,ArrayList<Rect> controls);
}