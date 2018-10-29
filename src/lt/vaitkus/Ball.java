package lt.vaitkus;

import java.awt.*;

/**
 * This is the Ball that bounces around the screen. Small stationary versions of
 * this ball are also used to represent lives remaining.
 *
 */
public class Ball extends Rectangle {

	/**
	 * The speed of movement in the x and y directions.
	 */
	private int dx, dy;

	/**
	 * The color of the ball.
	 */
	private Color color;

	/**
	 * The speed at which the ball moves with the paddle, while waiting to be
	 * launched.
	 */
	private int dragSpeed;

	/**
	 * The constructor for the ball that extends the Rectangle object.
	 *
	 * @param x      The X co-ordinate of the ball.
	 * @param y      The Y Co-ordiante of the ball.
	 * @param radius The radius of the ball.
	 * @param c      The color of the ball as a Color object.
	 * @param sp     The speed at which the ball moves.
	 * @param drag   the speed at which the ball is dragged with the paddle.
	 */
	public Ball(int x, int y, int radius, Color c, int sp, int drag) {
		setBounds(x, y, 2 * radius, 2 * radius);
		dragSpeed = drag;
		dx = sp;
		dy = -sp;
		color = c;
	}

	/**
	 * The default drawn state of the ball, with a color, x, y and height and width.
	 * 
	 * @param g the Graphic
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);

	}

	/**
	 * This method us called when the ball bounces against a brick, in order to
	 * change the direction of motion of the ball.
	 * 
	 * @param direction 1 if the ball bounces horizontally, and 2 if the ball
	 *                  bounces vertically.
	 */
	public void bounce(int direction) {
		if (direction == 1) {
			dx = -dx;
		}
		if (direction == 2) {
			dy = -dy;
		}
	}

	/**
	 * This method is used to drag the ball along with the paddle. It is designed to
	 * move at the same speed as the paddle.
	 * 
	 * @param winWidth The width of the main window.
	 * @param side     The side or direction of horizontal motion.
	 */
	public void dragBall(int winWidth, int side) {

		if (side == 1) {
			if (x < winWidth - width - 40) {
				translate(dragSpeed, 0);
			}
		}
		if (side == 2) {
			if (x > 0 + 40) {
				translate(-dragSpeed, 0);
			}
		}
	}

	/**
	 * This method contains the physics of the ball as is moves around and bounces
	 * off the walls and the paddle. If the ball meets the wall or the paddle, it
	 * bounces off in the relevant direction.
	 * 
	 * @param winWidth  The width of the main window.
	 * @param winHeight The height of the main window.
	 * @param PaddleX   The X location of the paddle.
	 * @param PaddleY   The Y location of the paddle.
	 */
	public void move(int winWidth, int winHeight, int PaddleX, int PaddleY) {

		if (x > winWidth - width || x < 0)
			dx = -dx;

		if (dy > 0 && (y + height) > PaddleY && x > PaddleX && x < PaddleX + 100) {
			dy = -dy;
		}

		if (y > winHeight - height || y < 0)
			dy = -dy;

		translate(dx, dy);
	}

	/**
	 * Retrieve the X direction of the ball. Used to check direction of the ball for
	 * bouncing off bricks.
	 * 
	 * @return the x direction.
	 */
	public int getXDirection() {
		return dx;
	}

	/**
	 * Retrieve the Y direction of the ball. Used to check direction of the ball for
	 * bouncing off bricks.
	 * 
	 * @return the y direction.
	 */
	public int getYDirection() {
		return dy;
	}
}