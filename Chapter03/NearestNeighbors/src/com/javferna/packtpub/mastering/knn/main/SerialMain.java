package com.javferna.packtpub.mastering.knn.main;
import java.util.List;

import com.javferna.packtpub.mastering.knn.data.BankMarketing;
import com.javferna.packtpub.mastering.knn.loader.BankMarketingLoader;
import com.javferna.packtpub.mastering.knn.serial.KnnClassifier;

import TimerUtil.Timer;

/**
 * Main class that launches the tests using the serial knn with serial sorting
 * @author Usuario
 *
 */
public class SerialMain {

	public static void main(String[] args) {

		BankMarketingLoader loader = new BankMarketingLoader();
		List<BankMarketing> train = loader.load("data\\bank.data");
		System.out.println("Train: " + train.size());
		List<BankMarketing> test = loader.load("data\\bank.test");
		System.out.println("Test: " + test.size());
		int success = 0, mistakes = 0;
		
		int k = 100;
		if (args.length==1) {
			k = Integer.parseInt(args[0]);
		}

		success = 0;
		mistakes = 0;
		KnnClassifier classifier = new KnnClassifier(train, k);
		Timer timer = new Timer();

		try {
			timer.start();
			for (BankMarketing example : test) {
				String tag = classifier.classify(example);
				if (tag.equals(example.getTag())) {
					success++;
				} else {
					mistakes++;
				}
			}
			timer.end();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("******************************************");
		System.out.println("Serial Classifier - K: " + k);
		System.out.println("Success: " + success);
		System.out.println("Mistakes: " + mistakes);
		System.out.println("Execution Time: " + timer.getTotalTime() + " seconds.");
		System.out.println("******************************************");

	}

}
