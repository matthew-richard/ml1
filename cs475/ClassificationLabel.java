package cs475;

import java.io.Serializable;

public class ClassificationLabel extends Label implements Serializable {

	int label = -1;
	public int getValue() { return label; }


	public ClassificationLabel(int label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return ((Integer)label).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassificationLabel))
			return false;
		return label == ((ClassificationLabel)obj).label;
	}

	@Override
	public int hashCode() {
		return ((Integer) ((Integer) label).hashCode()).hashCode();
	}
}
