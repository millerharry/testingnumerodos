// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.core;

import java.util.*;

import robotwar.actions.Move;
import robotwar.actions.Shoot;
import robotwar.core.Battle;
import robotwar.util.Position;

/**
 * An empty interface capturing the main aspects of a Robot;
 *
 * @author David J. Pearce
 *
 */
public interface Robot {

	/**
	 * The robot controller is the brains behind any given robot. It controls what
	 * the robot will attempt to do.
	 *
	 * @author David J. Pearce
	 *
	 */
	public interface Controller {
		/**
		 * This method is called to allow a robot to do something. The robot's view is
		 * provided as input in order that it can make decisions based on this. The
		 * intention is that this view is not modifiable directly though.
		 */
		public void takeTurn(Robot.View view);
	}

	/**
	 * Provides a robot's current view of the world. This specifically limits what
	 * the robot can actually do.
	 *
	 * @author David J. Pearce
	 *
	 */
	public interface View {

		/**
		 * Get the width of the arena.
		 * @return
		 */
		public int getWidth();

		/**
		 * Get the height of the arena.
		 * @return
		 */
		public int getHeight();

		/**
		 * Get the current position of the robot.
		 *
		 * @return
		 */
		public Position getPosition();

		/**
		 * Get the shooting range of this robot.
		 * @return
		 */
		public int getRange();

		/**
		 * Check whether robot is now destroyed.
		 *
		 * @return
		 */
		public boolean isDestroyed();

		/**
		 * Get the locations of visible robots in the arena. This allows the robots
		 * behaviour to alter depending on the proximity of other robots.
		 *
		 * @return
		 */
		public List<Robot.View> getRobots();

		/**
		 * Fire at a given position on the board.
		 *
		 * @param target
		 *            --- Position to fire at.
		 */
		public void fire(Position target);

		/**
		 * Move in a given direction on the board.
		 * @param direction
		 */
		public void move(Position.Direction direction);
	}

	/**
	 * The robot state holds critical information about a given robot. This is shown
	 * to the robot when it wants to make a move. The intention is this limits what
	 * they can do. For example, it limits what other robots they can see.
	 *
	 * @author David J. Pearce
	 *
	 */
	public static class State implements View {
		/**
		 * The underlying battle arena.
		 */
		private final Battle battle;

		/**
		 * The robot in question.
		 */
		private final Robot.Controller robot;

		/**
		 * Maximum distance which this robot can shoot.
		 */
		private final int range;

		/**
		 * Maximum strength of robot.
		 */
		private final int maxStrength;

		/**
		 * Position of the robot.
		 */
		private Position position;

		/**
		 * Current strength of robot.
		 */
		private int strength;

		/**
		 * Create a new Robot state instance.
		 *
		 * @param battle
		 * @param controller
		 * @param range
		 * @param position
		 * @param strength
		 */
		public State(Battle battle, Robot.Controller controller, int range, Position position, int strength) {
			this.battle = battle;
			this.robot = controller;
			this.range = range;
			this.position = position;
			this.maxStrength = this.strength = strength;
		}

		/**
		 * Get the width of the arena.
		 * @return
		 */
		@Override
		public int getWidth() {
			return battle.getWidth();
		}

		/**
		 * Get the height of the arena.
		 * @return
		 */
		@Override
		public int getHeight()  {
			return battle.getHeight();
		}

		/**
		 * Check whether this robot has run out of power.
		 *
		 * @return
		 */
		@Override
		public boolean isDestroyed() {
			return strength == 0;
		}

		/**
		 * Get the robot controller associated with this state.
		 * @return
		 */
		public Robot.Controller getController() {
			return robot;
		}

		/**
		 * Get the current position of the robot.
		 *
		 * @return
		 */
		@Override
		public Position getPosition() {
			return position;
		}

		/**
		 * Get the current strength of the robot.
		 * @return
		 */
		public int getStrength() {
			return strength;
		}

		/**
		 * Get the current range of this robot.
		 * @return
		 */
		@Override
		public int getRange() {
			return range;
		}

		/**
		 * Allow the robot behind this state to take a turn and register some actions.
		 */
		public void takeTurn() {
			if(strength > 0) {
				// If we have some strength then regenerate by 1 every round.
				strength = Math.min(strength + 1,maxStrength);
				// Only take a turn if has some strength.
				robot.takeTurn(this);
			}
		}

		/**
		 * Get the list of robots in the arena. This allows the robots behaviour to
		 * alter depending on other robots. However, the intention is that we should not
		 * be able to modify the underlying list of robots via this method.
		 *
		 * @return
		 */
		@Override
		public List<Robot.View> getRobots() {
			// NOTE: this line is a bit dodgy :)
			return (List) battle.getStates();
		}

		/**
		 * Set the current position of this robot
		 * @param p
		 */
		public void setPosition(Position p) {
			this.position = p;
		}

		/**
		 * Set the current strength of this robot
		 * @param p
		 */
		public void setStrength(int strength) {
			this.strength = strength;
		}

		@Override
		public void fire(Position target) {
			if(strength > 0) {
				// Can only fire if enough strength
				battle.getActions().add(new Shoot(this,target));
			}
		}


		@Override
		public void move(Position.Direction direction) {
			battle.getActions().add(new Move(this,direction));
		}
	}
}
