// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.ui;

import java.io.IOException;
import java.awt.*;
import java.util.*;
import javax.imageio.ImageIO;
import robotwar.core.Robot;
import robotwar.robots.PatrolBot;
import robotwar.util.Position;
import robotwar.actions.Move;
import robotwar.actions.Shoot;
import robotwar.core.*;

import robotwar.core.Battle;

/**
 * <p>
 * Implements the battle area in which robots are drawn. This is implement as an
 * extension of java.awt.Canvas, and robots are drawn directly onto this as
 * images. The background is constructed by drawing a sequence of filled squares
 * with different colours.
 * </p>
 *
 * <p>
 * The battle area uses "interpolation" to give the robots smoother movement
 * from one square to another. The idea behind interpolation is that we plot the
 * line from the current position to the next position and then draw the robot
 * at different positions along that line. This stops the robot from seemingly
 * "jumping" from one location to another.
 * </p>
 *
 * @author David J. Pearce
 *
 */
public class BattleCanvas extends Canvas {

	/**
	 * The image path simply determines where images are stored relative to this
	 * class.
	 */
	private static final String IMAGE_PATH = "images/";

	/**
	 * The square width constant determines the width (in pixels) of a square in
	 * the battle area. Changing this constant should automatically reshape the
	 * display. The optimal size is 32x32, based on the dimensions of the images
	 * used for the robots.
	 */
	private static final int SQUARE_WIDTH = 32;

	/**
	 * The square height constant determines the height (in pixels) of a square
	 * in the battle area. Changing this constant should automatically reshape
	 * the display. The optimal size is 32x32, based on the dimensions of the
	 * images used for the robots.
	 */
	private static final int SQUARE_HEIGHT = 32;

	/**
	 * The interpolation steps constants determines the number of interpolation
	 * points used to show robots as they move between squares. In essence, a
	 * robot moves from one square to another adjacency square. However, to make
	 * this seem smoother on the display we interpolate between the two squares
	 * so that the robot is drawn at different positions along the line of
	 * movement. This constant number of distinct steps used for interpolation.
	 * The more steps, the smoother the movement will seem (but the slower it
	 * will go). If you increase the number of steps, you probably want to
	 * decrease the clock tick interval.
	 */
	private static final int INTERPOLATION_STEPS = 10;

	/**
	 * Represents the image of a robot which is now dead. That is, where the
	 * isDead flag is true.
	 */
	private static Image deadBot = loadImage("DeadRobot.png");

	/**
	 * An image used to represent instances of "GuardBot".
	 */
	private static Image guardBot = loadImage("Robot4.png");

	/**
	 * Provides access to the battle being displayed. Through this, the battle
	 * canvas can determine what robots there are and where they need to be
	 * drawn.
	 */
	private final Battle battle;

	/**
	 * The step counter is used to determine what stage through an interpolation
	 * we are. This should always be a value between 0 and INTERPOLATION_STEPS
	 * (inclusive). Furthermore, when the step is zero, the robot has not moved
	 * at all. On the other hand, when the step == INTERPOLATION_STEPS then the
	 * robot has moved all the way into its next square.
	 */
	public int step = 0;

	/**
	 * Construct a canvas to visually display a given robot battle.
	 *
	 * @param battle
	 */
	public BattleCanvas(Battle battle) {
		this.battle = battle;
		setBounds(0, 0, battle.getWidth() * SQUARE_WIDTH, battle.getHeight()
				* SQUARE_HEIGHT);
	}

	/**
	 * Paint the given battle onto the given graphics object.
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();

		// Draw the background of the display as a rectangle of all white.
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);

		// Draw alternating boxes of light gray to give a checker-board display,
		// making it easier to distinguish the individual squares of the board.
		g2d.setColor(Color.LIGHT_GRAY);
		for (int x = 0; x < battle.getWidth(); x = x + 1) {
			for (int y = 0; y < battle.getHeight(); y = y + 1) {
				if ((x + y) % 2 == 0) {
					int xp = x * SQUARE_WIDTH;
					int yp = y * SQUARE_HEIGHT;
					g2d.fillRect(xp, yp, SQUARE_WIDTH, SQUARE_HEIGHT);
				}
			}
		}

		// For each robot, draw the appropriate image depending on what kind of
		// robot it is. In doing this, we need to determine the actual x and y
		// coordinates according to the interpolation scheme.
		HashSet<Robot.State> moved = new HashSet<>();
		for (Action a : battle.getActions()) {
			if(a instanceof Move) {
				Move m = (Move) a;
				if(m.isValid()) {
					drawRobotMoving(g, m);
					moved.add(m.getRobot());
				}
			}
		}
		// Draw any robots which didn't move in that turn.
		for (Robot.State r : battle.getStates()) {
			if(!moved.contains(r)) {
				drawRobotStill(g,r);
			}
		}
		// Increment steps before drawing shots.
		step = (step + 1) % INTERPOLATION_STEPS;
		// For each action, draw shot
		for (Action a : battle.getActions()) {
			if(a instanceof Shoot && step == 0) {
				// shots only fired at beginning of movement phase.
				drawShot(g2d, (Shoot)a);
			}
		}
	}

	private void drawShot(Graphics2D g2d, Shoot shot) {
		Position shooterPos = shot.getShooter().getPosition();
		Position targetPos = shot.getTarget();
		if(!shot.isWithinRange()) {
			// Shot not within range
			g2d.setColor(Color.GRAY);
		} else if(battle.getRobotState(targetPos.getX(), targetPos.getY()) != null) {
			// Shot hits target
			int xp = targetPos.getX() * SQUARE_WIDTH;
			int yp = targetPos.getY() * SQUARE_HEIGHT;
			g2d.setColor(Color.RED);
			g2d.fillRect(xp, yp, SQUARE_WIDTH, SQUARE_HEIGHT);
		} else {
			// Shot misses target
			g2d.setColor(Color.YELLOW);
		}
		g2d.setStroke(new BasicStroke(5));
		int sx = (shooterPos.getX() * SQUARE_WIDTH) + (SQUARE_WIDTH >> 1);
		int sy = (shooterPos.getY() * SQUARE_WIDTH) + (SQUARE_WIDTH >> 1);
		int tx = (targetPos.getX() * SQUARE_WIDTH) + (SQUARE_WIDTH >> 1);
		int ty = (targetPos.getY() * SQUARE_WIDTH) + (SQUARE_WIDTH >> 1);
		g2d.drawLine(sx,sy,tx,ty);
	}

	private void drawRobotStill(Graphics g, Robot.State r) {
		Image image;
		if (r.isDestroyed()) {
			image = deadBot;
		} else if (r.getController() instanceof PatrolBot) {
			image = guardBot;
		} else {
			// dead code
			return;
		}
		Position p = r.getPosition();
		// Finally, draw the robot!!
		g.drawImage(image, p.getX() * SQUARE_WIDTH, p.getY() * SQUARE_HEIGHT, SQUARE_WIDTH, SQUARE_HEIGHT, null,
				null);

	}

	private void drawRobotMoving(Graphics g, Move move) {
		Image image;
		if (move.getRobot().isDestroyed()) {
			image = deadBot;
		} else if (move.getRobot().getController() instanceof PatrolBot) {
			image = guardBot;
		} else {
			// dead code
			return;
		}

		// Compute the actual x and y position based on the interpolation step.
		Position origin = move.getOrigin();
		Position destination = move.getDestination();
		int rxp = computePosition(destination.getX() * SQUARE_WIDTH, origin.getX() * SQUARE_WIDTH, step);
		int ryp = computePosition(destination.getY() * SQUARE_HEIGHT, origin.getY() * SQUARE_HEIGHT, step);

		// Finally, draw the robot!!
		g.drawImage(image, rxp, ryp, SQUARE_WIDTH, SQUARE_HEIGHT, null,
				null);
	}

	/**
	 * An offscreen buffer used to reduce flicker between frames.
	 */
	private Image offscreen = null;

	@Override
	public void update(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		if (offscreen == null || offscreen.getWidth(this) != width
				|| offscreen.getHeight(this) != height) {
			offscreen = createImage(width, height);
		}
		Image localOffscreen = offscreen;
		Graphics2D offgc = (Graphics2D) offscreen.getGraphics();
		// do normal redraw
		paint(offgc);
		// transfer offscreen to window
		g.drawImage(localOffscreen, 0, 0, this);
	}

	private static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		java.net.URL imageURL = BattleCanvas.class.getResource(IMAGE_PATH
				+ filename);
		try {
			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}

	private int computePosition(int np, int op, int step) {
		int diff = np - op;
		diff = (diff * step) / INTERPOLATION_STEPS;
		return op + diff;
	}
}
