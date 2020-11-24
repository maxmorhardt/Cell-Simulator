package rockpaperscissors;

import javafx.scene.paint.Color;
import modelextras.Cell;

/**
 * 
 * @author Adam Behrman
 * 
 * Rock paper scissors cell.
 *
 */
public class RPSCell extends Cell {

	public RPSCell(Color color, boolean isActive) {
		super(color, isActive);
	}

	public void update() {

	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

}