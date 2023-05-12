// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.actions;

import robotwar.core.Action;
import robotwar.core.Battle;
import robotwar.core.Robot;
import robotwar.util.Position;

/**
 * Represents the act of one robot shooting another.
 *
 * @author David J. Pearce
 *
 */
public class Shoot implements Action {
	private final Robot.State shooter;
	private final Position target;

	public Shoot(Robot.State shooter, Position target) {
		this.shooter = shooter;
		this.target = target;
	}

	/**
	 * Get robot making the shot.
	 *
	 * @return
	 */
	public Robot.State getShooter() {
		return shooter;
	}

	/**
	 * Get target of shot.
	 *
	 * @return
	 */
	public Position getTarget() {
		return target;
	}

	public boolean isWithinRange() {
		Position origin = shooter.getPosition();
		double distance = target.distance(origin);
		return distance < shooter.getRange();
	}

	@Override
	public void apply(Battle b) {
		Robot.State shootee = b.getRobotState(target.getX(), target.getY());
		int shooterStrength = shooter.getStrength();
		if (shootee != null && isWithinRange()) {
			// Yes, there is a robot at the given target.
			int shooteeStrength = Math.max(0, shootee.getStrength() - 1);
			shootee.setStrength(shooteeStrength);
			shooter.setStrength(shooterStrength - 1);
		}
	}
}
