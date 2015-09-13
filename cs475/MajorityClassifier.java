package cs475;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Implement MajorityClassifier

/**
 * Created by matt on 9/13/15.
 */
public class MajorityClassifier extends Predictor implements Serializable {

    ClassificationLabel majorityLabel = null;

    @Override
    public void train(List<Instance> instances) {
        Map<ClassificationLabel, Integer> labelCounts = new HashMap<>();

        for (Instance instance : instances) {
            if (instance.getLabel().equals(new ClassificationLabel(-1)))
                continue;

            int labelCount = 0;
            if (labelCounts.containsKey(instance.getLabel()))
                labelCount = labelCounts.get(instance.getLabel());

            labelCounts.put((ClassificationLabel)instance.getLabel(), ++labelCount);
        }

        int maxCount = 0;
        ClassificationLabel majority = null;
        for (Map.Entry<ClassificationLabel, Integer> count : labelCounts.entrySet()) {
            if (count.getValue() > maxCount) {
                maxCount = count.getValue();
                majority = count.getKey();
            }
        }

        majorityLabel = majority;
    }

    @Override
    public Label predict(Instance instance) {
        return majorityLabel;
    }
}
