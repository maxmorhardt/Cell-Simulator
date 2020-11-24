package modelextras;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Adam Behrman
 * 
 * Handles the different parameters for each simulation.
 *
 */
public class ParameterList {

	List<Object> params;
	
	public ParameterList() { }

	public ParameterList(int numberOfParams) {
		params = new ArrayList<Object>(numberOfParams);
	}

	public void addParam(Object toBeAdded) {
		params.add(toBeAdded);
	}

	public Object getParamAt(int index) {
		return params.get(index);
	}

	public void updateParamAt(Object element, int index) {
		params.set(index, element);
	}

	public int getParamterListLength() {
		return params.size();
	}
	
}