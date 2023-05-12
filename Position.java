// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.util;

import robotwar.core.Robot.State;
import robotwar.util.Position.Direction;

/**
 * Represents an (x,y) position on the game board.
 *
 * @author David J. Pearce
 *
 */
public final class Position {

	/**
	 * Represents one of the four position of the compass (North, East, South and
	 * West). This is used to the direction in which the player moves.
	 *
	 * @author David J. Pearce
	 *
	 */
	public enum Direction {
		NORTH,
		EAST,
		SOUTH,
		WEST;

		public Direction rotate() {
			switch(this) {
			case NORTH:
				return Direction.EAST;
			case SOUTH:
				return Direction.WEST;
			case EAST:
				return Direction.SOUTH;
			case WEST:
				return Direction.NORTH;
			default:
				throw new IllegalArgumentException("dead code reached");
			}
		}
	}

	/**
	 * X coordinate of this position.
	 */
	public int x;

	/**
	 * Y coordinate of this position.
	 */
	public int y;

	/**
	 * Construct a position on the board
	 *
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculate a relative position after moving a number of steps in a given
	 * direction from a starting point.
	 *
	 * @param direction
	 *            --- Direction to move in
	 * @param steps
	 *            --- Number of steps to take
	 * @return
	 */
	public Position move(Direction direction, int steps) {
		int x = this.x;
		int y = this.y;
		switch (direction) {
		case NORTH:
			y += steps;
			break;
		case SOUTH:
			y -= steps;
			break;
		case EAST:
			x += steps;
			break;
		case WEST:
			x -= steps;
			break;
		}
		return new Position(x,y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * Add an origin from this position to produce a new position. For example,
	 * (3,4)+(1,1) gives (4,5).
	 *
	 * @param origin
	 * @return
	 */
	public Position add(Position origin) {
		return new Position(x + origin.x, y + origin.y);
	}

	/**
	 * Subtract an origin from this position to produce a new position. For example,
	 * (3,4)-(1,1) gives (2,3).
	 *
	 * @param origin
	 * @return
	 */
	public Position subtract(Position origin) {
		return new Position(x - origin.x, y - origin.y);
	}

	/**
	 * Determine the distance of this point from another (which we'll call the
	 * origin). For example, distance between (1,0) and (0,0) is 1, etc.
	 *
	 * @param origin
	 * @return
	 */
	public double distance(Position origin) {
		int dx = x - origin.x;
		int dy = y - origin.y;
		// Calculate distance from me to robot r using pythagorus
		// theorem.
		return Math.sqrt(dx * dx + dy * dy);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
