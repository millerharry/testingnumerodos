// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.core;

/**
 * Represents an action that a robot can take in the game
 *
 * @author David J. Pearce
 *
 */
public interface Action {

	/**
	 * Apply this action.
	 */
	public void apply(Battle b);
}
