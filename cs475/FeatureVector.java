package cs475;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FeatureVector implements Serializable {

    // Nonzero vector elements
	private Map<Integer, Double> supports = new HashMap<>();

	private int size = 0;
	public int getSize() {
		return size;
	}

	public FeatureVector() {};

	public FeatureVector(int size) { this.size = size; }


	public void add(int index, double value) {
        supports.put(index, value);

		if (index > size)
			size = index;
	}

	/*public void set (int index, double value) {
		if (index > size)

		add(index, value);
	}*/

	public int getNumSupports() { return supports.size(); }
	
	public double get(int index) {
        Double value = supports.get(index);
        if (value == null)
            value = 0.0;
		return value;
	}

	public Iterator<Element> nonzeroElementIterator() {
		return new FeatureVectorIterator(this);
	}

	public double dot (FeatureVector other) {
		double result = 0.0;

		Iterator<Element> it = this.nonzeroElementIterator();
		while (it.hasNext()) {
			Element our_element = it.next();
			double our_value = our_element.getValue();
			double their_value = other.get(our_element.getIndex());

			result += our_value * their_value;
		}

		return result;
	}

	public static class Element {
		Map.Entry<Integer, Double> entry;

		public Element (Map.Entry<Integer, Double> entry) {
			this.entry = entry;
		}

		public int getIndex() {
			return entry.getKey();
		}

		public double getValue() {
			return entry.getValue();
		}

		public void setValue(Double value) {
			entry.setValue(value);
		}

		@Override
		public int hashCode() {
			return ((Integer)entry.hashCode()).hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Element))
				return false;

			return entry.equals(((Element) obj).entry);
		}
	}

	private static class FeatureVectorIterator implements Iterator<Element> {

		protected Iterator<Map.Entry<Integer, Double>> setIterator = null;

		public FeatureVectorIterator (FeatureVector vector) {
			setIterator = vector.supports.entrySet().iterator();
		}

		@Override
		public boolean hasNext() {
			return setIterator.hasNext();
		}

		@Override
		public Element next() {
			return new Element (setIterator.next());
		}

		@Override
		public void remove() {
			setIterator.remove();
		}
	}

}
