package com.javferna.packtpub.mastering.knn.parallel.group;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.javferna.packtpub.mastering.knn.data.Distance;
import com.javferna.packtpub.mastering.knn.data.Sample;
import com.javferna.packtpub.mastering.knn.distances.EuclideanDistanceCalculator;

/**
 * Task that of the coarse-grained concurrent version 
 * @author author
 *
 */
public class GroupDistanceTask implements Runnable {

	private final Distance[] distances;
	private final int startIndex, endIndex;
	private final Sample example;
	private final List<? extends Sample> dataSet;

	private final CountDownLatch endControler;
	public GroupDistanceTask(Distance[] distances, int startIndex,
			int endIndex, List<? extends Sample> dataSet, Sample example,
			CountDownLatch endControler) {
		this.distances = distances;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.example = example;
		this.dataSet = dataSet;
		this.endControler = endControler;
	}


	public void run() {
		for (int index = startIndex; index < endIndex; index++) {
			Sample localExample=dataSet.get(index);
			distances[index] = new Distance();
			distances[index].setIndex(index);
				distances[index].setDistance(EuclideanDistanceCalculator
						.calculate(localExample, example));
		}
		endControler.countDown();
	}

}
