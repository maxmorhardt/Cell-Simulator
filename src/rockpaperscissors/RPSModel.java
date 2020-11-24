package rockpaperscissors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Color;
import modelextras.Cell;
import modelextras.ParameterList;
import mvc.Model;

/**
 * 
 * @author Michael Lesmez, Adam Behrman, and Kara Palumbo
 * 
 * Model for rock paper scissors simulation.
 *
 */
public class RPSModel extends Model {

	// Colors that represent states of the cells
	private final Color ROCK_COLOR = Color.RED;
	private final Color PAPER_COLOR = Color.WHITE;
	private final Color SCISSORS_COLOR = Color.BLUE;

	private int threshold;

	public RPSModel() {
		params = new ParameterList(1);
		threshold = 3;
		params.addParam(threshold);
	}

	@Override
	public void resize(int length, int height) {
		cells = new RPSCell[height][length];
	}

	@Override
	public void makeModel() {
		// rows and columns will already be checked
		// for invalid input
		int midwayDown = cells[0].length / 2;
		int midwaySideways = cells.length / 2;

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (i < midwaySideways) {
					cells[i][j] = new RPSCell(ROCK_COLOR, true);
				} else {
					cells[i][j] = new RPSCell(SCISSORS_COLOR, true);
				}
			}
		}

		final double blocksToAdd = (double) midwaySideways / (double) midwayDown;
		int cap = 1;
		double blocksAdded = 0;
		int paperPad = 0;
		for (int j = midwayDown; j < cells[0].length; j++) {
			blocksAdded += blocksToAdd;
			if (validateCell(paperPad + midwaySideways, j)) {
				for (int i = midwaySideways - paperPad; i < midwaySideways + paperPad; i++) {
					cells[i][j] = new RPSCell(PAPER_COLOR, true);
				}
			}

			while (blocksAdded >= cap) {
				paperPad++;
				blocksAdded -= 1;
			}

		}
	}

	@Override
	public void updateModel() {
		Color[][] tempCells = new Color[cells.length][cells[0].length];
		fillTempCells(tempCells);
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				Point currentPoint = new Point(i, j);

				int[] xPos = new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
				int[] yPos = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };

				Color currentState = cells[i][j].getColor();
				Cell[] neighbors = getCellNeighborhood(currentPoint, xPos, yPos);

				int[] states = countNeighborStates(neighbors);
				ArrayList<Color> stateOptions = chooseStatesAboveThreshold(states);
				//if there are no colors in stateOptions, the cell will remain in its
				//current state.
				if (stateOptions.size() >= 1) {
					tempCells[i][j] = chooseColor(currentState, stateOptions);
				}
			}
		}
		replaceCellWithTemp(tempCells);
	}

	public void updateParameters() {
		threshold = (int) params.getParamAt(0);
	}

	// counts the number of rock, paper, and scissors neighbors
	// for a specific cell
	private int[] countNeighborStates(Cell[] neighbors) {
		int[] states = new int[3];
		// State 0 is rock
		// State 1 is paper
		// State 2 is scissors
		HashMap<Color, Integer> stateCount = new HashMap<Color, Integer>();
		stateCount.put(ROCK_COLOR, 0);
		stateCount.put(PAPER_COLOR, 1);
		stateCount.put(SCISSORS_COLOR, 2);
		for (Cell cell : neighbors) {
			states[stateCount.get(cell.getColor())]++;
		}
		return states;
	}

	/**
	 * Makes an arraylist of all states above the threshold;
	 */
	private ArrayList<Color> chooseStatesAboveThreshold(int[] states) {
		ArrayList<Color> statesAboveThreshold = new ArrayList<Color>();
		HashMap<Integer, Color> colorOptions = new HashMap<Integer, Color>();
		colorOptions.put(0, ROCK_COLOR);
		colorOptions.put(1, PAPER_COLOR);
		colorOptions.put(2, SCISSORS_COLOR);
		for (int i = 0; i < states.length; i++) {
			if (states[i] >= threshold) {
				statesAboveThreshold.add(colorOptions.get(i));
			}
		}
		return statesAboveThreshold;
	}

	// using the current state and the ArrayList of the states above the threshold
	// will determine which state the cell should become
	// assumes arrayList will not be empty
	private Color chooseColor(Color currentColor, ArrayList<Color> statesAboveThreshold) {
		Color newState = currentColor;
		newState = rockPaperScissorsRules(currentColor, statesAboveThreshold.get(0));
		//if the first newState equals the current state and there is another state in
		//statesAboveThreshold, it means that the next state above the threshold could
		//change the cells state. If new state has already changed, the next state should
		//not be able to change the cell's state since only one state can beat another state
		if (statesAboveThreshold.size() == 2 && newState.equals(currentColor)) {
			newState = rockPaperScissorsRules(currentColor, statesAboveThreshold.get(1));
		}
		return newState;
	}

	//Uses a hashmap to show the rules of RPS. 
	//The value of a key beats the key in RPS.
	//stateA will always be the key in the hashMap and stateB will be the key
	//Coming from choose color, stateA will always be the current grid's state
	//while stateB will be a state that is above the threshold
	private Color rockPaperScissorsRules(Color stateA, Color stateB) {
		HashMap<Color, Color> RPSRules = new HashMap<Color, Color>();
		RPSRules.put(ROCK_COLOR, PAPER_COLOR);
		RPSRules.put(PAPER_COLOR, SCISSORS_COLOR);
		RPSRules.put(SCISSORS_COLOR, ROCK_COLOR);

		if (RPSRules.get(stateA).equals(stateB)) {
			return stateB;
		}

		return stateA;
	}

	private void replaceCellWithTemp(Color[][] tempCells) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j].changeState(tempCells[i][j]);
				cells[i][j].update();
			}
		}
	}

	private void fillTempCells(Color[][] tempCells) {
		for (int i = 0; i < tempCells.length; i++) {
			for (int j = 0; j < tempCells[0].length; j++) {
				tempCells[i][j] = cells[i][j].getColor();
			}
		}
	}

	public String toString() {
		int[] states = new int[3];

		HashMap<Color, Integer> stateCount = new HashMap<Color, Integer>();
		stateCount.put(ROCK_COLOR, 0);
		stateCount.put(PAPER_COLOR, 1);
		stateCount.put(SCISSORS_COLOR, 2);

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				Cell currentCell = cells[i][j];

				states[stateCount.get(currentCell.getColor())]++;
			}
		}
		return "There are " + states[0] + " rocks, " + states[1] + " scissors, and " + states[2]
				+ " papers left in the simulation.";
	}
	
}