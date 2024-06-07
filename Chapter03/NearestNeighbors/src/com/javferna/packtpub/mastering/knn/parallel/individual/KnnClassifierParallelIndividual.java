package com.javferna.packtpub.mastering.knn.parallel.individual;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.javferna.packtpub.mastering.knn.data.Distance;
import com.javferna.packtpub.mastering.knn.data.Sample;

public class KnnClassifierParallelIndividual {


	private final List<? extends Sample> dataSet;
	private final int k;
	private final ThreadPoolExecutor executor;
	private final int numThreads;
	private boolean parallelSort;
	public KnnClassifierParallelIndividual(List<? extends Sample> dataSet, int k, int factor, boolean parallelSort) {
		this.dataSet=dataSet;
		this.k=k;
		numThreads=factor*(Runtime.getRuntime().availableProcessors());
		executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
		this.parallelSort=parallelSort;
	}
	
	public String classify (Sample example) throws Exception {
		
		Distance[] distances=new Distance[dataSet.size()];
		CountDownLatch endController=new CountDownLatch(dataSet.size());
		
		int index=0;
		for (Sample localExample : dataSet) {
			//since we are assigning unique index to each thread we don't need synchronization over distances array, gooooood
			IndividualDistanceTask task=new IndividualDistanceTask(distances, index, localExample, example, endController);
			executor.execute(task);
			index++;
		}
		endController.await();
		
		if (parallelSort) {
			Arrays.parallelSort(distances);
		} else {
			Arrays.sort(distances);
		}
		
		Map<String, Integer> results = new HashMap<>();
		for (int i = 0; i < k; i++) {
		  Sample localExample = dataSet.get(distances[i].getIndex());
		  String tag = localExample.getTag();
		  results.merge(tag, 1, (a, b) -> a+b);
		}
		
		return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();

	}
	
	public void destroy() {
		executor.shutdown();
	}
	
}
