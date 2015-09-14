package cs475;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by matt on 9/13/15.
 */
public class EvenOddClassifier extends Predictor implements Serializable {
    @Override
    public void train(List<Instance> instances) {
        // Do nothing
    }

    @Override
    public Label predict(Instance instance) {
        double even_sum = 0;
        double odd_sum = 0;

        Iterator<FeatureVector.Element> it = instance.getFeatureVector().nonzeroElementIterator();
        while (it.hasNext()) {
            FeatureVector.Element element = it.next();
            if (element.getIndex() % 2 == 0)
                even_sum += element.getValue();
            else
                odd_sum += element.getValue();
        }

        if (even_sum >= odd_sum)
            return new ClassificationLabel(1);
        else return new ClassificationLabel(0);

    }
}
