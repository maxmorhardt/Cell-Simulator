package wildfire;

import javafx.scene.paint.Color;
import modelextras.Cell;

/**
 * 
 * @author Adam Behrman
 * 
 * Wild fire simulation view.
 *
 */
public class TreeCell extends Cell {

	protected int burnTime;
	protected boolean hasTree;

	/**
	 * Constructor for TreeCell (one with a tree)
	 * 
	 * @param color
	 * @param burnTime
	 */
	public TreeCell(Color color, boolean isActive, boolean hasTree, int burnTime) {
		super(color, isActive);
		this.hasTree = hasTree;
		this.burnTime = burnTime;
	}

	public boolean hasTree() {
		return hasTree;
	}

	public void changeState(Color color) {
		if (hasTree || burnTime == 0) {
			this.color = color;
			if (burnTime == 0) {
				burnTime -= 1;
			}
		}
	}

	/**
	 * 1. not burning - not active && tree 2. burning - active && tree 3. dead - not
	 * active && no tree.
	 */

	public boolean isActive() {
		return isActive && hasTree;
	}

	/**
	 * Public method to update this Cell's tree
	 */
	public void update() {
		if (hasTree && isActive) {
			burnTime -= 1;
			checkTreeAlive();
		}
	}

	/**
	 * Determines the status of the tree Private method so that nothing outside the
	 * Cell knows its own status.
	 */
	private void checkTreeAlive() {
		if (burnTime == 0) {
			hasTree = false;
			deactivate();
		}
	}

	public String toString() {
		if (hasTree && isActive) {
			return "Burn time left: " + burnTime + ".";
		} else if (hasTree && !isActive) {
			return "Healthy, not burning tree.";
		} else
			return "There is no tree left here.";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + burnTime;
		result = prime * result + (hasTree ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeCell other = (TreeCell) obj;
		if (burnTime != other.burnTime)
			return false;
		if (hasTree != other.hasTree)
			return false;
		return true;
	}

}