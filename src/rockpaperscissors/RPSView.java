package rockpaperscissors;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import mvc.Controller;
import mvc.View;

/**
 * 
 * @author Kara Palumbo and Max Morhardt
 * 
 * Class for the Rock Paper Scissors Cell Simulation GUI
 *
 */
public class RPSView extends View {

	// Simulation attributes
	private static final int MIN_THRESHOLD = 3;
	private static final int MAX_THRESHOLD = 7;

	// Initial value for controller
	private int threshold = 3;

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
		input.getChildren().add(gridWidthTextField);

		// Height label
		Text heightLabel = new Text("Height");
		input.getChildren().add(heightLabel);

		// Height text field for user input
		TextField gridHeightTextField = new TextField("" + gridHeight);
		input.getChildren().add(gridHeightTextField);
		
		// Threshold label
		Text thresholdLabel = new Text("Threshold");
		input.getChildren().add(thresholdLabel);
		
		// Threshold text field for user input
		TextField thresholdTextField = new TextField("" + threshold);
		input.getChildren().add(thresholdTextField);;

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
			
			// Sets threshold
			int newThreshold = newIntValue(thresholdTextField.getText(), MIN_THRESHOLD,
					MAX_THRESHOLD);
			if (newHeight != ERROR) {
				threshold = newThreshold;
			}
			
			// Updates simulation
			setModelParams();
			removeGrid();
			controller.resize(gridWidth, gridHeight);
			controller.makeModel();
			root.getChildren().add(GRID_INDEX, setupGrid());
			newSimulation();
		});
		input.getChildren().add(setValuesButton);;

		return input;
	}

	@Override
	protected void setModelParams() {
		controller.setParameters(new Object[] { threshold });
	}

	@Override
	protected void setupController() {
		controller = new Controller(1);
		controller.setBorder(true);
		setModelParams();
	}

}