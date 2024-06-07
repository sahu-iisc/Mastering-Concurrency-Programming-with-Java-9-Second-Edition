package com.javferna.packtpub.mastering.knn.parallel.individual;

import java.util.concurrent.CountDownLatch;

import com.javferna.packtpub.mastering.knn.data.Distance;
import com.javferna.packtpub.mastering.knn.data.Sample;
import com.javferna.packtpub.mastering.knn.distances.EuclideanDistanceCalculator;


public class IndividualDistanceTask implements Runnable {


	private final Distance[] distances;
	private final int index;
	private final Sample localExample;
	private final Sample example;
	private final CountDownLatch endControler;

	public IndividualDistanceTask(Distance[] distances, int index, Sample localExample, Sample example,
			CountDownLatch endControler) {
		this.distances = distances;
		this.index = index;
		this.localExample = localExample;
		this.example = example;
		this.endControler = endControler;
	}

	public void run() {
		distances[index] = new Distance();
		distances[index].setIndex(index);
		distances[index].setDistance(EuclideanDistanceCalculator.calculate(localExample, example));
		endControler.countDown();
	}

}
