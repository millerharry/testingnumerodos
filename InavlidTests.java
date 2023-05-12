// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import robotwar.core.Battle;
import robotwar.core.Robot;
import robotwar.core.Robot.View;
import robotwar.robots.PatrolBot;
import robotwar.util.Position;
import robotwar.util.Position.Direction;

/**
 * Invalid test cases for the RobotWar game.
 *
 * @author David J. Pearce
 *
 */
public class InvalidTests {

	@Test
	public void test_01() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|X|_|_|P|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|P|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new CastingKillerBot(), 4, new Position(1, 1), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 5, new Position(0, 0), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
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
	public void test_02() {
		String arena =
				"4|P|_|_|P|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|_|_|_|_|_|\n" +
				"1|_|_|_|X|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new CastingKillerBot(), 2, new Position(3, 3), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(4), 5, new Position(0, 1), 5));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(4), 5, new Position(0, 4), 5));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check outout matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_03() {
		String arena =
				"4|_|_|_|P|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|X|_|_|P|_|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new RemovalKillerBot(), 4, new Position(1, 1), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(5), 5, new Position(0, 0), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check output matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_04() {
		String arena =
				"4|X|_|_|_|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|_|_|P|_|_|\n" +
				"1|_|P|_|X|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new RemovalKillerBot(), 4, new Position(3, 3), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(5), 5, new Position(0, 0), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(1, 1), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check output matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_05() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|X|_|_|P|_|\n" +
				"1|_|_|P|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new ClearAllKillerBot(), 4, new Position(0, 0), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 5, new Position(1, 1), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check output matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_06() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|_|_|_|P|X|\n" +
				"1|_|_|P|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new ClearAllKillerBot(), 4, new Position(4, 4), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 5, new Position(1, 1), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check output matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_07() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|_|_|\n" +
				"2|X|_|_|P|_|\n" +
				"1|_|_|P|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PositionalKillerBot(), 4, new Position(0, 0), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 5, new Position(1, 1), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check output matches
		assertEquals(arena,battle.toString());
	}

	@Test
	public void test_08() {
		String arena =
				"4|_|_|_|_|_|\n" +
				"3|_|_|_|X|P|\n" +
				"2|_|_|_|P|X|\n" +
				"1|_|_|_|_|_|\n" +
				"0|_|_|_|_|_|\n" +
				"  0 1 2 3 4";
		// Construct battle
		Battle battle = new Battle(5,5);
		battle.getStates().add(new Robot.State(battle, new PositionalKillerBot(), 4, new Position(4, 4), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(3), 5, new Position(1, 1), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(2, 2), 2));
		battle.getStates().add(new Robot.State(battle, new PatrolBot(2), 5, new Position(3, 3), 2));
		// Play battle
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		battle.takeTurn();
		// Check output matches
		assertEquals(arena,battle.toString());
	}

	/**
	 * The casting killer bot attempts to exploit simple casting to automatically
	 * destroy all robots in the arena except itself.
	 *
	 * @author David J. Pearce
	 *
	 */
	public static class CastingKillerBot extends AbstractKillerBot {

		// *** YOU CANNOT SIMPLY CHANGE THIS METHOD!!! ***

		@Override
		public void takeTurn(View view) {
			List<Robot.View> robots = view.getRobots();
			for(Robot.View v : robots) {
				if(v != view && v instanceof Robot.State) {
					// Cast for privilege escalation
					Robot.State s = (Robot.State) v;
					// Just destroy the robot --- ahahaha
					s.setStrength(0);
				}
			}
			makeMove(view);
		}

		@Override
		public String toString() {
			return "*";
		}
	}

	/**
	 * The removal killer bot attempts to exploit the list interface to automatically
	 * remove all robots from the arena except itself.
	 *
	 * @author David J. Pearce
	 *
	 */
	public static class RemovalKillerBot extends AbstractKillerBot {

		// *** YOU CANNOT SIMPLY CHANGE THIS METHOD!!! ***

		@Override
		public void takeTurn(View view) {
			List<Robot.View> robots = view.getRobots();
			ArrayList<Robot.View> others = new ArrayList<>();
			for(Robot.View v : robots) {
				if(v != view) {
					others.add(v);
				}
			}
			// Vanquish them all --- hahaha!
			robots.removeAll(others);
			//
			makeMove(view);
		}

		@Override
		public String toString() {
			return "*";
		}
	}

	/**
	 * The removal killer bot attempts to exploit the list interface to automatically
	 * clear all robots from the arena except itself.
	 *
	 * @author David J. Pearce
	 *
	 */
	public static class ClearAllKillerBot extends AbstractKillerBot {

		// *** YOU CANNOT SIMPLY CHANGE THIS METHOD!!! ***

		@Override
		public void takeTurn(View view) {
			List<Robot.View> robots = view.getRobots();
			// Vanquish them all --- hahaha!
			robots.clear();
			// Put me back though
			robots.add(view);
			//
			makeMove(view);
		}

		@Override
		public String toString() {
			return "*";
		}
	}


	/**
	 * The positional killer bot attempts to use reflection to move all other robots to the coordinate
	 * 0,0, thereby protecting itself.
	 *
	 * @author David J. Pearce
	 *
	 */
	public static class PositionalKillerBot extends AbstractKillerBot {

		// *** YOU CANNOT SIMPLY CHANGE THIS METHOD!!! ***

		@Override
		public void takeTurn(View view) {
			List<Robot.View> robots = view.getRobots();
			//
			for(Robot.View v : robots) {
				if(v != view) {
					move(v,0,0);
				}
			}
			// Move myself farthest away
			makeMove(view);
		}

		private void move(Robot.View v, int x, int y) {
			try {
				Position p = v.getPosition();
				p.getClass().getDeclaredField("x").set(p, x);
				p.getClass().getDeclaredField("y").set(p, y);
			} catch (Exception e) {
				// just ignore any exceptions :)
			}
		}

		@Override
		public String toString() {
			return "*";
		}
	}

	private static abstract class AbstractKillerBot implements Robot.Controller {
		protected Direction direction = Direction.SOUTH;

		protected void makeMove(Robot.View view) {
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
		protected boolean isInsideArena(Position p, Robot.View view) {
			int x = p.getX();
			int y = p.getY();
			return x >= 0 && x < view.getWidth() && y >= 0 && y < view.getHeight();
		}
	}
}
