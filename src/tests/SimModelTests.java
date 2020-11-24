package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import gameoflife.GOLModel;
import javafx.scene.paint.Color;
import mvc.Model;
import rockpaperscissors.RPSCell;
import rockpaperscissors.RPSModel;
import wildfire.WildFireModel;

/**
 * 
 * @author Adam Behrman
 * 
 * Tests for Simulation Models
 *
 */
class SimModelTests {

	/**
	 * Tests the creation of a new Forest model using some basic parameters.
	 */
	@Test
	void test_FireSimModelCreation() {
		System.out.println("Model creation test");

		Model forest = new WildFireModel();
		forest.resize(10, 10);
		forest.makeModel();

		boolean statement1 = forest.toString()
				.equals("There are 100 trees left in the forest.\nOf those, 1 are burning.\n");
		assertTrue(statement1);
	}

	/**
	 * Tests the creation of a new Forest model and using the model to cycle all
	 * burning trees using 1 burning tree to start.
	 */
	@Test
	void test_FireSimModelCycling() {
		System.out.println("Model cycling test");

		Model forest = new WildFireModel();
		forest.resize(10, 10);
		forest.makeModel();

		int cycles = 1;
		for (int i = 0; i < cycles; i++) {
			forest.updateModel();
		}

		boolean statement1 = forest.toString()
				.equals("There are 99 trees left in the forest.\nOf those, 4 are burning.\n");
		boolean statement2 = forest.toString()
				.equals("There are 99 trees left in the forest.\nOf those, 3 are burning.\n");
		boolean statement3 = forest.toString()
				.equals("There are 99 trees left in the forest.\nOf those, 2 are burning.\n");

		assertTrue(statement1 || statement2 || statement3);
	}

	@Test
	void test_FireSimModelParameters() {
		System.out.println("Parameter test");

		WildFireModel forest = new WildFireModel();
		forest.resize(10, 10);
		forest.makeModel();

		System.out.println(forest.toString());

		double density_new = .1;
		int burningTrees_new = 2;
		int burnTime_new = 2;
		double spreadProb_new = .1;

		forest.setParameters(new Object[] { density_new, burningTrees_new, burnTime_new, spreadProb_new });
		forest.makeModel();

		System.out.println(forest.toString());
	}

	/**
	 * Allows the user to run their own simulations of a forest model.
	 * 
	 */
	void fireSimModelSimulation() {
		System.out.println("Forest simulation");

		WildFireModel forest = new WildFireModel();

		forest.resize(100, 100);
		forest.setBurningTrees(100);
		forest.setBurnTime(3);
		forest.setDensity(.8);
		forest.setSpreadProb(.3);
		forest.makeModel();

		System.out.println(forest.toString());

		int cycles = 100;
		for (int i = 0; i < cycles; i++) {
			forest.updateModel();
			System.out.println(forest.toString());
		}
	}

	@Test
	void test_GOLSimModelParameters() {
		System.out.println("Parameter test");

		Model test = new GOLModel();
		test.resize(10, 10);
		test.makeModel();

		System.out.println(test.toString());

		double infection_new = 1;

		test.setParameters(new Object[] { infection_new });
		test.makeModel();

		System.out.println(test.toString());
	}
	
}