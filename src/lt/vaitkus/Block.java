package lt.vaitkus;

import java.awt.*;
/**
 * This is the block class used to create the bricks that the player breaks
 * in the main game.
 * Created by Claude Daniel.
 */

public class Block extends Rectangle{
    /**
     * The color of the brick as a Color object.
     */
    private Color color;

    /**
     * The value of the color to determine the amount of points the player gets
     * upon destroying hte brick.
     */
    int colorValue;

    /**
     * boolean for whether the brick has already been destoyed or not.
     */
    private boolean destroyed;

    /**
     * The speed at which the brick moves horizontally.
     */
    int dx;

    /**
     * The constructor for the brick. Chooses a color based on the random integer
     * c that was passed in.
     * @param x X co-ordinate of the brick
     * @param y Y co-ordiante of the brick
     * @param width Width of the brick
     * @param height height of the brick
     * @param c color of the brick as an integer
     * @param destroyed boolean of wehther the brick is destoyed or not.
     */
    public Block (int x, int y, int width, int height, int c, boolean destroyed){
        this.destroyed = destroyed;
        colorValue = c;
        if (destroyed == false) {
            setBounds(x, y, width, height);
            if (c == 0) {
                color = Color.RED;
            }
            if (c == 1) {
                color = Color.BLUE;
            }
            if (c == 2) {
                color = Color.ORANGE;
            }
            if (c == 3) {
                color = Color.GREEN;
            }
        } else {
            setBounds(0,0,0,0);
        }
    }

    /**
     * Used to check whether the brick has already been destroyed.
     * @return boolean destroyed
     */
    public boolean isDestroyed(){
        return destroyed;
    }

    /**
     * Get the value of the color as an integer to determine the
     * points the player gets for destroying the brick.
     * @return color integer
     */
    public int getColorValue (){
        return colorValue;
    }

    /**
     * The default drawn state of the bricks, with a color, x, y and height
     * and width.
     * @param g the Graphic
     */
    public void draw( Graphics g ) {
        g.setColor(color);
        g.fillRect( x, y, width, height);
    }

    /**
     * This is used to make bricks move. They move to the right edge of the screen
     * and then wrap around to the the other side.
     * @param winWidth the width of the window of main program
     * @param speed the speed at which the block travels
     */
    public void move( int winWidth, int speed) {
        dx = speed;
        if(x > winWidth) {
            x = -width;
        }
        if(x < 0-width) {
            x = winWidth;
        }
        translate( dx, 0);
    }
}
