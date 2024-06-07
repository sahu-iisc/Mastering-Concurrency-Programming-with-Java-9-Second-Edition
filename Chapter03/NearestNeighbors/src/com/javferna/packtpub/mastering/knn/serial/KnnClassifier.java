package com.javferna.packtpub.mastering.knn.serial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.javferna.packtpub.mastering.knn.data.Distance;
import com.javferna.packtpub.mastering.knn.data.Sample;
import com.javferna.packtpub.mastering.knn.distances.EuclideanDistanceCalculator;


public class KnnClassifier {

	private final List<? extends Sample> dataSet;//Can store any kind of Sample sub-class
	private int k;

	public KnnClassifier(List<? extends Sample> dataSet, int k) {
		this.dataSet=dataSet;
		this.k=k;
	}
	
	public String classify (Sample example) {
		
		List<Distance> distances = new ArrayList<>();
		
		int index=0;
		

		//this can be run concurrently
		for (Sample localExample : dataSet) {
			Distance distance = new Distance();
			distance.setIndex(index);
			distance.setDistance(EuclideanDistanceCalculator.calculate(localExample, example));
			distances.add(distance);
		}

		//this can be run concurrently, fork & join
		Collections.sort(distances);
		Map<String, Long> results = distances.stream().limit(k).map(distance -> dataSet.get(distance.getIndex()).getTag()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		

		return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
	}
}
