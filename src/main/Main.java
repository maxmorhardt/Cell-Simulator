package main;

import gameoflife.GOLView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import mvc.View;
import rockpaperscissors.RPSView;
import wildfire.WildFireView;

/**
 * 
 * @author Max Morhardt
 * 
 * Main class that runs the game.
 *
 */
public class Main extends Application {
	
	// Spacing between buttons
	private static final int SPACING = 20;
	
	// Scene for the simulations
	private View scene;
	
	@Override
	public void start(Stage stage) throws Exception {
		// Starts as wild fire simulation
		scene = new WildFireView();
		// Starts simulation
		scene.startSimulation(stage);
		// Adds buttons to change simulation
		scene.addSimButtons(setupSimulationButtons(stage));
	}

	/**
	 * Starts program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Sets up buttons to change simulation.
	 * 
	 * @param stage
	 * @return
	 */
	private HBox setupSimulationButtons(Stage stage) {
		HBox simTypes = new HBox();
		simTypes.setAlignment(Pos.BASELINE_CENTER);
		simTypes.setSpacing(SPACING);

		// Changes to wild fire simulation
		Button wildFire = new Button("Wild Fire");
		wildFire.setOnAction(value -> {
			scene = new WildFireView();
			scene.startSimulation(stage);
			scene.addSimButtons(setupSimulationButtons(stage));
		});
		simTypes.getChildren().add(wildFire);

		// Changes to rock paper scissors simulation
		Button rps = new Button("RPS");
		rps.setOnAction(value -> {
			scene = new RPSView();
			scene.startSimulation(stage);
			scene.addSimButtons(setupSimulationButtons(stage));
		});
		simTypes.getChildren().add(rps);

		// Changes to game of life simulation
		Button gol = new Button("GOL");
		gol.setOnAction(value -> {
			scene = new GOLView();
			scene.startSimulation(stage);
			scene.addSimButtons(setupSimulationButtons(stage));
		});
		simTypes.getChildren().add(gol);

		return simTypes;
	}
	
}