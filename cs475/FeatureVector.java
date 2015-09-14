package cs475;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class FeatureVector implements Serializable {

    // Nonzero vector elements
	private Map<Integer, Double> supports = new HashMap<>();

	public void add(int index, double value) {
        supports.put(index, value);
	}
	
	public double get(int index) {
        Double value = supports.get(index);
        if (value == null)
            value = 0.0;
		return value;
	}

	public Iterator<Pair<Integer, Double>> nonzeroIterator() {
		return new FeatureVectorIterator(this);
	}

	private static class FeatureVectorIterator implements Iterator<Pair<Integer, Double>> {

		protected Iterator<Map.Entry<Integer, Double>> setIterator = null;

		public FeatureVectorIterator (FeatureVector vector) {
			setIterator = vector.supports.entrySet().iterator();
		}

		@Override
		public boolean hasNext() {
			return setIterator.hasNext();
		}

		@Override
		public Pair<Integer, Double> next() {
			Map.Entry<Integer, Double> entry = setIterator.next();
			return new Pair<Integer,Double> (entry.getKey(), entry.getValue());
		}
	}

}
