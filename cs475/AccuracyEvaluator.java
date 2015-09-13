package cs475;

import java.util.List;

/**
 * Created by matt on 9/13/15.
 */
public class AccuracyEvaluator extends Evaluator {

    @Override
    /**
     * @return Correct classification rate, in [0,1].
     */
    public double evaluate(List<Instance> instances, Predictor predictor) {
        int numCorrect = 0;

        for (Instance instance : instances) {
            if (predictor.predict(instance).equals(instance.getLabel())) {
                numCorrect++;
            }
        }

        return (double)numCorrect / (double)instances.size();
    }
}
