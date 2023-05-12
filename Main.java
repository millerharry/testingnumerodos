// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar;

import java.util.Random;

import robotwar.core.Battle;
import robotwar.ui.BattleFrame;

public class Main {
	private static Random random = new Random();

	/**
	 * Generate a random integer between 0 and n
	 */
	public static int randomInteger(int n) {
		return random.nextInt(n);
	}

	public static void main(String[] args) {
		Battle battle = new Battle(16,16);
		new BattleFrame(battle);
	}
}
