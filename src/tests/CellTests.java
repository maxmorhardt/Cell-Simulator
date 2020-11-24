package tests;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;
import modelextras.Cell;
import rockpaperscissors.RPSCell;
import wildfire.TreeCell;

/**
 * 
 * @author Adam Behrman
 * 
 * Tests for Cells
 *
 */
class CellTests {

	/**
	 * Tests the creation of a new TreeCell using some edge Cell parameters.
	 * 
	 */
	@Test
	void test_CellCreation() {
		Cell cell = new TreeCell(Color.BLACK, false, true, 1);
		assertEquals(Color.BLACK, cell.getColor());
	}

	/**
	 * Tests the creation of a new TreeCell that CAN BURN using some Cell
	 * parameters.
	 * 
	 */
	@Test
	void test_TreeCellBurning() {
		TreeCell treecell = new TreeCell(Color.BLACK, false, true, 1);
		treecell.activate();
		treecell.update();
		assertEquals(false, treecell.hasTree());
	}

	/**
	 * Tests the creation and full lifecycle of a new TreeCell that CAN BURN using
	 * some Cell parameters.
	 * 
	 */
	@Test
	void test_TreeCellCompleteCycle() {
		TreeCell treecell = new TreeCell(Color.RED, true, true, 3);
		// System.out.println(treecell.toString());
		treecell.update();
		// System.out.println(treecell.toString());
		treecell.update();
		// System.out.println(treecell.toString());
		treecell.update();
		// System.out.println(treecell.toString());
		assertEquals(false, treecell.hasTree());
	}

	/**
	 * Tests the changeState method of an RPSCell and changes the state to the color
	 * given
	 */
	@Test
	void test_RPSCellChangeState() {
		RPSCell rpsCell = new RPSCell(Color.BLACK, false);
		rpsCell.changeState(Color.RED);
		assertEquals(Color.RED, rpsCell.getColor());
	}
	
}