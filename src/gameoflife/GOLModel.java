package gameoflife;

import java.awt.Point;
import java.util.HashMap;
import javafx.scene.paint.Color;
import modelextras.Cell;
import modelextras.ParameterList;
import mvc.Model;

/**
 * 
 * @author Adam Behrman
 *
 * Game of life model.
 *
 */
public class GOLModel extends Model {

	private final Color EMPTY_COLOR = Color.GREEN;
	private final Color INFECTED_COLOR = Color.RED;

	private double bacteriaProb;

	public GOLModel() {
		params = new ParameterList(1);
		bacteriaProb = .4;
		params.addParam(bacteriaProb);
	}

	@Override
	public void updateParameters() {
		bacteriaProb = (double) params.getParamAt(0);
	}

	@Override
	public void resize(int length, int height) {
		cells = new GOLCell[length][height];
	}

	@Override
	public void makeModel() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (random.nextDouble() < bacteriaProb) {
					cells[i][j] = new GOLCell(INFECTED_COLOR, true);
				} else {
					cells[i][j] = new GOLCell(EMPTY_COLOR, false);
				}
			}
		}
	}

	@Override
	public void updateModel() {
		Cell[][] tempCells = new Cell[cells.length][cells[0].length];
		fillTempCells(tempCells);

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				Cell current = cells[i][j];
				int[] xPos = new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
				int[] yPos = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };

				Cell[] neighborhood = getCellNeighborhood(new Point(i, j), xPos, yPos);
				int infectedCells = getInfectedCells(neighborhood);
				tempCells[i][j] = determineRule(current, infectedCells);
			}
		}
		replaceCellWithTemp(tempCells);
	}

	private int getInfectedCells(Cell[] neighborhood) {
		int infectedCells = 0;
		for (Cell cell : neighborhood) {
			if (cell.isActive()) {
				infectedCells++;
			}
		}
		return infectedCells;
	}

	private Cell determineRule(Cell current, int length) {
		if (length == 2) {
			return current;
		} else if (length == 3) {
			return new GOLCell(INFECTED_COLOR, true);
		}
		return new GOLCell(EMPTY_COLOR, false);
	}

	private void replaceCellWithTemp(Cell[][] tempCells) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j] = new GOLCell(tempCells[i][j].getColor(), tempCells[i][j].isActive());
			}
		}
	}

	private void fillTempCells(Cell[][] tempCells) {
		for (int i = 0; i < tempCells.length; i++) {
			for (int j = 0; j < tempCells[0].length; j++) {
				tempCells[i][j] = new GOLCell(cells[i][j].getColor(), cells[i][j].isActive());
			}
		}
	}

	public String toString() {
		int[] states = new int[2];

		HashMap<Color, Integer> stateCount = new HashMap<Color, Integer>();
		stateCount.put(EMPTY_COLOR, 0);
		stateCount.put(INFECTED_COLOR, 1);

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				Cell currentCell = cells[i][j];

				states[stateCount.get(currentCell.getColor())]++;
			}
		}
		return "There are " + states[0] + " dead cells and " + states[1] + " infected left in the simulation.";
	}

}