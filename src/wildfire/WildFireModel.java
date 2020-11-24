package wildfire;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import modelextras.ParameterList;
import modelextras.Cell;
import mvc.Model;

/**
 * 
 * @author Max Morhardt and Adam Behrman
 * 
 * Wild fire simulation model.
 *
 */
public class WildFireModel extends Model {

	private final Color BURN_COLOR = Color.RED;
	private final Color TREE_COLOR = Color.GREEN;
	private final Color EMPTY_COLOR = Color.BROWN;
	private final Color BURNT_DOWN_COLOR = Color.YELLOW;

	private double density;
	private int burningTrees;
	private int burnTime;
	private double spreadProb;

	public WildFireModel() {
		params = new ParameterList(4);

		density = 1;
		burningTrees = 1;
		burnTime = 1;
		spreadProb = 1;

		params.addParam(density);
		params.addParam(burningTrees);
		params.addParam(burnTime);
		params.addParam(spreadProb);
	}

	/**
	 * Resizes the TreeCell[][] to the given params
	 * 
	 * @param length
	 * @param height
	 */
	@Override
	public void resize(int length, int height) {
		cells = new TreeCell[length][height];
	}

	/**
	 * Generates a TreeCell[][] using the params and starts burning TreeCells
	 */
	@Override
	public void makeModel() {
		generateTreeCells();
	}

	/**
	 * Creates the Cell[][] with burning/empty/regular Cells
	 */
	private void generateTreeCells() {
		List<Point> burningTrees = generateBurningTrees();

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				boolean currentTree = false;

				for (Point oldPoint : burningTrees) {
					if (oldPoint.x == i && oldPoint.y == j) {
						currentTree = true;
						break;
					}
				}

				if (currentTree) {
					cells[i][j] = new TreeCell(BURN_COLOR, true, true, burnTime);
				} else if (density > random.nextDouble()) {
					cells[i][j] = new TreeCell(TREE_COLOR, false, true, burnTime);
				} else {
					cells[i][j] = new TreeCell(EMPTY_COLOR, false, false, burnTime);
				}
			}
		}
	}

	/**
	 * Generates a List of Points where the burning trees will be put
	 * 
	 * @return the List of points for burning trees
	 */
	private List<Point> generateBurningTrees() {
		List<Point> burningTrees = new ArrayList<Point>();
		boolean cellGood = true;
		int treesBurning = 0;

		do {
			int i = random.nextInt(cells.length);
			int j = random.nextInt(cells[0].length);

			Point newPoint = new Point(i, j);

			for (Point oldPoint : burningTrees) {
				if (oldPoint.x != i || oldPoint.y != j) {
					cellGood = true;
					break;
				}
			}

			if (cellGood) {
				cellGood = false;
				burningTrees.add(newPoint);
				treesBurning++;
			}

		} while (treesBurning < this.burningTrees);
		return burningTrees;
	}

	@Override
	public void updateModel() {
		List<Point> burningTreeCells = new ArrayList<Point>();

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				Cell cellOfInterest = cells[i][j];
				if (cellOfInterest.isActive()) {
					burningTreeCells.add(new Point(i, j));
				}
			}
		}

		for (Point burningCellPoint : burningTreeCells) {
			Cell burningCell = cells[burningCellPoint.x][burningCellPoint.y];

			int[] xPos = new int[] { -1, 1, 0, 0 };
			int[] yPos = new int[] { 0, 0, 1, -1 };

			Cell[] neighbors = getCellNeighborhood(burningCellPoint, xPos, yPos);

			setNeighborsToBurn(neighbors);
			burningCell.update();

			if (!burningCell.isActive()) {
				burningCell.changeState(BURNT_DOWN_COLOR);
			}
		}
	}

	/**
	 * Attempts to burn the TreeCells in a given neighborhood
	 * 
	 * @param neighborhood
	 */
	private void setNeighborsToBurn(Cell[] neighborhood) {
		for (Cell currentCellNeighbor : neighborhood) {
			if (spreadProb > random.nextDouble() && !currentCellNeighbor.isActive()) {
				currentCellNeighbor.changeState(BURN_COLOR);
				currentCellNeighbor.activate();
			}
		}
	}

	public String toString() {
		int treeCount = 0;
		int totalBurningTrees = 0;

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				TreeCell currentCell = (TreeCell) cells[i][j];

				if (currentCell.hasTree()) {
					treeCount++;
				}
				if (currentCell.isActive()) {
					totalBurningTrees++;
				}
			}
		}
		System.out.println(density + " " + burningTrees + " " + burnTime + " " + spreadProb);
		return "There are " + treeCount + " trees left in the forest.\nOf those, " + totalBurningTrees
				+ " are burning.\n";
	}

	public void updateParameters() {
		density = (double) params.getParamAt(0);
		burningTrees = (int) params.getParamAt(1);
		burnTime = (int) params.getParamAt(2);
		spreadProb = (double) params.getParamAt(3);
	}

	/**
	 * Allows the View/Controller to pass different density value into the model.
	 * 
	 * @param density
	 */
	public void setDensity(double density) {
		this.density = density;
	}

	/**
	 * Allows the View/Controller to pass different burningTrees value into the
	 * model.
	 * 
	 * @param burningTrees
	 */
	public void setBurningTrees(int burningTrees) {
		this.burningTrees = burningTrees;
	}

	/**
	 * Allows the View/Controller to pass different burnTime value into the model.
	 * 
	 * @param burnTime
	 */
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	/**
	 * Allows the View/Controller to pass different spreadProb value into the model.
	 * 
	 * @param spreadProb
	 */
	public void setSpreadProb(double spreadProb) {
		this.spreadProb = spreadProb;
	}
	
}