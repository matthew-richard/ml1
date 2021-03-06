package cs475;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Classify {
	static public LinkedList<Option> options = new LinkedList<Option>();
	
	public static void main(String[] args) throws IOException {
		// Parse the command line.
		String[] manditory_args = { "mode"};
		createCommandLineOptions();
		CommandLineUtilities.initCommandLineParameters(args, Classify.options, manditory_args);
	
		String mode = CommandLineUtilities.getOptionValue("mode");
		String data = CommandLineUtilities.getOptionValue("data");
		String predictions_file = CommandLineUtilities.getOptionValue("predictions_file");
		String algorithm = CommandLineUtilities.getOptionValue("algorithm");
		String model_file = CommandLineUtilities.getOptionValue("model_file");
		
		if (mode.equalsIgnoreCase("train")) {
			if (data == null || algorithm == null || model_file == null) {
				System.out.println("Train requires the following arguments: data, algorithm, model_file");
				System.exit(0);
			}
			// Load the training data.
			DataReader data_reader = new DataReader(data, true);
			List<Instance> instances = data_reader.readData();
			data_reader.close();
			
			// Train the model.
			Predictor predictor = train(instances, algorithm);
			saveObject(predictor, model_file);		
			
		} else if (mode.equalsIgnoreCase("test")) {
			if (data == null || predictions_file == null || model_file == null) {
				System.out.println("Train requires the following arguments: data, predictions_file, model_file");
				System.exit(0);
			}
			
			// Load the test data.
			DataReader data_reader = new DataReader(data, true);
			List<Instance> instances = data_reader.readData();
			data_reader.close();
			
			// Load the model.
			Predictor predictor = (Predictor)loadObject(model_file);
			evaluateAndSavePredictions(predictor, instances, predictions_file);
		} else {
			System.out.println("Requires mode argument.");
		}
	}
	

	private static Predictor train(List<Instance> instances, String algorithm) {
		// Train the model using "algorithm" on "data"
		Predictor predictor = null;
		if (algorithm.equals("majority"))
			predictor = new MajorityClassifier();
		else if (algorithm.equals("even_odd"))
			predictor = new EvenOddClassifier();
		else if (algorithm.equals("logistic_regression")) {
			predictor = new LogisticRegressionClassifier();

			// Apply optional algorithm-specific options
			if (CommandLineUtilities.hasArg("sgd_eta0"))
				((LogisticRegressionClassifier) predictor)
						.setGradientDescentEta0(
								CommandLineUtilities.getOptionValueAsFloat("sgd_eta0"));

			if (CommandLineUtilities.hasArg("sgd_iterations"))
				((LogisticRegressionClassifier) predictor)
						.setGradientDescentNumIterations(
								CommandLineUtilities.getOptionValueAsInt("sgd_iterations"));
		}

		else {
			System.out.println("Invalid training algorithm.");
			return null;
		}

		// Learn the model
		predictor.train(instances);

		// Evaluate the model
		double accuracy = new AccuracyEvaluator().evaluate(instances, predictor);
		System.out.println("Accuracy of " + algorithm + " algorithm on training data: " + accuracy);
		return predictor;
	}

	private static void evaluateAndSavePredictions(Predictor predictor,
			List<Instance> instances, String predictions_file) throws IOException {
		PredictionsWriter writer = new PredictionsWriter(predictions_file);

		// Evaluate the model if labels are available.
		if (instances.get(0).getLabel() != null){
			double accuracy = new AccuracyEvaluator().evaluate(instances, predictor);
			System.out.println("Accuracy of algorithm: " + accuracy);
		}
		
		for (Instance instance : instances) {
			Label label = predictor.predict(instance);
			writer.writePrediction(label);
		}
		
		writer.close();
		
	}

	public static void saveObject(Object object, String file_name) {
		try {
			ObjectOutputStream oos =
				new ObjectOutputStream(new BufferedOutputStream(
						new FileOutputStream(new File(file_name))));
			oos.writeObject(object);
			oos.close();
		}
		catch (IOException e) {
			System.err.println("Exception writing file " + file_name + ": " + e);
		}
	}

	/**
	 * Load a single object from a filename. 
	 * @param file_name
	 * @return
	 */
	public static Object loadObject(String file_name) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(file_name))));
			Object object = ois.readObject();
			ois.close();
			return object;
		} catch (IOException e) {
			System.err.println("Error loading: " + file_name);
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading: " + file_name);
		}
		return null;
	}
	
	public static void registerOption(String option_name, String arg_name, boolean has_arg, String description) {
		OptionBuilder.withArgName(arg_name);
		OptionBuilder.hasArg(has_arg);
		OptionBuilder.withDescription(description);
		Option option = OptionBuilder.create(option_name);
		
		Classify.options.add(option);		
	}
	
	private static void createCommandLineOptions() {
		registerOption("data", "String", true, "The data to use.");
		registerOption("mode", "String", true, "Operating mode: train or test.");
		registerOption("predictions_file", "String", true, "The predictions file to create.");
		registerOption("algorithm", "String", true, "The name of the algorithm for training.");
		registerOption("model_file", "String", true, "The name of the model file to create/load.");
		registerOption("sgd_eta0", "double", true, "The constant scalar for learning rate in AdaGrad.");
		registerOption("sgd_iterations", "int", true, "The number of SGD iterations.");
		
		// Other options will be added here.
	}
}
