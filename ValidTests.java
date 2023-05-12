// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import robotwar.core.Battle;
import robotwar.core.Robot;
import robotwar.robots.PatrolBot;
import robotwar.util.Position;

/**
 * Valid test cases for RobotWar.
 *
 * @author David J. Pearce
 *
 */
public class ValidTests {

	@Test
	public void test_01() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|P|_|_|\n" +
				"2|_|_|_|_|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		// Play battle
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_02() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|P|_|\n" +
				"2|_|_|_|_|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_03() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|_|_|_|P|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_04() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|_|_|P|_|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_05() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|P|_|\n" +
				"2|_|_|_|_|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 5, new Position(1, 1), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_06() {
		// Check arena bounds
		String arena =
				"4|_|_|_|_|P|\n" +
				"3|_|_|_|_|_|\n" +
				"2|_|_|_|_|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(5), 5, new Position(2, 2), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}


	@Test
	public void test_07() {
		// Check arena bounds
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|_|_|_|_|_|\n" +
				"1|_|_|_|_|P|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 3, new Position(3, 0), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_08() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|_|_|_|_|_|\n" +
				"1|_|_|P|_|_|\n" +
				"0|_|P|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 4, new Position(1, 1), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 5, new Position(0, 0), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_09() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|X|_|_|_|\n" +
				"2|X|_|_|_|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 4, new Position(1, 1), 1));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 5, new Position(0, 0), 1));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}


	@Test
	public void test_10() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|P|_|_|\n" +
				"2|X|_|_|_|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 4, new Position(1, 1), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 5, new Position(0, 0), 1));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}
}
