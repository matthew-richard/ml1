package cs475;

import java.io.Serializable;

public class RegressionLabel extends Label implements Serializable {

	double label = -1;
	public double getValue() { return label; }

	public RegressionLabel(double label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return ((Double)label).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RegressionLabel))
			return false;
		return label == ((RegressionLabel)obj).label;
	}

	@Override
	public int hashCode() {
		return ((Integer) ((Double) label).hashCode()).hashCode();
	}

}
