package cs475;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 9/30/15.
 */
public class LogisticRegressionClassifier extends Predictor implements Serializable {

    private FeatureVector w = null;

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

    public LogisticRegressionClassifier () {
        // initialize sgd params to default values
        gradientDescentEta0 = 1.0;
        gradientDescentNumIterations = 20;

        w = new FeatureVector();
    }


    @Override
    public void train(List<Instance> instances) {

        // our first guess is a vector of 0's
        w = new FeatureVector();

        // estimate w using stochastic gradient descent

        for (int t = 1; t <= getGradientDescentNumIterations(); t++) {
            // current point we're working with
            Instance instance = instances.get((t - 1) % instances.size());


            // update every element of w
            for ()
        }

    }

    @Override
    public Label predict(Instance instance) {
        return null;
    }
}
