package wildfire;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import mvc.Controller;
import mvc.View;

/**
 * 
 * @author Max Morhardt and Adam Behrman
 * 
 * Wild fire simulation view.
 *
 */
public class WildFireView extends View {

	// Simulation attributes
	private static final int MIN_GRID_SIZE = 3;
	private static final int MAX_GRID_SIZE = 100;
	private static final int MIN_BURN_TIME = 0;
	private static final int MAX_BURN_TIME = 50;
	private static final double MIN_SPREAD_PROB = 0;
	private static final double MAX_SPREAD_PROB = 1;
	private static final double MIN_DENSITY = 0.01;
	private static final double MAX_DENSITY = 1;

	// Initial values for controller
	private double density = .8;
	private int burningTrees = 1;
	private int burnTime = 3;
	private double spreadProb = .3;

	@Override
	protected HBox setupInputTextFields() {
		HBox input = new HBox();
		input.setAlignment(Pos.BASELINE_CENTER);
		input.setSpacing(SPACING);

		// Width label
		Text widthLabel = new Text("Width");
		input.getChildren().add(widthLabel);

		// Width text field for user input
		TextField gridWidthTextField = new TextField("" + gridWidth);
		gridWidthTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		input.getChildren().add(gridWidthTextField);

		// Height label
		Text heightLabel = new Text("Height");
		input.getChildren().add(heightLabel);

		// Height text field for user input
		TextField gridHeightTextField = new TextField("" + gridHeight);
		gridHeightTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		input.getChildren().add(gridHeightTextField);

		// Burn time label
		Text burnTimeLabel = new Text("Burn Time");
		input.getChildren().add(burnTimeLabel);

		// Burn time text field for user input
		TextField burnTimeTextField = new TextField("" + burnTime);
		burnTimeTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		input.getChildren().add(burnTimeTextField);

		// Spread probability label
		Text spreadProbLabel = new Text("Spread Prob");
		input.getChildren().add(spreadProbLabel);

		// Spread probability text field for user input
		TextField spreadProbTextField = new TextField("" + spreadProb);
		spreadProbTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		input.getChildren().add(spreadProbTextField);

		// Density label
		Text densityLabel = new Text("Density");
		input.getChildren().add(densityLabel);

		// Density text field for user input
		TextField densityTextField = new TextField("" + density);
		densityTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		input.getChildren().add(densityTextField);

		// Burning trees label
		Text burningTreesLabel = new Text("Burning Trees");
		input.getChildren().add(burningTreesLabel);

		// Burning trees text field for user input
		TextField burningTreesTextField = new TextField("" + burningTrees);
		burningTreesTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		input.getChildren().add(burningTreesTextField);
		
		// Sets values
		Button setValuesButton = new Button("Set Values");
		setValuesButton.setOnAction(value -> {
			// Sets grid width
			int newWidth = newIntValue(gridWidthTextField.getText(), MIN_GRID_SIZE,
					MAX_GRID_SIZE);
			if (newWidth != ERROR) {
				gridWidth = newWidth;
			}
			
			// Sets grid height
			int newHeight = newIntValue(gridHeightTextField.getText(), MIN_GRID_SIZE,
					MAX_GRID_SIZE);
			if (newHeight != ERROR) {
				gridHeight = newHeight;
			}
			
			// Sets density
			double newDensity = newDoubleValue(densityTextField.getText(), MIN_DENSITY, MAX_DENSITY);
			if (newDensity != ERROR) {
				density = newDensity;
			}
			
			// Sets spread probability
			double newSpreadProb = newDoubleValue(spreadProbTextField.getText(),
					MIN_SPREAD_PROB, MAX_SPREAD_PROB);
			if (newSpreadProb != ERROR) {
				spreadProb = newSpreadProb;
			}
			
			// Sets burn time
			int newBurnTime = newIntValue(burnTimeTextField.getText(), MIN_BURN_TIME,
					MAX_BURN_TIME);
			if (newBurnTime != ERROR) {
				burnTime = newBurnTime;
			}
			
			// Sets number of burning trees
			int newBurningTrees = newIntValue(burningTreesTextField.getText(), 0,
					(gridWidth - 2) * (gridHeight - 2));
			if (newBurningTrees != ERROR) {
				burningTrees = newBurningTrees;
			}
			
			// Updates simulation
			setModelParams();
			removeGrid();
			controller.resize(gridWidth, gridHeight);
			controller.makeModel();
			root.getChildren().add(GRID_INDEX, setupGrid());
			newSimulation();
		});
		input.getChildren().add(setValuesButton);

		return input;
	}
	
	@Override
	protected void setupController() {
		controller = new Controller(0);
		controller.setBorder(true);
	}

	@Override
	protected void setModelParams() {
		controller.setParameters(new Object[] { density, burningTrees, burnTime, spreadProb });
	}

}