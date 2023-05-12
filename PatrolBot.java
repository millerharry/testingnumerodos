// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.robots;

import java.util.ArrayList;
import java.util.List;

import robotwar.core.Robot;
import robotwar.util.Position;
import robotwar.util.Position.Direction;

/**
 * The GuardBot marks out its starting location and hangs around there
 * protecting it.
 *
 * @author David J. Pearce
 *
 */
public class PatrolBot implements Robot.Controller {
	/**
	 * Records the starting position of the robot; this is needed to determine the
	 * area in which it patrols.
	 */
	private Position origin;
	/**
	 * The current direction in which the robot is moving.
	 */
	private Position.Direction direction;
	/**
	 * The size of the area to patrol
	 */
	private int area;
	/**
	 * Last know positions of robots in sight.
	 */
	private final ArrayList<Position> lastSeenPositions;

	/**
	 * Construct a patrol robot with a given starting position and strength.
	 *
	 * @param position
	 * @param strength
	 */
	public PatrolBot(int area) {
		this.area = area;
		this.direction = Direction.NORTH;
		this.lastSeenPositions = new ArrayList<>();
	}

	/**
	 * Move the robot according to a simple strategy which sees the robot
	 * circling (and protecting) its originating position. If another robot is
	 * sighted then it is attacked immediately.
	 */
	@Override
	public void takeTurn(Robot.View view) {
		// Set origin (if not already set)
		if(origin == null) {
			this.origin = view.getPosition();
		}
		// Attack any robots in sight
		attemptShot(view);
		// Update our direction (if necessary)
		makeMove(view);
	}

	/**
	 * Attempt to shot a robot in sight (if there are any). This requires predicting
	 * where the robot will be on the next move.
	 *
	 * @param view
	 */
	private void attemptShot(Robot.View view) {
		List<Robot.View> robots = view.getRobots();
		ArrayList<Position> currentRobotPositions = new ArrayList<>();
		for(int i=0;i!=robots.size();++i) {
			Robot.View robot = robots.get(i);
			Position position = robot.getPosition();
			Position target = getPredictedTarget(position);
			if(target != null && !robot.isDestroyed() && target.distance(view.getPosition()) < view.getRange()) {
				// Ok, we have target so attempt a shot.
				view.fire(target);
				break;
			}
			currentRobotPositions.add(position);
		}
		// Update the list of last seen positions
		lastSeenPositions.clear();
		lastSeenPositions.addAll(currentRobotPositions);
	}

	/**
	 * Make a move based on our current direction. The key challenge here is avoid
	 * trying to move out of the arena as otherwise we will get stuck.
	 *
	 * @param view
	 */
	private void makeMove(Robot.View view) {
		Position delta = view.getPosition().subtract(origin);
		switch(direction) {
		case NORTH:
			if(delta.getY() >= (area-1)) {
				// Reach north-western point
				this.direction = Direction.EAST;
			}
			break;
		case SOUTH:
			if(delta.getY() == 0) {
				// Reach south-eastern point
				this.direction = Direction.WEST;
			}
			break;
		case EAST:
			if(delta.getX() >= (area-1)) {
				// Reach north-eastern point
				this.direction = Direction.SOUTH;
			}
			break;
		case WEST:
			if(delta.getX() == 0) {
				// Reach south-wester point
				this.direction = Direction.NORTH;
			}
			break;
		}
		// Check whether this will take us out of the area.
		Position next = view.getPosition().move(direction, 1);
		while(!isInsideArena(next, view)) {
			direction = direction.rotate();
			next = view.getPosition().move(direction, 1);
		}
		// Add action to list of actions
		view.move(direction);
	}

	/**
	 * Check whether a given position is outside the arena or not.
	 *
	 * @param p Position to check
	 * @return
	 */
	private boolean isInsideArena(Position p, Robot.View view) {
		int x = p.getX();
		int y = p.getY();
		return x >= 0 && x < view.getWidth() && y >= 0 && y < view.getHeight();
	}

	/**
	 * Given the current position of a target robot, attempt to predice the robots
	 * future position based on its direction of movement.
	 *
	 * @param position
	 * @return
	 */
	private Position getPredictedTarget(Position position) {
		for(int i=0;i!=lastSeenPositions.size();++i) {
			Position target = getPredictedTarget(position,lastSeenPositions.get(i));
			if(target != null) {
				return target;
			}
		}
		return null;
	}

	private static Position NORTH = new Position(0,1);
	private static Position SOUTH = new Position(0,-1);
	private static Position EAST = new Position(1,0);
	private static Position WEST = new Position(-1,0);

	/**
	 * Given the current position of a target robot, and its potential last seen
	 * position, attempt to make a prediction. In many cases, this will fail because
	 * the last seen position is invalid for the target robot.
	 *
	 * @param current
	 * @param last
	 * @return
	 */
	private Position getPredictedTarget(Position current, Position last) {
		// Determine difference between current and last seen position
		Position delta = current.subtract(last);
		//
		if(last.equals(current)) {
			// Robot appears to be static
			return current;
		} else if(delta.equals(NORTH)) {
			// Robot appears to be moving NORTH
			return current.add(NORTH);
		} else if(delta.equals(SOUTH)) {
			// Robot appears to be moving SOUTH
			return current.add(SOUTH);
		} else if(delta.equals(EAST)) {
			// Robot appears to be moving EAST
			return current.add(EAST);
		} else if(delta.equals(WEST)) {
			// Robot appears to be moving WEST
			return current.add(WEST);
		} else {
			// Last seen position was invalid.
			return null;
		}
	}

	@Override
	public String toString() {
		return "P";
	}
}
