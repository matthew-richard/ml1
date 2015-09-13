package cs475;

import java.io.Serializable;

public class ClassificationLabel extends Label implements Serializable {

	int label;

	public ClassificationLabel(int label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return ((Integer)label).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == ClassificationLabel.class)
			return label == ((ClassificationLabel)obj).label;
		else return false;
	}

}
