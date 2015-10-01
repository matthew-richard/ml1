package cs475;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 9/30/15.
 */
public class LogisticRegressionClassifier extends Predictor implements Serializable {

    private List<Double> w = null;

    private double gradientDescentEta0; //sgd_eta0
    private int gradientDescentNumIterations; //sgd_iterations

    public int getGradientDescentNumIterations() {
        return gradientDescentNumIterations;
    }

    public void setGradientDescentNumIterations(int gradientDescentNumIterations) {
        this.gradientDescentNumIterations = gradientDescentNumIterations;
    }

    public double getGradientDescentEta0() {
        return gradientDescentEta0;
    }

    public void setGradientDescentEta0(double gradientDescentEta0) {
        this.gradientDescentEta0 = gradientDescentEta0;
    }


    @Override
    public void train(List<Instance> instances) {
        // initialize sgd params to default values
        gradientDescentEta0 = 1.0;
        gradientDescentNumIterations = 20;

        w = new ArrayList<Double>(instances.get(0).getFeatureVector().getNumSupports());

        // initialize w to first guess

        // estimate w using stochastic gradient descent

    }

    @Override
    public Label predict(Instance instance) {
        return null;
    }
}
