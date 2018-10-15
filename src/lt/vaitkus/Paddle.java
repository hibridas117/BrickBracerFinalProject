package lt.vaitkus;

import java.awt.*;
/**
 * This is the paddle the player uses to bounce the ball.
 *
 * Created by Claude Daniel.
 */
public class Paddle extends Rectangle{

    /**
     * The speed of the paddle
     */
    private int speed;

    /**
     * The color of the paddle
     */
    private Color color;

    /**
     * The constructor for the paddle. Its a rather simple rectangle
     * with added properties.
     * @param x X co-ordinate of the paddle.
     * @param y Y co-ordinate of the paddle.
     * @param width Width of the paddle
     * @param height Height of the paddle
     * @param c color of the paddle as a Color object.
     * @param sp speed of the paddle
     */
    public Paddle (int x, int y, int width, int height, Color c, int sp){
        setBounds(x, y, width, height);
        speed = sp;
        color = c;
    }

    /**
     * The default drawn state of the paddle, with a color, x, y and height
     * and width.
     * @param g the Graphic
     */
    public void draw( Graphics g ) {
        g.setColor(color);
        g.fillRect( x, y, width, height);
    }

    /**
     * This is used to move the paddle.
     * The paddle moves in horizontal axis and doesn't go past the screen edge.
     * @param winWidth Width of the window of the main program.
     * @param side The side at which the paddle has met the edge of the screen.
     */
    public void move(int winWidth, int side) {
        if (side == 1) {
            if (x <= winWidth - width){
                translate(speed, 0);
            }
        }
        if(side == 2) {
            if (x>0){
                translate(-speed, 0);
            }
        }
    }
}

