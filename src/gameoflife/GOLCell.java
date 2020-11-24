package gameoflife;

import javafx.scene.paint.Color;
import modelextras.Cell;

/**
 * 
 * @author Adam Behrman
 * 
 * Game of life cell.
 *
 */
public class GOLCell extends Cell {

	public GOLCell(Color color, boolean isActive) {
		super(color, isActive);
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

	@Override
	public void update() {}

}