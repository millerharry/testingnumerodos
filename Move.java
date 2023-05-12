// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.actions;

import robotwar.core.Action;
import robotwar.core.Battle;
import robotwar.core.Robot;
import robotwar.util.Position;

/**
 * Represents a move made by a Robot in the game.
 *
 * @author David J. Pearce
 *
 */
public class Move implements Action {
	/**
	 * Position to move from
	 */
	private Position origin;

	/**
	 * Position to move to
	 */
	private Position destination;

	/**
	 * Robot doing the move
	 */
	private Robot.State robot;

	public Move(Robot.State robot, Position.Direction direction) {
		this.robot = robot;
		this.origin = robot.getPosition();
		this.destination = origin.move(direction, 1);
	}

	/**
	 * Get the origin point for this move
	 *
	 * @return
	 */
	public Position getOrigin() {
		return origin;
	}

	/**
	 * Get the destination point for this move
	 *
	 * @return
	 */
	public Position getDestination() {
		return destination;
	}

	/**
	 * Get the robot making this move.
	 *
	 * @return
	 */
	public Robot.State getRobot() {
		return robot;
	}

	/**
	 * Check whether this move is valid or not. Specifically, a move is invalid if
	 * the destination is outside of the arena.
	 *
	 * @return
	 */
	public boolean isValid() {
		int x = destination.getX();
		int y = destination.getY();
		return x >= 0 && x < robot.getWidth() && y >= 0 && y < robot.getHeight();
	}

	@Override
	public void apply(Battle b) {
		if (isValid()) {
			// Destination is within bounds, so update location of robot
			robot.setPosition(destination);
		}
	}
}
