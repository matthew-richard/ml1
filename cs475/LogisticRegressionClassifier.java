package cs475;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
        gradientDescentEta0 = 0.01;
        gradientDescentNumIterations = 20;

        w = new FeatureVector();
    }

    /*
     * Logistic function.
     */
    private double logistic(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }


    @Override
    public void train(List<Instance> instances) {

        int numInstances = instances.size();
        int numFeatures = instances.get(0).getFeatureVector().getSize();

        // Our first guess for w is a vector of 0's
        w = new FeatureVector(numFeatures);

        // For AdaGrad, remember (1-indexed) squared sums of partial gradients for each feature
        FeatureVector partialSquareSums = new FeatureVector();

        // Estimate w using stochastic gradient descent
        for (int t = 1; t <= getGradientDescentNumIterations() * numInstances; t++) {
            // Current point we're working with
            Instance instance = instances.get((t - 1) % instances.size());
            int label = ((ClassificationLabel) instance.getLabel()).getValue();
            FeatureVector features = instance.getFeatureVector();

            // Compute g(w*x_i) and g(-w*x_i)
            double dot_product = w.dot(instance.getFeatureVector());
            double logistic_positive = logistic(dot_product);
            double logistic_negative = logistic(-dot_product);

            // Update each element of w (elements that need updating are only
            // those at nonzero indices in 'features')
            Iterator<FeatureVector.Element> it = features.nonzeroElementIterator();
            while (it.hasNext()) {
                FeatureVector.Element element = it.next();
                int featureIndex = element.getIndex();
                double featureValue = element.getValue();

                double partialGradient = label * logistic_negative * featureValue
                        + (1 - label) * logistic_positive * -featureValue;

                // Update partialSquareSums
                partialSquareSums.add(featureIndex,
                        partialSquareSums.get(featureIndex) + Math.pow(partialGradient, 2));

                // Compute AdaGrad eta
                double denominator = Math.sqrt(1 + partialSquareSums.get(featureIndex));
                double step = getGradientDescentEta0() / denominator;

                // Update coefficient in w
                w.add(featureIndex, w.get(featureIndex) + step * partialGradient);
            }
        }

    }

    @Override
    public Label predict(Instance instance) {
        // Compute g(w*x_i)
        double logistic = logistic(w.dot(instance.getFeatureVector()));

        if (logistic >= 0.5)
            return new ClassificationLabel(1);
        else return new ClassificationLabel(0);
    }
}
