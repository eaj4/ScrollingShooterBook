package com.emily.scrollingshooter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.ArrayList;

class HUD {
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private ArrayList<Rect> controls;
    static int UP = 0;
    static int DOWN = 1;
    static int FLIP = 2;
    static int SHOOT = 3;
    static int PAUSE = 4;

    HUD(Point size){
        //initializes the member variables to remember the screen width and height
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        //is useful to have mTextFormatting as it is now relative to the screen width
        // and can be used when scaling parts of the HUD in other parts of the class.
        mTextFormatting = size.x / 50;
        prepareControls();
    }

    private void prepareControls(){

        // declare and initialize three int variables that will act as the values to help us size and
        // space out our buttons. They are called buttonWidth, buttonHeight, and buttonPadding.
        // All the initialization formulas are based on values relative to the screen size.
        int buttonWidth = mScreenWidth / 14;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 90;

        //We can now use the three variables when scaling and positioning the Rect instances, which represent each of the buttons. 
        //they each take four parameters and those parameters are for the left, top, right, and bottom positions, in that order.
        Rect up = new Rect(buttonPadding, mScreenHeight - (buttonHeight * 2) - (buttonPadding * 2),
                buttonWidth + buttonPadding, mScreenHeight - buttonHeight - (buttonPadding *2));

        Rect down = new Rect(buttonPadding,mScreenHeight - buttonHeight - buttonPadding,
                buttonWidth + buttonPadding,mScreenHeight - buttonPadding);

        Rect flip = new Rect(mScreenWidth - buttonPadding - buttonWidth,mScreenHeight - buttonHeight -
                buttonPadding,mScreenWidth - buttonPadding,mScreenHeight - buttonPadding);

        Rect shoot = new Rect(mScreenWidth - buttonPadding - buttonWidth,mScreenHeight - (buttonHeight * 2) -
                (buttonPadding * 2),mScreenWidth - buttonPadding,mScreenHeight - buttonHeight -(buttonPadding *2));

        Rect pause = new Rect(mScreenWidth - buttonPadding - buttonWidth,buttonPadding,mScreenWidth - buttonPadding,
                buttonPadding + buttonHeight);

        //The final part of the prepareControls method initializes the controls ArrayList instance
        // and then adds each of the Rect objects using the ArrayList class's add method.
        controls = new ArrayList<>();
        controls.add(UP,up);
        controls.add(DOWN,down);
        controls.add(FLIP, flip);
        controls.add(SHOOT, shoot);
        controls.add(PAUSE, pause);

    }

    void draw(Canvas c, Paint p, GameState gs){

        // Draw the HUD
        //The color to draw with is chosen with the setColor method.
        //The size of the text is set with the setTextSize method and the mFormatting variable is used as the size.
        //Three lines of text are drawn using the drawText method to display the high score, score, and the number of
        // lives the player has. Notice how the mTextFormatting variable is used repeatedly to space out the lines of
        // text from each other and how the GameState reference (gs) is used to access the high score, score, and the
        // number of the player's lives remaining.
        p.setColor(Color.argb(255,255,255,255));
        p.setTextSize(mTextFormatting);
        c.drawText("Hi: " + gs.getHighScore(), mTextFormatting,mTextFormatting,p);
        c.drawText("Scrore: " + gs.getScore(), mTextFormatting,mTextFormatting * 2,p);
        c.drawText("Lives: " + gs.getNumShips(), mTextFormatting,mTextFormatting * 3,p);

        //first executes when the game is over (if(gs.getGameOver)) and inside, the text size and position
        // are reformatted and the PRESS PLAY message is drawn to the screen.
        if(gs.getGameOver()){
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PRESS PLAY",mScreenWidth /4, mScreenHeight /2 ,p);
        }

        //executes when the game is paused but not over. This is because we pause the game when the game is over
        // (to stop updates) but we also pause the game when the game is not over (because the player has pressed
        // the pause button and intends to resume eventually). Inside this if block, the text size and position are
        // reformatted and the PAUSED text is drawn to the screen.
        if(gs.getPaused() && !gs.getGameOver()){
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PAUSED",mScreenWidth /3, mScreenHeight /2 ,p);
        }

        drawControls(c, p);
    }


    private void drawControls(Canvas c, Paint p){
        // changes the drawing color with the setColor method. Look at the first argument sent to the Color.argb
        // method as it is different from all the times we have used it so far. The value of 100 will create a
        // transparent color. This means that any spaceships and the scrolling background will be visible beneath it.
        p.setColor(Color.argb(100,255,255,255));
        //enhanced for loop to loop through each Rect instance in turn and use the drawRect method of the Canvas
        // class to draw our transparent button in the position decided in the prepareControls method.
        for(Rect r : controls){
            c.drawRect(r.left, r.top, r.right, r.bottom, p);
        }
        // Set the colors back
        p.setColor(Color.argb(255,255,255,255));
    }

    //we can compare the position of the Rect objects to the position of the screen touches to decipher the player's intentions.
    ArrayList<Rect> getControls(){
        return controls;
    }

}