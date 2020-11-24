package mvc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import modelextras.Cell;
import modelextras.ParameterList;

/**
 * 
 * @author Adam Behrman
 * 
 * Abstraction of a model for all simulations.
 *
 */
public abstract class Model {

	protected Cell[][] cells;
	protected final Random random;
	protected ParameterList params;

	public Model() {
		random = new Random();
		params = new ParameterList();
	}

	public void setParameters(Object[] paramsToSet) {
		for (int i = 0; i < paramsToSet.length; i++) {
			params.updateParamAt(paramsToSet[i], i);
		}
		updateParameters();
	}

	public void addParameter(Object toBeAdded) {
		params.addParam(toBeAdded);
	}

	/**
	 * Collects and returns the Cells in the neighborhood of a given Cell
	 * 
	 * @param cell the point to determine neighbors from
	 * @param xPos the relative x coordinates
	 * @param yPos the relative y coordinates
	 * @return the Cell[] neighborhood
	 */
	protected Cell[] getCellNeighborhood(Point cell, int[] xPos, int[] yPos) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (i == cell.x && j == cell.y) {
					List<Cell> neighbors = new ArrayList<Cell>();
					for (int l = 0; l < xPos.length; l++) {
						if (validateCell(i + xPos[l], j + yPos[l])) {
							neighbors.add(cells[i + xPos[l]][j + yPos[l]]);
						}
					}
					Cell[] cellNeighbors = new Cell[neighbors.size()];
					return neighbors.toArray(cellNeighbors);
				}
			}
		}
		return null;
	}

	/**
	 * Checks if the cell at a given row/col is in bounds
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	protected boolean validateCell(int row, int column) {
		return (row > -1 && row < cells.length) && (column > -1 && column < cells[0].length);
	}

	/**
	 * Gets the Colors from each Cell
	 * 
	 * @return Color[][] of all colors for Controller/View to use
	 */
	public Color[][] getColors() {
		Color[][] colors = new Color[cells.length][cells[0].length];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				colors[i][j] = cells[i][j].getColor();
			}
		}
		return colors;
	}

	/**
	 * Allows user to update parameters easily
	 */
	protected abstract void updateParameters();

	/**
	 * Resizes the Cell[][] to the given params
	 * 
	 * @param length
	 * @param height
	 */
	public abstract void resize(int length, int height);

	/**
	 * Generate a Cell[][] using the set params
	 */
	public abstract void makeModel();

	/**
	 * Updates the entire model by calling update on each Cell.
	 */
	public abstract void updateModel();

}