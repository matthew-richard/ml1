package cs475;

import java.io.Serializable;
import java.util.Map;

public class FeatureVector implements Serializable {

    // Nonzero vector elements
	private Map<Integer, Double> supports;

	public void add(int index, double value) {
        supports.put(index, value);
	}
	
	public double get(int index) {
        Double value = supports.get(index);
        if (value == null)
            value = 0.0;
		return value;
	}

}
