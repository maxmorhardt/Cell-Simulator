package mvc;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Max Morhardt
 * 
 * Abstraction of a view for all simulations.
 * 
 */
public abstract class View {

	// Game scene attributes
	private static final int FRAMES_PER_SECOND = 5;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final int EXTRA_VERTICAL = 175;
	private static final int EXTRA_HORIZONTAL = 375;
	private static final int CELL_SIZE = 5;
	protected static final int MIN_GRID_SIZE = 3;
	protected static final int MAX_GRID_SIZE = 100;
	protected static final int TEXT_FIELD_WIDTH = 50;
	protected static final int SPACING = 10;
	protected static final int GRID_INDEX = 2;
	
	// Error value
	protected static final int ERROR = -1;

	// Pause button attributes
	private static final String START_TEXT = "Start";
	private static final String PAUSE_TEXT = "Pause";
	private static final String RESUME_TEXT = "Resume";

	// Scene variables
	private Scene myScene;
	protected VBox root;

	// Grid variables
	private Rectangle[][] grid;
	protected int gridWidth = 100;
	protected int gridHeight = 100;
	
	// Pause variables
	private Button pauseButton;
	private boolean paused = true;

	// Controller variable
	protected Controller controller;

	/**
	 * Starts the simulation.
	 * 
	 * @param stage
	 */
	public void startSimulation(Stage stage) {
		// Scene
		myScene = setupScene();
		
		// Picks correct model and sets those parameters
		setupController();
		setModelParams();

		// Shows scene
		stage.setScene(myScene);
		stage.show();

		// Game loop
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(MILLISECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	/**
	 * Puts together all elements of the scene.
	 * 
	 * @return
	 */
	protected Scene setupScene() {
		// Grid
		Group gridDrawing = setupGrid();
		// Control buttons
		HBox controls = setupControlButtons();
		// TextFields and Labels
		HBox input = setupInputTextFields();

		// Sets up root to align all elements
		root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(SPACING);
		root.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
		root.getChildren().addAll(input, gridDrawing, controls);

		// Creates scene
		Scene scene = new Scene(root, gridWidth * CELL_SIZE + EXTRA_HORIZONTAL, gridHeight * CELL_SIZE + EXTRA_VERTICAL,
				Color.ANTIQUEWHITE);
		return scene;
	}

	/**
	 * Creates a grid of rectangles.
	 * 
	 * @return
	 */
	protected Group setupGrid() {
		int sizeX = gridWidth;
		int sizeY = gridHeight;

		if (controller != null) {
			sizeX = controller.getDisplayColors().length;
			sizeY = controller.getDisplayColors()[0].length;
		}

		Group gridDrawing = new Group();

		grid = new Rectangle[sizeX][sizeY];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Rectangle currCell = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				grid[i][j] = currCell;
				gridDrawing.getChildren().add(currCell);
			}
		}
		return gridDrawing;
	}

	/**
	 * Sets up all buttons that run/alter the simulation.
	 * 
	 * @return
	 */
	protected HBox setupControlButtons() {
		HBox controls = new HBox();
		controls.setAlignment(Pos.BASELINE_CENTER);
		controls.setSpacing(SPACING);

		// Adds pause button
		pauseButton = new Button(START_TEXT);
		pauseButton.setOnAction(value -> {
			pressPause();
		});
		controls.getChildren().add(pauseButton);

		// Adds step button
		Button stepButton = new Button("Step");
		stepButton.setOnAction(value -> {
			doOneStep(MILLISECOND_DELAY);
		});
		controls.getChildren().add(stepButton);

		// Adds new simulation button
		Button newSimulationButton = new Button("New Simulation");
		newSimulationButton.setOnAction(value -> {
			removeGrid();
			controller.resize(gridWidth, gridHeight);
			setModelParams();
			controller.makeModel();
			root.getChildren().add(GRID_INDEX, setupGrid());
			newSimulation();
		});
		controls.getChildren().add(newSimulationButton);

		return controls;
	}

	/**
	 * Toggles the pause button.
	 */
	protected void pressPause() {
		paused = !paused;
		if (paused) {
			pauseButton.setText(RESUME_TEXT);
		} else {
			pauseButton.setText(PAUSE_TEXT);
		}
	}

	/**
	 * Based on the model, changes the cells on the view.
	 */
	protected void addColorsToScreen() {
		Color[][] cellColors = controller.getDisplayColors();
		for (int i = 0; i < cellColors.length; i++) {
			for (int j = 0; j < cellColors[i].length; j++) {
				grid[i][j].setFill(cellColors[i][j]);
				grid[i][j].setStroke(Color.BLACK);
			}
		}
	}

	/**
	 * Does one step in the search regardless of pause status.
	 * 
	 * @param elapsedTime
	 */
	protected void doOneStep(double elapsedTime) {
		controller.updateModelCells();
		addColorsToScreen();
	}

	/**
	 * Simulation loop called every frame
	 * 
	 * @param elapsedTime
	 */
	protected void step(double elapsedTime) {
		if (!paused) {
			doOneStep(elapsedTime);
		}
	}

	/**
	 * Creates new simulation.
	 */
	protected void newSimulation() {
		addColorsToScreen();
		paused = true;
		pauseButton.setText(START_TEXT);
	}

	/**
	 * Removes the grid from the scene.
	 */
	protected void removeGrid() {
		root.getChildren().remove(GRID_INDEX);
	}
	
	/**
	 * Returns the new value if it is within a given range and parseable.
	 * 
	 * @param textValue
	 * @param min
	 * @param max
	 * @return
	 */
	protected double newDoubleValue(String textValue, double min, double max) {
		double value = checkDoubleIsGood(textValue);
		if (value != ERROR) {
			if (value >= min && value <= max) {
				return value;
			}
		}
		return ERROR;
	}

	/**
	 * Returns the new value if it is within a given range and parseable.
	 * 
	 * @param textValue
	 * @param min
	 * @param max
	 * @return
	 */
	protected int newIntValue(String textValue, double min, double max) {
		int value = checkIntIsGood(textValue);
		if (value != ERROR) {
			if (value >= min && value <= max) {
				return value;
			}
		}
		return ERROR;
	}

	/**
	 * Checks for correct int input.
	 * 
	 * @param text
	 * @return
	 */
	protected int checkIntIsGood(String text) {
		try {
			int valueToCheck = Integer.parseInt(text);
			return valueToCheck;
		} catch (NumberFormatException e) {
			System.err.print("Non-number/Non-Integer input error.");
			e.printStackTrace();
		} catch (NegativeArraySizeException e) {
			System.err.print("Negative number input error.");
			e.printStackTrace();
		}
		return ERROR;
	}

	/**
	 * Checks for correct double input.
	 * 
	 * @param text
	 * @return
	 */
	protected double checkDoubleIsGood(String text) {
		try {
			double valueToCheck = Double.parseDouble(text);
			return valueToCheck;
		} catch (NumberFormatException e) {
			System.err.print("Non-number input error.");
			e.printStackTrace();
		} catch (NegativeArraySizeException e) {
			System.err.print("Negative number input error.");
			e.printStackTrace();
		}
		return ERROR;
	}
	
	/**
	 * Adds the buttons to allow for simulation changes.
	 * 
	 * @param simTypes
	 */
	public void addSimButtons(HBox simTypes) {
		root.getChildren().add(0, simTypes);
	}

	/**
	 * Sets up text fields for users to change simulation data.
	 * 
	 * @return
	 */
	protected abstract HBox setupInputTextFields();

	/**
	 * Sets parameters depending on each model and their required inputs.
	 */
	protected abstract void setModelParams();

	/**
	 * Sets up the controller for each different simulation.
	 */
	protected abstract void setupController();

}