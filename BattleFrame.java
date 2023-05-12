// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package robotwar.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;

import robotwar.Main;
import robotwar.core.Battle;
import robotwar.core.Robot;
import robotwar.robots.PatrolBot;
import robotwar.util.Position;

/**
 * Implements the outer window of the Robot War game. This includes any buttons,
 * the window frame itself and its title.
 *
 * @author David J. Pearce
 *
 */
public class BattleFrame extends JFrame implements ActionListener {
	private JPanel bottomPanel;
	private JPanel centerPanel;
	private JComboBox robotCombo;

	private BattleCanvas battleCanvas;
	private ClockThread clock;
	private Battle battle;

	/**
	 * Construct a BattleFrame to visually represent a given battle. This will
	 * internally construct a BattleCanvas onto which the battle itself will be
	 * drawn.
	 *
	 * @param battle
	 */
	public BattleFrame(Battle battle) {
		super("Robot War");

		this.battle = battle;
		this.battleCanvas = new BattleCanvas(battle);

		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new BorderLayout());
		Border cb = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(3, 3, 3, 3),
				BorderFactory.createLineBorder(Color.gray));
		this.centerPanel.setBorder(cb);
		this.centerPanel.add(battleCanvas, BorderLayout.CENTER);

		JButton startbk = new JButton("Start");
		JButton stopbk = new JButton("Stop");
		JButton clearbk = new JButton("Clear");
		JButton addbk = new JButton("Add Robot");
		robotCombo = new JComboBox(new String[] { "Guard x 2","Guard x 3","Guard x 4","Guard x 5"});
		robotCombo.setSelectedIndex(1);

		startbk.addActionListener(this);
		stopbk.addActionListener(this);
		clearbk.addActionListener(this);
		addbk.addActionListener(this);
		robotCombo.addActionListener(this);

		this.bottomPanel = new JPanel();
		this.bottomPanel.add(startbk);
		this.bottomPanel.add(stopbk);
		this.bottomPanel.add(clearbk);
		this.bottomPanel.add(addbk);
		this.bottomPanel.add(robotCombo);

		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		setFocusable(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

		// finally, start the clock ticking
		this.clock = new ClockThread(50, this);
		this.clock.start();
	}

	/**
	 * This function is called at a given period to drive the game. Effectively,
	 * this is the main loop of the game.
	 */
	public synchronized void clockTick() {
		if (battleCanvas.step == 0) {
			// First, check for shape collisions
			boolean aliveRobots = false;

			// Play the turn
			battle.takeTurn();

			// Check whether there are any alive robots remaining!
			aliveRobots = false;
			for (Robot.State r : battle.getStates()) {
				if (!r.isDestroyed()) {
					aliveRobots = true;
				}
			}

			if (!aliveRobots) {
				int r = JOptionPane.showConfirmDialog(this, new JLabel(
						"Game Over.  Restart?"), "Warning!",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (r == JOptionPane.YES_OPTION) {
					battle.reset();
					clock.setActive(false);
				} else {
					System.exit(0);
				}
			}
		}

		// Repaint the entire display
		battleCanvas.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start")) {
			clock.setActive(true);
		} else if (e.getActionCommand().equals("Stop")) {
			clock.setActive(false);
		} else if (e.getActionCommand().equals("Clear")) {
			battle.reset();
			battleCanvas.repaint();
		} else if (e.getActionCommand().equals("Add Robot")) {
			String robotKind = (String) robotCombo.getSelectedItem();
			int xPos = Main.randomInteger(battle.getWidth());
			int yPos = Main.randomInteger(battle.getHeight());
			Robot.Controller robot = null;
			switch(robotKind) {
			case "Guard x 2":
				robot = new PatrolBot(2);
				break;
			case "Guard x 3":
				robot = new PatrolBot(3);
				break;
			case "Guard x 4":
				robot = new PatrolBot(4);
				break;
			case "Guard x 5":
				robot = new PatrolBot(5);
				break;
			}
			battle.getStates().add(new Robot.State(battle, robot, 5, new Position(xPos, yPos), 5));
			battleCanvas.repaint();
		}
	}
}
