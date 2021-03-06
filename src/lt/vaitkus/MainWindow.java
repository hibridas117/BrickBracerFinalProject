package lt.vaitkus;

import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import javafx.stage.Stage;
import lt.vaitkus.graphic.HighScorePopUp;
import lt.vaitkus.graphic.StartWindow;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.awt.Color;

/**
 * This is the main class that runs the game. Contains a JFrame that contains a
 * JPanel which in turn contains the main components of the game.
 *
 */

public class MainWindow extends JFrame {
	/**
	 * The name of the player.
	 */

	public String playerName;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public static void main(String[] args) {
		// MainWindow w = new MainWindow();
	}

	/*
	 * public void startGame() { setBounds(100, 100, 800, 600);
	 * setTitle("Brick Braker game");
	 * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); Panel p = new Panel();
	 * setContentPane(p); Thread t = new Thread(p); t.start(); setVisible(true); }
	 */

	public void startGame(int level) {
		setBounds(100, 100, 800, 600);
		setTitle("Brick Braker game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panel p = new Panel(level);
		setContentPane(p);
		Thread t = new Thread(p);
		t.start();
		setVisible(true);
	}

	/**
	 * The constructor for the main window. This creates the panel and starts it as
	 * a thread.
	 */
	/*
	 * public MainWindow() { setBounds(100, 100, 800, 600);
	 * setTitle("Brick Braker game");
	 * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); Panel p = new Panel();
	 * setContentPane(p); Thread t = new Thread(p); t.start(); setVisible(true); }
	 */

	/**
	 * This is the Panel class that extends JPanel. Contains all the main components
	 * of the game.
	 */
	public class Panel extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {

		/**
		 * The main ball used for the game.
		 */
		private Ball ball;

		/**
		 * The one and only paddle used for the game.
		 */
		private Paddle paddle;

		/**
		 * The number of lives the player starts with.
		 */
		int lives = 3;

		/**
		 * The total points accumulated as blocks are broken. Starts at 0.
		 */
		int points = 0;

		/**
		 * The level of the game. Starts at 1.
		 */

		public int level;

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		/**
		 * The speed of the ball. 6 was the original
		 */

		int ballSpeed = 3;

		/**
		 * The array of blocks that the user tries to destroy.
		 */
		// TODO change to 32
		private Block[] blocks = new Block[32];

		/**
		 * The array balls that display the number of lives remaining.
		 */
		private Ball[] livesArray = new Ball[lives];

		/**
		 * This ballBegin boolean is set to true when the mouse is clicked or when the
		 * spacebar is pressed.
		 */
		boolean ballBegin = false;

		/**
		 * This gameOver boolean is set to true when all lives are lost.
		 */
		boolean gameOver = false;

		/**
		 * This gameWon boolean is set to true when all the blocks are destroyed in
		 * final level.
		 */
		boolean gameWon = false;

		/**
		 * This levelCompeted boolean is set to true when all the blocks of level are
		 * destroyed.
		 */
		boolean levelCompleted = false;

		/**
		 * It is set to true then you press P.
		 */

		boolean paused = false;

		// TODO Not used

		boolean musicPlay = false;

		Random random = new Random();

		/**
		 * Counter for destroyed blocks in every level.
		 */

		int countDestroyedBlocks = 0;

		/**
		 * The constructor for the panel. Here is where the objects' properties are
		 * initialized.
		 */
		public Panel(int level) {
			addMouseListener(this);
			addKeyListener(this);
			addMouseMotionListener(this);
			setFocusable(true);

			/**
			 * Plays the background music in a loop as long as the game has not yet been
			 * won.
			 */
			// TODO try to make mute button

			if (gameWon == false || paused == false) {
				//playInLoop("backgroundmusic.wav");
				// Thread.currentThread().getName();
			}

			setBackground(Color.DARK_GRAY);
			this.level = level;
			ballSpeed = ballSpeed * level;
			ball = new Ball(390 - ((3 - lives) * 10), 505, 10, Color.WHITE, (int) ballSpeed, 20);
			paddle = new Paddle(350, 522, 100, 15, Color.RED, 20);

			// Here, the blocks are created .
			int blockSpotX = 100;
			int blockSpotY = 100;
			for (int i = 1; i <= blocks.length; i++) {
				blocks[i - 1] = new Block(blockSpotX, blockSpotY, 70, 30, random.nextInt(4), false);
				blockSpotX = blockSpotX + 70 + 2;
				if (i % 8 == 0) {
					blockSpotY = blockSpotY + 30 + 2;
					blockSpotX = 100;
				}
			}

			// This creates the superficial balls representing lives remaining.
			int lifeBallSpotX = 90;
			for (int i = 0; i < lives; i++) {
				livesArray[i] = new Ball(lifeBallSpotX, 18, 7, Color.orange, 0, 0);
				lifeBallSpotX = lifeBallSpotX + 20;
			}

		}

		/**
		 * This where I decide which keys cause which actions. The left and right keys
		 * are used to move the paddle. The spacebar is used to launch the ball. P is
		 * used to pause the game
		 * 
		 * @param e the key event.
		 */
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				paddle.move(getWidth(), 1);
				if (ballBegin == false) {
					ball.dragBall(getWidth(), 1);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				paddle.move(getWidth(), 2);
				if (ballBegin == false) {
					ball.dragBall(getWidth(), 2);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				ballBegin = true;
				gameOver = false;
				play("shootball.wav");
			}
			if (e.getKeyCode() == KeyEvent.VK_P) {
				if (paused == false) {
					paused = true;
					// TODO
					musicPlay = false;
					// TODO change color of text
					getGraphics().drawString("GAME PAUSED ", 350, 300);

				} else {
					paused = false;
				}
			}
			// TODO Not in use
			if (e.getKeyCode() == KeyEvent.VK_M) {

				if (musicPlay == false) {
					musicPlay = true;
					// TODO change color of text

				} else {
					musicPlay = false;
				}
			}
		}

		/**
		 * The method to drag the paddle according to the movement of the mouse.
		 * 
		 * @param e The mouse event - the movement.
		 */
		public void mouseMoved(MouseEvent e) {
			Point move = new Point(e.getX(), e.getY());
			if (e.getX() > paddle.getX()) {
				paddle.move(getWidth(), 1);
				if (ballBegin == false) {
					ball.dragBall(getWidth(), 1);
				}
			}
			if (e.getX() < paddle.getX()) {
				paddle.move(getWidth(), 2);
				if (ballBegin == false) {
					ball.dragBall(getWidth(), 2);
				}
			}
		}

		/**
		 * This method is used to launch the ball when the mouse is clicked.
		 * 
		 * @param e The mouse event - the click.
		 */
		public void mouseClicked(MouseEvent e) {
			ballBegin = true;
			gameOver = false;
			play("shootball.wav");
		}

		/**
		 * A unused mouseMotionListener used to make MouseMoved work.
		 * 
		 * @param e the mouse event.
		 */
		public void mouseDragged(MouseEvent e) {
		}

		/**
		 * A unused mouseListener used to make MouseClicked work. An relevant action can
		 * be added later if required.
		 * 
		 * @param e the mouse event.
		 */
		public void mousePressed(MouseEvent e) {
		}

		/**
		 * A unused mouseListener used to make MouseClicked work. An relevant action can
		 * be added later if required.
		 * 
		 * @param e the mouse event.
		 */
		public void mouseReleased(MouseEvent e) {
		}

		/**
		 * A unused mouseListener used to make MouseClicked work. An relevant action can
		 * be added later if required.
		 * 
		 * @param e the mouse event.
		 */
		public void mouseEntered(MouseEvent e) {
		}

		/**
		 * A unused mouseListener used to make MouseClicked work. An relevant action can
		 * be added later if required.
		 * 
		 * @param e the mouse event.
		 */
		public void mouseExited(MouseEvent e) {
		}

		/**
		 * this method is used to play the various sounds. It uses file IO to retrieve
		 * the stores sound file.
		 *
		 * @param filename the sound file.
		 */
		public void play(String filename) {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(filename)));
				clip.start();
			} catch (LineUnavailableException e) {
				System.out.println("Audio Error");
			} catch (IOException e) {
				System.out.println("File Not Found");
			} catch (UnsupportedAudioFileException e) {
				System.out.println("Wrong File Type");
			}
		}

		/**
		 * Similar to the play() method but instead of playing the sound once, it plays
		 * it in a loop. Used in this program for the awesome 8-bit background music.
		 * 
		 * @param filename the sound file.
		 */
		public void playInLoop(String filename) {
			try {

				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(filename)));
				// clip.stop();
				clip.loop(Clip.LOOP_CONTINUOUSLY);

			} catch (LineUnavailableException e) {
				System.out.println("Audio Error");
			} catch (IOException e) {
				System.out.println("File Not Found");
			} catch (UnsupportedAudioFileException e) {
				System.out.println("Wrong File Type");
			}
		}

		/**
		 * Stop() method to stop selected sound track.
		 * 
		 * @param filename the sound file.
		 */
		// TODO not in use
		// public void stop(String filename) {
		public void stop() {
			try {
				Clip clip = AudioSystem.getClip();
				// clip.open(AudioSystem.getAudioInputStream(new File(filename)));
				clip.stop();
				// myClip.stop();
			} catch (LineUnavailableException e) {
				System.out.println("Audio Error");
			}
		}

		//

		/**
		 * This method is arguably the heart of the game. This is the method that is
		 * repeatedly repainted to create the animation of the game. The various
		 * graphics are drawn and set in motion here.
		 * 
		 * @param g the graphic.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.WHITE);

			// Draws the total points on the top right and the lives left text on the top
			// left corner.
			// +Level text
			g.drawString("Points: " + points, 700, 50);
			g.drawString("Lives left: ", 20, 30);
			g.drawString("Level " + level, 700, 30);

			// Draws the paddle and the ball.
			paddle.draw(g);
			ball.draw(g);

			// Release the ball on mouse click or spacebar press.
			if (ballBegin == true) {
				ball.move(getWidth(), getHeight(), (int) paddle.getX(), (int) paddle.getY());
			}

			// Level 2
			// Draws the blocks. The first and 3rd rows of blocks is set to move.
			for (int i = 0; i < blocks.length; i++) {
				blocks[i].draw(g);
				if (level == 2) {
					blocks[i].draw(g);
					if (i < 8) {
						blocks[i].move(getWidth(), 1);
					}
					if (i > 15 && i < 24) {
						blocks[i].move(getWidth(), -1);
					}
				}
				// Level 3
				// Draws the blocks. Even number colums moving up and down
				if (level == 3) {
					blocks[i].draw(g);
					if (i % 8 == 0) {
						blocks[i].moveUpAndDow(getHeight() - ((i % 7) * 33), (i % 7) * 33);
					}
					if (i % 8 == 2) {
						blocks[i].moveUpAndDow(getHeight() - (((i - 2) % 7) * 33), ((i - 2) % 7) * 33);
					}
					if (i % 8 == 4) {
						blocks[i].moveUpAndDow(getHeight() - (((i - 4) % 7) * 33), ((i - 4) % 7) * 33);
					}
					if (i % 8 == 6) {
						blocks[i].moveUpAndDow(getHeight() - (((i - 6) % 7) * 33), ((i - 6) % 7) * 33);
					}
				}
			}

			// Draws the three balls representing the lives left on the top left corner of
			// screen.
			for (int i = 0; i < lives; i++) {
				livesArray[i].draw(g);
			}

			// Here is the logic for destroying the blocks. Once the block is destroyed, the
			// ball bounces
			// off in the specified direction by calling the ball's bounce() method.
			for (int i = 0; i < blocks.length; i++) {
				if (!blocks[i].isDestroyed() && ball.getX() + ball.getWidth() > blocks[i].getX()
						&& ball.getX() < (blocks[i].getX() + blocks[i].getWidth())
						&& ball.getY() + ball.getHeight() > blocks[i].getY()
						&& ball.getY() < blocks[i].getY() + blocks[i].getHeight()) {
					points = points + blocks[i].getColorValue();
					play("breakbrick.wav");

					// Ball moves southeast and hits left edge or top edge of block
					if (ball.getXDirection() > 0 && ball.getYDirection() > 0) {
						if (ball.getX() + ball.getWidth() > blocks[i].getX()
								&& ball.getY() + ball.getHeight() > blocks[i].getY() + blocks[i].getHeight()) {
							ball.bounce(1);
							blocks[i] = new Block(0, 0, 0, 0, 0, true);
							break;
						} else if ((ball.getY() + ball.getHeight()) > blocks[i].getY()) {
							ball.bounce(2);
							blocks[i] = new Block(0, 0, 0, 0, 0, true);
							break;
						}
					}
					// Ball moves southwest and hits right edge or top edge of block
					else if (ball.getXDirection() < 0 && ball.getYDirection() > 0) {
						if (ball.getX() < (blocks[i].getX() + blocks[i].getWidth())
								&& ball.getY() + ball.getHeight() > blocks[i].getY() + blocks[i].getHeight()) {
							ball.bounce(1);
							blocks[i] = new Block(0, 0, 0, 0, 0, true);
							break;
						} else if ((ball.getY() + ball.getHeight()) > blocks[i].getY()) {
							ball.bounce(2);
							blocks[i] = new Block(0, 0, 0, 0, 0, true);
							break;
						}
					}
					// Ball moves northeast and hits left edge of block
					else if (ball.getXDirection() > 0 && ball.getYDirection() < 0) {
						if ((ball.getX() + ball.getWidth()) > blocks[i].getX()
								&& ball.getY() + ball.getHeight() < blocks[i].getY() + blocks[i].getHeight()) {
							ball.bounce(1);
							blocks[i] = new Block(0, 0, 0, 0, 0, true);
							break;
						} else if (ball.getY() < (blocks[i].getY() + blocks[i].getHeight())) {
							ball.bounce(2);
							blocks[i] = new Block(0, 0, 0, 0, 0, true);
							break;
						}
					}
					// Ball moves northwest and hits right edge of the block.
					else if (ball.getXDirection() < 0 && ball.getYDirection() < 0) {
						if (ball.getX() < (blocks[i].getX() + blocks[i].getWidth())
								&& ball.getY() + ball.getHeight() < blocks[i].getY() + blocks[i].getHeight()) {
							ball.bounce(1);
							blocks[i] = new Block(0, 0, 0, 0, 0, true);
							break;
						} else if (ball.getY() < (blocks[i].getY() + blocks[i].getHeight())) {
							ball.bounce(2);
							blocks[i] = new Block(0, 0, 0, 0, 0, true);
							break;
						}
					}
				}
			}

			// This is where the ball and paddle are reset if the player dies. If all lives
			// are lost,
			// the boolean gameOver is set to true, thereby telling the thread to stop
			// running.
			if (gameWon == false) {
				if (ball.getY() > (this.getHeight() - ball.getHeight())) {
					if (lives > 1) {
						lives--;
						ballBegin = false;
						ball = new Ball(390 - ((3 - lives) * 10), 505, 10, Color.WHITE, (int) ballSpeed, 20);
						paddle = new Paddle(350, 522, 100 - ((3 - lives) * 20), 15, Color.RED, 20);
					} else {
						g.setColor(Color.WHITE);
						g.drawString("GAME OVER!", 370, 250);
						g.drawString("Your score is " + points, 370, 265);
						gameOver = true;
					}
				}
			}
			// TODO call pop up window with high score result
			// call pop up to save high score
			if (gameOver == true) {
				// HighScorePopUp hs = new HighScorePopUp(new Stage());

			}

			// Checks to see if every block has been destroyed to determine if the game has
			// been won.

			for (int i = 0; i < blocks.length; i++) {
				if (blocks[i].isDestroyed()) {
					countDestroyedBlocks++;
					if (countDestroyedBlocks == blocks.length) {
						levelCompleted = true;
					}

				} else {
					countDestroyedBlocks = 0;
					levelCompleted = false;
					gameWon = false;
					// return;
				}
			}

			// After all 3 levels are completed gameWon set to true.
			if (level == 3 && levelCompleted == true) {
				gameWon = true;
			}

			// After level is competed reseting paddle and the ball
			if (levelCompleted == true && gameWon == false) {
				if ("AWT-EventQueue-0".equals(Thread.currentThread().getName())) {
					// TODO change color
					// Text desplayed for 1 second
					getGraphics().drawString("LEVEL " + level + " COMPLETED!", 350, 250);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
				System.out.println(Thread.currentThread().getName());
				// paused = true;
				ballBegin = false;
				// Speed increase with every level
				ballSpeed = ballSpeed + 2;
				// Extra points for completing level
				points = points + level * 50;
				ball = new Ball(390 - ((3 - lives) * 10), 505, 10, Color.WHITE, ballSpeed, 20);
				paddle = new Paddle(350, 522, 100 - ((3 - lives) * 20), 15, Color.RED, 20);
				level++;
				levelCompleted = false;

				// new blocks for level 2
				if (level == 2) {
					int blockSpotX = 100;
					int blockSpotY = 100;
					for (int i = 1; i <= blocks.length; i++) {
						blocks[i - 1] = new Block(blockSpotX, blockSpotY, 70, 30, random.nextInt(4), false);
						blockSpotX = blockSpotX + 70 + 2;
						if (i % 8 == 0) {
							blockSpotY = blockSpotY + 30 + 2;
							blockSpotX = 100;
						}
					}
				}
				// new blocks for level 3
				if (level == 3) {
					int blockSpotX = 100;
					int blockSpotY = 100;
					for (int i = 1; i <= blocks.length; i++) {
						blocks[i - 1] = new Block(blockSpotX, blockSpotY, 70, 30, random.nextInt(4), false);
						blockSpotX = blockSpotX + 70 + 2;
						if (i % 8 == 0) {
							blockSpotY = blockSpotY + 30 + 2;
							blockSpotX = 100;
						}
					}
				}
			}

			if (gameWon == true) {
				g.drawString("YOU WON!", 370, 250);
			}

		}
		//TODO
		public void paintString(Graphics g) {
			super.paintComponents(g);
			g.setColor(Color.WHITE);
			g.drawString("Points: " + points, 600, 50);
		}
		/**
		 * This method is used to run the panel in the main window. It keeps running as
		 * long as the player hasn't lost all lives.
		 */
		public void run() {
			while (gameOver == false) {
				// If paused = true
				if (!paused) {
					repaint();
				}
				if (levelCompleted == true && gameWon == false) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println("Error detected");
					}
				}
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					System.out.println("Error detected");
				}

			}
			if (gameWon == true || gameOver == true) {
				Player.pointsScored = points;
			
				System.out.println("Enter to file/database: "+ StartWindow.player.getName() +" "+Player.pointsScored);
		
				/*editFile e = new editFile();
				e.openFile("scores.txt");
				e.addRecords(StartWindow.player.getName(), Player.pointsScored);
				e.closeFile();*/
				/*	paintComponent(getGraphics());
				for (int i = 0; i > 5; i++) {

					try {
						getGraphics().drawString("Game will restart in " + (5-i)+ " seconds", 350, 250);
						Thread.sleep(1000);
					} catch (Exception e) {
						System.out.println("Error");
					}
				}*/

				// StartWindow sw= new StartWindow();
				// Stage st = sw.start();
				// TODO enter score and name to leader board
				// TODO countdown to restart the game
				//
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

}