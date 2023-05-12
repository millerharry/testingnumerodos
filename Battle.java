// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.core;

import java.util.*;

import robotwar.util.Position;

/**
 * Represents a robot battle and implemnents the logic for taking turns in the
 * battle.
 *
 * @author David J. Pearce
 *
 */
public class Battle {
	/**
	 * The list of robots in the arena, including those which are deceased.
	 */
	private List<Robot.State> states = new ArrayList<>();

	/**
	 * The list of actions applied the current turn.
	 */
	private List<Action> actions = new ArrayList<>();

	/**
	 * Width of the arena (in squares)
	 */
	private int arenaWidth;

	/**
	 * Height of the arena (in squares)
	 */
	private int arenaHeight;

	/**
	 * Construct a battle with given dimensions and number of robots.
	 *
	 * @param width
	 *            Width of battle arena
	 * @param height
	 *            Height of battle arena
	 * @param numRobots
	 *            Number of robots to fight
	 */
	public Battle(int width, int height) {
		arenaWidth = width;
		arenaHeight = height;
	}

	/**
	 * Get width of the arena (in squares)
	 *
	 * @return
	 */
	public int getWidth() {
		return arenaWidth;
	}

	/**
	 * Get height of arena (in squares)
	 *
	 * @return
	 */
	public int getHeight() {
		return arenaHeight;
	}

	/**
	 * Get the robot at a given position (if one exists); or, return null (if not).
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public Robot.State getRobotState(int x, int y) {
		for (Robot.State v : states) {
			Position p = v.getPosition();
			if (p.getX() == x && p.getY() == y) {
				return v;
			}
		}
		return null;
	}

	public List<Robot.State> getStates() {
		return states;
	}

	/**
	 * Get the list of all actions.
	 *
	 * @return
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 * Reset the battle. This removes all robots and clears all outstanding actions.
	 */
	public void reset() {
		this.actions.clear();
		this.states.clear();
	}

	/**
	 * The purpose of this method is to allow each of the robots to make a move.
	 */
	public void takeTurn() {
		actions.clear();
		for (Robot.State s : states) {
			s.takeTurn();
		}
		for (Action action : actions) {
			action.apply(this);
		}
	}

	@Override
	public String toString() {
		String r = "";
		for (int i = arenaHeight - 1; i >= 0; --i) {
			r += i;
			for (int j = 0; j != arenaWidth; ++j) {
				r += "|";
				Robot.State robot = getRobotState(j, i);
				if (robot == null) {
					r += "_";
				} else {
					if (robot.getStrength() == 0) {
						r += "X";
					} else {
						r += robot.getController().toString();
					}
				}
			}
			r += "|\n";
		}
		r += " ";
		// Do the X-Axis
		for (int j = 0; j != arenaWidth; ++j) {
			r += " " + j;
		}
		return r;
	}
}
