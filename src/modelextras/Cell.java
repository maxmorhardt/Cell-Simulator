package modelextras;

import javafx.scene.paint.Color;

/**
 * 
 * @author Adam Behrman
 * 
 * Abstraction of a cell for all simulations.
 *
 */
public abstract class Cell {

	protected Color color;
	protected boolean isActive;

	public Cell(Color color, boolean isActive) {
		this.color = color;
		this.isActive = isActive;
	}

	/**
	 * Abstract method implemented by specific Cells.
	 */
	public abstract void update();

	public void changeState(Color color) {
		this.color = color;
	}

	public boolean isActive() {
		return isActive;
	}

	public void activate() {
		isActive = true;
	}

	public void deactivate() {
		isActive = false;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (isActive != other.isActive)
			return false;
		return true;
	}

}