package mvc;

import gameoflife.GOLModel;
import javafx.scene.paint.Color;
import rockpaperscissors.RPSModel;
import wildfire.WildFireModel;

/**
 * 
 * @author Adam Behrman
 * 
 * Connection between model and view and handles all simulations.
 *
 */
public class Controller {

	private Model cm;
	private boolean hasBorder;
	private final Color EDGE_COLOR = Color.BLACK;

	private Model[] allModels = new Model[] { new WildFireModel(), new RPSModel(), new GOLModel()};

	public Controller(int modelIndex) {
		cm = allModels[modelIndex];
	}

	public void setBorder(boolean hasBorder) {
		this.hasBorder = hasBorder;
	}

	public void resize(int length, int height) {
		cm.resize(length, height);
	}

	public void makeModel() {
		cm.makeModel();
	}

	public void updateModelCells() {
		cm.updateModel();
	}

	public void setParameters(Object[] paramsToSet) {
		cm.setParameters(paramsToSet);
	}

	public Color[][] getDisplayColors() {
		Color[][] tempColors = cm.getColors();

		if (!hasBorder)
			return tempColors;

		Color[][] fixedColors = new Color[tempColors.length + 2][tempColors[0].length + 2];
		for (int i = 0; i < fixedColors.length; i++) {
			for (int j = 0; j < fixedColors[i].length; j++) {
				if (i == 0 || i == fixedColors.length - 1 || j == 0 || j == fixedColors[i].length - 1) {
					fixedColors[i][j] = EDGE_COLOR;
				} else {
					fixedColors[i][j] = tempColors[i - 1][j - 1];
				}
			}
		}
		return fixedColors;
	}
	
}