package gameoflife;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import mvc.Controller;
import mvc.View;

/**
 * 
 * @author Max Morhardt
 * 
 * Game of Life simulation view.
 *
 */
public class GOLView extends View {

	// Simulation attributes
	private static final int MIN_BACTERIA_PROB = 0;
	private static final int MAX_BACTERIA_PROB = 1;
	
	// Initial value in simulation
	private double bacteriaProb = .4;

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

		// Height label
		Text bacteriaProbLabel = new Text("Bacteria Probability");
		input.getChildren().add(bacteriaProbLabel);

		// Height text field for user input
		TextField bacteriaProbTextField = new TextField("" + bacteriaProb);
		bacteriaProbTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		input.getChildren().add(bacteriaProbTextField);
		
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
			
			// Sets bacteria probability
			double newBacteriaProb = newDoubleValue(bacteriaProbTextField.getText(), MIN_BACTERIA_PROB, MAX_BACTERIA_PROB);
			if (newBacteriaProb != ERROR) {
				bacteriaProb = newBacteriaProb;
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
		controller = new Controller(2);
		controller.setBorder(true);
		setModelParams();
	}

	@Override
	protected void setModelParams() {
		controller.setParameters(new Object[] { bacteriaProb });
	}
	
}